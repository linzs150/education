package com.one.education.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.one.education.education.R;

public class VideoActivity extends BaseActivity {

    private VideoView videoView;
    private RelativeLayout leftBtnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        leftBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        leftBtnLayout = findViewById(R.id.leftBtnLayout);
    }

    private void initData() {
        String path = getIntent().getStringExtra("videoUrl");
        if (!TextUtils.isEmpty(path)) {
            videoView.setVideoPath(path);
            //创建MediaController对象
            MediaController mediaController = new MediaController(this);

            //VideoView与MediaController建立关联
            videoView.setMediaController(mediaController);

            //让VideoView获取焦点
            videoView.requestFocus();
        }

    }
}
