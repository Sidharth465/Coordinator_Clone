package io.siddharth.myapplication.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.siddharth.myapplication.util.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "students")
@TypeConverters(io.siddharth.myapplication.database.Converters.class)
public class StudentModel implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String id;

    @SerializedName("hsgid")
    public String hsgId;

    @SerializedName("admissionNo")
    public String admissionNo;

    @SerializedName("name")
    public String name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("age")
    public int age;

    @SerializedName("gender")
    public String gender;

    @SerializedName("dob")
    public String dob;

    @SerializedName("class")
    public String stuClass;

    @SerializedName("section")
    public String stuSection;

    @SerializedName("imageName")
    public String imageName;

    @SerializedName("assessmentStatus")
    public int assessmentStatus = Constants.ASSESSMENT_STATUS_PENDING;

    @SerializedName("remark")
    public String remark;

    @SerializedName("highRiskList")
    public List<String> highRiskList = new ArrayList<>();

    @SerializedName("receivedStatus")
    public String receivedStatus;

    public Boolean isOpen;

    @SerializedName("anthro_metric")
    public GrowthModel anthro_metric;

    @Expose(serialize = false, deserialize = false)
    public int position;

    @SerializedName("ailment")
    public String ailment;

    public StudentModel() {
    }

    // Getters and Setters
    public String getHsgId() { return hsgId; }
    public void setHsgId(String hsgId) { this.hsgId = hsgId; }

    public String getAdmissionNo() { return admissionNo; }
    public void setAdmissionNo(String admissionNo) { this.admissionNo = admissionNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getStuClass() { return stuClass; }
    public void setStuClass(String stuClass) { this.stuClass = stuClass; }

    public String getStuSection() { return stuSection; }
    public void setStuSection(String stuSection) { this.stuSection = stuSection; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public int getAssessmentStatus() { return assessmentStatus; }
    public void setAssessmentStatus(int assessmentStatus) { this.assessmentStatus = assessmentStatus; }

    public List<String> getHighRiskList() { return highRiskList; }
    public void setHighRiskList(List<String> highRiskList) { this.highRiskList = highRiskList; }
}
