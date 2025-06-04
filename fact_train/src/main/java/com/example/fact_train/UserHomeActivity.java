package com.example.fact_train;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHomeActivity extends AppCompatActivity {
    // 移除常量声明（可选，可直接使用 R.id.*）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // 默认加载商品主页
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProductsFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int id = item.getItemId(); // 获取菜单项 ID

                    // 使用 if-else 替代 switch
                    if (id == R.id.nav_products) {
                        selectedFragment = new ProductsFragment();
                    } else if (id == R.id.nav_publish) {
                        selectedFragment = new PublishFragment();
                    } else if (id == R.id.nav_profile) {
                        selectedFragment = new ProfileFragment();
                    }

                    // 确保选择了有效的 Fragment
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        return true;
                    }
                    return false;
                }
            };
}