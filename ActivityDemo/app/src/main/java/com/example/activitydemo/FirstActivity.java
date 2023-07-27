package com.example.activitydemo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;

public class FirstActivity extends BaseActivity {
    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Task id is " + getTaskId());

        setContentView(R.layout.first_layout);  //加载布局


        //获取布局元素
        Button button = findViewById(R.id.button_1);
        button.setOnClickListener(new View.OnClickListener() {  //点击监听器
            @Override
            public void onClick(View view) {  //点击触发行为
                //调用Toast静态方法  创建toast对象  调用show方法显示
                //参数 ： 1.上下文 （活动本身就是context ：FirstActivity）  2.文本内容  3.显示时长
                Toast.makeText(FirstActivity.this,"you clicked button1",
                        Toast.LENGTH_SHORT).show();
                //销毁活动
//                finish();

                //使用Intent 在活动之间穿梭之显示intent
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                //将意图传入startActivity()方法
//                startActivity(intent);

                //使用Intent 在活动之间穿梭之隐式intent 用法1
//                Intent intent = new Intent("com.example.activitytest.ACTION_START");
//                intent.addCategory("android.intent.category.APP_CALENDAR");
                //将意图传入startActivity()方法
//                startActivity(intent);

                //使用Intent 在活动之间穿梭之隐式intent 用法2  安卓系统内置动作ACTION_VIEW
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:18280935085"));
                //将意图传入startActivity()方法
//                startActivity(intent);

                //使用Intent 传递数据
//                String data = "Hello SecondActivity";
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                intent.putExtra("extra_data",data);
//                startActivity(intent);

                //返回数据的隐式调用
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                String data = "Hello SecondActivity";
//                intent.putExtra("extra_data",data);
                //参数 ： intent  请求码
//                startActivityForResult(intent,1);

                //观察standard启动方式
//                Intent intent = new Intent(FirstActivity.this, FirstActivity.class);
//                startActivity(intent);

            }
        });
    }
    public static void actionStart(Context context,String parm){
        Intent intent = new Intent(context, FirstActivity.class);
        intent.putExtra("param",parm);
        context.startActivity(intent);

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");

        Intent intent = getIntent();
        String extra_data = intent.getStringExtra("param");
        Log.d(TAG, "123" + extra_data);

    }

    //得到启动活动销毁后返回的数据  requestCode请求码确定数据来源  resultCode处理结果   Intent带的返回数据
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        //没有实际作用   正如Su-Au Hwang所指出的那样，我关于基类行为在未来发生变化的预测已经成为现实
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String data_return = data.getStringExtra("data_return");
                    Log.d("FirstActivity", data_return);
                }
        }
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //得到 MenuInflater对象   inflate方法创建菜单
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    //菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.add_item:
                Toast.makeText(FirstActivity.this,"Add",Toast.LENGTH_SHORT).show();
                break;

            case R.id.remove_item:
                Toast.makeText(FirstActivity.this,"Del",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}