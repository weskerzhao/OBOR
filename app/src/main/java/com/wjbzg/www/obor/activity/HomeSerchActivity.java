package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.CompanyDTO;
import com.wjbzg.www.obor.entity.RequestDTO;
import com.wjbzg.www.obor.fragment.HomeFragment;
import com.wjbzg.www.obor.utils.ActivityUtils;
import com.wjbzg.www.obor.utils.Constant;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * purpose 首页搜索
 * author 赵辉
 */
public class HomeSerchActivity extends AppCompatActivity {
    //Gson
    private Gson gson = new Gson();
    //下拉框
    @BindView(R.id.toolbar_serch_spinner)
    Spinner toolbar_serch_spinner;
    //搜索框
    @BindView(R.id.serch)
    SearchView serch;
    //取消
    @BindView(R.id.cancel)
    TextView cancel;
    //桩位
    @BindView(R.id.pile_list)
    LinearLayout pile_list;
    //公司列表集合
    List<CompanyDTO> companys;

    //Activity工具类
    ActivityUtils activityUtils = ActivityUtils.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_serch);
        ButterKnife.bind(this);
        serchType();
//        pileList(this.getLayoutInflater());
        activityUtils.setSerchView(serch,getResources().getDimension(R.dimen.main_menu_text_size2));
        bindSerchView();
        requestHomeInfo();

    }

    public void bindSerchView(){
        serch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TAG","点击搜索");
                requestHomeInfo();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                requestHomeInfo();
                return false;
            }
        });
    }

    @OnClick({R.id.cancel})
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.cancel:
                Intent intent = new Intent();
                setResult(HomeFragment.REQUEST_CODE_SERCH,intent);
                activityUtils.hideKeyboard(this);
                finish();
                break;
            default:
                break;

        }
    }

    /**
     * 填充搜索类型
     */
    private void serchType(){
        //适配器
        ArrayAdapter<String> arr_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data());
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        toolbar_serch_spinner.setAdapter(arr_adapter);

    }

    /**
     * 搜索类型
     */
    private ArrayList<String> data(){
        //数据
        ArrayList<String> data_list = new ArrayList<>();
        data_list.add("综合");
        data_list.add("名称");
        data_list.add("行业");
        return data_list;
    }


    //Http请求首页信息
    private void requestHomeInfo() {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        map.put("search",serch.getQuery().toString());
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "companyList").execute(new StringCallback() {
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
                        //得到公司列表
                        getCompanys(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "Json转换异常");
                }
            }
        });
    }

    private void getCompanys(JSONObject info) throws JSONException {
        String needsStr = info.getString("companyList");
        Log.d("TAG", needsStr);
        companys = gson.fromJson(needsStr, new TypeToken<List<CompanyDTO>>() {
        }.getType());
        Log.d("TAG", companys.toString());
        companys(getLayoutInflater());
    }
    /**
     * 桩位列表
     */
    private void companys(LayoutInflater inflater){
        pile_list.removeAllViews();
        for (int i = 0; i < companys.size(); i++) {
            View view = inflater.inflate(R.layout.company_list, pile_list,false);
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
                    Intent intent = new Intent(HomeSerchActivity.this,ComponyInfoDetailActivity.class);
                    intent.putExtra("company_id",companys.get(finalI).getCompany_id());
                    startActivity(intent);
                }
            });
            pile_list.addView(view);
        }
    }
}
