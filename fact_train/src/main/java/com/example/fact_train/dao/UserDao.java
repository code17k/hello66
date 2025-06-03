package com.example.fact_train.dao;

import android.util.Log;

import com.example.fact_train.bean.User;
import com.example.fact_train.dbunit.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao{

    public void test() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Log.d("UserDao", "Executing test method...");
            con = JdbcHelper.getConnection();
            if (con == null) {
                Log.e("UserDao", "Failed to obtain database connection");
                return;
            }
            
            String sql = "select * from userinfo";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int recordCount = 0;
            while(rs.next()) {
                // 可选：添加实际数据处理逻辑（如打印字段值）
                String usr = rs.getString("name");
                String pwd = rs.getString("psw");
                Log.d("UserDao", "Record " + (recordCount + 1) + ": Name=" + usr + ", Password=" + pwd);
                recordCount++;
            }
            Log.d("UserDao", "Query successful, records count: " + recordCount);

        } catch (SQLException e) {
            Log.e("DB_ERROR", "Database operation failed | " + e.getClass().getSimpleName(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                Log.e("DB_ERROR", "Resource closure failed | " + ex.getClass().getSimpleName(), ex);
            }
        }
    }

    public User queryUserByName(String name) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = JdbcHelper.getConnection();
            if (con == null) return null;
            
            String sql = "select * from userinfo where name = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("psw")); // 补全密码字段
                return user;
            } else {
                Log.d("UserDao", "User not found: " + name);
                return null;
            }
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Query failed [SQLState:" + e.getSQLState() + "]", e);
            return null;
        } finally {
            try { if(rs != null) rs.close(); } catch (SQLException ex) {}
            try { if(pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if(con != null) con.close(); } catch (SQLException ex) {}
        }
    }

    public int insertUser(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            Log.d("UserDao", "Inserting user: " + user.getName());
            con = JdbcHelper.getConnection();
            if (con == null) {
                Log.e("UserDao", "Failed to obtain database connection");
                return 0;
            }
            String sql = "insert into userinfo (name, psw) values (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            int result = pstmt.executeUpdate();
            Log.d("UserDao", "Insertion result: " + result);
            return result;
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Database operation failed | " + e.getClass().getSimpleName(), e);
            return 0;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                Log.e("DB_ERROR", "Resource closure failed | " + ex.getClass().getSimpleName(), ex);
            }
        }
    }

    public boolean validateUser(String username, String password) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = JdbcHelper.getConnection();
            if (con == null) return false;
            
            String sql = "SELECT 1 FROM userinfo WHERE name = ? AND psw = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            
            return rs.next(); // 如果有结果返回true，否则false
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Validation failed | " + e.getClass().getSimpleName(), e);
            return false;
        } finally {
            try { if(rs != null) rs.close(); } catch (SQLException ex) {}
            try { if(pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if(con != null) con.close(); } catch (SQLException ex) {}
        }
    }
}


