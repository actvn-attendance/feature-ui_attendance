package com.example.attendanceqrcode.utils;

import android.app.Activity;
import android.widget.Toast;

import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.model.NotificationRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseHelper {
    public void subscribeTopic(Activity activity, String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(activity, "Xảy ra lỗi khi nhận thông báo", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(">>> "+ topic);
                    }
                });
    }

    public void unsubscribeTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

    public void sendMessageToTopic(Activity activity, NotificationRequest notificationRequest){
        ApiService.apiService.pushNotificationTopic(Utils.getToken(activity), notificationRequest).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code() == 200){
                    System.out.println(">>> pushed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
