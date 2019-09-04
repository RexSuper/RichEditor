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
