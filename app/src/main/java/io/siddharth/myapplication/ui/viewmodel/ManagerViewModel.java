package io.siddharth.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

import io.siddharth.myapplication.domain.model.DoctorModel;

public class ManagerViewModel extends ViewModel {

    private final MutableLiveData<List<DoctorModel>> doctors = new MutableLiveData<>();

    public LiveData<List<DoctorModel>> getDoctors() {
        return doctors;    }

    public void loadDoctors() {
        // Will load from Room database later
        doctors.setValue(new ArrayList<>());
    }

    // Button Actions
    public void addMoreDoctor() { /* Navigate to add doctor screen */ }
    public void addCoordinator() { /* Navigate to add coordinator screen */ }
    public void training() { /* Handle training child logic */ }
    public void showHelpSupportDialog() { /* Show support dialog */ }
}