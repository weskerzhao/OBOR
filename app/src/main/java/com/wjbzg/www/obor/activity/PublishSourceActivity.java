package com.wjbzg.www.obor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbzg.www.obor.R;
import com.wjbzg.www.obor.utils.InflateInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * purpose 发布页面
 * author 赵辉
 */
public class PublishSourceActivity extends Activity {
    //回退
    @BindView(R.id.reback)
    ImageView reback;
    //标题
    @BindView(R.id.tv_tittle)
    TextView tv_tittle;
    //设置信息
    @BindView(R.id.set_info_list)
    LinearLayout set_info_list;
    //上传图片
    @BindView(R.id.linear_update_image)
    LinearLayout linear_update_image;
    //editText桩位
    @BindView(R.id.linear_edit_text)
    LinearLayout linear_edit_text;
    //发布按钮
    @BindView(R.id.tv_publish)
    TextView tv_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        tv_tittle.setText(R.string.publish_source);
        setInfo(getLayoutInflater());
        updateImage(getLayoutInflater());
        editText(getLayoutInflater());
    }

    /**
     * 设置栏位
     */
    private void setInfo(LayoutInflater inflater) {
        InflateInfo inflateInfo = InflateInfo.getInstance();
        inflateInfo.inflateInfoPublish(getLayoutInflater(), set_info_list, R.string.publish_field_title, false);
        inflateInfo.inflateInfoPublish(inflater, set_info_list, R.string.publish_field_source_category, true);
        inflateInfo.inflateInfoPublish(inflater, set_info_list, R.string.publish_field_cooperative, true);
        inflateInfo.inflateInfoPublish(inflater, set_info_list, R.string.publish_field_contact_way, false);

    }

    /**
     * 上传图片
     */
    private void updateImage(LayoutInflater inflater) {
        inflater.inflate(R.layout.update_image, linear_update_image, true);
    }

    /**
     * editText
     */
    private void editText(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.publish_edit_text, linear_edit_text, false);
        EditTextViewHolder editTextViewHolder = new EditTextViewHolder(view);
        editTextViewHolder.tvTextView.setText(R.string.publish_field_provide);
        linear_edit_text.addView(view);
        view = inflater.inflate(R.layout.publish_edit_text, linear_edit_text, false);
        editTextViewHolder = new EditTextViewHolder(view);
        editTextViewHolder.tvTextView.setText(R.string.publish_field_want);
        linear_edit_text.addView(view);
    }


    @OnClick({R.id.reback,R.id.tv_publish})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.reback:
                finish();
                break;
            case R.id.tv_publish:
                Intent intent = new Intent(this,PublishSuccessActivity.class);
                startActivity(intent);
                break;
        }
    }


    static class EditTextViewHolder {
        @BindView(R.id.tv_text_view)
        TextView tvTextView;
        @BindView(R.id.et_edit_text)
        EditText etEditText;
        @BindView(R.id.linear)
        LinearLayout linear;

        EditTextViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
