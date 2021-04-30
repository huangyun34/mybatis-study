package com.my.mybatis;

import static org.junit.Assert.assertTrue;

import com.my.mybatis.dao.AdminUserMapper;
import com.my.mybatis.domain.AdminUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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
//        System.out.println(sqlSession.selectList("com.my.mybatis.dao.AdminUserMapper.selectById", 31317372L));
        AdminUserMapper mapper = sqlSession.getMapper(AdminUserMapper.class);
        int row = mapper.update("15599999999", 31317372L);
        System.out.println(row);
//        System.out.println(mapper.selectById(31317372L));
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
        System.out.println(row);
//        System.out.println(mapper.selectById(31317372L));
    }
}
