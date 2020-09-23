package com.zl.io.bio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.junit.Test;

/**
 * @author: zhangliangx
 * @Date: 2020/8/29 14:41
 * @Description:
 *
 * 转换流：属于字符流
 * InputStreamReader：字节输入流转换为字符输入流
 * OutStreamWriter：将一个字符的输出流转换为字节的输出流
 *
 * 作用：提供字节流和字符流的转换
 *
 * 解码：字节 -》 字符
 * 编码：字符 -》 字节
 */
public class InputStreamReaderTest {

  @Test
  public void test01() throws Exception{
    FileInputStream fis = new FileInputStream("");
    //参数2指明字符集：具体使用哪个字符集取决于文件保存时使用的字符集
    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

    char[] buf = new char[1024];
    int len;
    while((len = isr.read(buf)) != -1){
      String str = new String(buf, 0, len);
      System.out.print(str);
    }

    isr.close();
  }

  @Test
  public void test02() throws Exception{
    FileInputStream fis = new FileInputStream("");
    FileOutputStream fos = new FileOutputStream("");

    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");

    char[] buf = new char[1024];
    int len;
    while((len = isr.read(buf)) != -1){
      osw.write(buf, 0, len);
    }
    isr.close();
    osw.close(); 

  }

}
