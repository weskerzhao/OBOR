package com.wjbzg.www.obor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjbzg.www.obor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishSuccessActivity extends Activity {
    //回退
    @BindView(R.id.reback)
    ImageView reback;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_success);
        ButterKnife.bind(this);
        tvPublish.setVisibility(View.INVISIBLE);
        tvTittle.setText(R.string.publish_field_success_title);
    }

    @OnClick(R.id.reback)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.reback:
                finish();
        }
    }
}
