package io.siddharth.myapplication.domain.model;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.siddharth.myapplication.data.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private final LoginRepository repository ;

    public LoginViewModel(LoginRepository repository) {
        this.repository = repository;
    }

   public  enum LoginState {
        IDLE, LOADING, SUCCESS, ERROR_EMPTY_FIELDS, ERROR_NETWORK
    }
    private final MutableLiveData<String> userId = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<String> assessmentId = new MutableLiveData<>("");
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>(LoginState.IDLE);

    // Setters called from the View (or via Two-Way Data Binding)
    public void setUserId(String id) { userId.setValue(id); }
    public void setPassword(String pw) { password.setValue(pw); }
    public void setAssessmentId(String aid) { assessmentId.setValue(aid); }


    public void onLogin(){
        String uId = userId.getValue();
        String pass = password.getValue();
        String assessment = assessmentId.getValue();
        if (TextUtils.isEmpty(uId) ||TextUtils.isEmpty(pass) ||TextUtils.isEmpty(assessment)) {
            loginState.setValue(LoginState.ERROR_EMPTY_FIELDS);
            return;
        }
        loginState.setValue(LoginState.LOADING);

// Calling your Repository
        repository.login(uId, pass,assessment,new LoginRepository.LoginResponseCallback() {
            @Override
            public  void onSuccess(LoginResponseModel response) {
                loginState.postValue(LoginState.SUCCESS);
            }

            @Override
            public void onError(String error) {
                loginState.postValue(LoginState.ERROR_NETWORK);
            }
        });

    }
    public LiveData<LoginState> getLoginState() {
        return loginState;
    }


}
