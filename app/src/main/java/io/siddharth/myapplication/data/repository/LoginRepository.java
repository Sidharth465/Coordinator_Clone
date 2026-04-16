package io.siddharth.myapplication.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import io.siddharth.myapplication.data.remote.response.StudentListResponse;
import io.siddharth.myapplication.database.AppDatabase;
import io.siddharth.myapplication.database.StudentDao;
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
    private String buildXSelect(String key, String value) {
        return "{\"" + key + "\":\"" + value + "\"}";
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
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponseModel loginResponse = response.body();
                    UtilsSharedPreferences.getInstance().saveLoginResponseSharedPreference(context, loginResponse);

                    // START THE CHAIN
                    fetchNodeDetails(loginResponse, aid, callback);
                } else {
                    callback.onError("Login Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    private void fetchNodeDetails(LoginResponseModel loginResponse, String aid, LoginResponseCallback callback) {
        String xSelect = buildXSelect("startNode", aid);
        RetrofitClient.getApiService(context).getNodeIds(xSelect).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Node Details fetch successful.");
                    fetchOrganization(loginResponse, aid, callback);
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

    private void fetchOrganization(LoginResponseModel loginResponse, String aid, LoginResponseCallback callback) {
        String xSelect = "{\"orgType\":2}";
        RetrofitClient.getApiService(context).getOrganizations(xSelect).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Organization fetch successful.");
                    fetchMetaData(loginResponse, aid, callback);
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

    private void fetchMetaData(LoginResponseModel loginResponse, String aid, LoginResponseCallback callback) {
        RetrofitClient.getApiService(context).getAssessmentMeta().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Meta Data fetch successful.");
                    downloadStudentList(loginResponse, aid, callback);
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

    private void downloadStudentList(LoginResponseModel loginResponse, String aid, LoginResponseCallback callback) {
        // We pass 'aid' to both potential parameter names for the API
        String xSelect = buildXSelect("startNode", aid);
        RetrofitClient.getApiService(context).getStudentList(xSelect).enqueue(new Callback<StudentListResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentListResponse> call, @NonNull Response<StudentListResponse> response) {
                Log.d(TAG, "Download Student List Response Code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    StudentListResponse body = response.body();
                    // Log the full URL for verification if needed (this would require extra setup with OkHttp)
                    List<StudentListResponse.Result> results = body.getResults();

                    List<StudentModel> studentList = new ArrayList<>();
                    if (results != null) {
                        for (StudentListResponse.Result result : results) {
                            List<StudentModel> students = result.getStudents();
                            if (students != null) {
                                for (StudentModel student : students) {
                                    // 1. Ensure the student has a non-null ID for Room Primary Key
                                    if (student.id == null || student.id.isEmpty()) {
                                        student.id = student.hsgId;
                                    }
                                    
                                    // 2. The API doesn't provide assessmentStatus, so we default to PENDING (0)
                                    // so they appear in the "Scheduled" tab.
                                    student.assessmentStatus = io.siddharth.myapplication.util.Constants.ASSESSMENT_STATUS_PENDING;

                                    studentList.add(student);
                                }
                            }
                        }
                    }

                    Log.d(TAG, "Extracted " + studentList.size() + " students for assessmentId: " + aid);
                    if (studentList.size() > 0) {
                        Log.d(TAG, "First student sample: " + studentList.get(0).getName() + " Status: " + studentList.get(0).getAssessmentStatus());
                    } else {
                        Log.w(TAG, "Zero students returned from API for assessmentId: " + aid);
                    }

                    new Thread(() -> {
                        try {
                            // Clear old data for a fresh sync
                            studentDao.deleteAllStudents();
                            if (!studentList.isEmpty()) {
                                studentDao.insertStudents(studentList);
                                Log.d(TAG, "Successfully inserted " + studentList.size() + " students into database.");
                            } else {
                                Log.w(TAG, "No students found in the response to insert.");
                            }
                            // Notify success on the main thread via callback
                            callback.onSuccess(loginResponse);
                        } catch (Exception e) {
                            Log.e(TAG, "Database Error during sync: " + e.getMessage());
                            callback.onError("Failed to save data locally.");
                        }
                    }).start();

                } else {
                    Log.e(TAG, "Failed to download student list. Code: " + response.code());
                    callback.onError("Failed to download student list (" + response.code() + ")");
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
