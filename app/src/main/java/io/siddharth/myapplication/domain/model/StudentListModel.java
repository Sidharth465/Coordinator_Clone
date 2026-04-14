package io.siddharth.myapplication.domain.model;

import com.google.gson.annotations.SerializedName;

public class StudentListModel {

    @SerializedName("student")
    public StudentModel studentModel;

    @SerializedName("regDate")
    public String regDate;

    @SerializedName("type")
    public int type;

    public StudentListModel() {}
}
