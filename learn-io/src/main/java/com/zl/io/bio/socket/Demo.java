package com.zl.io.bio.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: zhangliangx
 * @Date: 2020/9/15 18:55
 * @Description:
 */
public class Demo {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket();
    socket.bind(new InetSocketAddress(9000));

    InputStream inputStream = socket.getInputStream();
    OutputStream outputStream = socket.getOutputStream();
  }

}
