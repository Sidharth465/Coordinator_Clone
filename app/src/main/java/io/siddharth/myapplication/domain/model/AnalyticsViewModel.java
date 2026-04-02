package io.siddharth.myapplication.domain.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class AnalyticsViewModel extends ViewModel {
    private final MutableLiveData<Integer> assessmentStatus = new MutableLiveData<>();
    private final MutableLiveData<List<String>> eventImages = new MutableLiveData<>();
    private final MutableLiveData<List<TempModelStudent>> doctorStatusList = new MutableLiveData<>();

    public LiveData<Integer> getAssessmentStatus() {
        return assessmentStatus;
    }

    public LiveData<List<String>> getEventImages() {
        return eventImages;
    }

    public LiveData<List<TempModelStudent>> getDoctorStatusList() {
        return doctorStatusList;
    }

    public void updateData(int status, List<String> images, List<TempModelStudent> doctors) {
        assessmentStatus.setValue(status);
        eventImages.setValue(images);
        doctorStatusList.setValue(doctors);
    }
}