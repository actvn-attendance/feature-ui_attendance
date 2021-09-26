package com.example.attendanceqrcode.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.model.GroupChat;


import java.util.List;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.ViewHolder> {
     FragmentActivity activity;
     List<GroupChat> groupChatList;
     OnClickChatGroup onClickChatGroup;

    public GroupChatAdapter(FragmentActivity activity, List<GroupChat> groupChatList, OnClickChatGroup onClickChatGroup) {
        this.activity = activity;
        this.groupChatList = groupChatList;
        this.onClickChatGroup = onClickChatGroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_group_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupChat groupChat = groupChatList.get(position);

        holder.imgGroup.setImageResource(groupChat.getImageGroup());
        holder.tvNameGroup.setText(groupChat.getNameGroup());
        holder.tvDescription.setText(groupChat.getDescription());

        holder.rlGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChatGroup.onClickItem();
            }
        });



    }

    @Override
    public int getItemCount() {
        return groupChatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGroup;
        TextView tvNameGroup;
        TextView tvDescription;
        RelativeLayout rlGroupChat;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            imgGroup = itemView.findViewById(R.id.img_group);
            tvNameGroup = itemView.findViewById(R.id.tv_nameGroup);
            tvDescription = itemView.findViewById(R.id.tv_description);
            rlGroupChat = itemView.findViewById(R.id.rl_ChatGroup);

        }
    }

    public interface OnClickChatGroup{
        void onClickItem();
    }
}
