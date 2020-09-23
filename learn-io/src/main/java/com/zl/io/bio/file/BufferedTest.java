package com.zl.io.bio.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;

/**
 * @author: zhangliangx
 * @Date: 2020/8/26 23:28
 * @Description:   处理流（套接在已有流的基础上）之一：缓冲流  -》提升流的读取写入速度 -> 使用缓冲区大小8192，先将流读取到缓冲区，再一次性输出
 *
 * BuffetInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWWriter
 *
 * 
 * 作用：提高流的读取、写入的速度
 *    原因：内部提供一个缓冲区
 *
 */
public class BufferedTest {

  public void copyFileWithBuffered(String srcPath, String destPath){
    FileInputStream fis = null;
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      File srcFile = new File(srcPath);
      File destFile = new File(destPath);
      fis = new FileInputStream(srcFile);
      fos = new FileOutputStream(destFile);
      //创建处理流，是在节点流之上又添加了一层包装
      bis = new BufferedInputStream(fis);
      bos = new BufferedOutputStream(fos);
      //通常一次大小初始化为1024
      byte[] data = new byte[1024];
      int len;
      while ((len = bis.read(data)) != -1){
        bos.write(data, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //处理流关闭，需要先关闭外层的流，再关闭内层的流
      //但是关闭外层流时，会直接自动关闭内层流，不需要手动关闭内层流
      try {
        if(Objects.nonNull(bis)){
          bis.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (Objects.nonNull(bos)){
          bos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void test01() {
    copyFileWithBuffered("src\\main\\resources\\springcloud架构图.jpg",
        "src\\main\\resources\\springcloud架构图2.jpg");
  }





}
