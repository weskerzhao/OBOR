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
import com.wjbzg.www.obor.entity.ResourceDTO;
import com.wjbzg.www.obor.fragment.DiscoverFragment;
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
 * purpose 发现fragment资源列表
 * author 赵辉
 */
public class DiscoverSourceListActivity extends AppCompatActivity {
    //Gson
    private Gson gson = new Gson();
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //资源列表
    @BindView(R.id.linear_source_list)
    LinearLayout linear_source_list;
    //回退键
    @BindView(R.id.reback)
    ImageView reback;
    //Picasso工具类
    PicassoUtils picassoUtils = PicassoUtils.getInstance();
    //Activity工具类
    ActivityUtils activityUtils = ActivityUtils.getInstance();
    //资源列表
    private List<ResourceDTO> resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_source_list);
        ButterKnife.bind(this);
        tv_tittle.setText(R.string.home_field_source_list);
        reback.setColorFilter(getResources().getColor(R.color.foreground_material_dark));
//        requestList(getLayoutInflater());
        requestHomeInfo();
    }
    /**
     * 需求列表
     */
    private void requestList(LayoutInflater inflater){
//        for (int i = 0; i < 10; i++) {
//            View view = inflater.inflate(R.layout.source_list, linear_source_list,false);
//            linear_source_list.addView(view);
//        }
        for (int i = 0; i < resources.size(); i++) {
            View view = inflater.inflate(R.layout.source_list, linear_source_list,false);
            //图片
            ImageView imageView = view.findViewById(R.id.id_iv_image);
            Picasso.get().load(Constant.SERVER_URL + resources.get(i).getImg1()).into(imageView);
            //需求标题
            TextView tv_resource_name = view.findViewById(R.id.tv_resource_name);
            tv_resource_name.setText(resources.get(i).getTitle());
            //资源类型
            TextView tv_industry = view.findViewById(R.id.tv_industry);
            tv_industry.setText(resources.get(i).getTypeName());
            //合作区域
            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(resources.get(i).getRegionName());
            tv_address.setTag(resources.get(i).getRegion());
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DiscoverSourceListActivity.this,ResourceInfoDetailActivity.class);
                    intent.putExtra("rid",resources.get(finalI).getRid());

                    startActivity(intent);
                }
            });
            linear_source_list.addView(view);
        }
    }
    @OnClick({R.id.reback})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.reback:
                Intent intent = new Intent();
                setResult(DiscoverFragment.SOURCE_CODE,intent);
                activityUtils.hideKeyboard(this);
                finish();
                break;

        }
    }

    //Http资源信息
    private void requestHomeInfo() {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "resList").execute(new StringCallback() {
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
                        getResource(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "Json转换异常");
                }
            }
        });
    }

    //资源列表
    private void getResource(JSONObject info) throws JSONException {
        String resListStr = info.getString("resList");
        Log.d("TAG",resListStr);
        resources = gson.fromJson(resListStr, new TypeToken<List<ResourceDTO>>(){}.getType());
        Log.d("TAG",resources.toString());
        requestList(getLayoutInflater());
    }
}
