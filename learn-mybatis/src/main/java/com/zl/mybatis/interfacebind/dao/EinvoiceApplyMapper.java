package com.zl.mybatis.interfacebind.dao;

import com.zl.mybatis.interfacebind.entity.EinvoiceApply;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 接口绑定： xml中的sql绑定 <select id="getUser" parameterType="int"
 * resultType="com.mayikt.entity.UserEntity"> select * from user where id=#{id}
 * </select>
 * 注解绑定
 * @Select("select * from inv_apply")
 * <p>
 * <p>
 * mapper接口没有实现类，是如何实例化?（接口不能使用反射初始化） jdk动态代理，底层使用aop实现 1，创建被代理的接口和类
 * 2，实现InvocationHandler接口，为目标接口中申明的所有方法进行统一处理 3，调用Proxy的静态方法，创建代理类并生成相应的代理对象
 */
public interface EinvoiceApplyMapper {

  @Select("select * from inv_apply id = #{id}")
  List<EinvoiceApply> selectEinvoiceApplyList(Integer id);
}
