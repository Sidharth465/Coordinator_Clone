package io.siddharth.myapplication.data.repository;

import io.siddharth.myapplication.data.remote.RetrofitClient;
import io.siddharth.myapplication.data.remote.request.LoginRequest;
import io.siddharth.myapplication.domain.model.LoginResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    public interface LoginResponseCallback {
        void onSuccess(LoginResponseModel response);
        void onError(String error);
    }

    public void login(String user, String pass, String aid, LoginResponseCallback callback) {
        LoginRequest loginRequest = new LoginRequest(user, pass, aid);

        RetrofitClient.getApiService().login(loginRequest).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Invalid credentials or server error");
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}