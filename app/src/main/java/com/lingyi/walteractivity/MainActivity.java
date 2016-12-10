package com.lingyi.walteractivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lingyi.library.WalterUtils;

/**
 * Created by lingyi on 16/12/10.
 * Copyright © 1994-2016 lingyi™ Inc. All rights reserved.
 */
public class MainActivity extends Activity {

    private View startView;

    private View startView1;

    private View startView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startView = findViewById(R.id.start_left_top);
        startView1 = findViewById(R.id.start_right_top);
        startView2 = findViewById(R.id.iv_flash_icon);
        startView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalterUtils.getInstance().startActivityDoAnim(startView,R.layout.walter_activity,MainActivity.this,WalterStartActivity.class);
            }
        });

        startView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalterUtils.getInstance().startActivityDoAnim(startView1,R.layout.walter_activity,MainActivity.this,WalterStartActivity.class);
            }
        });
        startView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalterUtils.getInstance().startActivityDoAnim(startView2,R.layout.walter_activity,MainActivity.this,WalterStartActivity.class);
            }
        });
    }
}
