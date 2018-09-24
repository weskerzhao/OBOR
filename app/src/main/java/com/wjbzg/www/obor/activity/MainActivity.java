package com.wjbzg.www.obor.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wjbzg.www.obor.fragment.DiscoverFragment;
import com.wjbzg.www.obor.fragment.HomeFragment;
import com.wjbzg.www.obor.fragment.InfonationFragment;
import com.wjbzg.www.obor.fragment.MineFragment;
import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.utils.ActivityUtils;
import com.wjbzg.www.obor.utils.PopupMenuUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //Activity工具类
    ActivityUtils activityUtils = ActivityUtils.getInstance();
    //灰色
    private int grey;
    //黑色
    private int black;

    private ImageView ivImg;

    private RelativeLayout rlClick;
    private Context context;
    //首页
    private HomeFragment homeFragment;
    //资讯
    private InfonationFragment infonationFragment;
    //发现
    private DiscoverFragment discoverFragment;
    //我的
    private MineFragment mineFragment;
//    private RecyclerView recyclerView;

    //ImageView集合
    List<ImageView> images = new ArrayList<>();
    //TextView集合
    List<TextView> texts = new ArrayList<>();
    /**
     * 底部导航栏图标
     */
    //首页info
    @BindView(R.id.linear_home)
    LinearLayout linear_home;
    @BindView(R.id.image_home)
    ImageView image_home;
    @BindView(R.id.tv_home)
    TextView tv_home;
    //资讯
    @BindView(R.id.linear_info)
    LinearLayout linear_info;
    @BindView(R.id.image_info)
    ImageView image_info;
    @BindView(R.id.tv_info)
    TextView tv_info;
    //发现
    @BindView(R.id.linear_discover)
    LinearLayout linear_discover;
    @BindView(R.id.image_discover)
    ImageView image_discover;
    @BindView(R.id.tv_discover)
    TextView tv_discover;
    //我的
    @BindView(R.id.linear_mine)
    LinearLayout linear_mine;
    @BindView(R.id.image_mine)
    ImageView image_mine;
    @BindView(R.id.tv_mine)
    TextView tv_mine;
    //Plus
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_img)
    TextView tv_img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }


    //导航图标变灰
    private void colorFilter(){
        for (ImageView image : images) {
            image.setColorFilter(grey);
        }
        for (TextView text : texts) {
            text.setTextColor(grey);
        }

    }

    /**
     * 将颜色设置成灰色
     * @param iv ImageView
     * @param tv TextView
     */
    private void setColorGrey(ImageView iv,TextView tv){
        iv.setColorFilter(grey);
        tv.setTextColor(grey);
    }
    /**
     * 将颜色设置成黑色
     * @param iv ImageView
     * @param tv TextView
     */
    private void setColorBlack(ImageView iv,TextView tv){
        iv.setColorFilter(black);
        tv.setTextColor(black);
    }

    private void initViews() {
        context = this;
        addImages();
        addTexts();
        grey = context.getResources().getColor(R.color.material_grey_300);
        black = context.getResources().getColor(R.color.primary_text_default_material_light);
        ivImg = (ImageView) findViewById(R.id.iv_img);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //导航栏动画效果
        rlClick = (RelativeLayout) findViewById(R.id.rl_click);
        rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenuUtil.getInstance()._show(context, ivImg);
            }
        });
        colorFilter();
        setColorBlack(image_home,tv_home);
        boolean loginFlag = getIntent().getBooleanExtra("loginFlag", false);
        if(loginFlag){
            setColorBlack(image_mine,tv_mine);
            MineFragment(loginFlag);
        }else{
            homeFragment();
        }
    }

    @Override
    public void onBackPressed() {
        // 当popupWindow 正在展示的时候 按下返回键 关闭popupWindow 否则关闭activity
        if (PopupMenuUtil.getInstance()._isShowing()) {
            PopupMenuUtil.getInstance()._rlClickAction();
        } else {
            super.onBackPressed();
        }
    }



    @OnClick({R.id.linear_home,R.id.linear_info,R.id.linear_discover,R.id.linear_mine})
    public void onClick(View v) {
        colorFilter();
        switch (v.getId()){
            case R.id.linear_home:
                setColorBlack(image_home,tv_home);
                homeFragment();
//                Toast.makeText(MainActivity.this,"首页",new Integer(100)).show();
                break;
            case R.id.linear_info:
                setColorBlack(image_info,tv_info);
                InformationFragment();
//                Toast.makeText(MainActivity.this,"资讯",new Integer(100)).show();
                break;
            case R.id.linear_discover:
                setColorBlack(image_discover,tv_discover);
                DiscoverFragment();
//                Toast.makeText(MainActivity.this,"发现",new Integer(100)).show();
                break;
            case R.id.linear_mine:
                setColorBlack(image_mine,tv_mine);
                MineFragment(false);
//                Toast.makeText(MainActivity.this,"我的",new Integer(100)).show();
                break;
            default:
                break;

        }
    }

    /**
     * 填充ImageView集合
     */
    private void addImages(){
        images.add(image_home);
        images.add(image_info);
        images.add(image_discover);
        images.add(image_mine);
    }
    /**
     * 填充TextView集合
     */
    private void addTexts(){
        texts.add(tv_home);
        texts.add(tv_info);
        texts.add(tv_discover);
        texts.add(tv_mine);
    }

    /**
     * 首页
     */
    private void homeFragment(){
        homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, homeFragment);
        fragmentTransaction.commit();
    }
    /**
     * 资讯
     */
    private void InformationFragment(){
        infonationFragment = new InfonationFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, infonationFragment);
        fragmentTransaction.commit();
    }
    /**
     * 发现
     */
    private void DiscoverFragment(){
        discoverFragment = new DiscoverFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, discoverFragment);
        fragmentTransaction.commit();
    }
    /**
     * 我的
     */
    private void MineFragment(boolean loginFlag){
        mineFragment = new MineFragment(loginFlag);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, mineFragment);
        fragmentTransaction.commit();
    }

}
