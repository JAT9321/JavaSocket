package com.jiao.common;
 
public interface MessageType {
    String MESSAGE_LOGIN_SUCCESS = "1"; //登录验证成功
    String MESSAGE_LOGIN_FAIL = "2";  //验证登录失败
    String MESSAGE_COMM_MES = "3";  //消息类型为普通消息
    String MESSAGE_GET_ONLINE_FRIEND = "4";  //请求得到在线用户信息
    String MESSAGE_RET_ONLINE_FRIEND = "5";  //返回在线用户信息
    String MESSAGE_CLIENT_EXIT = "6"; //退出消息
    String MESSAGE_TO_ALL_MES = "7";//群发文件
    String MESSAGE_FILE_MES = "8";//发送文件
    String MESSAGE_GET_NOT_ONLINE_MES = "9"; //接收离线消息
    String MESSAGE_SET_NOT_ONLINE_MES = "10"; //发送离线消息
}
//可以扩展功能