package com.example.attendanceqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.MessageAdapter;
import com.example.attendanceqrcode.middleware.BaseActivity;
import com.example.attendanceqrcode.model.Message;
import com.example.attendanceqrcode.model.NotificationRequest;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.utils.FirebaseHelper;
import com.example.attendanceqrcode.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatDetailActivity extends BaseActivity {
    RecyclerView recyclerChat;
    MessageAdapter messageAdapter;
    List<Message> messageList;
    EditText edtMessage;
    ImageButton btnSend;
    ImageView btnBack;
    TextView txtTitle;

    Account account;
    private DatabaseReference personalChatDB, meChatListDB, userChatListDB;
    private String fullName, chatID;
    private long uid;
    private final Date now = new Date();
    private boolean isFirst = true;
    private FirebaseHelper firebaseHelper = new FirebaseHelper();

    ChildEventListener chatListener = new ChildEventListener() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        account = (Account) getIntent().getSerializableExtra("chat");
        if (this.fullName == null)
            this.fullName = Utils.getUserFullName(this);
        if (this.uid == 0)
            this.uid = Utils.getUserID(this);
        if (this.chatID == null) {
            this.chatID = Utils.getPersonalID(this, account.getAccount_id());
        }

        personalChatDB = FirebaseDatabase.getInstance().getReference()
                .child("personalChat")
                .child(chatID)
                .child("messages");
        personalChatDB.addChildEventListener(chatListener);

        meChatListDB = FirebaseDatabase.getInstance().getReference().child("userChatList").child(String.valueOf(uid));
        userChatListDB = FirebaseDatabase.getInstance().getReference().child("userChatList").child(String.valueOf(account.getAccount_id()));

        recyclerChat = findViewById(R.id.recyclerChat);
        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        btnBack = findViewById(R.id.btnBack);
        txtTitle = findViewById(R.id.tvGroupDetail);

        txtTitle.setText(account.getFull_name());

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);
        recyclerChat.setAdapter(messageAdapter);


        btnSend.setOnClickListener(view -> {
            sendMessage();
        });

        edtMessage.setOnClickListener(view -> {
            checkKeyBoard();
        });

        btnBack.setOnClickListener(view -> finish());

    }

    private void sendMessage() {
        String message = edtMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        writeNewMessage(new Message(uid, fullName, message, "text"));


        if (isFirst) {
            initialUserChatList();
            isFirst = false;
        }

        firebaseHelper.sendMessageToTopic(ChatDetailActivity.this, new NotificationRequest(
                "Tin nhắn từ " + fullName,
                message,
                String.valueOf(account.getAccount_id()),
                1
        ));

        edtMessage.setText("");
    }

    private void writeNewMessage(Message message) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = personalChatDB.push().getKey();

        Map<String, Object> postValues = message.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, postValues);

        personalChatDB.updateChildren(childUpdates);
    }

    private void initialUserChatList() {
        meChatListDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                System.out.println(snapshot.hasChild(chatID));
                if (!snapshot.hasChild(chatID)) {
                    Map<String, Object> postValues = new HashMap<>();
                    postValues.put("id", chatID);
                    postValues.put("time", now.getTime());
                    postValues.put("members", mapMembers());

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/" + chatID, postValues);
                    meChatListDB.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        userChatListDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(chatID)) {
                    Map<String, Object> postValues = new HashMap<>();
                    postValues.put("id", chatID);
                    postValues.put("time", now.getTime());
                    postValues.put("members", mapMembers());

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/" + chatID, postValues);
                    userChatListDB.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private Map<String, Object> mapMembers() {
        Map<String, Object> meValue = new HashMap<>();
        meValue.put("fullName", fullName);
        meValue.put("uid", uid);

        Map<String, Object> userValueValue = new HashMap<>();
        userValueValue.put("fullName", account.getFull_name());
        userValueValue.put("uid", account.getAccount_id());

        Map<String, Object> membersValue = new HashMap<>();
        membersValue.put(String.valueOf(uid), meValue);
        membersValue.put(String.valueOf(account.getAccount_id()), userValueValue);

        return membersValue;
    }

    //check message khi an keyboard
    private void checkKeyBoard() {
        final View activityRootView = findViewById(R.id.activityRoot);
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