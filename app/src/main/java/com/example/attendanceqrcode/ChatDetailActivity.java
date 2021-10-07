package com.example.attendanceqrcode;

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

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.MessageAdapter;
import com.example.attendanceqrcode.middleware.BaseActivity;
import com.example.attendanceqrcode.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatDetailActivity extends BaseActivity {

    RecyclerView recyclerChat;
    MessageAdapter messageAdapter;
    List<Message> messageList;
    EditText edtMessage;
    ImageButton btnSend;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        recyclerChat = findViewById(R.id.recyclerChat);
        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        btnBack = findViewById(R.id.btnBack);


        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this,messageList);
        recyclerChat.setAdapter(messageAdapter);


        btnSend.setOnClickListener(view -> {
            sendMessage();
        });

        edtMessage.setOnClickListener(view -> {
            checkKeyBoard();
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void sendMessage() {
        String message = edtMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message))
        {
            return;
        }
        messageList.add(new Message(message));
        messageAdapter.notifyDataSetChanged();

        recyclerChat.scrollToPosition(messageList.size() - 1);

        edtMessage.setText("");
    }

    //check message khi an keyboard
    private void checkKeyBoard(){
        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if (heightDiff > 0.25*activityRootView.getRootView().getHeight())
                {
                    if (messageList.size() > 0)
                    {
                        recyclerChat.scrollToPosition(messageList.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}