package com.wjbzg.www.obor.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.LoginActivity;
import com.wjbzg.www.obor.activity.MapActivity;
import com.wjbzg.www.obor.activity.MineEditActivity;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.BaseUserInfo;
import com.wjbzg.www.obor.entity.LoginDTO;
import com.wjbzg.www.obor.utils.ColorFilter;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.InflateInfo;
import com.wjbzg.www.obor.utils.PicassoUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * purpose 我的Fragment
 * author 赵辉
 */
public class MineFragment extends Fragment {
    //Toast持续时间
    public final static int TIME = 1000;

    private BaseUserInfo baseUserInfo;
    //登录flag
    private boolean loginFlag = false;
    //编辑返回码
    public static final int EDIT_CODE = 1;
    //登录返回码
    public static final int LOGIN_CODE = 2;
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //公司信息
    @BindView(R.id.company_info)
    LinearLayout company_info;
    //设置栏位集合
    @BindView(R.id.set_info_list)
    LinearLayout set_info_list;

    //Picasso工具类
    PicassoUtils picassoUtils = PicassoUtils.getInstance();

    public MineFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public MineFragment(boolean loginFlag) {
        this.loginFlag = loginFlag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        tv_tittle.setText(R.string.info_field_mine);
        infoMatrix(inflater);
        setInfo(inflater);
        return view;
    }



    /**
     * 公司信息
     */
    @SuppressLint("PrivateResource")
    private void infoMatrix(LayoutInflater inflater){
        View view = inflater.inflate(R.layout.company_name, company_info,true);
        //公司头像
        ImageView companyImage = view.findViewById(R.id.id_iv_image);
        //设置图片为圆形
        picassoUtils.setRounded(companyImage,"http://imgstore03.cdn.sogou.com/v2/thumb/dl/60d2f4fe0275d790-fbe7539243950f9f-7f669dbeead0ad667f21be96b5efd843.jpg?appid=10150005&referer=http://pic.sogou.com&url=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Db1c873ffd709b3deebbfe460fcbe6cd3%2F71a8b4c27d1ed21be972fa72ab6eddc450da3faf.jpg");
        ImageView imageView = view.findViewById(R.id.id_prompt_image);
        imageView.setColorFilter(view.getResources().getColor(R.color.material_grey_300));
        if(loginFlag == true) {
            okHttpPost(view);


        }else{
            LinearLayout linear = view.findViewById(R.id.linear);
            linear.setVisibility(View.GONE);
            TextView tv_company_name = view.findViewById(R.id.tv_company_name);
            tv_company_name.setText("点击注册/登录");
            TextView tv_industry = view.findViewById(R.id.tv_industry);
            tv_industry.setVisibility(View.GONE);
            ColorFilter colorFilter = ColorFilter.getInstance();
            colorFilter.filter(companyImage);

        }
        LinearLayout linear_tv = view.findViewById(R.id.linear_tv);
        linear_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(loginFlag == true){
                    intent = new Intent(getActivity(),MineEditActivity.class);
                    startActivityForResult(intent, EDIT_CODE);
                }else{
                    intent = new Intent(getActivity(),LoginActivity.class);
                    startActivityForResult(intent, LOGIN_CODE);
                }
            }
        });
    }
    private void setUserInfo(View view) {
        //设置公司头像
        ImageView id_iv_image = view.findViewById(R.id.id_iv_image);
        System.out.println(Constant.SERVER_URL + baseUserInfo.getHead_img());
        //设置图片为圆形
        picassoUtils.setRounded(id_iv_image,Constant.SERVER_URL + baseUserInfo.getHead_img());
        //设置企业名称
        TextView tv_company_name = view.findViewById(R.id.tv_company_name);
        tv_company_name.setText(baseUserInfo.getCompany_name());
        //设置行业类别
        TextView tv_industry = view.findViewById(R.id.tv_industry);
        tv_industry.setText(baseUserInfo.getIndustryName());
    }


    /**
     * 设置栏位
     */
    private void setInfo(LayoutInflater inflater){
        InflateInfo inflateInfo = InflateInfo.getInstance();
        if(loginFlag){
            inflateInfo.inflateInfo(getLayoutInflater(),set_info_list,R.string.common_field_check_info,R.string.common_field_checked);
        }else{
            inflateInfo.inflateInfo(getLayoutInflater(),set_info_list,R.string.common_field_check_info,R.string.common_field_unchecked);
        }
        inflateInfo.inflateInfo(inflater,set_info_list,R.string.common_field_message_center);
        inflateInfo.inflateInfo(inflater,set_info_list,R.string.common_field_my_request);
        inflateInfo.inflateInfo(inflater,set_info_list,R.string.common_field_own_source);
        inflateInfo.inflateInfo(inflater,set_info_list,R.string.common_field_my_attention);
        inflateInfo.inflateInfo(inflater,set_info_list,R.string.common_field_safe_set);
        View view = inflateInfo.inflateInfo(inflater,set_info_list,R.string.common_field_about_our);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });

    }


    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EDIT_CODE:
                if (resultCode == RESULT_OK){
                    Toast.makeText(getActivity(),"保存成功", TIME).show();
                }
            case LOGIN_CODE:
                if (resultCode == RESULT_OK){
                    loginFlag = data.getBooleanExtra("loginFlag",false);
                    getView().clearAnimation();
                    infoMatrix(getLayoutInflater());
                    setInfo(getLayoutInflater());
//                    Toast.makeText(getActivity(),"登陆成功",TIME).show();
                }
        }
    }

    //登录
    private void okHttpPost(final View view) {
        String cid = getActivity().getIntent().getStringExtra("cid");
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        map.put("cid", cid);
        //发送http请求
        MyOkhttpUtils.okHttpPost(map,"baseUserInfo").execute(new StringCallback() {
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
                        Gson gson = new Gson();
                        baseUserInfo = gson.fromJson(data, BaseUserInfo.class);
                        System.out.println(baseUserInfo);
                        setUserInfo(view);
//                        success(loginDTO.getCid());
                    }
                    Toast.makeText(getActivity(),message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
