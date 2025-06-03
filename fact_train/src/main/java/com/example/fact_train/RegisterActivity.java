package com.example.fact_train;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fact_train.bean.User;
import com.example.fact_train.dao.UserDao;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etRegUsername, etRegPassword, etConfirmPassword;
    private CheckBox cbAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化视图
        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        cbAgreement = findViewById(R.id.cbAgreement);
        TextView tvGoToLogin = findViewById(R.id.kk); // 使用布局文件中定义的id
        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // 关闭当前注册页面
        });
        Button btnRegister = findViewById(R.id.btnRegister); // 确保使用Button而不是TextView
        btnRegister.setOnClickListener(v -> validateAndRegister()); // 确保点击事件绑定正确
    }

    private void validateAndRegister() {
        // 获取输入值
        String username = etRegUsername.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // 非空检查
        if (username.isEmpty()) {
            showError(etRegUsername, "请输入用户名");
            return;
        }

        if (password.isEmpty()) {
            showError(etRegPassword, "请输入密码");
            return;
        }

        if (confirmPassword.isEmpty()) {
            showError(etConfirmPassword, "请确认密码");
            return;
        }

        // 密码一致性检查
        if (!password.equals(confirmPassword)) {
            showError(etConfirmPassword, "两次输入的密码不一致");
            return;
        }

        // 协议勾选检查
        if (!cbAgreement.isChecked()) {
            Toast.makeText(this, "请同意用户协议", Toast.LENGTH_SHORT).show();
            return;
        }


        // 所有验证通过后的操作
        // 确保线程正确启动
        new Thread(() -> {
            try {
                UserDao userDao = new UserDao();
                User newUser = new User(username, password);
                int result = userDao.insertUser(newUser);
                
                runOnUiThread(() -> {
                    if(result > 0) {
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "注册失败，用户名可能已存在", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> 
                    Toast.makeText(this, "注册异常: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }



    private void showError(TextInputEditText editText, String errorMsg) {
        editText.setError(errorMsg);
        editText.requestFocus();
    }


    

}


