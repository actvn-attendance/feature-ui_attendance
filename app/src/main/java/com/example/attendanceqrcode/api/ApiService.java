package com.example.attendanceqrcode.api;


import com.example.attendanceqrcode.modelapi.AttendanceStatistics;
import com.example.attendanceqrcode.modelapi.HistoryAttendanceUser;
import com.example.attendanceqrcode.modelapi.InfoScores;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.ResponseAttendance;
import com.example.attendanceqrcode.modelapi.Schedule;
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
            .baseUrl("http://6daf-2001-ee0-41c1-90e9-583-1f27-ec9f-26cb.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(AddLoggingInterceptor.setLogging())
            .build()
            .create(ApiService.class);

    //Phuong thuc post(lấy thuộc tính cua account)
    @POST("api/auth/signin/student")
    Call<InfoUser> sendAccount(@Body User user);

    //phuong thuc get(lay lich hoc)
    @GET("api/schedule")
    Call<List<Schedule>> getSchedule(@Header("Authorization") String access_token,
                                     @Query("start_date_time") String start_date_time,
                                     @Query("end_date_time") String end_date_time);

//    @GET("api/live")
//    Call<Currency> convertUsdToVnd(@Query("access_key") String access_key,
//                                   @Query("currencies") String currencies,
//                                   @Query("source") String source,
//                                   @Query("format") int format);
//
//    @GET("api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1")
//    Call<Currency> convertUsdToVnd1();
//
//
//    @GET("api/live")
//    Call<Currency> convertUsdToVnd2( @QueryMap Map<String, String> options);

    //Phuong thuc post(lay kêt qua diem danh)
    @Headers({"Content-Type: application/json"})
    @POST("api/attendance/check-in")
    Call<ResponseAttendance> attendance(@Header("Authorization") String token,
                                        @Body JsonObject jsonObject);

    //phuong thuc get(lay thong ke diem danh)
    @GET("api/attendance/attendance-statistics")
    Call<List<AttendanceStatistics>> getAttendanceStatistics(@Header("Authorization") String accessToken);

    //phuong thuc get(lay thong ke diem danh)
    @GET("api/attendance/history-attendance-user")
    Call<List<HistoryAttendanceUser>> getHistoryAttendance(@Header("Authorization") String accessToken,
                                                           @Query("classroom_id") int classroom_id);

    //phuong thuc get(lay diem cua lop)
    @GET("api/scores/get-scores")
    Call<List<InfoScores>> getInfoScores(@Header("Authorization") String accessToken,
                                         @Query("classroom_id") int classroom_id);


}
