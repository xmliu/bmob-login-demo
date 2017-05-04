/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xm.xmlogin.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xm.xmlogin.R;
import com.xm.xmlogin.util.AppManager;
import com.xm.xmlogin.util.CommonUtils;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getInstance().addActivity(this);

        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.showLogoutDialog(MainActivity.this);
            }
        });

    }
    @Override
    public void onBackPressed() {
        CommonUtils.showExitDialog(MainActivity.this);

    }
}
