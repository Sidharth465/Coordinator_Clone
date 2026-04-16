package io.siddharth.myapplication.ui.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.siddharth.myapplication.domain.model.StudentModel;

public class StudentListViewModel extends ViewModel {

    private final MutableLiveData<List<StudentModel>> _students = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<StudentModel>> students = _students;

    private final MutableLiveData<String> _studentCount = new MutableLiveData<>("Students: 0");
    public final LiveData<String> studentCount = _studentCount;

    private List<StudentModel> fullList = new ArrayList<>();

    public void setStudentList(List<StudentModel> list) {
        this.fullList = list;
        _students.setValue(list);
        updateCount(list.size());
    }

    public void performSearch(String query) {
        if (query == null || query.isEmpty()) {
            _students.setValue(fullList);
            updateCount(fullList.size());
            return;
        }

        List<StudentModel> filteredList = new ArrayList<>();
        for (StudentModel student : fullList) {
            if (student.name.toLowerCase().contains(query.toLowerCase()) ||
                    student.admissionNo.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(student);
            }
        }
        _students.setValue(filteredList);
        updateCount(filteredList.size());
    }

    private void updateCount(int size) {
        _studentCount.setValue("Students: " + size);
    }
}