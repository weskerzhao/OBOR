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
import com.wjbzg.www.obor.entity.IndustryDTO;
import com.wjbzg.www.obor.entity.InfoDescribeDTO;
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
 * purpose 资讯列表
 * author 赵辉
 */
public class InfoListActivity extends AppCompatActivity {
    //Gson
    private Gson gson = new Gson();
    //行业资讯
    private List<InfoDescribeDTO> infoDescribes;
    //需求列表图片
    @BindView(R.id.iv_info_list)
    ImageView iv_info_list;
    //需求列表
    @BindView(R.id.linear_info_list)
    LinearLayout linear_info_list;
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
        setContentView(R.layout.activity_info_list);
        ButterKnife.bind(this);
        reback.setColorFilter(getResources().getColor(R.color.foreground_material_dark));
        picassoUtils.setRoundedRectangle(iv_info_list,R.mipmap.bottom_picture);
//        requestList(getLayoutInflater());
        requestHomeInfo();
    }
    /**
     * 资讯列表
     */
    private void infoList(LayoutInflater inflater){
//        for (int i = 0; i < 10; i++) {
//            View view = inflater.inflate(R.layout.info_category_list, linear_info_list,false);
//            linear_info_list.addView(view);
//        }
        for (int i = 0; i < infoDescribes.size(); i++) {
            View view = inflater.inflate(R.layout.info_list, linear_info_list, false);
            //资讯Id
            view.setTag(infoDescribes.get(i).getInfo_id());
            //图片
            ImageView imageView = view.findViewById(R.id.id_iv_image);
            Picasso.get().load(Constant.SERVER_URL + infoDescribes.get(i).getImg()).into(imageView);
            //资讯title
            TextView tv_new_info = view.findViewById(R.id.tv_new_info);
            tv_new_info.setText(infoDescribes.get(i).getTitle());
            //资讯短评
            TextView tv_short_comment = view.findViewById(R.id.tv_short_comment);
            tv_short_comment.setText(infoDescribes.get(i).getShort_comment());
            //time
            TextView tv_time = view.findViewById(R.id.tv_time);
            tv_time.setText(infoDescribes.get(i).getTime());
            linear_info_list.addView(view);
        }
    }
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
        MyOkhttpUtils.okHttpPost(map, "infoApp").execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                new Throwable(getString(R.string.throwable_request_fail)).printStackTrace();
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
                        //行业资讯
                        getIndustry(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR","Json转换异常");
                }
            }
        });
    }
    //行业资讯
    private void getIndustry(JSONObject info) throws JSONException{
        String industryStr = info.getString("infoList");
        Log.d("TAG",industryStr);
        infoDescribes = gson.fromJson(industryStr, new TypeToken<List<InfoDescribeDTO>>(){}.getType());
        Log.d("TAG",infoDescribes.toString());
        industrys();
    }
    //行业资讯
    private void industrys() {
        infoList(getLayoutInflater());
    }
}
