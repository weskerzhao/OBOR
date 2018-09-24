package com.wjbzg.www.obor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjbzg.www.obor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * purpose:重置密码
 * author: 赵辉
 */
public class ResetPwdActivity extends AppCompatActivity {

    public static final int RESET_PWD_EDIT = 1;
    //    标题
//    @BindView(R.id.tv_tittle)
//    TextView tvTittle;
    //登录按钮
    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;
    //回退按钮
    @BindView(R.id.reback)
    ImageView reback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd_login);
        ButterKnife.bind(this);
//        tvTittle.setText("账户登录");
//        tvPublish.setText("注册");
    }

//    @OnClick(R.id.email_sign_in_button)
//    public void onViewClicked() {
//        //跳转到首页
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
//    }

    @OnClick({R.id.reback,R.id.email_sign_in_button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.email_sign_in_button:
                //跳转到首页
                intent = new Intent(this, ResetPwdEditActivity.class);
                startActivityForResult(intent, RESET_PWD_EDIT);
//                intent = new Intent();
//                setResult(RESULT_OK,intent);
//                finish();
                break;
        }
    }
}

