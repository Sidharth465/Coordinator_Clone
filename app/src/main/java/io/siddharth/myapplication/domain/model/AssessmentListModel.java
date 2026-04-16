package io.siddharth.myapplication.domain.model;

import java.util.ArrayList;

public class AssessmentListModel {
    public StudentModel studentModel;
    public ArrayList<StudentAssessmentModel> assessmentModelArrayList;

    public AssessmentListModel() {
        this.assessmentModelArrayList = new ArrayList<>();
    }
}