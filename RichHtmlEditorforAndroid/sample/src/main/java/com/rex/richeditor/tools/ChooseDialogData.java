package com.rex.richeditor.tools;

/**
 * @author Rex on 2019/6/20.
 */
public class ChooseDialogData {
    public Object params;
    public String des;
    public int iconId;

    public ChooseDialogData(String des, int iconId) {
        this.des = des;
        this.iconId = iconId;
    }

    public ChooseDialogData() {
    }

    public ChooseDialogData(String des, Object params, int iconId) {
        this.params = params;
        this.des = des;
        this.iconId = iconId;
    }
}
