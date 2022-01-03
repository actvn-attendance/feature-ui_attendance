package com.example.attendanceqrcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.model.ChatList;
import com.example.attendanceqrcode.components.AppAlertDialog;
import com.example.attendanceqrcode.model.MemberChatList;
import com.example.attendanceqrcode.utils.Utils;


import java.util.List;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.ViewHolder> {
     FragmentActivity activity;
     List<ChatList> groupChatList;
     OnClickChatGroup onClickChatGroup;

    public GroupChatAdapter(FragmentActivity activity, List<ChatList> groupChatList, OnClickChatGroup onClickChatGroup) {
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
        ChatList groupChat = groupChatList.get(position);
        if (position == groupChatList.size() - 1)
        {
            holder.view.setVisibility(View.GONE);
        }
        MemberChatList member = new MemberChatList();
        int meID = Utils.getUserID(activity);

        for(MemberChatList mem : groupChat.getMembers()){
            if(mem.uid != meID){
                member = mem;
                break;
            }
        }

        holder.tvNameGroup.setText(member.fullName);

        long userID = member.uid;
        holder.rlGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChatGroup.onClickItem(userID);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (groupChatList == null)
        {
            AppAlertDialog.showTokenTimeOutDialog(activity);
        }
        return groupChatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGroup;
        TextView tvNameGroup;
        RelativeLayout rlGroupChat;
        View view;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            imgGroup = itemView.findViewById(R.id.img_group);
            tvNameGroup = itemView.findViewById(R.id.tv_nameGroup);
            rlGroupChat = itemView.findViewById(R.id.rl_ChatGroup);
            view = itemView.findViewById(R.id.view_line);

        }
    }

    public interface OnClickChatGroup{
        void onClickItem(long uid);
    }
}
