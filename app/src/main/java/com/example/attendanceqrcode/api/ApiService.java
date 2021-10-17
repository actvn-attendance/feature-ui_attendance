package com.example.attendanceqrcode.api;


import com.example.attendanceqrcode.middleware.UnauthorizedInterceptor;
import com.example.attendanceqrcode.model.NotificationRequest;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.modelapi.AttendanceStatistics;
import com.example.attendanceqrcode.modelapi.HistoryAttendanceUser;
import com.example.attendanceqrcode.modelapi.InfoScores;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.Notifications;
import com.example.attendanceqrcode.modelapi.ResponseAttendance;
import com.example.attendanceqrcode.modelapi.Schedule;
import com.example.attendanceqrcode.modelapi.ScheduleStudent;
import com.example.attendanceqrcode.modelapi.Subject;
import com.example.attendanceqrcode.modelapi.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://0b31-27-72-105-40.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(AddLoggingInterceptor.setLogging())
            .build()
            .create(ApiService.class);

    //Phuong thuc post(lấy thuộc tính cua account)
    @POST("api/auth/signin/student")
    Call<InfoUser> sendAccount(@Body User user);

    //phuong thuc get(lay lich hoc)
    @GET("api/schedule")
    Call<List<ScheduleStudent>> getSchedule(@Header("Authorization") String access_token,
                                            @Query("end_date_time") String end_date_time,
                                            @Query("start_date_time") String start_date_time);


    //Phuong thuc post(lay kêt qua diem danh)
    @Headers({"Content-Type: application/json"})
    @POST("api/attendance/check-in")
    Call<ResponseAttendance> attendance(@Header("Authorization") String token,
                                        @Body JsonObject jsonObject);

    //get subject class
    @Headers({"Content-Type: application/json"})
    @GET("api/subject/get-list-subject-student")
    Call<List<Subject>> getSubject(@Header("Authorization") String token);

    // get user in subject
    @Headers({"Content-Type: application/json"})
    @GET("api/subject/get-list-user")
    Call<List<Account>> getUserInSubject(@Header("Authorization") String token, @Query("subject_id") int idSubject);

    // get info user by id
    @Headers({"Content-Type: application/json"})
    @GET("api/user/get_user")
    Call<Account> getUserById(@Header("Authorization") String token, @Query("account_id") int idSubject);

    @Headers({"Content-Type: application/json"})
    @GET("api/notification/history")
    Call<Notifications> getNotification(@Header("Authorization") String token,
                                        @Query("page") int page,
                                        @Query("size") int size);

    //phuong thuc get(lay thong ke diem danh)
    @GET("api/attendance/history-attendance-user")
    Call<List<HistoryAttendanceUser>> getHistoryAttendance(@Header("Authorization") String accessToken,
                                                           @Query("subject_id") int subject_id);

    // push notification with topic
    @POST("api/notification/topic")
    Call<Object> pushNotificationTopic(@Header("Authorization") String accessToken, @Body NotificationRequest notificationRequest);


//    //phuong thuc get(lay thong ke diem danh)
//    @GET("api/attendance/attendance-statistics")
//    Call<List<AttendanceStatistics>> getAttendanceStatistics(@Header("Authorization") String accessToken);
//
//    //phuong thuc get(lay thong ke diem danh)
//    @GET("api/attendance/history-attendance-user")
//    Call<List<HistoryAttendanceUser>> getHistoryAttendance(@Header("Authorization") String accessToken,
//                                                           @Query("classroom_id") int classroom_id);
//
//    //phuong thuc get(lay diem cua lop)
//    @GET("api/scores/get-scores")
//    Call<List<InfoScores>> getInfoScores(@Header("Authorization") String accessToken,
//                                         @Query("classroom_id") int classroom_id);


}
