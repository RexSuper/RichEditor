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
        /**
         * 基本选项
         */
        NewLine,
        Blod,
        Italic,
        Subscript,
        Superscript,
        Strikethrough,
        Underline,
        JustifyLeft,
        JustifyCenter,
        JustifyRight,
        Blockquote,
        Heading,
        Undo,
        Redo,
        Indent,
        Outdent,
        Image,
        InsertLink,
        Checkbox,
        TextColor,
        TextBackgroundColor,
        FontSize,
        UnorderedList,
        OrderedList,
        Video
    }

    public interface OnItemClick {
        void click(int position, ChooseDialogData param);
    }

    public static void show(final Context context, Type type, final OnItemClick onItemClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.show_notice);
        builder.setTitle(type.name() + " Choose");
        final List<ChooseDialogData> dataList = new ArrayList<>();
        switch (type) {
            case TextColor:
                dataList.add(new ChooseDialogData("red", Color.RED, 0));
                dataList.add(new ChooseDialogData("gray", Color.GRAY, 0));
                dataList.add(new ChooseDialogData("blue", Color.BLUE, 0));
                dataList.add(new ChooseDialogData("yellow", Color.YELLOW, 0));
                dataList.add(new ChooseDialogData("cyan", Color.CYAN, 0));
                break;

            case TextBackgroundColor:
                dataList.add(new ChooseDialogData("red", Color.RED, 0));
                dataList.add(new ChooseDialogData("gray", Color.GRAY, 0));
                dataList.add(new ChooseDialogData("blue", Color.BLUE, 0));
                dataList.add(new ChooseDialogData("yellow", Color.YELLOW, 0));
                dataList.add(new ChooseDialogData("cyan", Color.CYAN, 0));
                break;
            case Heading:
                dataList.add(new ChooseDialogData("xx-small", 1, 0));
                dataList.add(new ChooseDialogData("x-small", 2, 0));
                dataList.add(new ChooseDialogData("small", 3, 0));
                dataList.add(new ChooseDialogData("medium", 4, 0));
                dataList.add(new ChooseDialogData("large", 5, 0));
                dataList.add(new ChooseDialogData("x-large", 6, 0));
                dataList.add(new ChooseDialogData("xx-large", 7, 0));
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
