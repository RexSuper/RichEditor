package com.rex.richeditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.rex.editor.common.CommonJs;
import com.rex.editor.view.RichEditor;
import com.rex.editor.view.RichEditorNew;
import com.rex.richeditor.tools.DownloadTask;

import java.io.File;

import static com.rex.richeditor.MainActivity.TAG;
import static com.rex.richeditor.tools.DownloadTask.getMIMEType;

/**
 * @author Rex
 * 展示部分
 */
public class ShowHtmlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_html);
        String html = getIntent().getStringExtra("html");
        boolean isPublish = getIntent().getBooleanExtra("isPublish", true);
        RichEditorNew richEditor = findViewById(R.id.richEditor);
        TextView tvHtmlCode = findViewById(R.id.tvHtmlCode);
        if (isPublish) {

            tvHtmlCode.setVisibility(View.GONE);
            richEditor.setVisibility(View.VISIBLE);
            //为图片加上点击事件 Js
            //不可编辑 预览模式
            richEditor.loadDataWithBaseURL("file:///android_asset/",
                    html + CommonJs.IMG_CLICK_JS, "text/html", "utf-8", null);
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
                    new DownloadTask(ShowHtmlActivity.this, new DownloadTask.DownloadTaskCallBack() {

                        @Override
                        public void onPreExecute() {
                            Toast.makeText(ShowHtmlActivity.this, "开始下载" , Toast.LENGTH_LONG).show();
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
                            Toast.makeText(ShowHtmlActivity.this, "下载失败:"+error, Toast.LENGTH_SHORT).show();
                        }
                    }).execute(url, destPath);
                }
            });
        } else {
            tvHtmlCode.setText(html);
            tvHtmlCode.setVisibility(View.VISIBLE);
            richEditor.setVisibility(View.GONE);
        }


    }
}
