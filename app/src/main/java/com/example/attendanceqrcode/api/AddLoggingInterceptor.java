package com.example.attendanceqrcode.api;

import com.example.attendanceqrcode.middleware.UnauthorizedInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class AddLoggingInterceptor {
    public static OkHttpClient setLogging(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new UnauthorizedInterceptor())
                .build();

        return okHttpClient;
    }
}
