package com.wjbzg.www.obor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.wjbzg.www.obor.adapter.MyArrayAdapter;
import com.wjbzg.www.obor.entity.IndustryDTO;
import com.wjbzg.www.obor.entity.RequestDTO;
import com.wjbzg.www.obor.fragment.DiscoverFragment;
import com.wjbzg.www.obor.utils.ActivityUtils;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.PicassoUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * purpose 发现fragment需求列表
 * author 赵辉
 */
public class DiscoverRequestListActivity extends AppCompatActivity {
    @BindView(R.id.sp_sort_type)
    Spinner sp_sort_type;
    @BindView(R.id.sp_industry_type)
    Spinner sp_industry_type;
    @BindView(R.id.sp_cooperation_region)
    Spinner sp_cooperation_region;
    //行业集合
    Map<String,String> industryMaps;
    //Gson
    private Gson gson = new Gson();
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //需求列表
    @BindView(R.id.linear_request_list)
    LinearLayout linear_request_list;
    List<RequestDTO> requestDTOs;
    List<IndustryDTO> industrys;
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
        setContentView(R.layout.activity_discover_request_list);
        ButterKnife.bind(this);
        tv_tittle.setText(R.string.home_field_request_list);
        reback.setColorFilter(getResources().getColor(R.color.foreground_material_dark));
        requestHomeInfo(null,true);
        initSpinner();
    }

    private void initSpinner(){
        serchType();
    }


    @OnClick({R.id.reback})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.reback:
                Intent intent = new Intent();
                setResult(DiscoverFragment.REQUEST_CODE, intent);
                activityUtils.hideKeyboard(this);
                finish();
                break;

        }
    }

    //Http需求信息
    private void requestHomeInfo(final String industryType, final boolean init) {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        if(industryType != null){
            map.put("type_id",industryType);
        }
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "needList").execute(new StringCallback() {
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
                        getInfoList(info);
                        if(init == true){
                            getIndustryList(info);
                        }


                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "Json转换异常");
                }
            }
        });
    }

    //行业下拉选
    private void getIndustryList(JSONObject info) throws JSONException {
        String industryList = info.getString("industryList");
        Log.d("TAG", industryList);
        List<IndustryDTO> industrys = gson.fromJson(industryList, new TypeToken<List<IndustryDTO>>() {
        }.getType());
        Log.d("TAG", industrys.toString());
        String[] industryStrs = new String[industrys.size()];
        industryMaps = new HashMap<>();
        for (int i = 0; i < industrys.size(); i++) {
            industryStrs[i] = industrys.get(i).getIndustryName();
            industryMaps.put(industrys.get(i).getIndustryName(),industrys.get(i).getIndustry_id());
        }

        //适配器
        ArrayAdapter<String> arr_adapter = new MyArrayAdapter(this, industryStrs);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_industry_type.setAdapter(arr_adapter);
        sp_industry_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String industry = industryMaps.get(sp_industry_type.getSelectedItem());
                Log.d("TAG","" + industry);
                requestHomeInfo(industry,false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getInfoList(JSONObject info) throws JSONException {
        String needList = info.getString("needList");
        Log.d("TAG", needList);
        requestDTOs = gson.fromJson(needList, new TypeToken<List<RequestDTO>>() {
        }.getType());
        Log.d("TAG", requestDTOs.toString());
        requestList(getLayoutInflater());
    }


    /**
     * 需求列表
     */
    private void requestList(LayoutInflater inflater) {
//        for (int i = 0; i < 10; i++) {
//            View view = inflater.inflate(R.layout.request_list, linear_request_list,false);
//            linear_request_list.addView(view);
//        }
        linear_request_list.removeAllViews();
        for (int i = 0; i < requestDTOs.size(); i++) {
            View view = inflater.inflate(R.layout.request_list, linear_request_list, false);
            //图片
            ImageView imageView = view.findViewById(R.id.id_iv_image);
            Picasso.get().load(Constant.SERVER_URL + requestDTOs.get(i).getImg1()).into(imageView);
            //需求标题
            TextView tv_request_title = view.findViewById(R.id.tv_request_title);
            tv_request_title.setText(requestDTOs.get(i).getTitle());
            //行业类型
            TextView tv_industry = view.findViewById(R.id.tv_industry);
            tv_industry.setText(requestDTOs.get(i).getTypeName());
            //时间
            TextView tv_time = view.findViewById(R.id.tv_time);
            tv_time.setText(requestDTOs.get(i).getTime());

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DiscoverRequestListActivity.this, RequestInfoDetailActivity.class);
                    intent.putExtra("nid", requestDTOs.get(finalI).getNid());
                    startActivity(intent);
                }
            });
            linear_request_list.addView(view);
        }
    }

    /**
     * 排序方式
     */
    private void serchType() {
        //适配器
        ArrayAdapter<String> arr_adapter = new MyArrayAdapter(this, data());
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_sort_type.setAdapter(arr_adapter);

    }

    /**
     * 搜索类型
     */
    private String[] data() {
        //数据
        String[] data_list = new String[2];
        data_list[0] = ("正 序");
        data_list[1] = ("逆 序");
        return data_list;
    }
}
