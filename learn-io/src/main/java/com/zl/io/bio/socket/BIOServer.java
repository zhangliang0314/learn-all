package com.zl.io.bio.socket;

import static java.util.concurrent.Executors.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author: zhangliangx
 * @Date: 2020/9/15 19:38
 * @Description:  使用BIO模型编写一个服务器，监听6666端口，当有客户端连接时，就启动一个线程
 */
public class BIOServer {

  public static void main(String[] args) throws Exception{
    ExecutorService newCachedThreadPool = newCachedThreadPool();

    ServerSocket serverSocket = new ServerSocket(6666);
    System.out.println("服务器启动");

    while (true){
      System.out.println("线程ID=" + Thread.currentThread().getId() + "线程名字=" + Thread.currentThread().getName());
      //监听，等待客户端连接，此方法阻塞
      System.out.println("等待连接");
      Socket socket = serverSocket.accept();
      System.out.println("连接成功");
      //创建一个线程与客户端进行通信
      newCachedThreadPool.execute(() -> {
        handler(socket);
      });
    }

  }

  private static void handler(Socket socket)  {
    try {
      InputStream inputStream = socket.getInputStream();
      byte[] bytes = new byte[1024];
      int len;
      while((len = inputStream.read(bytes)) != -1){
        String str = new String(bytes, 0, len);
        System.out.print(str);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
