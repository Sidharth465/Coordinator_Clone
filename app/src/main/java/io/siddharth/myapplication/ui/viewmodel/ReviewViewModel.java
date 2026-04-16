package io.siddharth.myapplication.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.siddharth.myapplication.activity.DashboardActivity;
import io.siddharth.myapplication.domain.model.CommunicationModel;
import io.siddharth.myapplication.domain.model.PhysicalAssessmentModel;
import io.siddharth.myapplication.domain.model.ResponseModel;
import io.siddharth.myapplication.domain.model.StudentModel;
import io.siddharth.myapplication.domain.model.EyeAssessmentModel;
import io.siddharth.myapplication.domain.model.DentalAssessmentModel;
import io.siddharth.myapplication.domain.model.EntAssessmentModel;
import io.siddharth.myapplication.domain.model.GrowthAssessmentModel;


public class ReviewViewModel extends ViewModel {

    // These MUST be public to be accessed as viewModel.growth in ReviewActivity
    public final MutableLiveData<StudentModel> student = new MutableLiveData<>();
    public final MutableLiveData<PhysicalAssessmentModel> physical = new MutableLiveData<>();
    public final MutableLiveData<EyeAssessmentModel> eye = new MutableLiveData<>();
    public final MutableLiveData<DentalAssessmentModel> dental = new MutableLiveData<>();
    public final MutableLiveData<EntAssessmentModel> ent = new MutableLiveData<>();
    public final MutableLiveData<GrowthAssessmentModel> growth = new MutableLiveData<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Fix for: Required type StudentModel, Provided String
    public void init(String studentId) {
        // Logic to fetch data from Repository using ID
        // For now, we initialize an empty student or mock data
    }

    public void init(StudentModel studentModel) {
        this.student.setValue(studentModel);
    }

    /**
     * Legacy Logic: Sends the finalized assessment via the Socket Thread (DashboardActivity)
     */
    public void submitFinal() {
        StudentModel currentStudent = student.getValue();
        if (currentStudent == null) return;

        try {
            // Prepare ResponseModel (Matches legacy server expectation)
            ResponseModel responseModel = new ResponseModel();
            responseModel.assessmentType = "";
            responseModel.assessmentVerified = true;
            responseModel.studentModel = currentStudent;
            responseModel.docId = "";

            // Wrap in CommunicationModel with ID "9" (Finalize ID)
            CommunicationModel commModel = new CommunicationModel();
            commModel.id = "9";
            commModel.content = objectMapper.writeValueAsString(responseModel);

            String jsonData = objectMapper.writeValueAsString(commModel);

            // Send through the legacy socket thread
            if (DashboardActivity.chatClientThread != null) {
                DashboardActivity.chatClientThread.sendMsg(jsonData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}