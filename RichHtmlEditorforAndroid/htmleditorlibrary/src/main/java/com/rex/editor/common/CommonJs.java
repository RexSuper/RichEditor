package com.rex.editor.common;

/**
 * @author Rex on 2019/6/20.
 */
public class CommonJs {

    /**
     * 为全局图片加上点击事件 回调 imageOnclick.openImage imageOnclick.openImage需要客户端对应实现
     * 时机为window.onload
     */
    public final static String IMG_CLICK_JS = "<script type='text/javascript'>window.onload = function(){" +
            "var $img = document.getElementsByTagName('img');" +
            "for(var p in  $img){" +
            "    if (typeof $img[p] === 'object') {" +
            "        $img[p].style.width = '100%';" +
            "        $img[p].style.height ='auto';" +
            "        $img[p].onclick = function(e){" +
            "            ImgClick(e.srcElement.src);" +
            "        };" +
            "    }" +
            "}" +
            "};" +
            "function ImgClick(src) {" +
            "    var message = {" +
            "        'imgUrl' : src," +
            "    };" +
            "   window.imageOnclick.openImage(src);" +
            "};" +
            "</script>";
}
