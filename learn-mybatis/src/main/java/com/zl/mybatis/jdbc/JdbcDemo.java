package com.zl.mybatis.jdbc;

import com.zl.mybatis.interfacebind.entity.EinvoiceApply;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.apache.commons.compress.utils.Lists;

/**
 * @author: zhangliangx
 * @Date: 2020/9/20 15:42
 * @Description:  ORM框架：就是将数据库字段  映射  为对象的属性
 *
 *
 * 1，加载JDBC驱动
 *
 * 2，获得连接 Connection
 *
 * 3，预编译sql PrepareStatement
 *
 * 4，设置参数
 *
 * 5，执行SQL ResultSet
 *
 * 6，关闭连接
  */
public class JdbcDemo {

  public void test01() throws Exception{

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection("localhost:3306/secondweek?");
      statement = connection.createStatement();
      resultSet = statement.executeQuery("");
      List<EinvoiceApply> einvoiceApplyList = Lists.newArrayList();
      while (resultSet.next()){
        int id = resultSet.getInt(1);
        String fpqqlsh = resultSet.getString(2);
        einvoiceApplyList.add(new EinvoiceApply(id, fpqqlsh));
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } finally {
      if (Objects.nonNull(resultSet)){
        try {
          resultSet.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
      if (Objects.nonNull(statement)){
        try {
          statement.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
      if (Objects.nonNull(connection)){
        try {
          connection.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    }


  }

}
