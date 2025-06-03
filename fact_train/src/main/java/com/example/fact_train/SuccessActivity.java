package com.example.fact_train;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        String username = getIntent().getStringExtra("USERNAME");
        tvWelcome.setText("欢迎回来，" + username + "！");
        
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            // 清除自动登录状态但保留记住密码状态
            getSharedPreferences("user_prefs", MODE_PRIVATE)
                .edit()
                .putBoolean("autoLogin", false)
                .apply();
            finish();
        });
    }
}