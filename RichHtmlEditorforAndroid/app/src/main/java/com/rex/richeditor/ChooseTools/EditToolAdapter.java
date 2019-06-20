package com.rex.richeditor.ChooseTools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rex.richeditor.ChooseTools.ChooseDialog;
import com.rex.richeditor.ChooseTools.ChooseDialogData;
import com.rex.richeditor.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rex on 2019/6/20.
 */
public class EditToolAdapter extends BaseAdapter {

    private Context mContext;

    public EditToolAdapter(Context context) {
        mContext = context;
    }

    private List<ChooseDialogData> dataList = Arrays.asList(
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color),
            new ChooseDialogData(ChooseDialog.Type.Color.name(), R.drawable.color)
    );

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public ChooseDialogData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gv_edit, null);
        }
        ImageView imageView = convertView.findViewById(R.id.ivIcon);
        TextView tvDes = convertView.findViewById(R.id.tvDes);
        imageView.setImageResource(dataList.get(position).iconId);
        tvDes.setText(dataList.get(position).des);
        return convertView;
    }
}
