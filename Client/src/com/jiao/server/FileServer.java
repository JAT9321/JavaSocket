package com.jiao.server;

import com.jiao.common.Message;
import com.jiao.common.MessageType;

import java.io.*;
import java.net.Socket;

/**
 * @author : 赵高天
 * @version : 1.0
 * @email : zgt9321@qq.com
 * @since : 2022/9/7
 **/
public class FileServer {

    //发送文件
    public void sendFile(String senderId, String getterId, String senderFileSrc, String getterFileSrc) throws IOException {

        int fileLen = (int) new File(senderFileSrc).length();
        byte[] data = new byte[fileLen];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(senderFileSrc));
            bis.read(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
        }
        Message message = new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setFileLen(fileLen);
        message.setFileByte(data);
        message.setDest(getterFileSrc);
        message.setSrc(senderFileSrc);

        Socket socket = SocketList.getSocketByUserId(senderId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
