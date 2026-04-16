package io.siddharth.myapplication.domain.model;

import java.io.Serializable;

public class StudentAssessmentModel implements Serializable {
    public String assessmentType;
    public String assessmentStatus;
    public String remark;
    public String doctorId;

    public StudentAssessmentModel() {}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudentAssessmentModel) {
            StudentAssessmentModel s = (StudentAssessmentModel) obj;
            return this.assessmentStatus != null && this.assessmentStatus.equals(s.assessmentStatus);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return assessmentStatus != null ? assessmentStatus.hashCode() : 0;
    }
}