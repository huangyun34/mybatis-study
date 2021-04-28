package com.my.mybatis;

import org.junit.Test;

import java.sql.*;

public class Jdbctest {

    @Test
    public void test1() throws Exception {
        String url = "jdbc:mysql://10.200.80.221/land?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Hongkong";
        String username = "land";
        String pw = "dev%5tgdfgsdf";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, pw);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from land_Admin_user where id = ?");
        preparedStatement.setLong(1, 1334766243160391680L);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getLong(1));
            System.out.println(resultSet.getLong(2));
        }
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
