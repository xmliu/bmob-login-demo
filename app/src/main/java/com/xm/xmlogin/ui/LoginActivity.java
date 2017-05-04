/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xm.xmlogin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.EditText;
import com.xm.xmlogin.BaseActivity;
import com.xm.xmlogin.util.CommonUtils;
import com.xm.xmlogin.util.LogUtil;
import com.xm.xmlogin.R;
import com.xm.xmlogin.util.StringUtils;
import com.xm.xmlogin.bean.UserBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class LoginActivity extends BaseActivity {

    EditText mPhoneET;
    EditText mPasswordET;
    Button mLoginBtn;
    TextView mRegTV;
    TextView mResetTV;
    ImageView mIconIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPhoneET = (EditText) findViewById(R.id.id_phone_et);
        mPasswordET = (EditText) findViewById(R.id.id_password_et);
        mLoginBtn = (Button) findViewById(R.id.id_login_btn);
        mRegTV = (TextView) findViewById(R.id.id_reg_tv);
        mResetTV = (TextView) findViewById(R.id.id_reset_tv);
        mIconIV = (ImageView) findViewById(R.id.ic_launcher);

        mPasswordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mIconIV.setImageResource(R.drawable.ic_launcher_close);
                }else{
                    mIconIV.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPhoneStr = mPhoneET.getText().toString().trim()
                        .replaceAll(" ", "");
                String mPasswordStr = mPasswordET.getText().toString().trim();
                if (StringUtils.isBlank(mPhoneStr)) {
                    CommonUtils.showToast(LoginActivity.this, "手机号不能为空");
                } else if (!StringUtils.isMobilePhone(mPhoneStr)) {
                    CommonUtils.showToast(LoginActivity.this, "手机号输入有误");
                } else if (StringUtils.isBlank(mPasswordStr)) {
                    CommonUtils.showToast(LoginActivity.this, "密码不能为空");
                } else {
                    CommonUtils.showProgressDialog(LoginActivity.this, "正在登陆");
                    UserBean bu2 = new UserBean();
                    bu2.setUsername(mPhoneStr);
                    bu2.setPassword(mPasswordStr);
                    bu2.login(new SaveListener<UserBean>() {
                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            if(e == null){
                                CommonUtils.hideProgressDialog();
                                // 登录成功后设置默认昵称为ObjectId
                                BmobQuery<UserBean> bmobQuery = new BmobQuery<UserBean>();
                                bmobQuery.addWhereEqualTo("username", userBean.getUsername());
                                bmobQuery.findObjects(new FindListener<UserBean>() {
                                    @Override
                                    public void done(List<UserBean> list, BmobException e) {
                                        if(e == null){
                                            // 如果昵称为空的话就修改，否则不做操作
                                            if(StringUtils.isBlank(list.get(0).getNickname())){
                                                UserBean uBean = new UserBean();
                                                uBean.setNickname(list.get(0).getObjectId());
                                                uBean.update(list.get(0).getObjectId(), new UpdateListener() {
                                                    @Override
                                                    public void done(cn.bmob.v3.exception.BmobException e) {
                                                        if(e == null){
                                                            LogUtil.i(TAG, "更新nickname success" );
                                                        }else {
                                                            LogUtil.i(TAG, "更新nickname error=" + e.getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                LoginActivity.this.finish();
                            }else{
                                CommonUtils.hideProgressDialog();
                                CommonUtils.showToast(LoginActivity.this, "登录失败，" + e.getMessage() );
                            }
                        }
                    });
                }
            }
        });

        mRegTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("resetpass", false));
            }
        });
        mResetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("resetpass", true));
            }
        });

    }

    @Override
    public void onBackPressed() {
        CommonUtils.showExitDialog(LoginActivity.this);

    }
}
