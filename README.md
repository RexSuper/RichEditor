# Android富文本编辑器 说明
[博客部分原理讲解--->](https://blog.csdn.net/qq_28844947/article/details/91870015 "")


本编辑器基于静态html实现了客户端编辑器所需要的几乎全部功能及其实际流程所需，包括下载，视频缩略图，自定义标签样式
自定义点击事件本地资源转网络资源需要改为你自己的服务器）等等 

下面一个实际项目的应用演示

<img src="https://img-blog.csdnimg.cn/20190904180215968.gif" width="50%" height="50%"/>

例子：apk

https://github.com/RexSuper/RichEditor/blob/master/RichHtmlEditorforAndroid/sample/release/release/sample-release.apk

# 集成方式

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.RexSuper:RichEditor:1.0.5'
	}


# 使用示例
下面的示例代码必须要看，因为富文本有些功能后续不得不自己填充，比如用户的资源文件最终得放到自己服务器生成链接,视频缩略图等拓展功能，编辑模式和再现的模式也是不同的，务必看演示案例

# https://github.com/RexSuper/RichEditor/tree/master/RichHtmlEditorforAndroid/sample

# 更新日志

***2019-09-04  优化用户体验，在编辑时候所有资源改为本地，加载转在线改为，最后统一发布的时候***

***2019-08-20  增加音频功能 增加链接下载功能 和视频下载功能***

***2019-08-16  增加各种文件功能***

***2019-08-15  增加添加视频功能***

***2019-06-21  增加基本图文混排等功能***

该项目采用的方法是，放静态网页到项目中，通过webview js和html交互的方式，调用html自带的各种富文本编辑的功能，并加上了一些细节性需求优化，提供一定的拓展性，尝试用过用原生实现，安卓自带的Html类里面自定义标签，但是非常难实现和再现，再和其他端打通非常困难，最后发现直接用html编辑是最好的选择，可直接生成html源代码，也可以直接设置显示，这个方法需要你知道一定的js和webview前端知识

