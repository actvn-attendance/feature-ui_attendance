package com.example.attendanceqrcode.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.model.NotificationRequest;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseHelper {
    public static void subscribeTopic(Activity activity, String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(activity, "Xảy ra lỗi khi nhận thông báo", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println(">>> " + topic);
                    }
                });
    }

    public static void unsubscribeTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

    public static void sendMessageToTopic(Activity activity, NotificationRequest notificationRequest) {
        ApiService.apiService.pushNotificationTopic(Utils.getToken(activity), notificationRequest).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    System.out.println(">>> pushed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public static Uri uploadImageInGroup(Activity activity, Uri file) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/groups/" + file.getLastPathSegment());

        Task<Uri> urlTask = storageRef.putFile(file).addOnFailureListener(exception -> {
            System.out.println("lỗi: "+   (exception.toString()));
            Toast
                    .makeText(activity,
                            "Gửi thất bại!",
                            Toast.LENGTH_LONG)
                    .show();
        }).addOnSuccessListener(taskSnapshot -> {
            System.out.println(">>> thành công");
            Toast
                    .makeText(activity,
                            "Gửi ảnh thành công!",
                            Toast.LENGTH_LONG)
                    .show();

        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return storageRef.getDownloadUrl();
            }
        });

        if (urlTask.isSuccessful()) {
            return urlTask.getResult();
        }

        return null;
    }
}
