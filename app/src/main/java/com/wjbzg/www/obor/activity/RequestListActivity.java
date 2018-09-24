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
import com.wjbzg.www.obor.entity.RequestDTO;
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
 * purpose 需求列表
 * author 赵辉
 */
public class RequestListActivity extends AppCompatActivity {
    //Gson
    private Gson gson = new Gson();
    //需求列表
    private List<RequestDTO> requests;
    //需求列表图片
    @BindView(R.id.iv_request_list)
    ImageView iv_request_list;
    //需求列表
    @BindView(R.id.linear_request_list)
    LinearLayout linear_request_list;
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
        setContentView(R.layout.activity_request_list);
        ButterKnife.bind(this);
        reback.setColorFilter(getResources().getColor(R.color.foreground_material_dark));
        picassoUtils.setRoundedRectangle(iv_request_list, R.mipmap.bottom_picture);
//        requestList(getLayoutInflater());
        requestHomeInfo();
    }

    /**
     * 需求列表
     */
    private void requestList(LayoutInflater inflater) {
//        for (int i = 0; i < 10; i++) {
//            View view = inflater.inflate(R.layout.request_list, linear_request_list, false);
//            linear_request_list.addView(view);
//        }
        for (int i = 0; i < requests.size(); i++) {
            View view = inflater.inflate(R.layout.request_list, linear_request_list,false);
            //图片
            ImageView imageView = view.findViewById(R.id.id_iv_image);
            Picasso.get().load(Constant.SERVER_URL + requests.get(i).getImg1()).into(imageView);
            //需求标题
            TextView tv_request_title = view.findViewById(R.id.tv_request_title);
            tv_request_title.setText(requests.get(i).getTitle());
            //行业类型
            TextView tv_industry = view.findViewById(R.id.tv_industry);
            tv_industry.setText(requests.get(i).getTypeName());
            //时间
            TextView tv_time = view.findViewById(R.id.tv_time);
            tv_time.setText(requests.get(i).getTime());
            linear_request_list.addView(view);
        }
    }

    @OnClick({R.id.reback})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.reback:
                Intent intent = new Intent();
                setResult(HomeFragment.COMPANY_CODE_SERCH, intent);
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
                        //需求列表
                        getRequests(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR","Json转换异常");
                }
            }
        });
    }
    //需求列表
    private void getRequests( JSONObject info) throws JSONException {

        String needsStr = info.getString("needs");
        Log.d("TAG",needsStr);
        requests = gson.fromJson(needsStr, new TypeToken<List<RequestDTO>>(){}.getType());
        Log.d("TAG",requests.toString());
        requests();
    }
    //需求列表
    private void requests() {
        requestList(getLayoutInflater());
    }
}
