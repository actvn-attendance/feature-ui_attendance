package com.example.attendanceqrcode.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendanceqrcode.ChatDetailActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerListSinhVien;
import com.example.attendanceqrcode.adapter.GroupChatAdapter;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.model.ChatList;
import com.example.attendanceqrcode.model.GroupChat;
import com.example.attendanceqrcode.model.Message;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment implements GroupChatAdapter.OnClickChatGroup {
    RecyclerView recyclerViewGroupChat;
    GroupChatAdapter groupChatAdapter;
    List<ChatList> groupChatList = new ArrayList<>();

    private DatabaseReference userChatListDB;
    private int uid;

    ValueEventListener chatListListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null) {
                System.out.println(dataSnapshot.getValue());
                HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                mapMessage.forEach((o, o2) -> {
                    ChatList chatList = new ChatList((HashMap) o2);
                    groupChatList.add(chatList);
                });

                groupChatAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        this.uid = Utils.getUserID(getActivity());
        userChatListDB = FirebaseDatabase.getInstance().getReference()
                .child("userChatList")
                .child(String.valueOf(uid));
        userChatListDB.addListenerForSingleValueEvent(chatListListener);

        getActivity().setTitle("Trò chuyện");
        recyclerViewGroupChat = view.findViewById(R.id.recycleGroupChat);

        groupChatAdapter = new GroupChatAdapter(getActivity(), groupChatList, this);
        recyclerViewGroupChat.setAdapter(groupChatAdapter);


        return view;
    }

    @Override
    public void onClickItem(long uid) {
        String token = Utils.getToken(getActivity());
        ApiService.apiService.getUserById(token, (int) uid).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.code() == 200) {


                    Intent intent = new Intent(getActivity(), ChatDetailActivity.class);
                    intent.putExtra("chat", response.body());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
        
      
    }
}