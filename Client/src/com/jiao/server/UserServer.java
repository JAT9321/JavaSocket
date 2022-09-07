package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;
import com.jiao.common.User;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author: 赵高天
 * @create: 2022/9/4
 * @email: zgt9321@qq.com
 * @version: 1.0
 **/
public class UserServer {

    private Socket socket;
    private User user = new User();

    //验证用户，密码
    public boolean login(String userId, String pwd) throws IOException, ClassNotFoundException {

        String host = "127.0.0.1";
//        String host = "106.14.219.106";
        user.setUserId(userId);
        user.setPassword(pwd);
        socket = new Socket(host, 9999);
        //向服务端发送客户登录信息，进行验证
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(user);

        //等待获取服务端返回的验证信息
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) ois.readObject();
        //登录成功
        if (MessageType.MESSAGE_LOGIN_SUCCESS.equals(message.getMesType())) {
            new SocketThread(socket).start();
            SocketList.addSocket(userId, socket);
            return true;
        } else {
            ois.close();
            oos.close();
            socket.close();
            return false;
        }

    }

    //退出
    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserId());
        Socket socket = SocketList.getSocketByUserId(user.getUserId());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //获取在线用户列表申请
    public void getOnlineUser() {
        Socket socket = SocketList.getSocketByUserId(user.getUserId());
        try {
            Message message = new Message();
            message.setSender(user.getUserId());
            message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
