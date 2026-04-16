package io.siddharth.myapplication.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorModel implements Serializable {

    public DoctorModel() {
    }

    public String id;
    public String docId;
    public String docName;
    public String docQualification;
    public String docNatureOfAssessment;
    public String gender;
    public String docContactNumber;
    public String docSignImagePath = "";
    public String docSignAWS = "";
    public String docProfileImagePath = "";
    public String remark;
    public String docHospital;
    public String docHospitalId;
    public String docExperience;
    public String docMciNumber;
    @JsonIgnore
    public boolean isSelected;

    public DoctorModel(String docNatureOfAssessment) {
        this.docNatureOfAssessment = docNatureOfAssessment;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DoctorModel) {
            DoctorModel s = (DoctorModel) obj;
            return this.docId.equals(s.docId);
        }
        return false;
    }



    @Override
    public int hashCode() {
        return docId.hashCode();
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNatureOfAssessment() {
        return docNatureOfAssessment;
    }

    public void setDocNatureOfAssessment(String docNatureOfAssessment) {
        this.docNatureOfAssessment = docNatureOfAssessment;
    }
}

