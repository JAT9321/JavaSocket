package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;
import com.jiao.util.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/7
 **/
public class MessageThread implements Runnable {

    //群发通知
    @Override
    public void run() {
        while (true) {
            String msg = Utility.readString(100);
            if ("exit".equals(msg)) {
                break;
            }
            Message message = new Message();
            message.setSender("服务器");
            message.setContent(msg);
            message.setSendTime(new Date().toString());
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            Map<String, ServerThread> serverThreadMap = ThreadList.getServerThread();
            for (Map.Entry<String, ServerThread> entry : serverThreadMap.entrySet()
            ) {
                Socket socket = entry.getValue().getSocket();
                message.setGetter(entry.getKey());
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
