/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xm.xmlogin;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.xm.xmlogin.util.AppManager;
import com.xm.xmlogin.util.NetWorkUtils;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public abstract class BaseActivity extends Activity {

	protected BaseApplication mApplication;
	protected Handler mHandler;
	protected String TAG;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		NetWorkUtils.networkStateTips(this);
		mApplication = (BaseApplication) getApplication();
		TAG=this.getLocalClassName();
	}
	    
}