最后特别感谢[wasabeef/richeditor-android](https://github.com/wasabeef/richeditor-android "wasabeef/richeditor-android")，因为里面的RichEditor提供了给我全新的思路，来实现这些需求，让我越过了的原生实现的种种困难，
另外感谢HTML"Mogul" @ZX 让我知道html这些细节坑点 如何实现 ，可以视为它的RichEditor升级补充版





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

#Edit Demo

```
package com.rex.richeditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rex.editor.common.EssFile;
import com.rex.editor.view.RichEditorNew;
import com.rex.richeditor.tools.HttpFakeUtils;
import com.rex.editor.common.FilesUtils;

import java.util.List;

/**
 * 主要用于演示  视频 音频 文件 插入 及其后续实际流程
 */
public class ActualDemoActivity extends AppCompatActivity {

    public final static int RESULT_CHOOSE = 123;
    private RichEditorNew richEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_demo);
        richEditor = findViewById(R.id.richEditor);
        //自动为视频添加缩略图
        richEditor.setNeedAutoPosterUrl(true);
        richEditor.focusEditor();
        //本地文件上传到服务器生成在线文件，这一步如果需要发布到服务器，是必不可少的
        //为了优化体验，我们将这一步放到最后，将所有本地src或者href，一起上传，避免在编辑的时候 选择一次等待上传一次
        //后续也可以做后台上传，进一步增加你的app的用户体验

        //1.用户编辑插入各种文件  src是本地文件
        //2.最后发布的时候将所有src提取出来，一起上传
        //3.替换掉html中本地的src
        //4.将html code存到服务器
    }


    private String orginHtml = "";
    private int index = 0;
    private int max = 0;

    public void publish(View view) {
        index = 0;
        orginHtml = richEditor.getHtml();

        List<String> allSrcAndHref = richEditor.getAllSrcAndHref();
        if (allSrcAndHref == null || allSrcAndHref.size() == 0) {
            //没有资源文件直接发布
            publishArticle(orginHtml);
        } else {
            max = allSrcAndHref.size();
            Log.i("rex", "上传进度：" + index + "/" + max);
            for (String src : allSrcAndHref) {
                final String orginSrc = src;

                HttpFakeUtils.postFile(new EssFile(src), new HttpFakeUtils.HttpResult() {
                    @Override
                    public void onSuccess(String url) {

                        orginHtml = orginHtml.replace(orginSrc, url);
                        checkStatus(orginHtml);
                    }

                    private void checkStatus(String httpHtml) {
                        ++index;
                        //失败也算进度
                        Log.i("rex", "上传进度：" + index + "/" + max);
                        if (index >= max) {
                            Log.i("rex", "上传结束：" + index + "/" + max);
                            publishArticle(httpHtml);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        checkStatus(orginHtml);
                    }
                });
            }
        }


    }

    private void publishArticle(final String httpHtml) {
        HttpFakeUtils.publishArticle(httpHtml, new HttpFakeUtils.HttpResult() {
            @Override
            public void onSuccess(String msg) {
                //缩略图功能会产生本地文件可调用此方法清理
                richEditor.clearLocalRichEditorCache();
                Toast.makeText(ActualDemoActivity.this, msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActualDemoActivity.this, ShowHtmlActivity.class);
                intent.putExtra("html", httpHtml);
                intent.putExtra("isPublish", true);
                startActivity(intent);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    /**
     * 这里采用系统自带方法，可替换为你更方便的自定义文件选择器
     */
    public void openDirChooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);//多选
        startActivityForResult(intent, RESULT_CHOOSE);
    }

    public void chooseFile(Activity activity, int requestCode) {
//        String[] mimeTypes = {MimeType.DOC, MimeType.DOCX, MimeType.PDF, MimeType.PPT, MimeType.PPTX, MimeType.XLS, MimeType.XLSX};
//        openDirChooseFile(activity, requestCode, mimeTypes);
    }

    /**
     * 根据返回选择的文件，来进行上传操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CHOOSE) {
            Uri uri = data.getData();
            if (uri != null) {
                String abUrl = FilesUtils.getPath(ActualDemoActivity.this, uri);
                Log.i("rex", "abUrl:" + abUrl);
                EssFile essFile = new EssFile(abUrl);
                if (essFile.isImage() || essFile.isGif()) {
                    richEditor.insertImage(essFile.getAbsolutePath());
                } else if (essFile.isVideo()) {
                    richEditor.insertVideo(essFile.getAbsolutePath());
                } else if (essFile.isAudio()) {
                    richEditor.insertAudio(essFile.getAbsolutePath());
                } else {
                    richEditor.insertFileWithDown(essFile.getAbsolutePath(), "文件:" + essFile.getName());
                }
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


```

#Show

```
      RichEditorNew richEditor = findViewById(R.id.richEditor);
        TextView tvHtmlCode = findViewById(R.id.tvHtmlCode);
        if (isPublish) {

            tvHtmlCode.setVisibility(View.GONE);
            richEditor.setVisibility(View.VISIBLE);
            //为图片加上点击事件 Js
            //不可编辑 预览模式
            richEditor.loadRichEditorCode(html);
            richEditor.setOnClickImageTagListener(new RichEditor.OnClickImageTagListener() {
                @Override
                public void onClick(String url) {
                    Toast.makeText(ShowHtmlActivity.this, "url:" + url, Toast.LENGTH_LONG).show();
                }
            });

            richEditor.setDownloadListener(DownloadTask.getDefaultDownloadListener(this));
        } else {
            tvHtmlCode.setText(html);
            tvHtmlCode.setVisibility(View.VISIBLE);
            richEditor.setVisibility(View.GONE);
        }

```

疑难细节问题记录：https://github.com/RexSuper/RichEditor/wiki/富文本编辑器-特殊记录
