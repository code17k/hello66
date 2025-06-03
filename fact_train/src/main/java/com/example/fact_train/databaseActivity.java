package com.example.fact_train;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class databaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        String mDatabaseName=getFilesDir()+"/test.db";
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(v -> {
            try {
                SQLiteDatabase db = openOrCreateDatabase(mDatabaseName, Context.MODE_PRIVATE, null);
                String desc = String.format("路径: %s\n创建状态: %s", db.getPath(), "成功");
                TextView textView = findViewById(R.id.tv1);
                textView.setText(desc);
            } catch (Exception e) {
                String desc = "数据库创建失败: " + e.getMessage();
                TextView textView = findViewById(R.id.tv1);
                textView.setText(desc);
            }
        });

        button2.setOnClickListener(v -> {
            boolean result = deleteDatabase(mDatabaseName);
            String desc = String.format("删除状态: %s", result ? "成功" : "失败");
            TextView textView = findViewById(R.id.tv1);
            textView.setText(desc);
        });



    }
}