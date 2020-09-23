package com.zl.io.bio.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: zhangliangx
 * @Date: 2020/8/29 15:31
 * @Description:
 *
 * 标准的输入流输出流
 *  System.in :标准的输入流，默认从键盘输入  System.setIn(InputStream is)
 *  System.out:标准的输出流，默认向控制台输出 System.setOut(PrintStream ps)
 */
public class SystemTest {


  public void test01() throws Exception{
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    while (true){
      String data = br.readLine();
      if ("e".equalsIgnoreCase(data)){
        break;
      }

      String result = data.toUpperCase();
      System.out.println(result);
    }
    br.close();
  }

  public static void main(String[] args) throws Exception {
    //使用junit测试无法从控制台输入
    new SystemTest().test01();
  }

}
