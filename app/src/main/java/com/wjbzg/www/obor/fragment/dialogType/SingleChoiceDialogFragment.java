package com.wjbzg.www.obor.fragment.dialogType;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by ZY on 2017/2/23.
 */

public class SingleChoiceDialogFragment extends DialogFragment {

    private String title;

    private String[] items;

    //默认选中项
    int defaultSelectedValue = 0;

    private DialogInterface.OnClickListener onClickListener;

    private DialogInterface.OnClickListener positiveCallback;

    public SingleChoiceDialogFragment() {}

    @SuppressLint("ValidFragment")
    public SingleChoiceDialogFragment(int defaultSelectedValue ) {
        this.defaultSelectedValue = defaultSelectedValue;
    }

    public void show(String title, String[] items, DialogInterface.OnClickListener onClickListener,
                     DialogInterface.OnClickListener positiveCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.items = items;
        this.onClickListener = onClickListener;
        this.positiveCallback = positiveCallback;
        show(fragmentManager, "SingleChoiceDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setSingleChoiceItems(items, defaultSelectedValue, onClickListener)
                .setPositiveButton("确定", positiveCallback);
        return builder.create();
    }

}
