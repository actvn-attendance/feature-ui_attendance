package com.example.attendanceqrcode.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatClassFragment extends Fragment {

    RecyclerView recyclerChat;
    MessageAdapter messageAdapter;
    List<Message> messageList;
    EditText edtMessage;
    ImageButton btnSend;

    public ChatClassFragment(Subject subject) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_class, container, false);

        recyclerChat = view.findViewById(R.id.recyclerChat);
        edtMessage = view.findViewById(R.id.edt_message);
        btnSend = view.findViewById(R.id.btn_send);



        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(),messageList);
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
        final View activityRootView = getActivity().findViewById(R.id.activityRoot);
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
