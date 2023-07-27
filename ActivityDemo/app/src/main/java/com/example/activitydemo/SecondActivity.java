package com.example.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        //通过Intent 获取活动传过来的信息
        Intent intent = getIntent();
        String extra_data = intent.getStringExtra("extra_data");
        Log.d(TAG, "Task id is " + getTaskId());

        //绑定点击事件  添加返回数据的逻辑
        Button button = findViewById(R.id.button_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
//                startActivity(intent);
//                intent.putExtra("data_return","Hello FisrtActivity");
                //参数  ：  1.处理结果   2.intent
//                setResult(RESULT_OK,intent);
                //活动结束  调用FirstActivity的onActivityResult方法
//                finish();

                //调用封装的启动方式
                FirstActivity.actionStart(SecondActivity.this,"123");
            }
        });
    }
    //Back键绑定数据返回行为
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello FisrtActivity From BackPressed");
        //参数  ：  1.处理结果   2.intent
        setResult(RESULT_OK,intent);
        //活动结束  调用FirstActivity的onActivityResult方法
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}