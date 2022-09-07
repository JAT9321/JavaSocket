package com.jiao.common;

import java.io.Serializable;
/**
 * @author:赵高天
 * @create:2022/9/4
 * @email:zgt9321@qq.com
 * @version:1.0
 **/

public class Message implements Serializable {
    private static final long serialVersionUID = 1l;

    private String sender; // 发送者
    private String getter; //接收者
    private String content; //消息内容
    private String sendTime; //发送时间
    private String mesType; //消息类型

    //    传送文件的成员变量
    private byte[] fileByte;
    private int fileLen =0;
    private String dest;//文件输入地址
    private String src;//文件输出地址

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Message() {
    }

    public Message(String sender, String getter, String content, String sendTime, String mesType) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.sendTime = sendTime;
        this.mesType = mesType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
