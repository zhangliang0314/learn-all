package com.zl.io.bio.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;

/**
 * @author: zhangliangx
 * @Date: 2020/8/26 10:22
 * @Description:
 *
 * 输入流input：读取外部数据（磁盘、光盘等）到程序内存中
 * 输出流output：将程序内存数据输出到磁盘、光盘等
 *
 * 字节流：非文本，如图片视频
 * 字符流：文本
 *
 * 节点流：原始流
 * 处理流：对原始流进行包装
 *
 *
 * （抽象基类）  字节流（8bit）   字符流（16bit）
 *-----------------------------------
 * 输入流 |     InputStream   Reader
 * 输出流 |     OutputStream  Writer
 * -----------------------------------
 *
 *
 * 为什么定义抽象类：具体IO读写操作太多复杂，抽象类只定义规范，具体实现交给子类
 *               IO流所有的类都是基于上述四个抽象类派生的
 *
 *
 *
 * 文件流     FileInputStream    FileOutputStream         FileReader         FileWriter
 * 缓冲流     BuffetInputStream  BufferedOutputStream     BufferedReader     BufferedWWriter  缓冲流是处理流的一种，对流进行包装
 *                                                      InputStreamReader   OutputStreamWriter
 * 对象流     ObjectInputStream  ObjectOutputStream
 */
public class FileReaderWriterTest {


  /**
   * 1，读取一个字符并以整数的形式返回(0~255),如果返回-1已到输入流的末尾。
   *    int read() ；
   * 2，读取一系列字符并存储到一个数组buffer，返回实际读取的字符数，如果读取前已到输入流的末尾返回-1。
   *    int read(char[] cbuf) ；
   * 3，读取length个字符,并存储到一个数组buffer，从off位置开始存,最多读取len，返回实际读取的字符数，如果读取前以到输入流的末尾返回-1。
   *    int read(char[] cbuf, int off, int len)
   */
  @Test
  public void testFileReader01() throws Exception {
    //1,实例化File类的对象，指明要操作的文件
    File file = new File("src\\main\\resources\\hello.txt");
    //2，使用具体的流:字符输入流
    FileReader fr = new FileReader(file);
    //3,开始读文件，read()返回读入的一个字符，达到文件末尾返回-1
    int data = fr.read();
    while(data != -1){
      System.out.println((char)data);
      data = fr.read();
    }
    //4，关闭流
    fr.close();
  }


  /**
   * 问题：
   *    流操作不建议直接抛出异常，直接抛出异常会导致流关闭代码没有执行，造成资源浪费
   *      程序打开的IO资源不属于内存资源，垃圾回收机制无法回收该资源，需要显示关闭
   * 解决：
   *    使用try-catch-finally，
   *      使用try-catch原因：即使抛出异常，try-catch之后的语句也会执行
   *      使用finally原因：如果在catch中return了，那么try-catch之后的语句就不会执行了
   *                     这就是为什么需要finally的原因，它可以保证其中代码一定会执行。
   */
  @Test
  public void testFileReader02(){
    FileReader fr = null;
    try {
      File file = new File("src\\main\\resources\\hello.txt");
      fr = new FileReader(file);
      int data;
      while((data = fr.read()) != -1){
        System.out.println((char) data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        //防止FileReader在实例化过程中抛出异常，会导致空指针
        if (fr != null){
          fr.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  @Test
  public void testFileReader03(){
    FileReader fr = null;
    try {
      File file = new File("src\\main\\resources\\hello.txt");
      fr = new FileReader(file);
      char[] cbuf = new char[5];
      int len;
      while((len = fr.read(cbuf)) != -1){
        //必须指定长度是len，代表每次读取的长度，不能使用cbuf的长度
        System.out.print(new String(cbuf, 0, len));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fr != null){
          fr.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * 从内存中写出数据到磁盘的文件中
   *
   *    对应的file可以不存在
   *        不存在：创建
   *        存在：覆盖或者追加
   */
  @Test
  public void testFileWriter01(){
    FileWriter fw = null;
    try {
      //1，提供File类的对象，指明写出到的文件
      File file = new File("src\\main\\resources\\hello1.txt");
      //2，提供FileWriter对象，用于数据的写出
      fw = new FileWriter(file);
      //3，具体写出的操作
      fw.write("I have a dream!\n");
      fw.write("you have a dream!");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //4，流资源的关闭
      try {
        if (Objects.nonNull(fw)){
          fw.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  @Test
  public void testFileReaderAndFileWriter(){
    FileReader fr = null;
    FileWriter fw = null;
    try {
      //1，创建File类的对象，指明读取和写入的文件
      File srcFile = new File("src\\main\\resources\\src.txt");
      File destFile = new File("src\\main\\resources\\dest.txt");
      //2，创建输入流和输出流的对象
      fr = new FileReader(srcFile);
      fw = new FileWriter(destFile);
      //3，数据读取和写入操作
      int len;
      char[] cbuf = new char[5];
      while((len = fr.read(cbuf)) != -1){
        fw.write(cbuf, 0 ,len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //4，流关闭
      try {
        if (Objects.nonNull(fr)){
          fr.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      //上面流关闭抛出异常，下面的流还会关闭吗 -------会关闭。try catch处理异常，后面语句会继续执行
      try {
        if (Objects.nonNull(fw)){
          fw.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
