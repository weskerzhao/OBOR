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
import com.wjbzg.www.obor.entity.InfoDescribeDTO;
import com.wjbzg.www.obor.entity.ResourceDTO;
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
 * purpose 政策法规列表
 * author 赵辉
 */
public class RegulationListActivity extends AppCompatActivity {
    //Gson
    private Gson gson = new Gson();
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //热门资讯列表
    @BindView(R.id.hot_info_list)
    LinearLayout hot_info_list;
    //最新资讯列表
    @BindView(R.id.new_info_list)
    LinearLayout new_info_list;
    //回退键
    @BindView(R.id.reback)
    ImageView reback;
    //资讯列表
    List<InfoDescribeDTO> InfoDescribeDTOs;
    //Activity工具类
    ActivityUtils activityUtils = ActivityUtils.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulation_list);
        ButterKnife.bind(this);
        tv_tittle.setText(R.string.info_field_regulations);
        reback.setColorFilter(getResources().getColor(R.color.foreground_material_dark));
//        hotInfoList(getLayoutInflater());
//        newInfoList(getLayoutInflater());
        requestHomeInfo();
    }
    /**
     * 热门资讯列表
     */
//    private void hotInfoList(LayoutInflater inflater){
//        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, hot_info_list,true);
//        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
//        tv_company_list.setText(R.string.info_field_hot_info);
//        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
//        tv_look_over.setHint(null);
//        for (int i = 0; i < 3; i++) {
//            View view = inflater.inflate(R.layout.info_list, hot_info_list,false);
//            hot_info_list.addView(view);
//        }
//    }

    /**
     * 最新资讯列表
     */
    private void newInfoList(LayoutInflater inflater){
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, new_info_list,true);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
        tv_company_list.setText(R.string.info_field_new_info);
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setHint(R.string.home_field_look_over_more);
        for (int i = 0; i < InfoDescribeDTOs.size(); i++) {
            View view = inflater.inflate(R.layout.info_list, new_info_list,false);
            //图片
            ImageView id_iv_image = view.findViewById(R.id.id_iv_image);
            Picasso.get().load(Constant.SERVER_URL + InfoDescribeDTOs.get(i).getImg()).into(id_iv_image);
            //title
            TextView tv_new_info = view.findViewById(R.id.tv_new_info);
            tv_new_info.setText(InfoDescribeDTOs.get(i).getTitle());
            //评语
            TextView tv_short_comment = view.findViewById(R.id.tv_short_comment);
            tv_short_comment.setText(InfoDescribeDTOs.get(i).getShort_comment());
            //time
            TextView tv_time = view.findViewById(R.id.tv_time);
            tv_time.setText(InfoDescribeDTOs.get(i).getTime());
            new_info_list.addView(view);
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

    //Http公司企业详情信息
    private void requestHomeInfo() {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "infoList").execute(new StringCallback() {
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
                        getNewInfoList(info);


                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "Json转换异常");
                }
            }
        });
    }

    public void getNewInfoList(JSONObject info) throws JSONException {
        String infoStr = info.getString("infoList");
        Log.d("TAG",infoStr);
        InfoDescribeDTOs = gson.fromJson(infoStr, new TypeToken<List<InfoDescribeDTO>>(){}.getType());
        Log.d("TAG",InfoDescribeDTOs.toString());
        //资讯列表
        newInfoList(getLayoutInflater());
    }

}
