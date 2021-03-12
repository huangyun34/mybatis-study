package com.my.spring;

import static org.junit.Assert.assertTrue;

import com.my.spring.bean.A;
import com.my.spring.bean.SiChuan;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit test for simple App.
 */
public class AppTest
{

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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring.xml");
        SiChuan siChuan = (SiChuan) context.getBean("siChuan");
        System.out.println(siChuan.getDesc());
    }

    @Test
    public void test2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring.xml");
        final TService tService = (TService) context.getBean("TService");
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    A a = new A();
                    a.setName("a");
                    a.setSex("male");
                    tService.setA(a);
                    tService.t("hello");
                }
            });
        }
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    A a = new A();
                    a.setName("b");
                    a.setSex("female");
                    tService.setA(a);
                    tService.t("bye");
                }
            });
        }

    }
    @Test
    public void test3() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring.xml");
        A a = (A) context.getBean("a");
        a.hallo();
    }
}
