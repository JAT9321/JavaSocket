package com.jiao.common;

import java.io.Serializable;

/**
 * @author : 赵高天
 * @since  : 2022/9/4
 * @email :z gt9321@qq.com
 * @version : 1.0
 **/
public class User implements Serializable {
    private static final long serialVersionUID = 1l;

    private String userId; //用户Id
    private String password; //用户密码

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", Password='" + password + '\'' +
                '}';
    }

}
