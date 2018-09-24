package com.wjbzg.www.obor.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SearchView;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wjbzg.www.obor.R;

/**
 * Purpose Activity 工具类
 * Created by 赵辉 on 2018\8\24 0024.
 */

public class ActivityUtils {

   public final static  ActivityUtils INSTANCE = new ActivityUtils();

   private ActivityUtils(){};

   public static ActivityUtils getInstance(){ return ActivityUtils.INSTANCE; }

   //设置SerchView
   public void setSerchView(android.support.v7.widget.SearchView serch,float textSize,int color,boolean spread,boolean site){
       serch.setIconified(false);//设置searchView处于展开状态
       serch.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
       serch.setIconifiedByDefault(site);
       SearchView.SearchAutoComplete textView =  serch.findViewById(R.id.search_src_text);
       textView.setTextSize(textSize);
       if(color != -1){
           textView.setTextColor(color);
       }
   }

    //设置SerchView
    public void setSerchView(android.support.v7.widget.SearchView serch,float textSize){
        setSerchView(serch,textSize,-1,false,true);
    }

    //关闭键盘
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
