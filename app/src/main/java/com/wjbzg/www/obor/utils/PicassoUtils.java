package com.wjbzg.www.obor.utils;

import android.graphics.Color;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wjbzg.www.obor.utils.picassoTransfer.CircleTransform;

/**
 * purpose Picasso 工具类
 * Created by 赵辉 on 2018\8\27 0027.
 */

public class PicassoUtils {

    public final static PicassoUtils INSTANCE = new PicassoUtils();

    private PicassoUtils(){}

    public static PicassoUtils getInstance(){ return PicassoUtils.INSTANCE; }

    //处理圆角图片
    public void setRoundedRectangle (ImageView view, String url){
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .cornerRadiusDp(15)
                .borderWidthDp(0)
                .oval(false)
                .build();

        Picasso.get()
                .load(url)
                .fit()
                .transform(transformation)
                .into(view);
    }
    //处理圆角图片
    public void setRoundedRectangle (ImageView view, int image){
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .cornerRadiusDp(15)
                .borderWidthDp(0)
                .oval(false)
                .build();

        Picasso.get()
                .load(image)
                .fit()
                .transform(transformation)
                .into(view);
    }

    //处理圆形图片
    public void setRounded(ImageView view, String url) {
        Picasso.get()
                .load(url)
                .transform(new CircleTransform())
                .into(view);
    }

}
