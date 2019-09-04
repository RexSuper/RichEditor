package com.rex.richeditor.tools;

import android.webkit.MimeTypeMap;

/**
 * @author Rex on 2019/9/3.
 * 模拟假的 上传本地文件到服务器生成在线url，这部分 还是得替换成你自己服务器的
 */
public class HttpFakeUtils {

    public final static String TEST_VIDEO_URL = "https://www.w3school.com.cn/example/html5/mov_bbb.mp4";
    public final static String TEST_IMAGE_URL = "http://pic44.nipic.com/20140716/8716187_010828140000_2.jpg";
    public final static String TEST_WEB_URL = "https://github.com/RexSuper/RichHtmlEditorForAndroid";

    public interface HttpResult{
      void  onSuccess(String url);
      void  onError(String error);
    }
    public static void  postFile(String localUrl,HttpResult httpResult){
        if (httpResult != null) {
            if (isGif(localUrl)||isImage(localUrl)){
                httpResult.onSuccess(TEST_IMAGE_URL);
            }
            else if (isVideo(localUrl)){
                httpResult.onSuccess(TEST_VIDEO_URL);
            }else {
                httpResult.onSuccess(TEST_VIDEO_URL);
            }
        }
    }


    public static boolean isImage(String localUrl) {
        String mimeType =  getMimeType(localUrl);
        if (mimeType == null) return false;
        return mimeType.equals(MimeType.JPEG.toString())
                || mimeType.equals(MimeType.PNG.toString())
                || mimeType.equals(MimeType.GIF.toString())
                || mimeType.equals(MimeType.BMP.toString())
                || mimeType.equals(MimeType.WEBP.toString());
    }

    public static  boolean isGif(String localUrl) {
        String mimeType =  getMimeType(localUrl);
        if (mimeType == null) return false;
        return mimeType.equals(MimeType.GIF.toString());
    }

    /**
     * 获取文件的MIME类型
     */
    public static String getMimeType(String pathOrUrl) {
        String ext = getExtension(pathOrUrl);
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String mimeType;
        if (map.hasExtension(ext)) {
            mimeType = map.getMimeTypeFromExtension(ext);
        } else {
            mimeType = "*/*";
        }
        return mimeType;
    }

    /**
     * 获取文件后缀,不包括“.”
     */
    public static String getExtension(String pathOrUrl) {
        int dotPos = pathOrUrl.lastIndexOf('.');
        if (0 <= dotPos) {
            return pathOrUrl.substring(dotPos + 1);
        } else {
            return "ext";
        }
    }


    public static boolean isVideo(String localUrl) {

        String mimeType =  getMimeType(localUrl);
        if (mimeType == null) return false;
        return mimeType.equals(MimeType.MPEG.toString())
                || mimeType.equals(MimeType.MP4.toString())
                || mimeType.equals(MimeType.QUICKTIME.toString())
                || mimeType.equals(MimeType.THREEGPP.toString())
                || mimeType.equals(MimeType.THREEGPP2.toString())
                || mimeType.equals(MimeType.MKV.toString())
                || mimeType.equals(MimeType.WEBM.toString())
                || mimeType.equals(MimeType.TS.toString())
                || mimeType.equals(MimeType.AVI.toString());

    }
}
