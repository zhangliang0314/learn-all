package com.zl.io.bio.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;

/**
 * @author: zhangliangx
 * @Date: 2020/8/26 22:15
 * @Description: 测试InputStream和OutputStream使用
 *
 * 字符流能实现的功能字节流都能实现
 * 字节流能实现的功能字符流不一定都能实现   如图片、视频等二进制文件
 */
public class FileInputOutputTest {

  public void copyFile(String srcPath, String destPath){
    FileInputStream fis = null;
    FileOutputStream fos = null;
    try {
      File srcFile = new File(srcPath);
      File destFile = new File(destPath);
      fis = new FileInputStream(srcFile);
      fos = new FileOutputStream(destFile);
      //通常一次大小初始化为1024
      byte[] data = new byte[1024];
      int len;
      while ((len = fis.read(data)) != -1){
        fos.write(data, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if(Objects.nonNull(fis)){
          fis.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (Objects.nonNull(fos)){
          fos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  @Test
  public void testInputStream01(){
    long start = System.currentTimeMillis();
    copyFile("src\\main\\resources\\springcloud架构图.jpg", "src\\main\\resources\\springcloud架构图1.jpg");
    long end = System.currentTimeMillis();
    System.out.println(start-end);
  }

}
