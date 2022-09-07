package com.jiao.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/4
 **/
public class ThreadList {
    private static Map<String, ServerThread> serverThreadMap = new HashMap<String, ServerThread>();

    public static void addServerThread(String userId, ServerThread serverThread) {
        serverThreadMap.put(userId, serverThread);
    }

    public static ServerThread getServerThread(String userId) {
        return serverThreadMap.get(userId);
    }

    public static String threadOnline() {
        StringBuilder online = new StringBuilder();
        for (Map.Entry<String, ServerThread> thread : serverThreadMap.entrySet()) {
            online.append(thread.getKey()).append("-");
        }
        return online.toString();
    }

    public static void remove(String userId) {
        serverThreadMap.remove(userId);
    }

    public static Map<String, ServerThread> getServerThread() {
        return serverThreadMap;
    }
}
