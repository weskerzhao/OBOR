package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.IndustryDTO;
import com.wjbzg.www.obor.fragment.dialogType.SingleChoiceDialogFragment;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.InflateInfo;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class LoginNewEditActivity extends AppCompatActivity {


    //回调函数识别码
    public static final int REQUEST_CODE = 100;

    //    标题
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    //登录按钮
    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;
    //回退按钮
    @BindView(R.id.reback)
    ImageView reback;
    //右上角控件
    @BindView(R.id.tv_publish)
    TextView tv_publish;
    //设置栏位
    @BindView(R.id.set_info_list)
    LinearLayout set_info_list;
    private List<IndustryDTO> industrys;
    //手机号
    private EditText et_phone;
    private String phone;
    //企业名
    private EditText et_company_name;
    //企业行业id
    private TextView et_industry;
    private String industry_id;
    //psd
    private EditText et_psd;
    private EditText et_repsd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_login);
        ButterKnife.bind(this);
        getIntentMothod();
        dropListIndustry();
        tv_publish.setVisibility(View.INVISIBLE);
        tvTittle.setText(R.string.register_field_tittle);
        setInfo(getLayoutInflater());
    }

    private void getIntentMothod(){
        Intent intent = getIntent();
        phone = intent.getStringExtra("et_phone");
    }

    @OnClick({R.id.reback, R.id.email_sign_in_button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.email_sign_in_button:
                if(et_company_name == null || et_company_name.getText().toString().isEmpty()){
                    et_company_name.requestFocus();
                    et_company_name.setError("请输入企业名称");
                } else if (et_industry == null || et_industry.getText().toString().isEmpty()){
                    et_industry.setFocusableInTouchMode(true);
                    et_industry.setFocusable(true);
                    et_industry.requestFocus();
                    et_industry.setError("请输入所属行业");
                } else if (et_psd == null || et_psd.getText().toString().isEmpty()){
                    et_psd.requestFocus();
                    et_psd.setError("请输入密码");
                } else if (et_repsd == null || et_repsd.getText().toString().isEmpty()){
                    et_repsd.requestFocus();
                    et_repsd.setError("请确认输入密码");
                } else if (!et_repsd.getText().toString().equals(et_psd.getText().toString())){
                    et_repsd.requestFocus();
                    et_repsd.setError("密码不一致");
                }else {
                    confirmRegist();
                }
                break;
        }
    }

    /**
     * 设置栏位
     */
    private void setInfo(LayoutInflater inflater) {
        View view;
        EditText editText;
        InflateInfo inflateInfo = InflateInfo.getInstance();
        view  = inflateInfo.inflateInfoRegister_Edit(getLayoutInflater(), set_info_list, R.string.register_field_company_name,R.string.register_hint_company_name, false);
        et_company_name = view.findViewById(R.id.tx2);
        view = inflateInfo.inflateInfoRegister_Edit_select(inflater, set_info_list, R.string.register_field_industry,R.string.register_hint_industry_select, true);
        et_industry = view.findViewById(R.id.tx2);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialogFragment(v,industrys);
            }
        });

        view = inflateInfo.inflateInfoRegister_Edit(inflater, set_info_list, R.string.register_field_pwd,R.string.register_hint_pwd, false);
        et_psd = view.findViewById(R.id.tx2);
        et_psd.setMaxLines(13);
        view = inflateInfo.inflateInfoRegister_Edit(inflater, set_info_list, R.string.register_field_repwd,R.string.register_hint_repwd, false);
        et_repsd = view.findViewById(R.id.tx2);
        et_psd.setMaxLines(13);
    }

    //请求下拉列表
    private void dropListIndustry(){
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
//        map.put("phone", etPhone.getText().toString());
//        map.put("company_name", etPhone.getText().toString());
//        map.put("industry", etPhone.getText().toString());
//        map.put("psd", etPhone.getText().toString());
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "industryList").execute(new StringCallback() {
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
                    String data = resp.getString("data");
                    if (1 == resultCode) {
                        JSONObject jsonData = new JSONObject(data);
                        String listStr = jsonData.getString("list");
                        IndustryDTO[] array = new Gson().fromJson(listStr,IndustryDTO[].class);
                        industrys = Arrays.asList(array);
                        success();
                    }
                    Toast.makeText(LoginNewEditActivity.this, message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //请求注册
    private void confirmRegist(){
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        //手机号
        map.put("phone", phone);
        //企业名
        map.put("company_name", et_company_name.getText().toString());
        //企业行业id
        map.put("industry", industry_id);
        //psd
        map.put("psd", et_psd.getText().toString());
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "reg").execute(new StringCallback() {
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
                        //跳转到首页
                        Intent intent = new Intent(LoginNewEditActivity.this, LoginActivity.class);
                        intent.putExtra("phone",phone);
                        intent.putExtra("psd",et_repsd.getText().toString());
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    Toast.makeText(LoginNewEditActivity.this, message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void success() {
//        industrys
    }

    //单选框Dialog
    private int index;
    public void showSingleChoiceDialogFragment(final View view, final List<IndustryDTO> industrys) {
        SingleChoiceDialogFragment singleChoiceDialogFragment = new SingleChoiceDialogFragment(index);
        String[] items = new String[industrys.size()];
        for (int i = 0; i < industrys.size(); i++) {
            items[i] = industrys.get(i).getIndustryName();
        }

        singleChoiceDialogFragment.show("Hi,你好", items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView tx2= view.findViewById(R.id.tx2);
                tx2.setText(industrys.get(index).getIndustryName());
//                tx2.setTag(industrys.get(index).getIndustry_id());
                industry_id = industrys.get(index).getIndustry_id();

                Toast.makeText(LoginNewEditActivity.this, industrys.get(index).getIndustry_id(), Toast.LENGTH_SHORT).show();
            }
        }, getFragmentManager());
    }
}

