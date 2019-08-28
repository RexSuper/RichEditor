APk:https://github.com/RexSuper/RichEditor/blob/master/RichHtmlEditorforAndroid/app/release/RichEditorAndroid.apk


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.RexSuper:RichEditor:1.0.3'
	}

The following code must be read, because rich text has some functions that must be filled in later, such as the user's resource files have to be placed in their own server to generate links

下面的示例代码必须看，因为富文本有些功能后续不得不自己填充，比如用户的资源文件得先放到自己服务器生成链接

https://github.com/RexSuper/RichEditor/tree/example/Example

[使用方式1 编辑模式--->](https://github.com/RexSuper/RichHtmlEditorForAndroid/blob/master/RichHtmlEditorforAndroid/app/src/main/java/com/rex/richeditor/MainActivity.java "")

[使用方式2 展示模式--->](https://github.com/RexSuper/RichEditor/blob/master/RichHtmlEditorforAndroid/app/src/main/java/com/rex/richeditor/ShowHtmlActivity.java "")

# Android富文本编辑器

***2019-08-20 更新日志 增加音频功能 增加链接下载功能 和视频下载功能***

***2019-08-16 更新日志 增加各种文件功能***

***2019-08-15 更新日志 增加添加视频功能***

***2019-06-21 更新日志 增加基本图文混排等功能***

该项目采用的方法是，放静态网页到项目中，通过webview js和html交互的方式，调用html自带的各种富文本编辑的功能，并加上了一些细节性需求优化，提供一定的拓展性，尝试用过用原生实现，安卓自带的Html类里面自定义标签，但是非常难实现和再现，再和其他端打通非常困难，最后发现直接用html编辑是最好的选择，可直接生成html源代码，也可以直接设置显示，这个方法需要你知道一定的js和webview前端知识

最后特别感谢[wasabeef/richeditor-android](https://github.com/wasabeef/richeditor-android "wasabeef/richeditor-android")，因为里面的RichEditor提供了给我全新的思路，来实现这些需求，让我越过了的原生实现的种种困难，
另外感谢HTML"Mogul" @ZX 让我知道html这些细节坑点 如何实现 ，可以视为它的RichEditor升级补充版

# RichHtmlEditorForAndroid

***2019-08-20 Update Log, Audio Function, Link Download Function and Video Download Function***

***2019-08-16 Update Log Added Files Function***

***2019-08-15 Update Log Added Video Function***

***2019-06-21 Update logs add basic graphics and text mixing functions***




This project adopts the method of putting static web pages into the project, calling various rich text editing functions of HTML through interaction between WebView JS and html, and adding some detail requirements optimization to provide certain extensibility, trying to use native implementation, Android's own custom tags in the Html class. But it is very difficult to realize and reproduce, and it is very difficult to get through with other end. Finally, it is found that editing directly with html is the best choice. It can generate HTML source code directly or set up display directly. This method requires you to know some knowledge of JS and WebView front-end.



Finally, I would like to thank

[wasabeef/richeditor-android] (https://github.com/wasabeef/richeditor-android,'wasabeef/richeditor-android'),

because Rich Editor provides me with new ideas to meet these needs and overcomes the difficulties of native implementation.

Thanks to HTML Mogul@ZX for letting me know how to implement these details of HTML as a supplement to its Rich Editor upgrade.



[原理讲解--->](https://blog.csdn.net/qq_28844947/article/details/91870015 "")




        
--->  | 支持功能  | Support function| <---
---- | ----- | ------ | ------
Video  | Image  | Audio | File And Download
Blod  | Italic | Subscript| Strikethrough
Underline  | JustifyLeft | JustifyCenter| JustifyRight
Blockquote  | Heading | Undo| Redo
Indent  | Outdent | InsertLink| Checkbox
TextColor  | TextBackgroundColor | FontSize| UnorderedList
OrderedList  | Hint |Superscript | InsertLink
# 实现内容

<img src="https://img-blog.csdnimg.cn/2019061317083452.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODQ0OTQ3,size_16,color_FFFFFF,t_70" width="50%" height="50%" />

<img src="https://img-blog.csdnimg.cn/20190820115556197.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODQ0OTQ3,size_16,color_FFFFFF,t_70" width="50%" height="50%" />

<img src="https://img-blog.csdnimg.cn/20190815113453215.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODQ0OTQ3,size_16,color_FFFFFF,t_70" width="50%" height="50%"/>

#Edit

```
   private void insertFile() {
        richEditor.insertFileWithDown(TEST_VIDEO_URL, "点击下载");
    }

    private void insertAudio() {
        //默认调用
        richEditor.insertAudio(TEST_AUDIO_URL);
        //自定义  
//        richEditor.insertAudio(TEST_VIDEO_URL,
//                //增加进度控制
//                "controls=\"controls\"" +
//                        //宽高
//                        "height=\"300\" " +
//                        //样式
//                        " style=\"margin-top:10px;max-width:100%;\""
//        );
    }

    private void insertVideo() {

        //默认样式
        richEditor.insertVideo(TEST_VIDEO_URL);
        //自定义       
        // richEditor.insertVideo(TEST_VIDEO_URL,
        //                //增加进度控制
        //                "controls=\"controls\"" +
        //                        //视频显示第一帧
        //                        " initial-time=\"0.01\" " +
        //                        //宽高
        //                        "height=\"300\" " +
        //                        //样式
        //                        " style=\"margin-top:10px;max-width:100%;\""
        //);

    }

    public void insertImage() {
        //可按htmlstyle 自定义间距居中等 类似margin-right 不会生效的问题 都是html本身的问题 可用一样的替换方案
        //实战过程中 本地图片需要先上传到服务器生成url 再调用
        //默认样式
        richEditor.insertImage(TEST_IMAGE_URL);
        //自定义 
//        richEditor.insertImage(TEST_IMAGE_URL,
//                "picvision",
//                "margin-top:10px;max-width:100%;");
    }

```

#Show

```
// richEditor.loadDataWithBaseURL("file:///android_asset/",
                    html + CommonJs.IMG_CLICK_JS, "text/html", "utf-8", null);
 richEditor.loadRichEditorCode(html);
            richEditor.setOnClickImageTagListener(new RichEditor.OnClickImageTagListener() {
                @Override
                public void onClick(String url) {
                    Toast.makeText(ShowHtmlActivity.this, "url:" + url, Toast.LENGTH_LONG).show();
                }
            });

            richEditor.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                    String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
                    String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .getAbsolutePath() + File.separator + fileName;
                    new DownloadTask(new DownloadTask.DownloadTaskCallBack() {

                        @Override
                        public void onPreExecute() {
                            Toast.makeText(ShowHtmlActivity.this, "开始下载", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void doInBackground(int progress) {

                        }

                        @Override
                        public void onPostExecute(boolean noError, String url, String destPath) {
                            if (noError) {
                                Toast.makeText(ShowHtmlActivity.this, "下载完成" + destPath, Toast.LENGTH_LONG).show();
                                Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
                                String mimeType = getMIMEType(url);
                                Uri uri = Uri.fromFile(new File(destPath));
                                handlerIntent.setDataAndType(uri, mimeType);
                                startActivity(handlerIntent);
                            } else {
                                Toast.makeText(ShowHtmlActivity.this, "下载失败!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(ShowHtmlActivity.this, "下载失败:" + error, Toast.LENGTH_SHORT).show();
                        }
                    }).execute(url, destPath);
                }
            });

```

疑难细节问题记录：https://github.com/RexSuper/RichEditor/wiki/富文本编辑器-特殊记录
