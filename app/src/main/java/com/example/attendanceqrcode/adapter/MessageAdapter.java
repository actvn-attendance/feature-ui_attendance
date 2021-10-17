package com.example.attendanceqrcode.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.components.AppAlertDialog;
import com.example.attendanceqrcode.model.Message;
import com.example.attendanceqrcode.utils.Utils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<Message> messageList;
    private int uid;

    public MessageAdapter(FragmentActivity activity, List<Message> messageList) {
        this.activity = activity;
        this.messageList = messageList;
        this.uid = Utils.getUserID(activity);
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (message.getUid() == uid) {
            holder.layoutCompat.setGravity(Gravity.END);
            holder.tvMessage.setBackgroundResource(R.drawable.bg_mesage_user);
            holder.tvFullName.setVisibility(View.GONE);
            holder.tvMessage.setText(message.getMessage());
        } else {
            holder.layoutCompat.setGravity(Gravity.START);
            holder.tvMessage.setBackgroundResource(R.drawable.bg_message_other);
            holder.tvFullName.setVisibility(View.VISIBLE);
            holder.tvFullName.setText(message.getFullName());
            holder.tvMessage.setText(message.getMessage());
        }

        if (message.getType().contains("text")) {
            holder.tvMessage.setText(message.getMessage());
            holder.imageView.setVisibility(View.GONE);
        } else if (message.getType().contains("image")) {
            holder.imageView.setVisibility(View.VISIBLE);
            String url = message.getMessage();
            Glide
                    .with(activity)
                    .load(url)
                    .centerCrop()
                    .into(holder.imageView);
            holder.tvMessage.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (messageList == null) {
            AppAlertDialog.showTokenTimeOutDialog(activity);
        }
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage, tvFullName;
        private LinearLayoutCompat layoutCompat;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvFullName = itemView.findViewById(R.id.tv_FullName);
            layoutCompat = itemView.findViewById(R.id.layout_a_chat);
            imageView = itemView.findViewById(R.id.imgView);
        }

    }
}
