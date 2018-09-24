package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.CompanyDTO;
import com.wjbzg.www.obor.fragment.HomeFragment;
import com.wjbzg.www.obor.utils.ActivityUtils;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.PicassoUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * purpose 企业列表
 * author 赵辉
 */
public class CompanyListActivity extends AppCompatActivity {
    //Gson
    private Gson gson = new Gson();
    //企业列表图片
    @BindView(R.id.iv_company_list)
    ImageView iv_company_list;
    //企业列表
    @BindView(R.id.linear_company_list)
    LinearLayout linear_company_list;
    //企业列表
    private List<CompanyDTO> companys;
    //回退键
    @BindView(R.id.reback)
    ImageView reback;
    //Picasso工具类
    PicassoUtils picassoUtils = PicassoUtils.getInstance();
    //Activity工具类
    ActivityUtils activityUtils = ActivityUtils.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);
        reback.setColorFilter(getResources().getColor(R.color.foreground_material_dark));
        picassoUtils.setRoundedRectangle(iv_company_list,R.mipmap.bottom_picture);
//      companyList(getLayoutInflater());
        requestHomeInfo();
    }

//    /**
//     * 需求列表
//     */
//    private void companyList(LayoutInflater inflater) {
//        for (int i = 0; i < 10; i++) {
//            View view = inflater.inflate(R.layout.company_list, linear_company_list, false);
//            linear_company_list.addView(view);
//        }
//    }

    @OnClick({R.id.reback})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.reback:
                Intent intent = new Intent();
                setResult(HomeFragment.COMPANY_CODE_SERCH,intent);
                activityUtils.hideKeyboard(this);
                finish();
                break;

        }
    }

    //Http请求首页信息
    private void requestHomeInfo(){
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "home").execute(new StringCallback() {
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
                    Log.d("TAG",message);
                    if (1 == resultCode) {
                        String data = resp.getString("data");
                        JSONObject info = new JSONObject(data);
                        //企业列表
                        getCompany(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR","Json转换异常");
                }
            }
        });
    }
    //企业列表
    private void getCompany( JSONObject info) throws JSONException {
        String companysStr = info.getString("companyList");
        Log.d("TAG",companysStr);
        companys = gson.fromJson(companysStr, new TypeToken<List<CompanyDTO>>(){}.getType());
        Log.d("TAG",companys.toString());
        companys();
    }
    //企业列表
    private void companys() {
        companyList(getLayoutInflater());
    }
    /**
     * 企业列表
     */
    private void companyList(LayoutInflater inflater){
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, linear_company_list,false);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);

        tv_company_list.setText(R.string.home_field_company_list);
        //查看更多
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setVisibility(View.GONE);

        linear_company_list.addView(list_tittle);
        for (int i = 0; i < companys.size(); i++) {
            View view = inflater.inflate(R.layout.company_list, linear_company_list,false);
            //图片
            ImageView imageView = view.findViewById(R.id.id_iv_image);
            Picasso.get().load(Constant.SERVER_URL + companys.get(i).getImg_company()).into(imageView);
            //公司名称
            TextView tv_company_name = view.findViewById(R.id.tv_company_name);
            tv_company_name.setText(companys.get(i).getCompany_name());
            //行业类型
            TextView tv_industry = view.findViewById(R.id.tv_industry);
            tv_industry.setText(companys.get(i).getIndustryName());
            //联系地址
            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(companys.get(i).getAddress());

            //查看详情
            TextView tv_look_detail = view.findViewById(R.id.tv_look_detail);

            final int finalI = i;
            tv_look_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CompanyListActivity.this,ComponyInfoDetailActivity.class);
                    intent.putExtra("company_id",companys.get(finalI).getCompany_id());
                    Log.d("TAG",companys.get(finalI).getCompany_id());
                    startActivity(intent);
                }
            });

            linear_company_list.addView(view);
        }
    }
}
