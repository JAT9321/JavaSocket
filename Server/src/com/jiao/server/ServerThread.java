package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/4
 **/
public class ServerThread extends Thread {

    private Socket socket;
    private String userId;

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //返回在线好友列表
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    System.out.println(">> 用户：" + message.getSender() + "正在请求获取好友列表");
                    String online = ThreadList.threadOnline();
                    Message resMes = new Message();
                    resMes.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    resMes.setGetter(message.getSender());
                    resMes.setContent(online);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(resMes);
                }
                //用户退出
                else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println(">> 用户" + message.getSender() + "退出");
                    ThreadList.remove(message.getSender());
                    socket.close();
                    break;
                }
                //私聊中转
                else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {

                    //如果用户不在线，把消息存放到SaveMessage中
                    if (ThreadList.getServerThread(message.getGetter()) == null) {
                        if (Server.SaveMessage.containsKey(message.getGetter())) {
                            Server.SaveMessage.get(message.getGetter()).add(message);
                        } else {
                            ArrayList<Message> messages = new ArrayList<>();
                            messages.add(message);
                            Server.SaveMessage.put(message.getGetter(), messages);
                        }
                        continue;
                    }

                    //得到收取方的socket
                    ServerThread serverThread = ThreadList.getServerThread(message.getGetter());
                    Socket socket = serverThread.getSocket();
                    System.out.println(">>" + message.getSendTime() + message.getSender() + "发送消息给" + message.getGetter());
                    //转发给收取方
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                }
                //群发
                else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {

                    System.out.println(">>" + message.getSendTime() + message.getSender() + "发送消息给" + message.getGetter());

                    for (Map.Entry<String, ServerThread> entry : ThreadList.getServerThread().entrySet()) {
                        if (entry.getKey().equals(message.getSender())) continue;
                        ServerThread serverThread = entry.getValue();
                        Socket socket = serverThread.getSocket();
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message);
                    }
                    //不想修改上面的循环了，再来一个循环，也可以搞到一个里面，保存不在线用户接受的群发消息
                    for (String key : Server.validUser.keySet()) {
                        if (ThreadList.getServerThread().containsKey(key)) continue;
                        //如果用户不在线，把消息存放到SaveMessage中
                        if (Server.SaveMessage.containsKey(key)) {
                            Server.SaveMessage.get(message.getGetter()).add(message);
                        } else {
                            ArrayList<Message> messages = new ArrayList<>();
                            messages.add(message);
                            Server.SaveMessage.put(key, messages);
                        }
                    }
                }
                //文件
                else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {

                    //如果用户不在线，把消息存放到SaveMessage中
                    if (ThreadList.getServerThread(message.getGetter()) == null) {
                        if (Server.SaveMessage.containsKey(message.getGetter())) {
                            Server.SaveMessage.get(message.getGetter()).add(message);
                        } else {
                            ArrayList<Message> messages = new ArrayList<>();
                            messages.add(message);
                            Server.SaveMessage.put(message.getGetter(), messages);
                        }
                        continue;
                    }

                    ServerThread serverThread = ThreadList.getServerThread(message.getGetter());
                    Socket socket = serverThread.getSocket();
                    new ObjectOutputStream(socket.getOutputStream()).writeObject(message);
                    System.out.println(">>" + message.getSender() + "发送文件给" + message.getGetter());
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //用户上线，发送未上线前的消息
    public static void sendHistory(String userId, ArrayList<Message> messages) throws IOException {
        for (Message message : messages) {
            ServerThread serverThread = ThreadList.getServerThread(userId);
            Socket socket = serverThread.getSocket();
            new ObjectOutputStream(socket.getOutputStream()).writeObject(message);
        }
    }

    public ServerThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
