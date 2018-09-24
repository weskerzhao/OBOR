package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.utils.Constant;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class LoginNewActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    //回调函数识别码
    public static final int REQUEST_CODE = 100;
    public static final int CODE = 1000;
    //    标题
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    //登录按钮
    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;
    //回退按钮
    @BindView(R.id.reback)
    ImageView reback;
    //验证码
    @BindView(R.id.verification)
    TextView verification;
    @BindView(R.id.et_phone)
    EditText etPhone;
    //验证码
    String verificationStr;
    @BindView(R.id.et_verification)
    EditText et_verification;
    @BindView(R.id.linear_verification)
    LinearLayout linear_verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
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

    @OnClick({R.id.reback, R.id.email_sign_in_button, R.id.verification})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.email_sign_in_button:
                if (etPhone.getText() == null || etPhone.getText().toString().trim().isEmpty()) {
                    etPhone.requestFocus();
                    etPhone.setError("请输入手机号码");
                }
                else if (et_verification.getText() == null || et_verification.getText().toString().trim().isEmpty()) {
                    et_verification.requestFocus();
                    et_verification.setError("请输入验证码");
                } else if (et_verification.getText().toString().trim().equals(verificationStr)){
                    intent = new Intent(this, LoginNewEditActivity.class);
                    intent.putExtra("et_phone",etPhone.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    et_verification.requestFocus();
                    et_verification.setError("验证码输入错误");
                }
//                intent = new Intent();
//                setResult(RESULT_OK,intent);
//                finish();
                break;
            case R.id.verification:
                onClickVerifica();
                getVerification();
                break;
        }
    }

    @OnClick()
    public void onViewClicked() {
    }

    private void onClickVerifica() {
        final Handler handler = new TestHandler(this);
        Message message = Message.obtain();
        message.arg1 = 60000;
        message.what = CODE;
        handler.sendMessageDelayed(message, 0);
    }

    public static class TestHandler extends Handler {

        public final WeakReference<LoginNewActivity> mWeakReference;

        public TestHandler(LoginNewActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginNewActivity activity = mWeakReference.get();
            if (msg.what == CODE) {
                int time = msg.arg1;
                activity.verification.setText(String.valueOf(time / 1000) + "s");
                Message message = Message.obtain();
                message.what = CODE;
                message.arg1 = time - 1000;

                if (time > 0) {
                    sendMessageDelayed(message, 1000);
                } else {
                    activity.verification.setText(R.string.register_field_send_fill);
                }
            }
        }
    }

    private void getVerification() {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", etPhone.getText().toString());
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "sendMsg").execute(new StringCallback() {
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
                        JSONObject verifi = new JSONObject(data);
                        verificationStr = verifi.getString("code");
//                        Gson gson = new Gson();
//                        String verificationStr = gson.fromJson(data,Object);
//                        System.out.println(loginDTO);
                        success();
                    }
                    Toast.makeText(LoginNewActivity.this, message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void success() {

    }
}

