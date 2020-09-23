package com.zl.io.bio.file;

import java.io.File;

/**
 * @author: zhangliangx
 * @Date: 2020/8/26 10:07
 * @Description:
 *    File类的一个对象，待变一个文件或者一个文件夹
 *    File类只涉及文件或者文件夹的创建、删除、重命名、修改时间、文件大小等操作
 *        写入和读取文件内容必须使用io流来完成
 */
public class FileTest {
  /**
  1,如何创建File类的实例
    File(URL url)
    Fiel(String filePath)
    Fiel(String parentPath, String childPath)
    Fiel(File ParentFile, String childPath)

  2,行对路径和绝对路径

  3，路径分隔符
    windows  \\
    unix     /
   */
  public void test01(){

    File file = new File("");

  }

}
