package com.example.uibestpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //初始化数据
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsg();     //初始化数据源
        //输入框
        inputText = findViewById(R.id.input_text);
        //发送按钮
        send = findViewById(R.id.send);
        //msgRecyclerView布局
        msgRecyclerView = findViewById(R.id.msg_recycle_view);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        //适配器实例
        msgAdapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);
        //绑定响应事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入
                String inputString = inputText.getText().toString();
                if (!"".equals(inputString)){
                    Msg msg = new Msg(inputString, Msg.TYPE_SEND);
                    msgList.add(msg);
                    //新消息 刷新msgRecyclerView显示
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    //定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    //清空输入框的内容
                    inputText.setText("");
                }
            }
        });

    }

    private void initMsg() {
        Msg msg1 = new Msg("Hello gay1",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello gay2",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("Hello gay3",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4 = new Msg("Hello gay4",Msg.TYPE_SEND);
        msgList.add(msg4);
    }
}