/*****************************************************************************
 * *                                                                         **
 * *               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 * *                                                                         **
 * *                          All rights reserved.                           **
 * *                                                                         **
 *****************************************************************************/
package com.xm.xmlogin.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.xm.xmlogin.R;
import com.xm.xmlogin.bean.UserBean;
import com.xm.xmlogin.ui.LoginActivity;



/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class CommonUtils {

    protected static Toast toast = null;

    public static MaterialDialog materialDialog;


    public static void showExitDialog(final Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content("确定退出" + context.getResources().getString(R.string.app_name) + "吗?")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        AppManager.getInstance().AppExit(context);
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public static void showLogoutDialog(final Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content("确定退出当前登录账号" + UserBean.getCurrentUser().getUsername() + "吗？")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        UserBean.logOut();
                        AppManager.getInstance().killAllActivity();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public static void showProgressDialog(Context context, String content) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.close_dialog);
        TextView textView = (TextView) view.findViewById(R.id.dialog_meassage);
        textView.setText(content);
        materialDialog = new MaterialDialog.Builder(context)
                .customView(view,true)
                .cancelable(false)
                .theme(Theme.LIGHT)
                .show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
    }

    public static void hideProgressDialog() {
        materialDialog.dismiss();
    }


    /**
     * 显示Toast消息
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getSysVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

}
