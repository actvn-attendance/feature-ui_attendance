package com.example.attendanceqrcode.modelapi;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class InfoUser implements Serializable {
    private String access_token;
    private String token_type;
    private com.example.attendanceqrcode.modelapi.Account account;

    public InfoUser() {
    }

    public InfoUser(String access_token, String token_type, Account account) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.account = account;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
