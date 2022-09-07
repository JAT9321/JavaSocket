package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;
import com.jiao.common.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/6
 **/
public class MessageServer {


    public void oneToOne(String sender, String getter, String msg) {
        Message message = new Message();
        message.setSender(sender);
        message.setGetter(getter);
        message.setContent(msg);
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSendTime(new Date().toString());
        Socket socket = SocketList.getSocketByUserId(sender);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oneToAll(String sender, String msg) {
        Message message = new Message();
        message.setSender(sender);
        message.setGetter("在线人员");
        message.setContent(msg);
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSendTime(new Date().toString());
        Socket socket = SocketList.getSocketByUserId(sender);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
