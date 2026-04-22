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
    private final MutableLiveData<String> selectedClass = new MutableLiveData<>(null);
    private final MutableLiveData<String> selectedSection = new MutableLiveData<>(null);
    private final MutableLiveData<Integer> selectedStatus = new MutableLiveData<>(-1); // -1 for All

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentDao = AppDatabase.getDatabase(application).studentDao();
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void setSelectedClass(String stuClass) {
        selectedClass.setValue(stuClass);
    }

    public void setSelectedSection(String section) {
        selectedSection.setValue(section);
    }

    public void setSelectedStatus(int status) {
        selectedStatus.setValue(status);
    }

    public LiveData<List<String>> getClasses() {
        return studentDao.getClasses();
    }

    public LiveData<List<String>> getSections(String stuClass) {
        if (stuClass == null || stuClass.equals("All")) {
            return studentDao.getAllSections();
        }
        return studentDao.getSectionsByClass(stuClass);
    }

    public LiveData<List<StudentModel>> getScheduledStudents() {
        // We'll combine all filters for the scheduled tab
        // Scheduled tab generally shows Pending and Not Started
        return Transformations.switchMap(searchQuery, query -> 
            Transformations.switchMap(selectedClass, stuClass ->
                Transformations.switchMap(selectedSection, section ->
                    Transformations.switchMap(selectedStatus, status -> {
                        
                        int[] statuses;
                        if (status == -1) {
                            statuses = new int[]{Constants.ASSESSMENT_STATUS_PENDING, Constants.ASSESSMENT_STATUS_NOT_STARTED};
                        } else {
                            statuses = new int[]{status};
                        }

                        String classFilter = (stuClass == null || stuClass.equals("All")) ? null : stuClass;
                        String sectionFilter = (section == null || section.equals("All")) ? null : section;
                        String q = (query == null || query.isEmpty()) ? "%%" : "%" + query + "%";

                        return studentDao.filterStudents(statuses, classFilter, sectionFilter, q);
                    })
                )
            )
        );
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
