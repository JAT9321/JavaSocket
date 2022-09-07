package com.jiao.view;

import com.jiao.server.FileServer;
import com.jiao.server.UserServer;
import com.jiao.util.Utility;
import com.jiao.server.MessageServer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author: 赵高天
 * @create: 2022/9/4
 * @email: zgt9321@qq.com
 * @version: 1.0
 **/
public class View {


    private UserServer userServer = new UserServer();
    private MessageServer messageServer = new MessageServer();
    private FileServer fileServer = new FileServer();
    private boolean loop = true;
    private String key = "";


    public View() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("========欢迎登录网络通信系统=======");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 2 退出系统");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("请输入用户号 >>");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密 码 >>");
                    String pwd = Utility.readString(50);
                    if (userServer.login(userId, pwd)) {
                        System.out.println("=========欢迎（用户" + userId + "登录成功）========");
                        while (loop) {
                            System.out.println("\n=========网络通信系统二级菜单（用户" + userId + "）======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择>>");
                            key = Utility.readString(1);

                            switch (key) {
                                case "1":
                                    userServer.getOnlineUser();
                                    break;
                                case "2":
                                    System.out.print("请输入发送的信息>>");
                                    String msgS = scanner.nextLine();
                                    messageServer.oneToAll(userId, msgS);
                                    System.out.println(">>消息已发送给所有在线人员");
                                    break;
                                case "3":
                                    System.out.print("请输入收取方Id>>");
                                    String getter = scanner.nextLine();
                                    System.out.print("请输入发送的信息>>");
                                    String msg = scanner.nextLine();
                                    messageServer.oneToOne(userId, getter, msg);
                                    System.out.println(">>消息已发送给" + getter);
                                    break;
                                case "4":
                                    System.out.print("请输入接收者>>");
                                    String fileGetter = Utility.readString(50);
                                    System.out.print("请输入文件路径>>");
                                    String fileSrc = Utility.readString(50);
                                    System.out.print("请输入接收者路径>>");
                                    String fileDesc = Utility.readString(50);
                                    fileServer.sendFile(userId, fileGetter, fileSrc, fileDesc);
                                    break;
                                case "9":
                                    userServer.logout();
                                    System.exit(0);
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("=======登录失败=====");  //当然也可以写成密码或账号错误
                    }
                    break;
                case "2":
                    loop = false;
                    System.out.println(">>退出系统");
                    break;
            }
        }
    }
}



