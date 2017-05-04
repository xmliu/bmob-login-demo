/*****************************************************************************
 * *                                                                         **
 * *               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 * *                                                                         **
 * *                          All rights reserved.                           **
 * *                                                                         **
 *****************************************************************************/
package com.xm.xmlogin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xm.xmlogin.BaseActivity;
import com.xm.xmlogin.Constants;
import com.xm.xmlogin.R;
import com.xm.xmlogin.bean.UserBean;
import com.xm.xmlogin.bean.VersionBean;
import com.xm.xmlogin.util.CommonUtils;
import com.xm.xmlogin.util.LogUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class SplashActivity extends BaseActivity {


    private String currentVersionName;
    private String latestVersionName;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_splash);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.Screen.width = metrics.widthPixels;
        Constants.Screen.height = metrics.heightPixels;
        Constants.Screen.density = metrics.density;

        mHandler = new Handler();

        currentVersionName = CommonUtils.getSysVersionName(SplashActivity.this);
        // 检测版本更新
        checkVersion();


    }

    private void afterVersionCheck() {
        UserBean userInfo = BmobUser.getCurrentUser( UserBean.class);
        boolean isFirstTime = mApplication.mSharedPreferences.getBoolean("isFirstTime", true);

        if (userInfo != null && !isFirstTime) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }, 1000);
        } else {
            if (isFirstTime) {
                // 如果是第一次进入应用，停留1.5秒进入引导页面
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        SplashActivity.this.finish();
                    }
                }, 1500);
            } else {
                // 否则，停留1.5秒进入登陆页面
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        SplashActivity.this.finish();
                    }
                }, 1500);
            }
        }
    }

    private void checkVersion() {

        BmobQuery<VersionBean> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("type", 1);
        bmobQuery.findObjects(new FindListener<VersionBean>() {
            @Override
            public void done(List<VersionBean> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        downloadUrl = list.get(0).getPath();
                        LogUtil.i(TAG, "版本更新成功" + downloadUrl);
                        LogUtil.i(TAG, "文件名：" + downloadUrl.substring(downloadUrl.lastIndexOf("/")+1));
                        latestVersionName = list.get(0).getVersion();
                        if (latestVersionName.toLowerCase().contains("v")) {
                            latestVersionName = latestVersionName.replace("v", "");
                        }
                        if (currentVersionName.equals(latestVersionName)) {
                            afterVersionCheck();
                        } else {
                            showVersionUpdateDialog();
                        }
                    } else {
                        CommonUtils.showToast(SplashActivity.this, "版本更新失败");
                        afterVersionCheck();
                    }

                } else {
                    CommonUtils.showToast(SplashActivity.this, "版本更新失败" + e.getMessage());
                    afterVersionCheck();
                }
            }
        });

    }

    private void showVersionUpdateDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("版本更新")
                .content("当前版本：" + currentVersionName + "\n新版本："
                        + latestVersionName + "\n是否更新？")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        mApplication.mSharedPreferences.edit().putBoolean("isFirstTime", true).apply();
                        // 此处应该开启下载服务
                        afterVersionCheck();
                        LogUtil.i(TAG, "downloadUrl" + downloadUrl);

                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        afterVersionCheck();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

}
