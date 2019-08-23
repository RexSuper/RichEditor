package com.rex.richeditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.rex.editor.common.CommonJs;
import com.rex.editor.view.RichEditor;
import com.rex.editor.view.RichEditorNew;
import com.rex.editor.common.DownloadTask;

import java.io.File;

import static com.rex.editor.common.DownloadTask.getMIMEType;
import static com.rex.richeditor.MainActivity.verifyStoragePermissions;

/**
 * @author Rex
 * 展示部分
 */
public class ShowHtmlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_html);

        //下载权限需要
        verifyStoragePermissions(this);
        String html = getIntent().getStringExtra("html");
        boolean isPublish = getIntent().getBooleanExtra("isPublish", true);
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


    }
}
