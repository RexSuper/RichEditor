package com.rex.editor.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private Utils() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }


    /**
     * 获取html本地的地址 方便上传的时候转为在线的地址
     * @param html
     * @return
     */
    public static List<String> getHtmlSrcOrHrefList(String html) {

        if (TextUtils.isEmpty(html)) {
            return null;
        }
        Document doc = Jsoup.parse(html);
        List<String> listData = new ArrayList();

        Elements elementsSrc = new Elements();
        Elements elementsImg = doc.select("img[src]");
        Elements elementsAudio = doc.select("audio[src]");
        Elements elementsVideo = doc.select("video[src]");
        Elements elementsFiles = doc.select("a[href]");

        elementsSrc.addAll(elementsImg);
        elementsSrc.addAll(elementsAudio);
        elementsSrc.addAll(elementsVideo);
        for (Element element : elementsSrc) {
            String src = element.attr("src");
            if (!TextUtils.isEmpty(src)&&!src.contains("http")){
                listData.add(src);
            }
        }

        for (Element element : elementsFiles) {
            String src = element.attr("href");
            if (!TextUtils.isEmpty(src)&&!src.contains("http")){
                listData.add(src);
            }
        }

        return listData;
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap decodeResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取视频缩略图
     *
     * @param filePath
     * @return
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap b = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            b = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    public static byte[] toBase64byte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

}
