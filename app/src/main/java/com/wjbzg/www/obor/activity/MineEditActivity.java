package com.wjbzg.www.obor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.utils.InflateInfo;
import com.wjbzg.www.obor.utils.PicassoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

public class MineEditActivity extends Activity {
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //公司信息
    @BindView(R.id.company_info)
    LinearLayout company_info;
    //设置栏位集合
    @BindView(R.id.set_info_list)
    LinearLayout set_info_list;

    //Picasso工具类
    PicassoUtils picassoUtils = PicassoUtils.getInstance();
    //回退键
    @BindView(R.id.reback)
    ImageView reback;
    //保存按钮
    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_edit);
        ButterKnife.bind(this);
        tv_tittle.setText(R.string.info_field_edit_info);
        infoMatrix(getLayoutInflater());
        setInfo(getLayoutInflater());
        //设置回退键颜色
        reback.setColorFilter(getResources().getColor(R.color.main_menu_white));
    }

    /**
     * 公司信息
     */
    private void infoMatrix(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.company_name_edit, company_info, true);
        LinearLayout linear_tv = view.findViewById(R.id.linear_tv);
        TextView line = view.findViewById(R.id.line);
        line.setVisibility(GONE);
        LinearLayout linear = view.findViewById(R.id.linear);
        linear.setVisibility(GONE);
        linear_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this,);
            }
        });
        ImageView companyImage = view.findViewById(R.id.id_iv_image);
        //设置图片为圆形
        picassoUtils.setRounded(companyImage, "http://imgstore03.cdn.sogou.com/v2/thumb/dl/60d2f4fe0275d790-fbe7539243950f9f-7f669dbeead0ad667f21be96b5efd843.jpg?appid=10150005&referer=http://pic.sogou.com&url=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Db1c873ffd709b3deebbfe460fcbe6cd3%2F71a8b4c27d1ed21be972fa72ab6eddc450da3faf.jpg");
        ImageView imageView = view.findViewById(R.id.id_prompt_image);
        imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));

    }

    /**
     * 设置栏位
     */
    private void setInfo(LayoutInflater inflater) {
        InflateInfo inflateInfo = InflateInfo.getInstance();
        inflateInfo.inflateInfo_Edit(getLayoutInflater(), set_info_list, R.string.edit_field_company_name, R.string.common_hint_name);
        inflateInfo.inflateInfo_Edit(inflater, set_info_list, R.string.edit_field_industry_type, R.string.common_hint_select, true);
        inflateInfo.inflateInfo_Edit(inflater, set_info_list, R.string.edit_field_linkman, R.string.common_hint_fill);
        inflateInfo.inflateInfo_Edit(inflater, set_info_list, R.string.edit_field_phone, R.string.common_hint_fill);
        inflateInfo.inflateInfo_Edit(inflater, set_info_list, R.string.edit_field_email, R.string.common_hint_fill);
        inflateInfo.inflateInfo_Edit(inflater, set_info_list, R.string.edit_field_address, R.string.common_hint_fill);
    }

    @OnClick({R.id.reback, R.id.tv_save})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.reback:
                setResult(RESULT_CANCELED,intent);
                finish();
                break;
            case R.id.tv_save:
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

}
