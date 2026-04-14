package io.siddharth.myapplication.domain.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {
    @SerializedName("username")
    private String username;

    @SerializedName("userId")
    private String userId;

    @SerializedName("password")
    private String password;

    @SerializedName("assessmentId")
    private String assessmentId;

    public LoginRequestModel(String username, String password, String assessmentId) {
        this.username = username;
        this.userId = username; // Map both to same value for legacy support
        this.password = password;
        this.assessmentId = assessmentId;
    }
}