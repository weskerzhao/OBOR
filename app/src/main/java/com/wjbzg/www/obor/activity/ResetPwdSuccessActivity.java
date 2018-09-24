package com.wjbzg.www.obor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjbzg.www.obor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPwdSuccessActivity extends Activity {
    //回退
    @BindView(R.id.reback)
    ImageView reback;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.bt_back2home)
    Button btBack2home;
    @BindView(R.id.bt_back2login)
    Button btBack2login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd_success);
        ButterKnife.bind(this);
        tvTittle.setText(R.string.register_field_new_pwd_success);
    }

    @OnClick({R.id.reback, R.id.bt_back2home, R.id.bt_back2login})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.bt_back2home:
                //返回首页
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_back2login:
                //返回登录
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
