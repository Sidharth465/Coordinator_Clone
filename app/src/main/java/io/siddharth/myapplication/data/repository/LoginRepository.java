package io.siddharth.myapplication.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import io.siddharth.myapplication.data.remote.response.StudentListResponse;
import io.siddharth.myapplication.database.AppDatabase;
import io.siddharth.myapplication.database.StudentDao;
import io.siddharth.myapplication.domain.model.StudentListModel;
import io.siddharth.myapplication.domain.model.StudentModel;
import io.siddharth.myapplication.data.remote.RetrofitClient;
import io.siddharth.myapplication.domain.model.LoginRequestModel;
import io.siddharth.myapplication.domain.model.LoginResponseModel;
import io.siddharth.myapplication.util.UtilsSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static final String TAG = "LoginRepository";
    private final Context context;
    private final StudentDao studentDao;

    public LoginRepository(Context context) {
        this.context = context.getApplicationContext();
        this.studentDao = AppDatabase.getDatabase(context).studentDao();
    }

    public interface LoginResponseCallback {
        void onSuccess(LoginResponseModel response);
        void onError(String error);
    }

    public void login(String user, String pass, String aid, LoginResponseCallback callback) {
        LoginRequestModel loginRequest = new LoginRequestModel(user, pass, aid);

        RetrofitClient.getApiService(context).login(loginRequest).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                Log.d(TAG, "Login Response Code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponseModel loginResponse = response.body();
                    Log.d(TAG, "Login Successful. Token received.");
                    
                    UtilsSharedPreferences.getInstance().saveLoginResponseSharedPreference(context, loginResponse);
                    fetchNodeDetails(loginResponse, callback);
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e(TAG, "Login Failed. Code: " + response.code() + " Body: " + errorBody);
                        callback.onError("Login Failed (" + response.code() + "): " + errorBody);
                    } catch (Exception e) {
                        callback.onError("Login Failed: " + response.code());
                    }
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
                    Log.d(TAG, "Node Details fetch successful.");
                    fetchOrganization(loginResponse, callback);
                } else {
                    Log.e(TAG, "Node Details fetch Failed. Code: " + response.code());
                    callback.onError("Failed to fetch Node Details (" + response.code() + ")");
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
                    Log.d(TAG, "Organization fetch successful.");
                    fetchMetaData(loginResponse, callback);
                } else {
                    Log.e(TAG, "Organization fetch Failed. Code: " + response.code());
                    callback.onError("Failed to fetch Organization details (" + response.code() + ")");
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
                    Log.d(TAG, "Meta Data fetch successful.");
                    downloadStudentList(loginResponse, callback);
                } else {
                    Log.e(TAG, "Meta Data fetch Failed. Code: " + response.code());
                    callback.onError("Failed to fetch Meta Data (" + response.code() + ")");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                callback.onError("Meta Data network error");
            }
        });
    }

    private void downloadStudentList(LoginResponseModel loginResponse, LoginResponseCallback callback) {
        RetrofitClient.getApiService(context).downloadStudentList().enqueue(new Callback<StudentListResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentListResponse> call, @NonNull Response<StudentListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<StudentListResponse.Result> results = response.body().getResults();
                    
                    if (results == null || results.isEmpty()) {
                        Log.e(TAG, "Results list is null or empty in response.");
                        callback.onError("No results found in the response.");
                        return;
                    }

                    List<StudentModel> studentList = new ArrayList<>();
                    for (StudentListResponse.Result result : results) {
                        List<StudentListModel> wrappedList = result.getStudents();
                        if (wrappedList != null) {
                            for (StudentListModel wrapper : wrappedList) {
                                if (wrapper.studentModel != null) {
                                    studentList.add(wrapper.studentModel);
                                }
                            }
                        }
                    }

                    if (studentList.isEmpty()) {
                        Log.e(TAG, "Student list is empty after extraction.");
                        // Even if empty, we might want to proceed to login success if that's expected
                        // but usually it's an error in this context.
                    }

                    // Insert into Room Database in background thread
                    new Thread(() -> {
                        studentDao.deleteAllStudents();
                        if (!studentList.isEmpty()) {
                            studentDao.insertStudents(studentList);
                        }
                        Log.d(TAG, "Sync complete. Students inserted into Room: " + studentList.size());
                        callback.onSuccess(loginResponse);
                    }).start();

                } else {
                    callback.onError("Failed to download student list");
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentListResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Student list download error: " + t.getMessage());
                callback.onError("Student list download error: " + t.getMessage());
            }
        });
    }
}