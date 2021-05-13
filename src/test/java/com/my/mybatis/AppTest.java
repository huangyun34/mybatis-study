package com.my.mybatis;

import static org.junit.Assert.assertTrue;

import com.my.mybatis.dao.AdminUserMapper;
import com.my.mybatis.domain.AdminUser;
import com.my.mybatis.domain.AdminUserUseTypeHandler;
import com.my.mybatis.typehandler.bo.AESObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    private static Logger logger = Logger.getLogger(AppTest.class);

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test1() {
        logger.info("12323");
        logger.debug("gjjf");
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        System.out.println(sqlSession.selectList("com.my.mybatis.dao.AdminUserMapper.selectById", 31317372L));
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        System.out.println(mapper.selectById(31317372L));
    }

    @Test
    public void test2() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        System.out.println(sqlSession.selectList("com.my.mybatis.dao.AdminUserMapper.selectById", 31317372L));
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        System.out.println(mapper.selectById(31317372L));
    }

    @Test
    public void test3() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        int row = mapper.update(new AESObject("15599999999"), 31317372L);
        System.out.println(row);
    }

    @Test
    public void test4() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        System.out.println(sqlSession.selectList("com.my.mybatis.dao.AdminUserMapper.selectById", 31317372L));
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        AdminUser adminUser = new AdminUser();
        adminUser.setId(31317372L);
        adminUser.setMobile("15599999999");
        int row = mapper.updateDomain(adminUser);
        System.out.println(adminUser.getMobile());
        System.out.println(row);
//        System.out.println(mapper.selectById(31317372L));
    }

    @Test
    public void test5() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        AdminUser adminUser = new AdminUser();
        adminUser.setId(19867673L);
        adminUser.setMobile("18176567636");
        int row = mapper.insertSelective(adminUser);
        System.out.println(row);
    }

    @Test
    public void test6() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        AdminUserUseTypeHandler adminUser = new AdminUserUseTypeHandler();
        adminUser.setId(19867673L);
        adminUser.setMobile(new AESObject("18176567636"));
        adminUser.setCreatedBy("1");
        adminUser.setUpdatedBy("2");
        int row = mapper.insertSelectiveUseTypeHandler(adminUser);
        System.out.println(adminUser);
        System.out.println(row);
    }

    @Test
    public void test7() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        List<AdminUserUseTypeHandler> adminUsers = mapper.selectByMobile(new AESObject("13120210001"));
        System.out.println(adminUsers);
    }
}
