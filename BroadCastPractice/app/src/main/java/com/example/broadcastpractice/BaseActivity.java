package com.example.broadcastpractice;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private MyBcReceiver myBcReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.jay.mybcreceiver.LOGIN_OTHER");
        myBcReceiver = new MyBcReceiver();
        registerReceiver(myBcReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myBcReceiver != null){
            unregisterReceiver(myBcReceiver);
            myBcReceiver = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
