package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.CompanyDetaiDTO;
import com.wjbzg.www.obor.entity.RequestDTO;
import com.wjbzg.www.obor.fragment.InfonationFragment;
import com.wjbzg.www.obor.utils.Constant;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ResourceInfoDetailActivity extends AppCompatActivity {

    //Gson
    private Gson gson = new Gson();
    //需求列表
    private RequestDTO requestDTO;
    //公司详情
    private CompanyDetaiDTO companyDetaiDTO;

    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.reback)
    ImageView reback;

    InfonationFragment infonationFragment;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.linear_content)
    LinearLayout linear_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_info_detail);
        ButterKnife.bind(this);
        init();
        requestHomeInfo();

    }

    private void init() {
        Picasso.get()
                .load("http://imgstore03.cdn.sogou.com/v2/thumb/dl/60d2f4fe0275d790-fbe7539243950f9f-7f669dbeead0ad667f21be96b5efd843.jpg?appid=10150005&referer=http://pic.sogou.com&url=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Db1c873ffd709b3deebbfe460fcbe6cd3%2F71a8b4c27d1ed21be972fa72ab6eddc450da3faf.jpg")
                .into(iv_image);
        reback.setColorFilter(getResources().getColor(R.color.main_menu_white));
    }

    @OnClick(R.id.reback)
    public void onViewClicked() {
        finish();
    }

    //设置页签
    public void setTabsUnselect(TextView v) {
        textView1.setBackground(getResources().getDrawable(R.color.main_menu_color_check));
        textView1.setTextColor(getResources().getColor(R.color.main_menu_white));
        textView2.setBackground(getResources().getDrawable(R.color.main_menu_color_check));
        textView2.setTextColor(getResources().getColor(R.color.main_menu_white));
        v.setBackground(getResources().getDrawable(R.drawable.rectangle_bootom));
        v.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    /**
     * 资讯
     */
    private void InformationFragment() {
        infonationFragment = new InfonationFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, infonationFragment);
        fragmentTransaction.commit();
    }

    //

    @OnClick({R.id.textView1, R.id.textView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView1:
                linear_content.removeAllViews();
//                companyDetai();
                requests();
                setTabsUnselect(textView1);
                break;
            case R.id.textView2:
                linear_content.removeAllViews();
                companyDetai();
                setTabsUnselect(textView2);
                break;
        }
    }

    //Http公司企业详情信息
    private void requestHomeInfo() {
        //准备资源参数
        HashMap<String, String> map = new HashMap<>();
        map.put("rid",getIntent().getStringExtra("rid"));
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "resDetails").execute(new StringCallback() {
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
                    Log.d("TAG", message);
                    if (1 == resultCode) {
                        String data = resp.getString("data");
                        JSONObject info = new JSONObject(data);
                        //资源详情
                        getRequestsDetail(info);
                        //公司详情
                        getCompanyDetail(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "Json转换异常");
                }
            }
        });
    }

    /**
     * 需求列表
     */
    private void request(LayoutInflater inflater) {
            View view = inflater.inflate(R.layout.request_introduction, linear_content, false);
            //需求标题
            TextView tv_request_name = view.findViewById(R.id.tv_request_name);
            tv_request_name.setText(requestDTO.getTitle());
            //行业类型
            TextView tv_industry = view.findViewById(R.id.tv_industry);
            tv_industry.setText(requestDTO.getTypeName());
            //合作区域
            TextView tv_cooperation_address = view.findViewById(R.id.tv_cooperation_address);
            tv_cooperation_address.setText(requestDTO.getRegionName());
            //合作截止时间
            TextView tv_cooperation_time = view.findViewById(R.id.tv_cooperation_time);
            tv_cooperation_time.setText(requestDTO.getEndTime());
            //联系地址
            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(requestDTO.getAddress());
            //联系电话
            TextView tv_tel = view.findViewById(R.id.tv_tel);
            tv_tel.setText(requestDTO.getPhone());
            //公司简介
            TextView tv_introduction = view.findViewById(R.id.tv_introduction);
            tv_introduction.setText(requestDTO.getContent());
            //公司简介
            TextView tv_provide = view.findViewById(R.id.tv_provide);
            tv_provide.setText(requestDTO.getProvide());
            //公司简介
            TextView tv_request = view.findViewById(R.id.tv_request);
            tv_request.setText(requestDTO.getNeed());

            linear_content.addView(view);
    }

    //需求列表
    private void getRequestsDetail(JSONObject info) throws JSONException {

        String needsStr = info.getString("resDetails");
        Log.d("TAG", needsStr);
        requestDTO = gson.fromJson(needsStr, RequestDTO.class);
        Log.d("TAG", requestDTO.toString());
        requests();

    }

    //需求列表
    private void requests() {
        request(getLayoutInflater());
    }

    //企业简介
    private void getCompanyDetail( JSONObject info) throws JSONException {
        String infoStr = info.getString("companyDetails");
        Log.d("TAG",infoStr);
        companyDetaiDTO = gson.fromJson(infoStr, CompanyDetaiDTO.class);
        Log.d("TAG",companyDetaiDTO.toString());

//        companyDetai();
    }

    private void companyDetai() {
        companyDetai(getLayoutInflater());
    }

    //公司详情
    private void companyDetai(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.company_introduction, linear_content,false);
        //图片
        ImageView iv_image = view.findViewById(R.id.iv_image);
        Picasso.get().load(Constant.SERVER_URL + companyDetaiDTO.getImg_logo()).into(iv_image);
        //公司名
        TextView tv_company_listName = view.findViewById(R.id.tv_company_listName);
        tv_company_listName.setText(companyDetaiDTO.getCompany_name());
        //行业类别
        TextView tv_industry = view.findViewById(R.id.tv_industry);
        tv_industry.setText(companyDetaiDTO.getIndustryName());

        //联系地址
        TextView tv_address = view.findViewById(R.id.tv_address);
        tv_address.setText(companyDetaiDTO.getAddress());

        //联系电话
        TextView tv_tel = view.findViewById(R.id.tv_tel);
        tv_tel.setText(companyDetaiDTO.getPhone());

        //公司简介
        TextView tv_introduction = view.findViewById(R.id.tv_introduction);
        tv_introduction.setText(companyDetaiDTO.getContent());

        linear_content.addView(view);
    }

}
