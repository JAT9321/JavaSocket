package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;
import com.jiao.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/4
 **/
public class Server {

    private ServerSocket serverSocket;

    static ConcurrentHashMap<String, User> validUser = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, ArrayList<Message>> SaveMessage = new ConcurrentHashMap<>();


    //  静态代码段，将用户信息输入到集合当中
    static {
        validUser.put("100", new User("100", "123456"));
        validUser.put("200", new User("200", "123456"));
        validUser.put("300", new User("300", "123456"));
        validUser.put("至尊宝", new User("至尊宝", "123456"));
        validUser.put("菩提老祖", new User("紫霞仙子", "123456"));
        validUser.put("紫霞仙子", new User("菩提老祖", "123456"));
    }

    private boolean check(User usr) {
        User user = validUser.get(usr.getUserId());
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(usr.getPassword())) {
            return true;
        }
        return false;
    }

    public Server() {
        try {
            serverSocket = new ServerSocket(9999);
            new Thread(new MessageThread()).start();
            System.out.println(">> 服务器开启");
            while (true) {
                //获取登录请求
                Socket socket = serverSocket.accept();
                System.out.println(socket.getLocalAddress().getHostName() + "正在登录...");
                //获取登录用户对象
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();
                //检查登录，合格给配置一个线程进行监听
                Message message = new Message();
                if (check(user)) {
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    //开启与此socket联系的thread
                    ServerThread thread = new ServerThread(socket, user.getUserId());
                    ThreadList.addServerThread(user.getUserId(), thread);
                    thread.start();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);

                    if (SaveMessage.containsKey(user.getUserId())) {
                        System.out.println(">>正在给" + user.getUserId() + "发送历史消息");
                        ServerThread.sendHistory(user.getUserId(), SaveMessage.get(user.getUserId()));
                        SaveMessage.remove(user.getUserId());
                    }

                } else {
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);

                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
