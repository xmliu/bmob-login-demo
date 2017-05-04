/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xm.xmlogin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO
 */
public class StringUtils {
	public static boolean isMobilePhone(String phone) {//
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}
	public static boolean isBlank(String str) {
		boolean isBlank = false;
		if (null == str || str.equals("") || str.equals("null")
				|| str.trim().length() == 0) {
			isBlank = true;
		}
		return isBlank;
	}
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		boolean isEmpty = false;
		if (null == str || str.equals("") || str.equals("null") || str.trim().length() == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * 判断是否是手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
