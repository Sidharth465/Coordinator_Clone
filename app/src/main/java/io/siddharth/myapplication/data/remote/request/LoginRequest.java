package io.siddharth.myapplication.data.remote.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("assessmentId")
    private String assessmentId;

    public LoginRequest(String username, String password, String assessmentId) {
        this.username = username;
        this.password = password;
        this.assessmentId = assessmentId;
    }
}