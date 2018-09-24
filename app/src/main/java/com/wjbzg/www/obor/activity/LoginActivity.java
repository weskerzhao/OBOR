package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.LoginDTO;
import com.wjbzg.www.obor.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //注册返回码
    public static final int REGISTER = 1;
    public static final int RESET_PWD = 2;
    //标题
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    //注册按钮
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    //登录按钮
    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;
    //回退按钮
    @BindView(R.id.reback)
    ImageView reback;
    //忘记密码提示
    @BindView(R.id.tv_pwd_prompt)
    TextView tv_pwd_prompt;
    //手机号码
    @BindView(R.id.et_phone)
    EditText etPhone;
    //密码
    @BindView(R.id.et_pwd)
    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tvTittle.setText("账户登录");
        tvPublish.setText("注册");
        tv_pwd_prompt.setVisibility(View.VISIBLE);
        init();
    }

    private void init(){
        etPhone.setText(getIntent().getStringExtra("phone"));
        etPwd.setText(getIntent().getStringExtra("psd"));
    }


    //登录
    private void okHttpPost() {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", etPhone.getText().toString());
        map.put("psd", etPwd.getText().toString());
        //发送http请求
        MyOkhttpUtils.okHttpPost(map,"login").execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                new Throwable("请求失败");
            }
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(String response, int id) {
                JSONObject resp = null;
                try {
                    resp = new JSONObject(response);
                    int resultCode = resp.getInt("status");
                    String message = resp.getString("message");
                    if (1 == resultCode) {
                        String data = resp.getString("data");
                        Gson gson = new Gson();
                        LoginDTO loginDTO = gson.fromJson(data, LoginDTO.class);
                        System.out.println(loginDTO);
                        success(loginDTO.getCid());
                    }
                    Toast.makeText(LoginActivity.this,message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //登录成功
    private void success(String cid) {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("cid",cid);
        intent.putExtra("loginFlag",true);
        startActivity(intent);
    }


    @OnClick({R.id.reback, R.id.tv_publish, R.id.email_sign_in_button, R.id.tv_pwd_prompt})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.tv_publish:
                //跳转到注册页面
                intent = new Intent(LoginActivity.this, LoginNewActivity.class);
                startActivityForResult(intent, REGISTER);
                break;

            case R.id.email_sign_in_button:
                if(etPhone.getText() == null || etPhone.getText().toString().trim().isEmpty()){
                    etPhone.requestFocus();
                    etPhone.setError("请输入手机号码");
                }else if(etPwd.getText() == null || etPwd.getText().toString().trim().isEmpty()){
                    etPwd.requestFocus();
                    etPwd.setError("请输入密码");
                }else{
                    okHttpPost();
                }
                break;
            case R.id.tv_pwd_prompt:
                //跳转到密码重置页面
                intent = new Intent(LoginActivity.this, ResetPwdActivity.class);
                startActivityForResult(intent, RESET_PWD);
                break;

        }
    }


}

