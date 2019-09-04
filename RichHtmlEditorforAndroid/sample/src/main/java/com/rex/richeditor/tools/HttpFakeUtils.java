package com.rex.richeditor.tools;

import com.rex.editor.common.EssFile;

/**
 * @author Rex on 2019/9/3.
 * 模拟假的 上传本地文件到服务器生成在线url，这部分 还是得替换成你自己服务器的
 */
public class HttpFakeUtils {

    public final static String TEST_VIDEO_URL = "https://www.w3school.com.cn/example/html5/mov_bbb.mp4";
    public final static String TEST_IMAGE_URL = "http://pic44.nipic.com/20140716/8716187_010828140000_2.jpg";
    public final static String TEST_WEB_URL = "https://github.com/RexSuper/RichHtmlEditorForAndroid";
    public final static String TEST_VIDEOTHUMBNAIL_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568188445&di=7adf93be0afc08663f4950934f6976ec&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F14%2F20170714172302_MTvSB.png";

    public interface HttpResult {
        void onSuccess(String url);

        void onError(String error);
    }

    public static void postFile(EssFile essFile, HttpResult httpResult) {
        if (httpResult != null) {
            if (essFile.isGif() || essFile.isImage()) {
                if (essFile.getAbsolutePath().contains("poster")) {
                    httpResult.onSuccess(TEST_VIDEOTHUMBNAIL_URL);
                } else {
                    httpResult.onSuccess(TEST_IMAGE_URL);
                }

            } else if (essFile.isVideo()) {
                httpResult.onSuccess(TEST_VIDEO_URL);
            } else {
                httpResult.onSuccess(TEST_VIDEO_URL);
            }
        }
    }

    public static void publishArticle(String html, HttpResult httpResult) {
        if (httpResult != null) {
            httpResult.onSuccess("发布成功");
        }

    }


}
