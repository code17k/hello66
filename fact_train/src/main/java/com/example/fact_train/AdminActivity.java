package com.example.fact_train;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        
        TextView tvWelcome = findViewById(R.id.tvAdminWelcome);
        tvWelcome.setText("欢迎管理员登录");
        
        // 这里可以添加管理员特有的功能

        findViewById(R.id.btnLogout1).setOnClickListener(v -> {
            // 清除自动登录状态但保留记住密码状态
            getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("autoLogin", false)
                    .apply();
            finish();
        });
    }
}