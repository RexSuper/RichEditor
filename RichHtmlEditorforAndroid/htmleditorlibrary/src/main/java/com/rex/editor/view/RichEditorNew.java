package com.rex.editor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;

import com.rex.editor.common.CommonJs;
import com.rex.editor.common.FilesUtils;
import com.rex.editor.common.Utils;

import java.io.File;
import java.util.List;

import static com.rex.editor.common.FilesUtils.getVideoThumbnail;

/**
 * @author Rex on 2019/6/20.
 */
public class RichEditorNew extends RichEditor {
    private OnTextChangeNewListener mTextChangeListener;
    private OnConsoleMessageListener mOnConsoleMessageListener;
    /**
     * 用于在ontextchange中执行操作标识避免循环
     */
    private boolean isUnableTextChange = false;
    private boolean isNeedSetNewLineAfter = false;
    //视频缩略图
    private boolean isNeedAutoPosterUrl = false;
    public final static String FILE_TAG = "/rich_editor";

    public RichEditorNew(Context context) {
        super(context);
        initConfig();
    }

    public RichEditorNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig();
    }

    public RichEditorNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    public boolean isNeedAutoPosterUrl() {
        return isNeedAutoPosterUrl;
    }

    public void setNeedAutoPosterUrl(boolean needAutoPosterUrl) {
        isNeedAutoPosterUrl = needAutoPosterUrl;
    }

    public void getCurrChooseParams() {
        exec("javascript:RE.getSelectedNode();");
    }

    public void loadRichEditorCode(String html) {
        loadDataWithBaseURL("file:///android_asset/",
                html + CommonJs.IMG_CLICK_JS, "text/html", "utf-8", null);
    }


    public interface OnTextChangeNewListener {
        void onTextChange(String text);
    }

    public interface OnConsoleMessageListener {
        void onTextChange(String message, int lineNumber, String sourceID);
    }


    public void setOnTextChangeListener(OnTextChangeNewListener listener) {
        this.mTextChangeListener = listener;
    }

    public void setOnConsoleMessageListener(OnConsoleMessageListener listener) {
        this.mOnConsoleMessageListener = listener;
    }

    private void initConfig() {
        setOnTextChangeListener(new OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {

                if (isNeedSetNewLineAfter()) {
                    setNewLine();
                }
                if (isUnableTextChange) {
                    isUnableTextChange = false;
                } else {
                    if (mTextChangeListener != null) {
                        mTextChangeListener.onTextChange(text);
                    }
                }

            }
        });

        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                super.onConsoleMessage(message, lineNumber, sourceID);
                if (mOnConsoleMessageListener != null) {
                    mOnConsoleMessageListener.onTextChange(message, lineNumber, sourceID);
                }

            }
        });
    }

    public void insertImage(String url) {
        insertImage(url, "", "");
    }

    @Override
    public void insertImage(String url, String alt, String style) {
        if (TextUtils.isEmpty(style)) {
            alt = "picvision";
            style = "margin-top:10px;max-width:100%;";
        }
        super.insertImage(url, alt, style);
    }

    public void insertHtml(String html) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertHTML('" + html + "');");
    }


    public void setNewLine() {
        isNeedSetNewLineAfter = false;
        isUnableTextChange = true;
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertHTML('<br></br>');");
    }

    public void setHint(String placeholder) {
        setPlaceholder(placeholder);
    }

    public void setHintColor(String placeholderColor) {
        exec("javascript:RE.setPlaceholderColor('" + placeholderColor + "');");
    }

    public void setNeedSetNewLineAfter(boolean needSetNewLineAfter) {
        isNeedSetNewLineAfter = needSetNewLineAfter;
    }

    public boolean isNeedSetNewLineAfter() {
        return isNeedSetNewLineAfter;
    }


    public void insertFileWithDown(String downloadUrl, String title) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return;
        }

        String fileName;
        try {
            String[] split = downloadUrl.split("/");
            fileName = split[split.length - 1];
        } catch (Exception e) {
            fileName = "rich" + System.currentTimeMillis();
        }

        title += fileName;
        insertHtml("<a href=\"" + downloadUrl + "\" download=\"" + fileName + "\">" +
                title +
                "</a><br></br>");
    }

    public void insertAudio(String audioUrl) {
        insertAudio(audioUrl, "");
    }

    public void insertAudio(String audioUrl, String custom) {
        if (TextUtils.isEmpty(custom)) {
            custom =             //增加进度控制
                    "controls=\"controls\"" +
                            //宽高
                            "height=\"300\" " +
                            //样式
                            " style=\"margin-top:10px;max-width:100%;\"";
        }
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertAudio('" + audioUrl + "', '" + custom + "');");
    }


    public void insertVideo(String videoUrl) {
        insertVideo(videoUrl, "", "");
    }

    /**
     * @param videoUrl
     * @param customStyle
     * @param posterUrl   视频默认缩略图
     */
    public void insertVideo(String videoUrl, String customStyle, String posterUrl) {
        if (TextUtils.isEmpty(customStyle)) {
            customStyle =             //增加进度控制
//                    "controls=\"controls\"" + //已修改到video标签里面
                    //视频显示第一帧
//                            " initial-time=\"0.01\" " +//客户端无效
                    //宽高
                    "height=\"300\" " +
                            //样式
                            " style=\"margin-top:10px;max-width:100%;\"";
        }

        if (TextUtils.isEmpty(posterUrl) && isNeedAutoPosterUrl) {
            Bitmap videoThumbnail = getVideoThumbnail(videoUrl);
            if (videoThumbnail != null) {
                String videoThumbnailUrl = FilesUtils.saveBitmap(videoThumbnail);
                if (!TextUtils.isEmpty(videoThumbnailUrl)) {
                    posterUrl = videoThumbnailUrl;
                }
            }

        }


        System.out.println("videoUrl = [" + videoUrl + "], custom = [" + customStyle + "]");
        System.out.println("videoUrl getAbsolutePath = [" + new File(videoUrl).getAbsolutePath() + "]");
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertVideo('" + videoUrl + "', '" + customStyle + "', '" + posterUrl + "');");
    }


    // 获取html本地的地址 方便上传的时候转为在线的地址
    public List<String> getAllSrcAndHref() {
        return Utils.getHtmlSrcOrHrefList(getHtml());
    }

    public void clearLocalRichEditorCache() {
        FilesUtils.clearLocalRichEditorCache();
    }

}
