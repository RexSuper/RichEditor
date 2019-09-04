package com.rex.richeditor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rex.editor.view.RichEditorNew;
import com.rex.richeditor.tools.ChooseDialog;
import com.rex.richeditor.tools.ChooseDialog.Type;
import com.rex.richeditor.tools.ChooseDialogData;
import com.rex.richeditor.tools.EditToolAdapter;

import static com.rex.richeditor.tools.HttpFakeUtils.TEST_IMAGE_URL;
import static com.rex.richeditor.tools.HttpFakeUtils.TEST_VIDEO_URL;
import static com.rex.richeditor.tools.HttpFakeUtils.TEST_WEB_URL;

/**
 * @author Rex
 * 编辑部分 快捷演示
 */
public class SimpleCallsActivity extends Activity {

    private RichEditorNew richEditor;
    private ProgressBar pb;
    private GridView gvList;
    private View tools;
    private EditToolAdapter editToolAdapter;
    private Context mContext;
    public final static String TAG = "rex";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_call);
        mContext = SimpleCallsActivity.this;
        verifyStoragePermissions(this);
        initView();
        initListener();


    }

    private void initListener() {
        richEditor.setOnTextChangeListener(new RichEditorNew.OnTextChangeNewListener() {
            @Override
            public void onTextChange(String text) {
                pb.setVisibility(View.GONE);
                Log.i(TAG, "text:" + text);
            }
        });

        richEditor.setOnConsoleMessageListener(new RichEditorNew.OnConsoleMessageListener() {
            @Override
            public void onTextChange(String message, int lineNumber, String sourceID) {
                //控制台打印消息 也可以传值
                if (message != null && message.contains("|")) {
                    Toast.makeText(SimpleCallsActivity.this, "message:" + message, Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "message:" + message);
            }
        });


        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tools.setVisibility(View.GONE);
                ChooseDialogData item = editToolAdapter.getItem(position);
                switch (Type.valueOf(item.des)) {
                    case File:
                        insertFile();
                        break;
                    case Audio:
                        insertAudio();
                        break;
                    case Video:
                        insertVideo();
                        break;
                    case TextColor:
                        ChooseDialog.show(mContext, Type.TextColor, new ChooseDialog.OnItemClick() {
                            @Override
                            public void click(int position, ChooseDialogData data) {
                                richEditor.setTextColor((int) data.params);
                            }
                        });
                        break;
                    case TextBackgroundColor:
                        ChooseDialog.show(mContext, Type.TextBackgroundColor, new ChooseDialog.OnItemClick() {
                            @Override
                            public void click(int position, ChooseDialogData data) {
                                richEditor.setTextBackgroundColor((int) data.params);
                            }
                        });
                        break;

                    case Heading:
                        ChooseDialog.show(mContext, Type.Heading, new ChooseDialog.OnItemClick() {
                            @Override
                            public void click(int position, ChooseDialogData data) {
                                richEditor.setFontSize((int) data.params);
                            }
                        });
                        break;
                    case Image:
                        insertImage();
                        break;

                    case InsertLink:
                        richEditor.insertLink(TEST_WEB_URL, "test link");
                        break;
                    case NewLine:
                        richEditor.setNewLine();
                        break;
                    case Blod:
                        richEditor.setBold();
                        break;
                    case Subscript:
                        richEditor.setSubscript();
                        break;
                    case Superscript:
                        richEditor.setSuperscript();
                        break;

                    case Strikethrough:
                        richEditor.setStrikeThrough();
                        break;
                    case Underline:
                        richEditor.setUnderline();
                        break;
                    case JustifyLeft:
                        richEditor.setAlignLeft();
                        break;
                    case JustifyCenter:
                        richEditor.setAlignCenter();
                        break;
                    case JustifyRight:
                        richEditor.setAlignRight();
                    case Blockquote:
                        richEditor.setBlockquote();
                        break;
                    case Undo:
                        richEditor.undo();
                        break;
                    case Redo:
                        richEditor.redo();
                        break;
                    case Indent:
                        richEditor.setIndent();
                        break;
                    case Outdent:
                        richEditor.setOutdent();
                        break;
                    case Checkbox:
                        richEditor.insertTodo();
                        break;
                    case FontSize:
                        // TODO
                        break;
                    case UnorderedList:
                        richEditor.setBullets();
                        break;
                    case OrderedList:
                        richEditor.setNumbers();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void insertFile() {
        richEditor.insertFileWithDown(TEST_VIDEO_URL, "点击下载");
    }

    private void insertAudio() {
        //默认调用 也可以自定义insertAudio(String audioUrl, String custom)
        richEditor.insertAudio(TEST_VIDEO_URL);
    }

    private void insertVideo() {
        richEditor.insertVideo(TEST_VIDEO_URL);
    }

    public void insertImage() {
        richEditor.insertImage(TEST_IMAGE_URL);
    }

    @Override
    public void onBackPressed() {
        if (tools.getVisibility() == View.VISIBLE) {
            tools.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();

    }

    private void initView() {

        tools = findViewById(R.id.tools);
        richEditor = findViewById(R.id.richEditor);
        gvList = findViewById(R.id.gvList);
        pb = findViewById(R.id.pb);
        richEditor.setHint("请输入内容");
        // fixme
//        richEditor.setHintColor("#123456");
        richEditor.setPadding(10, 10, 10, 10);
        editToolAdapter = new EditToolAdapter(this);
        gvList.setAdapter(editToolAdapter);
    }

    public void openTools(View view) {
        tools.setVisibility(View.VISIBLE);
    }

    public void closeTools(View view) {
        tools.setVisibility(View.GONE);
    }

    public void getHtmlCode(View view) {
        startShow(false);
    }


    /**
     * 获取当前是否加粗 字体是多少 颜色是多少等 根据需要 如果需求需要实现 选中加粗区域 控制加粗的图标会跟随的变化则调用此方法
     * 一般将此方法加到点击事件中
     *
     * @param view
     */
    public void getCurrChooseParams(View view) {
        richEditor.getCurrChooseParams();
    }

    /**
     * 模拟发布内容，然后回来展示，并新增一些新事件
     *
     * @param view
     */
    public void publish(View view) {
        startShow(true);
    }

    public void startShow(Boolean isPublish) {
        Intent intent = new Intent(mContext, ShowHtmlActivity.class);
        String html = richEditor.getHtml();
        intent.putExtra("html", html);
        intent.putExtra("isPublish", isPublish);
        startActivity(intent);

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 在对sd卡进行读写操作之前调用这个方法
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
