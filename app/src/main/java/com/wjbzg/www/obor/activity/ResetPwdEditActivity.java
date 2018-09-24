package com.wjbzg.www.obor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.utils.InflateInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * purpose:重置密码编辑
 * author: 赵辉
 */
public class ResetPwdEditActivity extends AppCompatActivity {

    //回调函数识别码
    public static final int REQUEST_CODE = 100;
    // 标题
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    //登录按钮
    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;
    //回退按钮
    @BindView(R.id.reback)
    ImageView reback;
//    //设置栏位
//    @BindView(R.id.set_info_list)
//    LinearLayout set_info_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd_edit_login);
        ButterKnife.bind(this);
        tvTittle.setText(R.string.register_field_new_reset_pwd);
//        setInfo(getLayoutInflater());
    }

//    @OnClick(R.id.email_sign_in_button)
//    public void onViewClicked() {
//        //跳转到首页
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
//    }

    @OnClick({R.id.reback, R.id.email_sign_in_button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.email_sign_in_button:
                //跳转到首页
                intent = new Intent(this, ResetPwdSuccessActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
//                intent = new Intent();
//                setResult(RESULT_OK, intent);
//                finish();
                break;
        }
    }

    /**
     * 设置栏位
     */
//    private void setInfo(LayoutInflater inflater) {
//        InflateInfo inflateInfo = InflateInfo.getInstance();
//        inflateInfo.inflateInfoRegister_Edit(inflater, set_info_list, R.string.register_field_new_pwd,R.string.register_hint_pwd, false);
//        inflateInfo.inflateInfoRegister_Edit(inflater, set_info_list, R.string.register_field_repwd,R.string.register_hint_repwd_confirm, false);
//
//    }
}

