package com.example.attendanceqrcode.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.model.Message;
import com.example.attendanceqrcode.utils.Utils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<Message> messageList;

    public MessageAdapter(FragmentActivity activity, List<Message> messageList) {
        this.activity = activity;
        this.messageList = messageList;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        int uid  = Utils.getUserID(activity);

        if(message.getUid() == uid){
            holder.layoutCompat.setGravity(Gravity.END);
            holder.tvMessage.setBackgroundResource(R.color.background);
            holder.tvFullName.setVisibility(View.INVISIBLE);
        }else{
            holder.layoutCompat.setGravity(Gravity.START);
            holder.tvMessage.setBackgroundColor(Color.GRAY);
            holder.tvFullName.setVisibility(View.VISIBLE);
            holder.tvFullName.setText(message.getFullName());
        }

        holder.tvMessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage, tvFullName;
        private LinearLayoutCompat layoutCompat;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvFullName = itemView.findViewById(R.id.tv_FullName);
            layoutCompat = itemView.findViewById(R.id.layout_a_chat);
        }
    }
}
