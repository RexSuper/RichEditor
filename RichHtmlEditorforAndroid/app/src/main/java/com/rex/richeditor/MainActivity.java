package com.rex.richeditor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.rex.editor.view.RichEditorNew;
import com.rex.richeditor.ChooseTools.ChooseDialog;
import com.rex.richeditor.ChooseTools.ChooseDialog.Type;
import com.rex.richeditor.ChooseTools.ChooseDialogData;
import com.rex.richeditor.ChooseTools.EditToolAdapter;

/**
 * @author Rex
 */
public class MainActivity extends Activity {

    private RichEditorNew richEditor;
    private ProgressBar pb;
    private GridView gvList;
    private EditToolAdapter editToolAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initView();
        initListener();


    }

    private void initListener() {
        richEditor.setOnTextChangeListener(new RichEditorNew.OnTextChangeNewListener() {
            @Override
            public void onTextChange(String text) {
                pb.setVisibility(View.GONE);
                Log.i("rex", "text:" + text);

            }
        });

        richEditor.setOnConsoleMessageListener(new RichEditorNew.OnConsoleMessageListener() {
            @Override
            public void onTextChange(String message, int lineNumber, String sourceID) {
                //控制台打印消息 也可以传值
                Log.i("rex", "message:" + message);
            }
        });

        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseDialogData item = editToolAdapter.getItem(position);
                switch (Type.valueOf(item.des)) {
                    case Color:
                        ChooseDialog.show(mContext, Type.Color, new ChooseDialog.OnItemClick() {
                            @Override
                            public void click(int position, ChooseDialogData data) {
                                richEditor.setTextColor((int)data.params);
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initView() {

        richEditor = findViewById(R.id.richEditor);
        gvList = findViewById(R.id.gvList);
        pb = findViewById(R.id.pb);
        richEditor.setHint("请输入内容");
        richEditor.setHintColor("#123456");
        richEditor.setFontSize(2);
        editToolAdapter = new EditToolAdapter(this);
        gvList.setAdapter(editToolAdapter);
    }

    public void insertImage(View view) {
        //需要编辑框有光标才行
        richEditor.focusEditor();
        pb.setVisibility(View.VISIBLE);
        // 如果你想在加入图片后换行如果这样调用
        // 加载图片是耗时过长 所以需要在textchange后
        richEditor.setNeedSetNewLineAfter(true);
        //可按htmlstyle 自定义间距居中等 类似margin-right 不会生效的问题 都是html本身的问题 可用一样的替换方案
        //实战过程中 本地图片需要先上传到服务器生成url 再调用
        richEditor.insertImage("http://pic44.nipic.com/20140716/8716187_010828140000_2.jpg",
                "picvision",
                "margin-top:10px;max-width:100%;");
    }

    public void newLine(View view) {
        richEditor.focusEditor();
        richEditor.setNewLine();
    }

    /**
     * 获取当前是否加粗 字体是多少 颜色是多少等 根据需要 如果需求需要实现 选中加粗区域 控制加粗的图标会跟随的变化则调用此方法
     * 一般将此方法加到点击事件中
     *
     * @param view
     */
    public void getCurrChooseParams(View view) {
        richEditor.focusEditor();
        richEditor.getCurrChooseParams();
    }

    /**
     * 模拟发布内容，然后回来展示，并新增一些新事件
     *
     * @param view
     */
    public void publish(View view) {
        String html = richEditor.getHtml();
    }
}
