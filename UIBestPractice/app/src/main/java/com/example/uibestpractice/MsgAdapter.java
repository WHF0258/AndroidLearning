package com.example.uibestpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> msgList; //数据源
    //ViewHolder内部类
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView left_msg;
        TextView right_msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = itemView.findViewById(R.id.left_layout);
            rightLayout = itemView.findViewById(R.id.right_layout);
            left_msg = itemView.findViewById(R.id.left_msg);
            right_msg = itemView.findViewById(R.id.right_msg);
        }
    }
    //构造方法  传入数据源
    public MsgAdapter(List<Msg> msgList){
        this.msgList = msgList;
    }

    //创建ViewHolder实例
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载msg_item布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }
    //绑定数据源
    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED){
            //搜到信息 显示左边的消息布局  隐藏右边的消息布局
        }
    }



    @Override
    public int getItemCount() {
        return 0;
    }


}
