package com.zl.mybatis.interfacebind;

import com.zl.mybatis.interfacebind.entity.EinvoiceApply;
import com.zl.mybatis.interfacebind.dao.EinvoiceApplyMapper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MybatisDemo {


  public List<EinvoiceApply> test01(Integer id, String fpqqlsh) throws IOException {
    //从xml配置文件中构建sqlsessionfactory
    InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    List<EinvoiceApply> einvoiceApplyList = sqlSession
        .selectList("com.zl.mybatis.interfacebind.dao.EinvoiceApplyMapper.selectEinvoiceApplyList",
            1);
    return einvoiceApplyList;
  }

  public List<EinvoiceApply> test02(Integer id, String fpqqlsh) throws IOException {
    //从xml配置文件中构建sqlsessionfactory
    InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    EinvoiceApplyMapper mapper = sqlSession.getMapper(EinvoiceApplyMapper.class);
    List<EinvoiceApply> einvoiceApplyList = mapper.selectEinvoiceApplyList(1);
    return einvoiceApplyList;
  }
  public static void main(String[] args) {
    EinvoiceApplyMapper einvoiceApplyMapper = (EinvoiceApplyMapper) Proxy
        .newProxyInstance(MybatisDemo.class.getClassLoader(),
            new Class<?>[]{EinvoiceApplyMapper.class}, new InvocationHandler() {
              //为目标接口中申明的所有方法进行统一处理
              @Override
              public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println(method.getName());
                System.out.println(Arrays.toString(args));

                Map<String, Object> nameArgMap = buildMethodNameArgMap(method, args);
                System.out.println(nameArgMap.toString());
                Select select = method.getAnnotation(Select.class);
                if (Objects.nonNull(select)) {
                  List<String> values = Arrays.stream(select.value()).collect(Collectors.toList());
                  System.out.println(values.toString());
                  values.forEach(value -> {
                    value = parseSql(value,nameArgMap);
                    System.out.println(value);
                  });
                }
                return null;
              }
            });

    einvoiceApplyMapper.selectEinvoiceApplyList(1);
  }

  /**
   * 替换参数，构造最终执行的sql
   * @param sql
   * @param nameArgMap
   * @return
   */
  public static String parseSql(String sql, Map<String, Object> nameArgMap){
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sql.length(); i++) {
      char c = sql.charAt(i);
      if (c == '#'){
        char next = sql.charAt(i + 1);
        if (next != '{'){
          throw new RuntimeException(String.format(("这里应该为#{\n sql:%s\n index:%d"), sb.toString(), i+1));
        }
        StringBuilder argSb = new StringBuilder();
        i = parseArg(argSb, sql, i+1);
        String argName = argSb.toString();
        Object argValue = nameArgMap.get(argName);
        if (argValue == null){
          throw new RuntimeException("找不到参数");
        }
        sb.append(argValue);
        continue;
      }
      sb.append(c);
    }
    return sb.toString();
  }

  private static int parseArg(StringBuilder argSb, String sql, int i) {
    i++;
    for(; i<sql.length(); i++){
      char c = sql.charAt(i);
      if (c != '}'){
        argSb.append(c);
        continue;
      }
      if (c == '}'){
        return i;
      }
    }
    throw new RuntimeException("找不到}");
  }

  /**
   * 构造参数名称和参数值的map
   *  String  -> Object
   * @param method
   * @param args
   * @return
   */
  public static Map<String, Object> buildMethodNameArgMap(Method method, Object[] args) {
    Map<String, Object> nameArgMap = new HashMap<>(8);
    AtomicInteger index = new AtomicInteger(0);
    Parameter[] parameters = method.getParameters();
    Arrays.asList(parameters).forEach(parameter -> nameArgMap.put(parameter.getName(), args[index
        .getAndIncrement()]));
    return nameArgMap;
  }

}
