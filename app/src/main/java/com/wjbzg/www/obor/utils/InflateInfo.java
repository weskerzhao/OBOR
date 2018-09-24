package com.wjbzg.www.obor.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbzg.www.obor.R;

/**
 * purpose 文本布局引入工具类
 * Created by 赵辉 on 2018\8\23 0023.
 */

public class InflateInfo {

    private final static InflateInfo INSTANCE = new InflateInfo();

    private InflateInfo(){}

    public static InflateInfo getInstance(){
        return INSTANCE;
    }

    //我的TextView
    public View inflateInfo(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2) {
        View view = inflater.inflate(R.layout.set_info,set_info_list,false);
        TextView tx1 = view.findViewById(R.id.tx1);
        tx1.setText(str1);
        if(str2 != -1){
            TextView tx2 = view.findViewById(R.id.tx2);
            tx2.setText(str2);
        }
        ImageView imageView = view.findViewById(R.id.id_prompt_image);
        imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));
        set_info_list.addView(view);
        return view;
    }

    public View  inflateInfo(LayoutInflater inflater,LinearLayout set_info_list,int str1) {
        View view = inflateInfo(inflater,set_info_list,str1,-1);
        return view;
    }

    //EditText
    public void inflateInfo_Edit(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2,boolean visible) {
        View view = inflater.inflate(R.layout.edit_info,set_info_list,false);
        TextView tx1 = view.findViewById(R.id.tx1);
        tx1.setText(str1);
        if(str2 != -1){
            TextView tx2 = view.findViewById(R.id.tx2);
            tx2.setHint(str2);
        }
        if(visible){
            ImageView imageView = view.findViewById(R.id.id_prompt_image);
            imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));
            imageView.setVisibility(View.VISIBLE);
        }
        set_info_list.addView(view);
    }
    public void inflateInfo_Edit(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2) {
        inflateInfo_Edit(inflater,set_info_list,str1,str2,false);
    }
    public void inflateInfo_Edit(LayoutInflater inflater,LinearLayout set_info_list,int str1,boolean visible) {
        inflateInfo_Edit(inflater,set_info_list,str1,-1,visible);
    }

    //EditTextRegister
    public View inflateInfoRegister_Edit(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2,boolean visible) {
        View view = inflater.inflate(R.layout.edit_info_register,set_info_list,false);
        TextView tx1 = view.findViewById(R.id.tx1);
        EditText tx2 = view.findViewById(R.id.tx2);
        tx1.setText(str1);
        if(str2 != -1){
            tx2.setHint(str2);
        }
        if(visible){
            ImageView imageView = view.findViewById(R.id.id_prompt_image);
            imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));
            imageView.setVisibility(View.VISIBLE);
        }
        set_info_list.addView(view);
        return view;
    }
    public View inflateInfoRegister_Edit(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2) {
        return inflateInfoRegister_Edit(inflater,set_info_list,str1,str2,false);
    }
    public View inflateInfoRegister_Edit(LayoutInflater inflater,LinearLayout set_info_list,int str1,boolean visible) {
        return inflateInfoRegister_Edit(inflater,set_info_list,str1,-1,visible);
    }

    //EditTextRegister
    public View inflateInfoRegister_Edit_select(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2,boolean visible) {
        View view = inflater.inflate(R.layout.edit_info_register_select,set_info_list,false);
        TextView tx1 = view.findViewById(R.id.tx1);
        tx1.setText(str1);
        if(str2 != -1){
            TextView tx2 = view.findViewById(R.id.tx2);
            tx2.setHint(str2);
        }
        if(visible){
            ImageView imageView = view.findViewById(R.id.id_prompt_image);
            imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));
            imageView.setVisibility(View.VISIBLE);
        }
        set_info_list.addView(view);
        return view;
    }


    //发布
    public void inflateInfoPublish(LayoutInflater inflater,LinearLayout set_info_list, int str1, int str2,boolean visible) {
        View view = inflater.inflate(R.layout.set_info_publish,set_info_list,false);
        TextView tx1 = view.findViewById(R.id.tx1);
        tx1.setHint(str1);
        if(str2 != -1){
            TextView tx2 = view.findViewById(R.id.tx2);
            tx2.setHint(str2);
        }
        if(visible){
            ImageView imageView = view.findViewById(R.id.id_prompt_image);
            imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));
            imageView.setVisibility(View.VISIBLE);
        }
        set_info_list.addView(view);
    }

    public void inflateInfoPublish(LayoutInflater inflater,LinearLayout set_info_list,int str1,boolean visible) {
        inflateInfoPublish(inflater,set_info_list,str1,-1,visible);
    }

}
