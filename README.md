# Android富文本编辑器

***2019-08-16 更新日志 增加各种文件功能***

***2019-08-15 更新日志 增加添加视频功能***

该项目采用的方法是，放静态网页到项目中，通过webview js和html交互的方式，调用html自带的各种富文本编辑的功能，并加上了一些细节性需求优化，提供一定的拓展性，尝试用过用原生实现，安卓自带的Html类里面自定义标签，但是非常难实现和再现，再和其他端打通非常困难，最后发现直接用html编辑是最好的选择，可直接生成html源代码，也可以直接设置显示，这个方法需要你知道一定的js和webview前端知识

最后特别感谢[wasabeef/richeditor-android](https://github.com/wasabeef/richeditor-android "wasabeef/richeditor-android")，因为里面的RichEditor提供了给我全新的思路，来实现这些需求，让我越过了的原生实现的种种困难，
另外感谢HTML"Mogul" @ZX 让我知道html这些细节坑点 如何实现 ，可以视为它的RichEditor升级补充版

# RichHtmlEditorForAndroid

***2019-08-16 Update Log Added Files Function***

***2019-08-15 Update Log Added Video Function***

This project adopts the method of putting static web pages into the project, calling various rich text editing functions of HTML through interaction between WebView JS and html, and adding some detail requirements optimization to provide certain extensibility, trying to use native implementation, Android's own custom tags in the Html class. But it is very difficult to realize and reproduce, and it is very difficult to get through with other end. Finally, it is found that editing directly with html is the best choice. It can generate HTML source code directly or set up display directly. This method requires you to know some knowledge of JS and WebView front-end.



Finally, I would like to thank

[wasabeef/richeditor-android] (https://github.com/wasabeef/richeditor-android,'wasabeef/richeditor-android'),

because Rich Editor provides me with new ideas to meet these needs and overcomes the difficulties of native implementation.

Thanks to HTML Mogul@ZX for letting me know how to implement these details of HTML as a supplement to its Rich Editor upgrade.



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

<img src="https://img-blog.csdnimg.cn/2019061317083452.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODQ0OTQ3,size_16,color_FFFFFF,t_70" width="50%" height="50%" />

<img src="https://img-blog.csdnimg.cn/20190815113400481.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODQ0OTQ3,size_16,color_FFFFFF,t_70" width="50%" height="50%" />

<img src="https://img-blog.csdnimg.cn/20190815113453215.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODQ0OTQ3,size_16,color_FFFFFF,t_70" width="50%" height="50%"/>



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
