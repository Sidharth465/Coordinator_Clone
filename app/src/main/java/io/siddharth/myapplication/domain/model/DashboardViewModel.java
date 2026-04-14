package io.siddharth.myapplication.domain.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {
    
    private final MutableLiveData<Boolean> exitApp = new MutableLiveData<>(false);

    public LiveData<Boolean> getExitApp() {
        return exitApp;
    }

    public void triggerExit() {
        exitApp.setValue(true);
    }
}
