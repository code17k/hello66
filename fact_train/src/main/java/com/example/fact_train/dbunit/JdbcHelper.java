package com.example.fact_train.dbunit;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcHelper {
    // 改为可配置的静态变量
    private static String url = "jdbc:mysql://10.0.2.2:3306/androidpt?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static String username = "root";
    private static String password = "123456";

    public static void setConfig(String newUrl, String newUsername, String newPassword) {
        url = newUrl;
        username = newUsername;
        password = newPassword;
    }

    public static Connection getConnection() {
        try {
            Log.d("JdbcHelper", "Attempting to load MySQL driver...");
            Class.forName("com.mysql.jdbc.Driver"); // 改为兼容5.x版本的驱动类名
            
            Log.d("JdbcHelper", "Connecting to database: " + url);
            Connection con = DriverManager.getConnection(url, username, password);
            Log.d("JdbcHelper", "Database connection established successfully");
            return con;
            
        } catch (ClassNotFoundException e) {
            Log.e("JdbcHelper", "MySQL driver not found | " + e.getClass().getSimpleName(), e);
        } catch (SQLException e) {
            Log.e("JdbcHelper", "Connection failed | " + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
        }
        return null;
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                if(!con.isClosed()) {
                    con.close();
                    Log.d("JdbcHelper", "Database connection closed");
                }
            } catch (SQLException e) {
                Log.e("JdbcHelper", "Connection closure failed | " + e.getClass().getSimpleName(), e);
            }
        }
    }




}