package com.example.fact_train;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fact_train.dao.UserDao;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etUsername, etPassword;
    private CheckBox cbRemember, cbAutoLogin;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("user_prefs", MODE_PRIVATE);
        
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        cbAutoLogin = findViewById(R.id.cbAutoLogin);

        // 自动填充记住的账号密码
        if(sp.getBoolean("remember", false)) {
            etUsername.setText(sp.getString("username", ""));
            etPassword.setText(sp.getString("password", ""));
            cbRemember.setChecked(true);
        }

        // 自动登录检查
        if(sp.getBoolean("autoLogin", false)) {
            performLogin(sp.getString("username", ""), sp.getString("password", ""));
        }

        // 记住密码复选框监听
        cbRemember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sp.edit().putBoolean("remember", isChecked).apply();
            if(!isChecked) {
                // 如果取消记住密码，也取消自动登录
                cbAutoLogin.setChecked(false);
                sp.edit().putBoolean("autoLogin", false).apply();
            }
        });

        // 自动登录复选框监听
        cbAutoLogin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sp.edit().putBoolean("autoLogin", isChecked).apply();
            if(isChecked) {
                // 如果启用自动登录，必须启用记住密码
                cbRemember.setChecked(true);
                sp.edit().putBoolean("remember", true).apply();
            }
        });
    }

    private void performLogin(String username, String password) {
        // 添加输入验证
        if(username.isEmpty() || password.isEmpty()) {
            runOnUiThread(() -> 
                Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show());
            return;
        }
        
        // 添加密码强度检查
        if(password.length() < 6) {
            runOnUiThread(() -> 
                Toast.makeText(this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show());
            return;
        }
        // 实现登录逻辑
        new Thread(() -> {
            UserDao userDao = new UserDao();
            boolean success = userDao.validateUser(username, password);
            
            runOnUiThread(() -> {
                if(success) {
                    // 检查是否是admin账号
                    if(username.equals("admin") && password.equals("12345678")) {
                        // 跳转到管理员界面
                        Toast.makeText(this, "管理员登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, AdminActivity.class));
                    } else {
                        // 普通用户登录
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, UserHomeActivity.class));
                    }
                    finish();
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();

    }
}