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
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.EditText;
import com.xm.xmlogin.BaseActivity;
import com.xm.xmlogin.util.CommonUtils;
import com.xm.xmlogin.Constants;
import com.xm.xmlogin.util.LogUtil;
import com.xm.xmlogin.R;
import com.xm.xmlogin.util.StringUtils;
import com.xm.xmlogin.bean.UserBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class RegisterActivity extends BaseActivity {

    EditText mPhoneET;
    EditText mCodeET;
    EditText mPasswordET;
    Button mResetBtn;
    Button mSendBtn;
    Button mRegBtn;
    ImageButton mBackBtn;
    TextView mBackTV;
    TextView mTitleTV;
    TextView mProtocolTV;
    LinearLayout mProtocolLayout;
    CheckBox mPasswordCB;
    CheckBox mProtocolCB;

    private TimeCount time;
    private String areaStr = null;
    private boolean isForgetPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LogUtil.e(TAG, "onCreate");
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case Constants.Tags.LOAD_DATA_SUCCESS:
                        final String phone = mPhoneET.getText().toString().trim();
                        final String password = mPasswordET.getText().toString().trim();
                        final String code = mCodeET.getText().toString().trim();
                        UserBean userBean = new UserBean();
                        userBean.setPassword(password);
                        userBean.setUsername(phone);
                        userBean.setCode(Integer.parseInt(code));
                        userBean.setArea(areaStr);
                        userBean.setAvatar("http://file.bmob.cn/M02/79/0B/oYYBAFawXZOALjy5AAACPGs5RvU833.png");
                        userBean.setBgurl("http://file.bmob.cn/M02/79/0B/oYYBAFawXXqAAX_OAAhPzr3vBNo866.png");
                        userBean.setGender(1);
                        userBean.setNickname("");
                        userBean.setRid(0);
                        userBean.setSignature("开启一段美好旅程吧！");
                        userBean.signUp(new SaveListener<UserBean>() {
                            @Override
                            public void done(UserBean userBean, cn.bmob.v3.exception.BmobException e) {
                                if (e == null) {
                                    LogUtil.i("TAG", "reg success");
                                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    if (e.getErrorCode() == 202) {
                                        CommonUtils.showToast(RegisterActivity.this, "该用户已注册");
                                    } else {
                                        CommonUtils.showToast(RegisterActivity.this, "注册失败");
                                    }
                                    LogUtil.i(TAG, "reg error=" + e.getMessage());
                                }
                            }
                        });
                        break;
                }
            }
        };
        mPhoneET = (EditText) findViewById(R.id.reg_phone_et);
        mCodeET = (EditText) findViewById(R.id.id_code_et);
        mPasswordET = (EditText) findViewById(R.id.id_password_et);
        mResetBtn = (Button) findViewById(R.id.id_reset_btn);
        mSendBtn = (Button) findViewById(R.id.id_send_btn);
        mRegBtn = (Button) findViewById(R.id.id_reg_btn);
        mBackBtn = (ImageButton) findViewById(R.id.common_back_btn);
        mBackTV = (TextView) findViewById(R.id.common_back_tv);
        mTitleTV = (TextView) findViewById(R.id.regsiter_title);
        mProtocolTV = (TextView) findViewById(R.id.register_protocol_tv);
        mProtocolLayout = (LinearLayout) findViewById(R.id.register_protocol_checkbox_layout);
        mPasswordCB = (CheckBox) findViewById(R.id.chat_register_password_checkbox);
        mProtocolCB = (CheckBox) findViewById(R.id.register_protocol_checkbox);

        isForgetPass = this.getIntent().getBooleanExtra("resetpass", false);//从引导页过来

        mBackBtn.setVisibility(View.VISIBLE);
        mBackTV.setVisibility(View.GONE);

        if (isForgetPass) {
            mProtocolLayout.setVisibility(View.GONE);
            mRegBtn.setVisibility(View.GONE);
            mResetBtn.setVisibility(View.VISIBLE);
            mTitleTV.setText("找回密码");
        } else {
            mRegBtn.setVisibility(View.VISIBLE);
            mResetBtn.setVisibility(View.GONE);
            mTitleTV.setText("注册");
        }
        mBackTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mProtocolLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProtocolCB.isChecked()) {
                    mProtocolCB.setChecked(false);
                    mProtocolTV.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.grey));
                } else {
                    mProtocolCB.setChecked(true);
                    mProtocolTV.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));
                }
            }
        });
        time = new TimeCount(60000, 1000);// 构造CountDownr对象

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mPhoneStr = mPhoneET.getText().toString().trim()
                        .replaceAll(" ", "");
                if (StringUtils.isBlank(mPhoneStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "手机号不能为空");
                } else if (!StringUtils.isMobileNumber(mPhoneStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "手机号输入有误");
                } else {
                    // 先查询手机号是否已注册
                    BmobQuery<UserBean> bmobQuery = new BmobQuery<UserBean>();
                    //查询mobile叫mPhoneStr的数据
                    bmobQuery.addWhereEqualTo("username", mPhoneStr);
                    bmobQuery.findObjects(new FindListener<UserBean>() {
                        @Override
                        public void done(List<UserBean> list, cn.bmob.v3.exception.BmobException e) {
                            if (e == null) {
                                if (list.size() > 0) {
                                    Toast.makeText(RegisterActivity.this, "该手机号已注册，请直接登录", Toast.LENGTH_SHORT).show();
                                    mCodeET.setText("");
                                    mPasswordET.setText("");
                                } else {
                                    mSendBtn.setText("获取中...");
                                    BmobSMS.requestSMSCode(mPhoneET.getText().toString().trim().replaceAll(" ", ""), getResources().getString(R.string.app_name), new QueryListener<Integer>() {
                                        @Override
                                        public void done(Integer smsId, BmobException ex) {
                                            if (ex == null) {//验证码发送成功
                                                LogUtil.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                                                time.start();// 开始倒计时
                                                CommonUtils.showToast(RegisterActivity.this, "验证码已经发送");
                                                mPhoneET.setTextColor(getResources().getColor(R.color.grey));
                                                mPhoneET.setEnabled(false);
                                            }
                                        }
                                    });

                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败，请稍后重试"+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }

            }
        });

        mPasswordCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPasswordCB.setChecked(true);
                    //动态设置密码是否可见
                    mPasswordET
                            .setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                } else {
                    mPasswordCB.setChecked(false);
                    mPasswordET
                            .setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());
                }
                mPasswordET.setSelection(mPasswordET.getText().toString().length());//设置光标位置在文本框末尾
            }
        });


        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("TAG", "reging");
                final String mPhoneStr = mPhoneET.getText().toString().trim()
                        .replaceAll(" ", "");
                final String mPasswordStr = mPasswordET.getText().toString().trim();
                final String mCodeStr = mCodeET.getText().toString().trim();

                if (StringUtils.isBlank(mPhoneStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "手机号不能为空");
                } else if (!StringUtils.isMobileNumber(mPhoneStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "手机号输入格式有误");
                } else if (StringUtils.isEmpty(mPasswordStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "密码不能为空");
                } else if (StringUtils.isEmpty(mCodeStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "验证码不能为空");
                } else if (!mProtocolCB.isChecked()) {
                    CommonUtils.showToast(RegisterActivity.this, "您还未同意用户协议");
                } else {
                    CommonUtils.showProgressDialog(RegisterActivity.this, "正在注册");
                    // 先查询手机号是否已注册
                    BmobQuery<UserBean> bmobQuery = new BmobQuery<UserBean>();
                    //查询mobile叫mPhoneStr的数据
                    bmobQuery.addWhereEqualTo("username", mPhoneStr);
                    bmobQuery.findObjects(new FindListener<UserBean>() {
                        @Override
                        public void done(List<UserBean> list, cn.bmob.v3.exception.BmobException e) {
                            if (e == null) {
                                if (list.size() > 0) {
                                    CommonUtils.hideProgressDialog();
                                    Toast.makeText(RegisterActivity.this, "该手机号已注册，请直接登录", Toast.LENGTH_SHORT).show();
                                    mCodeET.setText("");
                                    mPasswordET.setText("");
                                } else {
                                    BmobSMS.verifySmsCode(mPhoneET.getText()
                                            .toString().replaceAll(" ", ""), mCodeStr, new UpdateListener() {
                                        @Override
                                        public void done(BmobException ex) {
                                            CommonUtils.hideProgressDialog();
                                            if (ex == null) {//短信验证码已验证成功
                                                LogUtil.i("bmob", "验证通过");
                                                onRegister();
                                            } else {
                                                CommonUtils.showToast(RegisterActivity.this, "验证码验证失败"+ ",msg = " + ex.getLocalizedMessage());
                                                LogUtil.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                            }
                                        }
                                    });
                                }

                            } else {
                                CommonUtils.hideProgressDialog();
                                Toast.makeText(RegisterActivity.this, "注册失败，请稍后重试"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("TAG", "reset");
                final String mPhoneStr = mPhoneET.getText().toString().trim()
                        .replaceAll(" ", "");
                final String mPasswordStr = mPasswordET.getText().toString().trim();
                final String mCodeStr = mCodeET.getText().toString().trim();
                if (StringUtils.isBlank(mPhoneStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "手机号不能为空");
                } else if (!StringUtils.isMobileNumber(mPhoneStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "手机号输入格式有误");
                } else if (StringUtils.isEmpty(mPasswordStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "密码不能为空");
                } else if (StringUtils.isEmpty(mCodeStr)) {
                    CommonUtils.showToast(RegisterActivity.this, "验证码不能为空");
                } else {
                    // 先查询手机号是否已注册
                    BmobQuery<UserBean> bmobQuery = new BmobQuery<UserBean>();
                    //查询mobile叫mPhoneStr的数据
                    bmobQuery.addWhereEqualTo("username", mPhoneStr);
                    bmobQuery.findObjects(new FindListener<UserBean>() {
                        @Override
                        public void done(List<UserBean> list, cn.bmob.v3.exception.BmobException e) {
                            if (e == null) {
                                if (list.size() == 1) {
                                    String mPasswordStr = mPasswordET.getText().toString().trim();
                                    UserBean userBean = new UserBean();
                                    userBean.setPassword(mPasswordStr);
                                    userBean.update(list.get(0).getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(cn.bmob.v3.exception.BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(RegisterActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(RegisterActivity.this, "更新失败，请稍后重试"+ e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(RegisterActivity.this, "更新失败，请稍后重试", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败，请稍后重试" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    private void onRegister() {
        final String phone = mPhoneET.getText().toString().trim()
                .replaceAll(" ", "");
        // 获取手机号码归属地作为地区值
        new Thread(new Runnable() {
            @Override
            public void run() {
//                    areaStr = retrofitWithGet(phone);
                    areaStr = "北京";
                    LogUtil.i(TAG,"getMobileArea" + areaStr);
                    mHandler.sendEmptyMessage(Constants.Tags.LOAD_DATA_SUCCESS);
            }
        }).start();


    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mSendBtn.setClickable(true);
            mSendBtn.setText("重新获取");
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mSendBtn.setClickable(false);
            mSendBtn.setText(millisUntilFinished / 1000 + "s");
        }
    }
}
