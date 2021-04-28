package com.my.mybatis.dao;

import com.my.mybatis.domain.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminUserMapper {

    @Select("select * from land_admin_user where id = #{id}")
    AdminUser selectById(@Param("id") Long id);

    @Select("select * from land_admin_user where is_delete = #{isDelete}")
    List<AdminUser> selectByIsDelete(@Param("isDelete") Integer isDelete);

    List<AdminUser> selectByNickname(@Param("nickname") String nickname);

    List<AdminUser> selectByUsername(@Param("username") String username);
}
