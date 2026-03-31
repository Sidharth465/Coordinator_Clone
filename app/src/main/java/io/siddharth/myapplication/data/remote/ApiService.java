package io.siddharth.myapplication.data.remote;

import io.siddharth.myapplication.data.remote.request.LoginRequest;

import io.siddharth.myapplication.domain.model.LoginResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/login") // Replace with your actual endpoint
    Call<LoginResponseModel> login(@Body LoginRequest request);
}