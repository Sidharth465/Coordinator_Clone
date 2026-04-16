package io.siddharth.myapplication.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import java.util.List;
import io.siddharth.myapplication.database.AppDatabase;
import io.siddharth.myapplication.database.StudentDao;
import io.siddharth.myapplication.domain.model.StudentModel;
import io.siddharth.myapplication.util.Constants;

public class StudentViewModel extends AndroidViewModel {

    private final StudentDao studentDao;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentDao = AppDatabase.getDatabase(application).studentDao();
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public LiveData<List<StudentModel>> getScheduledStudents() {
        return Transformations.switchMap(searchQuery, query -> {
            // Include both Pending (0) and Not Started (5) in the Scheduled tab
            int[] statuses = {Constants.ASSESSMENT_STATUS_PENDING, Constants.ASSESSMENT_STATUS_NOT_STARTED};
            if (query == null || query.isEmpty()) {
                return studentDao.getStudentsByMultipleStatuses(statuses);
            } else {
                return studentDao.searchStudentsByMultipleStatuses(statuses, "%" + query + "%");
            }
        });
    }

    public LiveData<List<StudentModel>> getOngoingStudents() {
        return Transformations.switchMap(searchQuery, query -> {
            // Include Ongoing (1) and Incomplete (7) in the Ongoing tab
            int[] statuses = {Constants.ASSESSMENT_STATUS_ONGOING, Constants.ASSESSMENT_STATUS_INCOMPLETE};
            if (query == null || query.isEmpty()) {
                return studentDao.getStudentsByMultipleStatuses(statuses);
            } else {
                return studentDao.searchStudentsByMultipleStatuses(statuses, "%" + query + "%");
            }
        });
    }
}
