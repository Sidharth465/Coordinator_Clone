package io.siddharth.myapplication.domain.model;


import java.io.Serializable;

public class GrowthAssessmentModel implements Serializable {
    public String height = "";
    public String weight = "";
    public String bmi = "";
    public String bmiCategory = "";
    public String studentId = "";

    public GrowthAssessmentModel() {}
}