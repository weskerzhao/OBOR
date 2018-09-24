package com.wjbzg.www.obor.fragment.dialogType;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.FragmentManager;

/**
 * Created by ZY on 2017/2/23.
 */

public class MultiChoiceDialogFragment extends DialogFragment {

    private String title;

    private String[] items;

    private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;

    private DialogInterface.OnClickListener positiveCallback;

    public void show(String title, String[] items, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener,
                     DialogInterface.OnClickListener positiveCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.items = items;
        this.onMultiChoiceClickListener = onMultiChoiceClickListener;
        this.positiveCallback = positiveCallback;
        show(fragmentManager, "MultiChoiceDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMultiChoiceItems(items, null, onMultiChoiceClickListener)
                .setPositiveButton("确定", positiveCallback);
        return builder.create();
    }



}