package io.siddharth.myapplication.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.siddharth.myapplication.data.remote.RetrofitClient;
import io.siddharth.myapplication.data.remote.request.LoginRequest;
import io.siddharth.myapplication.domain.model.LoginResponseModel;
import io.siddharth.myapplication.util.UtilsSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static final String TAG = "LoginRepository";
    private final Context context;

    public LoginRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public interface LoginResponseCallback {
        void onSuccess(LoginResponseModel response);
        void onError(String error);
    }

    public void login(String user, String pass, String aid, LoginResponseCallback callback) {
        LoginRequest loginRequest = new LoginRequest(user, pass, aid);

        RetrofitClient.getApiService(context).login(loginRequest).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponseModel loginResponse = response.body();
                    
                    // Save credentials/token
                    UtilsSharedPreferences.getInstance().saveLoginResponseSharedPreference(context, loginResponse);

                    // Start the data fetching chain
                    fetchNodeDetails(loginResponse, callback);
                } else {
                    callback.onError("Invalid credentials or server error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                callback.onError("Network failed: " + t.getMessage());
            }
        });
    }

    private void fetchNodeDetails(LoginResponseModel loginResponse, LoginResponseCallback callback) {
        RetrofitClient.getApiService(context).getNodeIds().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    // Logic to save Node IDs to Database would go here
                    fetchOrganization(loginResponse, callback);
                } else {
                    callback.onError("Failed to fetch Node Details (401 Unauthorized likely)");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                callback.onError("Node Details network error");
            }
        });
    }

    private void fetchOrganization(LoginResponseModel loginResponse, LoginResponseCallback callback) {
        RetrofitClient.getApiService(context).getOrganization().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    // Logic to save Organization to Database would go here
                    fetchMetaData(loginResponse, callback);
                } else {
                    callback.onError("Failed to fetch Organization details");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                callback.onError("Organization network error");
            }
        });
    }

    private void fetchMetaData(LoginResponseModel loginResponse, LoginResponseCallback callback) {
        RetrofitClient.getApiService(context).getAssessmentMeta().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    downloadStudentList(loginResponse, callback);
                } else {
                    callback.onError("Failed to fetch Meta Data");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                callback.onError("Meta Data network error");
            }
        });
    }

    private void downloadStudentList(LoginResponseModel loginResponse, LoginResponseCallback callback) {
        RetrofitClient.getApiService(context).downloadStudentList().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Logic to parse and insert students into SQLite Database would go here
                    Log.d(TAG, "Sync complete. Moving to Dashboard.");
                    callback.onSuccess(loginResponse);
                } else {
                    callback.onError("Failed to download student list");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                callback.onError("Student list download error");
            }
        });
    }
}