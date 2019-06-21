package com.rex.richeditor;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rex.editor.common.CommonJs;
import com.rex.editor.view.RichEditor;
import com.rex.editor.view.RichEditorNew;

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
        } else {
            tvHtmlCode.setText(html);
            tvHtmlCode.setVisibility(View.VISIBLE);
            richEditor.setVisibility(View.GONE);
        }


    }
}
