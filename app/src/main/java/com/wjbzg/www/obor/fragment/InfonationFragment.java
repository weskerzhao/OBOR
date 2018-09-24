package com.wjbzg.www.obor.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.RegulationListActivity;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.CompanyDTO;
import com.wjbzg.www.obor.entity.HomeBannerDTO;
import com.wjbzg.www.obor.entity.InfoDescribeDTO;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.GlideImageLoader;
import com.youth.banner.Banner;
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
 * purpose 资讯Fragment
 * author 赵辉
 */
public class InfonationFragment extends Fragment implements OnBannerListener {
    //政策法规返回码
    public static final int REGULATIONG_CODE = 1;
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //banner控件
    @BindView(R.id.banner)
    Banner banner;
    //最新资讯
    @BindView(R.id.new_info_list)
    LinearLayout new_info_list;
    //矩阵选项
    @BindView(R.id.info_matrix)
    LinearLayout info_matrix;
    //Gson
    private Gson gson = new Gson();
    //轮播图
    private List<HomeBannerDTO> banners;
    //最新资讯
    private List<InfoDescribeDTO> infoDescribes;

    //政策法规
    private TableRow regulation;

    public InfonationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InfonationFragment.
     */
    public static InfonationFragment newInstance(String param1, String param2) {
        InfonationFragment fragment = new InfonationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infonation, container, false);
        ButterKnife.bind(this, view);
        requestHomeInfo();
        tv_tittle.setText(R.string.info_field_title);
//        banner();
        infoMatrix(inflater);
//        newInfoList(inflater);
        return view;
    }

    //banner控件
    private void banner() {
        List<String> imageUrls = new ArrayList<>();
        for (HomeBannerDTO homeBannerDTO : banners) {
            imageUrls.add(Constant.SERVER_URL + homeBannerDTO.getImg());
            Log.d("imag",Constant.SERVER_URL + homeBannerDTO.getImg());
        }
//        imageUrls.add("http://imgstore03.cdn.sogou.com/v2/thumb/dl/60d2f4fe0275d790-fbe7539243950f9f-7f669dbeead0ad667f21be96b5efd843.jpg?appid=10150005&referer=http://pic.sogou.com&url=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Db1c873ffd709b3deebbfe460fcbe6cd3%2F71a8b4c27d1ed21be972fa72ab6eddc450da3faf.jpg");
//        imageUrls.add("http://imgstore04.cdn.sogou.com/v2/thumb/dl/ac75323d6b6de243-4ae01c7db4cb0b81-19dbc2535747183ad927e5fadca326a0.jpg?appid=10150005&referer=http://pic.sogou.com&url=http%3A%2F%2Fpic12.nipic.com%2F20110223%2F2709576_111836168000_2.jpg");
        banner.setImages(imageUrls)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    /**
     * 最新资讯
     */
    private void infoMatrix(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.info_matrix, info_matrix, true);
        regulation = view.findViewById(R.id.regulation);
        regulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegulationListActivity.class);
                startActivityForResult(intent,REGULATIONG_CODE);
            }
        });
    }

    /**
     * 最新资讯
     */
    private void newInfoList(LayoutInflater inflater) {
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, new_info_list, true);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
        tv_company_list.setText(R.string.info_field_new_info);
        TextView linear_look_over = list_tittle.findViewById(R.id.tv_look_over);
        linear_look_over.setText(R.string.home_field_look_over_more);
        for (int i = 0; i < infoDescribes.size(); i++) {
            View view = inflater.inflate(R.layout.info_list, new_info_list, false);
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
            new_info_list.addView(view);
        }
    }

    @Override
    public void OnBannerClick(int position) {

    }

    //Http请求首页信息
    private void requestHomeInfo(){
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "infoApp").execute(new StringCallback() {
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
                        //轮播图
                        getBanner(info);
                      //最新资讯
                        getinfo(info);
                    }
//                    Toast.makeText(getActivity(), message, Constant.TOAST_TIME).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR","Json转换异常");
                }
            }
        });
    }

    //轮播图
    private void getBanner( JSONObject info) throws JSONException {
        String bannerStr = info.getString("ads");
        Log.d("TAG",bannerStr);
        banners = gson.fromJson(bannerStr, new TypeToken<List<HomeBannerDTO>>(){}.getType());
        Log.d("TAG",banners.toString());
        banner();
    }
    //最新资讯
    private void getinfo( JSONObject info) throws JSONException {
        String infoStr = info.getString("infoList");
        Log.d("TAG",infoStr);
        infoDescribes = gson.fromJson(infoStr, new TypeToken<List<InfoDescribeDTO>>(){}.getType());
        Log.d("TAG",infoDescribes.toString());
        newInfoList(getLayoutInflater());
    }
}
