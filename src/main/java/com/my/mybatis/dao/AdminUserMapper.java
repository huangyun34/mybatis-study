package com.my.mybatis.dao;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.anno.DESParameter;
import com.my.mybatis.domain.AdminUser;
import com.my.mybatis.domain.AdminUserUseTypeHandler;
import com.my.mybatis.typehandler.bo.AESObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import sun.security.krb5.internal.crypto.Aes128;

import java.util.List;

public interface AdminUserMapper {

    @Select("select * from land_admin_user where id = #{id}")
    AdminUser selectById(@Param("id") Long id);

    @Select("select * from land_admin_user where is_delete = #{isDelete}")
    List<AdminUser> selectByIsDelete(@Param("isDelete") Integer isDelete);

    List<AdminUser> selectByNickname(@Param("nickname") String nickname);

    List<AdminUser> selectByUsername(@Param("username") String username);

    @DESDomain
    @Update("update land_admin_user set mobile = #{mobile} where id = #{id}")
    int update(@Param("mobile") AESObject mobile, @Param("id") Long id);

    @Update("update land_admin_user set mobile = #{mobile} where id = #{id}")
    int updateDomain(AdminUser adminUser);

    int insertSelective(AdminUser adminUser);

    int insertSelectiveUseTypeHandler(AdminUserUseTypeHandler adminUser);

    /**
     *
     * @param aesObject
     * @return
     */
    @Select("select * from land_admin_user where mobile = #{mobile}")
    List<AdminUserUseTypeHandler> selectByMobile(@Param("mobile") AESObject aesObject);
}
