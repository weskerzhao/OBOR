package com.wjbzg.www.obor.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.activity.DiscoverRequestListActivity;
import com.wjbzg.www.obor.activity.DiscoverSourceListActivity;
import com.wjbzg.www.obor.activity.RequestInfoDetailActivity;
import com.wjbzg.www.obor.activity.ResourceInfoDetailActivity;
import com.wjbzg.www.obor.activity.net.MyOkhttpUtils;
import com.wjbzg.www.obor.entity.RequestDTO;
import com.wjbzg.www.obor.entity.ResourceDTO;
import com.wjbzg.www.obor.utils.Constant;
import com.wjbzg.www.obor.utils.PicassoUtils;
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
 * purpose 发现Fragment
 * author 赵辉
 */
public class DiscoverFragment extends Fragment {
    //Gson
    private Gson gson = new Gson();
    //下拉框
    @BindView(R.id.toolbar_serch_spinner)
    Spinner toolbar_serch_spinner;
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //需求类表
    @BindView(R.id.request_list)
    LinearLayout request_list;
    //资源列表
    @BindView(R.id.source_list)
    LinearLayout source_list;
    //图片
    @BindView(R.id.iv_request)
    ImageView iv_request;
    @BindView(R.id.iv_source)
    ImageView iv_source;
    //需求返回码
    public final static int REQUEST_CODE = 1;
    //资源返回码
    public final static int SOURCE_CODE = 2;
    //需求列表
    private List<RequestDTO> requests;
    //资源列表
    private List<ResourceDTO> resources;


    //搜索框
//    @BindView(R.id.serch)
//    SearchView serch;
    //TextView集合
    List<TextView> texts = new ArrayList<>();
    //Picasso工具类
    PicassoUtils picassoUtils = PicassoUtils.getInstance();

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);
        tv_tittle.setText(R.string.info_field_discover);
//        requestList(inflater);
//        newInfoList(inflater);
        serchType();
//        activityUtils.setSerchView(serch,getResources().getDimension(R.dimen.main_menu_text_size2));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        picassoUtils.setRoundedRectangle(iv_request, R.mipmap.request_picture);
        picassoUtils.setRoundedRectangle(iv_source, R.mipmap.resource_picture);

        requestHomeInfo();
        return view;
    }

    /**
     * 搜索类型
     */
    private ArrayList<String> data() {
        //数据
        ArrayList<String> data_list = new ArrayList<>();
        data_list.add("需求");
        data_list.add("资源");
        return data_list;
    }

    /**
     * 填充搜索类型
     */
    private void serchType() {
        //适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, data());
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        toolbar_serch_spinner.setAdapter(arr_adapter);

    }

    /**
     * 需求列表
     */
    private void requestList(LayoutInflater inflater) {
        View list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, request_list, false);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
        tv_company_list.setText(R.string.home_field_request_list);
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setText(R.string.home_field_look_over_more);
        tv_look_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiscoverRequestListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        request_list.addView(list_tittle);
        for (int i = 0; i < requests.size(); i++) {
            View view = inflater.inflate(R.layout.request_list, request_list, false);
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
            //跳转
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RequestInfoDetailActivity.class);
                    intent.putExtra("nid", requests.get(finalI).getNid());
                    startActivity(intent);
                }
            });
            request_list.addView(view);
        }
    }


    /**
     * 资源列表
     */
    private void newInfoList(LayoutInflater inflater) {
        LinearLayout list_tittle = (LinearLayout) inflater.inflate(R.layout.list_title, source_list, false);
        TextView tv_company_list = list_tittle.findViewById(R.id.tv_company_list);
        tv_company_list.setText(R.string.home_field_source_list);
        TextView tv_look_over = list_tittle.findViewById(R.id.tv_look_over);
        tv_look_over.setText(R.string.home_field_look_over_more);
        tv_look_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiscoverSourceListActivity.class);
                startActivityForResult(intent, SOURCE_CODE);
            }
        });
        source_list.addView(list_tittle);
        for (int i = 0; i < resources.size(); i++) {
            View view = inflater.inflate(R.layout.source_list, source_list, false);
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
                    Intent intent = new Intent(getActivity(), ResourceInfoDetailActivity.class);
                    intent.putExtra("rid", resources.get(finalI).getRid());

                    startActivity(intent);
                }
            });
            source_list.addView(view);
        }
    }

    //Http请求首页信息
    private void requestHomeInfo() {
        //准备请求参数
        HashMap<String, String> map = new HashMap<>();
        //发送http请求
        MyOkhttpUtils.okHttpPost(map, "findApp").execute(new StringCallback() {
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
                        //需求列表
                        getRequests(info);
                        //资源列表
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

    //需求列表
    private void getRequests(JSONObject info) throws JSONException {

        String needsStr = info.getString("needList");
        Log.d("TAG", needsStr);
        requests = gson.fromJson(needsStr, new TypeToken<List<RequestDTO>>() {
        }.getType());
        Log.d("TAG", requests.toString());
        requests();
    }

    //需求列表
    private void requests() {
        requestList(getLayoutInflater());
    }

    //资源列表
    private void getResource(JSONObject info) throws JSONException {
        String resListStr = info.getString("resList");
        Log.d("TAG", resListStr);
        resources = gson.fromJson(resListStr, new TypeToken<List<ResourceDTO>>() {
        }.getType());
        Log.d("TAG", requests.toString());
        resources();
    }

    //资源列表
    private void resources() {
        newInfoList(getLayoutInflater());
    }


    @OnClick({R.id.iv_request, R.id.iv_source})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_request:
                intent = new Intent(getActivity(), DiscoverRequestListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_source:
                intent = new Intent(getActivity(), DiscoverSourceListActivity.class);
                startActivityForResult(intent, SOURCE_CODE);
                break;
        }
    }
}
