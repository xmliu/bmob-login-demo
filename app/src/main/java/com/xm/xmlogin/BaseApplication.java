/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xm.xmlogin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import cn.bmob.v3.Bmob;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class BaseApplication extends Application {
	public SharedPreferences mSharedPreferences;
	public static BaseApplication mInstance = null;
	@Override
	public void onCreate() {

		super.onCreate();

		mInstance = this;
		mSharedPreferences = getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

		Bmob.initialize(this, "457c601a7a0bde14841dcbf4e402edfe");

	}

}
