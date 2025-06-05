package com.example.fact_train;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fact_train.dao.UserDao;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private CheckBox cbRemember, cbAutoLogin;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 初始化视图
        sp = getSharedPreferences("user_prefs", MODE_PRIVATE);
        cbRemember = findViewById(R.id.cbRemember);
        cbAutoLogin = findViewById(R.id.cbAutoLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        TextView tvGoToRegister = findViewById(R.id.tvGoToRegister);
        Button btnLogin = findViewById(R.id.btnLogin);

        // 初始化复选框状态
        boolean remember = sp.getBoolean("remember", false);
        cbRemember.setChecked(remember);
        cbAutoLogin.setChecked(sp.getBoolean("autoLogin", false));
        
        if(remember) {
            etUsername.setText(sp.getString("username", ""));
            etPassword.setText(sp.getString("password", ""));
        }

        // 自动登录检查
        if(cbAutoLogin.isChecked()) {
            performLogin(sp.getString("username", ""), sp.getString("password", ""));
        }

        // 记住密码复选框监听
        cbRemember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sp.edit().putBoolean("remember", isChecked).apply();
            if(!isChecked) {
                cbAutoLogin.setChecked(false);
                sp.edit().putBoolean("autoLogin", false).apply();
            }
        });

        // 自动登录复选框监听
        cbAutoLogin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sp.edit().putBoolean("autoLogin", isChecked).apply();
            if(isChecked) {
                cbRemember.setChecked(true);
                sp.edit().putBoolean("remember", true).apply();
            }
        });

        // 登录按钮点击事件
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            performLogin(username, password);
        });

        // 注册跳转
        tvGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void performLogin(String username, String password) {
        new Thread(() -> {
            UserDao userDao = new UserDao();
            boolean success = userDao.validateUser(username, password);
            
        //     runOnUiThread(() -> {
        //         if(success) {
        //             // 保存凭证
        //             if(cbRemember.isChecked()) {
        //                 sp.edit()
        //                     .putString("username", username)
        //                     .putString("password", password)
        //                     .apply();
        //             }
                    
        //             Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
        //             intent.putExtra("USERNAME", username);
        //             startActivity(intent);
        //             finish();
        //         } else {
        //             Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        //         }
        //     });
        // }).start();

        runOnUiThread(() -> {
            if(success) {

                if(cbRemember.isChecked()) {
                                    sp.edit()
                                        .putString("username", username)
                                       .putString("password", password)
                                      .apply();
                                 }

                // 检查是否是admin账号
                if(username.equals("admin") && password.equals("12345678")) {
                    // 跳转到管理员界面
//                   Toast.makeText(this, "管理员登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();

                } else {

                    if(cbRemember.isChecked()) {
                        sp.edit()
                                .putString("username", username)
                                .putString("password", password)
                                .apply();
                    }

                    // 普通用户登录
                    Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                                 intent.putExtra("USERNAME", username);
                                 startActivity(intent);
                    finish();

                }



            } else {
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }).start();

        
    }



}