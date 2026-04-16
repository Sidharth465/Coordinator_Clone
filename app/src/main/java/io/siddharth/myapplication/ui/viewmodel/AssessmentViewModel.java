package io.siddharth.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.siddharth.myapplication.domain.model.DentalAssessmentModel;
import io.siddharth.myapplication.domain.model.EntAssessmentModel;
import io.siddharth.myapplication.domain.model.EyeAssessmentModel;
import io.siddharth.myapplication.domain.model.GrowthAssessmentModel;
import io.siddharth.myapplication.domain.model.PhysicalAssessmentModel;
import io.siddharth.myapplication.domain.model.StudentModel;



public class AssessmentViewModel extends ViewModel {

    private final MutableLiveData<StudentModel> student = new MutableLiveData<>();

    private final MutableLiveData<PhysicalAssessmentModel> physicalData = new MutableLiveData<>(new PhysicalAssessmentModel());
    private final MutableLiveData<EyeAssessmentModel> eyeData = new MutableLiveData<>(new EyeAssessmentModel());
    private final MutableLiveData<DentalAssessmentModel> dentalData = new MutableLiveData<>(new DentalAssessmentModel());
    private final MutableLiveData<EntAssessmentModel> entData = new MutableLiveData<>(new EntAssessmentModel());
    private final MutableLiveData<GrowthAssessmentModel> growthData = new MutableLiveData<>(new GrowthAssessmentModel());

    public void init(StudentModel studentModel) {
        student.setValue(studentModel);
    }

    public LiveData<StudentModel> getStudent() { return student; }

    public MutableLiveData<PhysicalAssessmentModel> getPhysicalData() { return physicalData; }
    public MutableLiveData<EyeAssessmentModel> getEyeData() { return eyeData; }
    public MutableLiveData<DentalAssessmentModel> getDentalData() { return dentalData; }
    public MutableLiveData<EntAssessmentModel> getEntData() { return entData; }
    public MutableLiveData<GrowthAssessmentModel> getGrowthData() { return growthData; }

    public void updatePhysicalData(PhysicalAssessmentModel data) { physicalData.setValue(data); }
    public void updateEyeData(EyeAssessmentModel data) { eyeData.setValue(data); }
    public void updateDentalData(DentalAssessmentModel data) { dentalData.setValue(data); }
    public void updateEntData(EntAssessmentModel data) { entData.setValue(data); }
    public void updateGrowthData(GrowthAssessmentModel data) { growthData.setValue(data); }
}