package com.lingyi.walteractivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lingyi.library.WalterUtils;

/**
 * Created by lingyi on 16/12/10.
 * Copyright © 1994-2016 lingyi™ Inc. All rights reserved.
 */

public class WalterStartActivity extends Activity {

    private View contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = WalterUtils.getInstance().doAnim(this);
        setContentView(contentView);
    }
}
