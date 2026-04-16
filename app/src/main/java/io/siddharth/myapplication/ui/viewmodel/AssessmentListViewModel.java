package io.siddharth.myapplication.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import io.siddharth.myapplication.data.repository.StudentRepository;
import io.siddharth.myapplication.domain.model.AssessmentListModel;
import io.siddharth.myapplication.domain.model.StudentModel;

public class AssessmentListViewModel extends AndroidViewModel {
    private final StudentRepository repository;

    public AssessmentListViewModel(Application application) {
        super(application);
        repository = new StudentRepository(application);
    }

    // This method transforms StudentModel -> AssessmentListModel automatically
    public LiveData<List<AssessmentListModel>> getStudentsByStatus(int status) {
        return Transformations.map(repository.getStudentsByStatus(status), students -> {
            List<AssessmentListModel> list = new ArrayList<>();
            for (StudentModel student : students) {
                AssessmentListModel model = new AssessmentListModel();
                model.studentModel = student;
                // In the future, you can also fetch/add specific assessments here
                list.add(model);
            }
            return list;
        });
    }
}