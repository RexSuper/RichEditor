# Android富文本编辑器

***2019-08-15 更新日志 增加添加视频功能***

该项目采用的方法是，放静态网页到项目中，通过webview js和html交互的方式，调用html自带的各种富文本编辑的功能，并加上了一些细节性需求优化，提供一定的拓展性，尝试用过用原生实现，安卓自带的Html类里面自定义标签，但是非常实现和再现，再和其他端打通非常困难，最后发现直接用html编辑是最好的选择，可直接生成html源代码，也可以直接设置显示，这个方法需要你知道一定的js和webview前端知识

最后特别感谢[wasabeef/richeditor-android](https://github.com/wasabeef/richeditor-android "wasabeef/richeditor-android")，因为里面的RichEditor提供了给我全新的思路，来实现这些需求，让我越过了的原生实现的种种困难，
另外感谢HTML"Mogul" @ZX 让我知道html这些细节坑点 如何实现 ，可以视为它的RichEditor升级补充版

# RichHtmlEditorForAndroid

***2019-08-15 Update Log Added Video Function***

This project adopts the method of putting static web pages into the project, calling various rich text editing functions of HTML through the way of interaction between WebView JS and html, and adding some detail requirements optimization to provide certain extensibility, trying to use native implementation, Android's own Html class to customize labels, but very achieve and reproduce, and then communicate with other terminals. It's very difficult. Finally, we find that editing directly with html is the best choice. It can generate HTML source code directly or set up display directly. This method requires you to know some knowledge of JS and WebView front-end.

Finally, 

I would like to thank [wasabeef/richeditor-android](https://github.com/wasabeef/richeditor-android "wasabeef/richeditor-android") in particular, because Rich Editor provides me with new ideas to achieve this requirement, which can be regarded as an upgraded supplement to it.

Thank you also for HTML Mogul @ZX aha



[原理讲解--->](https://blog.csdn.net/qq_28844947/article/details/91870015 "")

 [使用方式--->](https://github.com/RexSuper/RichHtmlEditorForAndroid/blob/master/RichHtmlEditorforAndroid/app/src/main/java/com/rex/richeditor/MainActivity.java "")



        
--->  | 支持功能  | Support function| <---
---- | ----- | ------ | ------
Video  | Image  | InsertLink| Superscript
Blod  | Italic | Subscript| Strikethrough
Underline  | JustifyLeft | JustifyCenter| JustifyRight
Blockquote  | Heading | Undo| Redo
Indent  | Outdent | InsertLink| Checkbox
TextColor  | TextBackgroundColor | FontSize| UnorderedList
OrderedList  | Hint | | 


# 实现内容
![image](https://upload-images.jianshu.io/upload_images/7292870-e51416c2189f8b3c?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.gif](https://upload-images.jianshu.io/upload_images/7292870-e9f1e4c3f08c9782.gif?imageMogr2/auto-orient/strip) ​![image](https://upload-images.jianshu.io/upload_images/7292870-d4833b9b48a92b5e?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.gif](https://upload-images.jianshu.io/upload_images/7292870-05c1f22fb0b1642f.gif?imageMogr2/auto-orient/strip) ​

```
private void insertVideo() {
        //需要编辑框有光标才行
        richEditor.focusEditor();
        //将视频上传到自己服务器得到链接
        //============>
        richEditor.setNeedSetNewLineAfter(true);
        richEditor.insertVideo("https://www.w3school.com.cn/example/html5/mov_bbb.mp4",
                        //增加进度控制
                "controls=\"controls\"" +
                        //视频显示第一帧
                        " initial-time=\"0.01\" " +
                        //宽高
                        "height=\"300\" " +
                        //样式
                        " style=\"margin-top:10px;max-width:100%;\""
        );

}

public void insertImage() {
        //需要编辑框有光标才行
        richEditor.focusEditor();
        // 如果你想在加入图片后换行如果这样调用
        // 加载图片是耗时过长 所以需要在textchange后
        richEditor.setNeedSetNewLineAfter(true);
        //可按htmlstyle 自定义间距居中等 类似margin-right 不会生效的问题 都是html本身的问题 可用一样的替换方案
        //实战过程中 本地图片需要先上传到服务器生成url 再调用
        richEditor.insertImage("http://pic44.nipic.com/20140716/8716187_010828140000_2.jpg",
                "picvision",
                "margin-top:10px;max-width:100%;");
}

```
