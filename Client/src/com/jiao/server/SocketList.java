package com.jiao.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/4
 **/
public class SocketList {
    //保存socket
    private static Map<String, Socket> socketMap = new HashMap<>();

    public static void addSocket(String userId, Socket socket) {
        socketMap.put(userId, socket);
    }

    public static Socket getSocketByUserId(String userId) {
        return socketMap.get(userId);
    }

    public static Socket delSocket(String userId) {
        return socketMap.remove(userId);
    }

}
