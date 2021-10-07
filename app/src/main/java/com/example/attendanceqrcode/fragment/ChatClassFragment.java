package com.example.attendanceqrcode.fragment;


import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.MessageAdapter;
import com.example.attendanceqrcode.model.Message;
import com.example.attendanceqrcode.modelapi.Subject;
import com.example.attendanceqrcode.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatClassFragment extends Fragment {
    static final String TAG = "ChatClassFragment";

    final String CLASS_CHAT_PATH = "groups";
    RecyclerView recyclerChat;
    MessageAdapter messageAdapter;
    List<Message> messageList;
    EditText edtMessage;
    ImageButton btnSend;

    private Subject subject;
    private DatabaseReference classChatDB, messageDB;
    private String fullName;
    private long uid;

    @SuppressLint("RestrictedApi")
    public ChatClassFragment(Subject subject) {
        this.subject = subject;
    }

    ChildEventListener postListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (dataSnapshot.getValue() != null) {
                HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                Message newMessage = new Message(mapMessage);
                messageList.add(newMessage);
                messageAdapter.notifyDataSetChanged();

                recyclerChat.scrollToPosition(messageList.size() - 1);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_class, container, false);
        classChatDB = FirebaseDatabase.getInstance().getReference()
                .child(CLASS_CHAT_PATH)
                .child(subject.getId() + "");
        messageDB = classChatDB.child("message");
        messageDB.addChildEventListener(postListener);

        if (this.fullName == null)
            this.fullName = Utils.getUserFullName(getActivity());
        if (this.uid == 0)
            this.uid = Utils.getUserID(getActivity());


        recyclerChat = view.findViewById(R.id.recyclerChat);
        edtMessage = view.findViewById(R.id.edt_message);
        btnSend = view.findViewById(R.id.btn_send);


        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(), messageList);
        recyclerChat.setAdapter(messageAdapter);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        edtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkKeyBoard();
            }
        });

        return view;
    }

    private void sendMessage() {
        String message = edtMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        writeNewMessage(new Message(uid, fullName, message, "text"));
        edtMessage.setText("");
    }

    private void writeNewMessage(Message message) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = messageDB.push().getKey();

        Map<String, Object> postValues = message.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, postValues);

        messageDB.updateChildren(childUpdates);
    }

    //check message khi an keyboard
    private void checkKeyBoard() {
        final View activityRootView = getActivity().findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if (heightDiff > 0.25 * activityRootView.getRootView().getHeight()) {
                    if (messageList.size() > 0) {
                        recyclerChat.scrollToPosition(messageList.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

}
