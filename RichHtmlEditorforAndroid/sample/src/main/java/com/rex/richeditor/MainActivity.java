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
 * 编辑部分
 */
public class MainActivity extends Activity {

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
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        verifyStoragePermissions(this);
    }



    public void goSimpleCalls(View view){
        startActivity(new Intent(this,SimpleCallsActivity.class));
    }

    public void goActualDemo(View view){
        startActivity(new Intent(this,ActualDemoActivity.class));
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
