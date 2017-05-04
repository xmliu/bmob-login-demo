/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2017 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xm.xmlogin.bean;

import cn.bmob.v3.BmobUser;

/**
 * Author: liuxunming
 * Contact: http://blog.csdn.net/diyangxia
 * Date: 2017-05-04
 * Description: TODO BmobUser有默认的username和password字段使用，可以objectedId用作用户id
 */
public class UserBean extends BmobUser {

    /**
     * 验证码，只作记录用
     */
    Integer code;
    /**
     * 头像地址
     */
    String avatar;
    /**
     * 用户背景图片
     */
    String bgurl;
    /**
     * 昵称
     */
    String nickname;
    /**
     * 性别：男 女
     */
    Integer gender;
    /**
     * 所在地区
     */
    String area;
    /**
     * 所在地区ids
     */
    String areaIds;
    /**
     * 个性签名
     */
    String signature;
    /**
     * 极光推送id
     */
    Integer rid;
    /**
     * 出生年月
     */
    String birth;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBgurl() {
        return bgurl;
    }

    public void setBgurl(String bgurl) {
        this.bgurl = bgurl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}