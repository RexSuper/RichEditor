package com.rex.richeditor.ChooseTools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Toast;

import com.rex.richeditor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rex on 2019/6/20.
 */
public class ChooseDialog {

    public enum Type {
        Color
    }

    public interface OnItemClick {
        void click(int position, ChooseDialogData param);
    }

    public static void show(final Context context, Type type, final OnItemClick onItemClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog);
        builder.setTitle(type.name() + " Choose");
        final List<ChooseDialogData> dataList = new ArrayList<>();
        switch (type) {
            case Color:
                builder.setIcon(R.drawable.color);
                dataList.add(new ChooseDialogData("RED", Color.RED,0));
                dataList.add(new ChooseDialogData("GRAY", Color.GRAY,0));
                dataList.add(new ChooseDialogData("BLUE", Color.BLUE,0));
                dataList.add(new ChooseDialogData("YELLOW", Color.YELLOW,0));
                break;

            default:
                break;
        }

        if (dataList.size() == 0) {
            return;
        }
        builder.setItems(getDesList(dataList), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                ChooseDialogData chooseDialogData = dataList.get(position);
                if (onItemClick != null) {
                    onItemClick.click(position, chooseDialogData);
                }
                Toast.makeText(context, "Choose：" + chooseDialogData.des, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    private static String[] getDesList(List<ChooseDialogData> dataList) {
        if (dataList != null) {
            int size = dataList.size();
            String[] des = new String[size];
            for (int i = 0; i < size; i++) {
                des[i] = dataList.get(i).des;
            }
            return des;
        }
        return null;
    }

}
