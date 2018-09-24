package com.wjbzg.www.obor.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.CompanyListActivity;
import com.wjbzg.www.obor.activity.ComponyInfoDetailActivity;
import com.wjbzg.www.obor.activity.HomeSerchActivity;
import com.wjbzg.www.obor.activity.InfoListActivity;
import com.wjbzg.www.obor.activity.LoginActivity;
import com.wjbzg.www.obor.activity.LoginNewEditActivity;
import com.wjbzg.www.obor.activity.MainActivity;
import com.wjbzg.www.obor.activity.RequestInfoDetailActivity;
import com.wjbzg.www.obor.activity.RequestListActivity;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.CompanyDTO;
import com.wjbzg.www.obor.entity.HomeBannerDTO;
import com.wjbzg.www.obor.entity.IndustryDTO;
import com.wjbzg.www.obor.entity.RequestDTO;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.GlideImageLoader;
import com.youth.banner.listener.OnBannerListener;
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
 * purpose 首页初始Fragment
 * author 赵辉
 */
public class HomeFragment extends Fragment implements OnBannerListener{

    /**
     * 搜索返回码
     */
    public static final int REQUEST_CODE_SERCH = 1;
    /**
     * 企业列表返回码
     */
    public static final int COMPANY_CODE_SERCH = 2;
    /**
     * 需求列表返回码
     */
    public static final int REQUEST_CODE_SERCH_ALL = 3;

    //Gson
    private Gson gson = new Gson();
    @BindView(R.id.company_list)
    LinearLayout company_list;
    @BindView(R.id.request_list)
    LinearLayout request_list;
    @BindView(R.id.info_list)
    LinearLayout info_list;
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.serch)
    TextView serch;
    //轮播图
    private List<HomeBannerDTO> banners;
    //企业列表
    private List<CompanyDTO> companys;
    //需求列表
    private List<RequestDTO> requests;
    //行业资讯
    private List<IndustryDTO> industrys;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        requestHomeInfo();
//        companyList(inflater);
//        infoList(inflater);
//        requestList(inflater);
//        banner();
        return view;
    }

    /**
     * 企业列表
     */
    private void companyList(LayoutInflater inflater){
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, company_list,false);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);

        tv_company_list.setText(R.string.home_field_company_list);
        //查看更多
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CompanyListActivity.class);
                startActivityForResult(intent,COMPANY_CODE_SERCH);
            }
        });
        company_list.addView(list_tittle);
        for (int i = 0; i < 3; i++) {
           View view = inflater.inflate(R.layout.company_list, company_list,false);
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
                    Intent intent = new Intent(getActivity(),ComponyInfoDetailActivity.class);
                    intent.putExtra("company_id",companys.get(finalI).getCompany_id());
                    startActivity(intent);
                }
            });
            company_list.addView(view);
        }
    }
    /**
     * 行业资讯
     */
    private void infoList(LayoutInflater inflater){
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, info_list,true);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
        tv_company_list.setText(R.string.home_field_industry_info);
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setHint(R.string.home_field_all_category);
        tv_look_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),InfoListActivity.class);
                startActivityForResult(intent,COMPANY_CODE_SERCH);
            }
        });
        View view = inflater.inflate(R.layout.industry_info_list, info_list,true);
        TextView v;
        v = view.findViewById(R.id.tv0);
        setIndustryInfo(0,v);
        v = view.findViewById(R.id.tv1);
        setIndustryInfo(1,v);
        v = view.findViewById(R.id.tv2);
        setIndustryInfo(2,v);
        v = view.findViewById(R.id.tv3);
        setIndustryInfo(3,v);
    }

    private void setIndustryInfo(int i,TextView v) {
        v.setText(industrys.get(i).getIndustryName());
        v.setTag(industrys.get(i).getIndustry_id());
    }

    /**
     * 需求列表
     */
    private void requestList(LayoutInflater inflater){
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, request_list,false);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
        tv_company_list.setText(R.string.home_field_request_list);

        //查看更多
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RequestListActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SERCH_ALL);
            }
        });
        request_list.addView(list_tittle);
        for (int i = 0; i < requests.size(); i++) {
            View view = inflater.inflate(R.layout.request_list, request_list,false);
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

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),RequestInfoDetailActivity.class);
                    intent.putExtra("nid",requests.get(finalI).getNid());
                    startActivity(intent);
                }
            });

            request_list.addView(view);
        }


    }

    private void banner(){
        List<String> imageUrls = new ArrayList<>();
        for (HomeBannerDTO homeBannerDTO : banners) {
            imageUrls.add(Constant.SERVER_URL + homeBannerDTO.getImg());
            Log.d("imag",Constant.SERVER_URL + homeBannerDTO.getImg());
        }
        banner.setImages(imageUrls)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void OnBannerClick(int position) {

    }
    @OnClick({R.id.serch})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.serch:
                Intent intent = new Intent(getActivity(),HomeSerchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SERCH);
                break;
            default:
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
//                new Throwable(getString(R.string.throwable_request_fail)).printStackTrace();
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
                        //轮播图
                        getBanner(info);
                        //需求列表
                        getRequests(info);
                        //企业列表
                        getCompany(info);
                        //行业资讯
                        getIndustryCategory(info);
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
    private void getIndustryCategory(JSONObject info) throws JSONException{

        String industryStr = info.getString("industryList");
        Log.d("TAG",industryStr);
        industrys = gson.fromJson(industryStr, new TypeToken<List<IndustryDTO>>(){}.getType());
        Log.d("TAG",industrys.toString());
        industrys();
    }
    //需求列表
    private void getRequests( JSONObject info) throws JSONException {

        String needsStr = info.getString("needs");
        Log.d("TAG",needsStr);
        requests = gson.fromJson(needsStr, new TypeToken<List<RequestDTO>>(){}.getType());
        Log.d("TAG",requests.toString());
        requests();
    }
    //轮播图
    private void getBanner( JSONObject info) throws JSONException {
        String bannerStr = info.getString("ads");
        Log.d("TAG",bannerStr);
        banners = gson.fromJson(bannerStr, new TypeToken<List<HomeBannerDTO>>(){}.getType());
        Log.d("TAG",banners.toString());
        banner();
    }
    //企业列表
    private void getCompany( JSONObject info) throws JSONException {
        String companysStr = info.getString("companyList");
        Log.d("TAG",companysStr);
        companys = gson.fromJson(companysStr, new TypeToken<List<CompanyDTO>>(){}.getType());
        Log.d("TAG",requests.toString());
        companys();
    }
    //需求列表
    private void requests() {
        requestList(getLayoutInflater());
    }
    //企业列表
    private void companys() {
        companyList(getLayoutInflater());
    }
    //行业资讯
    private void industrys() {
        infoList(getLayoutInflater());
    }

}
