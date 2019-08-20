package com.rex.editor.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;

/**
 * @author Rex on 2019/6/20.
 */
public class RichEditorNew extends RichEditor {
    private OnTextChangeNewListener mTextChangeListener;
    private OnConsoleMessageListener mOnConsoleMessageListener;
    /**
     * 用于在ontextchange中执行操作标识避免循环
     */
    public boolean isUnableTextChange = false;

    private boolean isNeedSetNewLineAfter = false;

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


    public void getCurrChooseParams() {
        exec("javascript:RE.getSelectedNode();");
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


    @Override
    public void insertImage(String url, String alt, String style) {
        focusEditor();
        setNeedSetNewLineAfter(true);
        super.insertImage(url, alt, style);
    }

    public void insertHtml(String html) {
        focusEditor();
        setNeedSetNewLineAfter(true);
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
                "</a>");
    }


    public void insertAudio(String audioUrl, String custom) {
        focusEditor();
        setNeedSetNewLineAfter(true);
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

    public void insertVideo(String videoUrl, String custom) {
        focusEditor();
        setNeedSetNewLineAfter(true);
        if (TextUtils.isEmpty(custom)) {
            custom =             //增加进度控制
                    "controls=\"controls\"" +
                            //视频显示第一帧
                            " initial-time=\"0.01\" " +
                            //宽高
                            "height=\"300\" " +
                            //样式
                            " style=\"margin-top:10px;max-width:100%;\"";
        }
        System.out.println("videoUrl = [" + videoUrl + "], custom = [" + custom + "]");
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertVideo('" + videoUrl + "', '" + custom + "');");
    }

}
