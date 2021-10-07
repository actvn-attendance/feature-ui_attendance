package com.example.attendanceqrcode.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendanceqrcode.ChatDetailActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.GroupChatAdapter;
import com.example.attendanceqrcode.model.GroupChat;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements GroupChatAdapter.OnClickChatGroup {
    RecyclerView recyclerViewGroupChat;
    GroupChatAdapter groupChatAdapter;
    List<GroupChat> groupChatList ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        getActivity().setTitle("Trò chuyện");
        recyclerViewGroupChat = view.findViewById(R.id.recycleGroupChat);
        initData();
        groupChatAdapter = new GroupChatAdapter(getActivity(),groupChatList,this);
        recyclerViewGroupChat.setAdapter(groupChatAdapter);


        return view;
    }

    private void initData()
    {
        groupChatList = new ArrayList<>();
        groupChatList.add(new GroupChat("Android nang cao","Thay Cuong",R.drawable.image_group_chat));
        groupChatList.add(new GroupChat("Toi uu phan mem","Thay Cuong",R.drawable.image_group_chat));
        groupChatList.add(new GroupChat("An toan thong tin","Thay Cuong",R.drawable.image_group_chat));
        groupChatList.add(new GroupChat("Java card","Thay Cuong",R.drawable.image_group_chat));

    }

    @Override
    public void onClickItem() {
        Intent intent  = new Intent(getActivity(),ChatDetailActivity.class);
        startActivity(intent);
    }
}