package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketThread extends Thread {

    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //持续监听与服务器端的socket
        while (true) {
            try {
                Message message = (Message) new ObjectInputStream(socket.getInputStream()).readObject();
                //得到在线用户列表

                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //规定使用-分割不同的用户
                    String[] onlineUsers = message.getContent().split("-");
                    System.out.println(">> 当前在线的好友");
                    for (String userId : onlineUsers) {
                        System.out.println("用户: " + userId);
                    }
                } //私聊接受到的消息或群发
                else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES) || message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    System.out.println("\n>>来自" + message.getSender() + "的消息：" + message.getContent());
                }
                //接受文件
                else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(message.getDest()));
                    bos.write(message.getFileByte());
                    System.out.println("\n>>来自" + message.getSender() + "的文件，已保存到" + message.getDest());
                    bos.close();
                } else {
                    System.out.println("其他信息");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
