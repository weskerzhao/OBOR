package com.wjbzg.www.obor.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;

/**
 * Created by 赵辉 on 2018\8\21 0021.
 */

public class ColorFilter {

    private final static ColorFilter INSTANCE = new ColorFilter();

    private ColorFilter(){}

    public static ColorFilter getInstance(){
          return INSTANCE;
    }

    public void filter(ImageView image){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);//饱和度 0灰色 100过度彩色，50正常
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        image.setColorFilter(filter);

    }
    // 恢复
    public void refilter(ImageView image){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(50);//饱和度 0灰色 100过度彩色，50正常
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        image.setColorFilter(filter);

    }
}
