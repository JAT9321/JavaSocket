package com.jiao.view;

import com.jiao.util.Utility;

/**
 * @author:赵高天
 * @create:2022/9/4
 * @email:zgt9321@qq.com
 * @version:1.0
 **/
public class View {

    private boolean loop = true;
    private String key = "";

    public static void main(String[] args) {
        new View().mainMenu();
    }


    private void mainMenu() {
        while (loop) {
            System.out.println("========欢迎登录网络通信系统=======");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 2 退出系统");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("请输入用户号>>");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密 码>>");
                    String pwd = Utility.readString(50);
                    if (true) {
                        System.out.println("=========欢迎（用户" + userId + "登录成功）========");
                        while (loop) {
                            System.out.println("\n=========网络通信系统二级菜单（用户" + userId + "）======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择");
                            key = Utility.readString(1);

                            switch (key) {
                                case "1":
                                    System.out.println("1");
                                    break;
                                case "2":
                                    System.out.println("2");
                                    break;
                                case "3":
                                    System.out.println("3");
                                    break;
                                case "4":
                                    System.out.println("4");
                                    break;
                                case "9":
                                    System.out.println("9");
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



