package io.siddharth.myapplication.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.siddharth.myapplication.database.AppDatabase;
import io.siddharth.myapplication.database.StudentDao;
import io.siddharth.myapplication.domain.model.StudentModel;

public class StudentRepository {
    private final StudentDao studentDao;

    public StudentRepository(Application application) {
        // Assuming you have a standard AppDatabase.getDatabase() method
        AppDatabase db = AppDatabase.getDatabase(application);
        studentDao = db.studentDao();
    }

    public LiveData<List<StudentModel>> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public LiveData<List<StudentModel>> getStudentsByStatus(int status) {
        return studentDao.getStudentsByStatus(status);
    }

    public LiveData<List<StudentModel>> searchStudents(String query) {
        return studentDao.searchAllStudents("%" + query + "%");
    }
}