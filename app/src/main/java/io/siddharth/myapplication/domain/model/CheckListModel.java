package io.siddharth.myapplication.domain.model;


import java.io.Serializable;

public class CheckListModel implements Serializable {

    public boolean mspCheck;
    public boolean ispCheck;
    public boolean studentListCheck;
    public boolean batteryCheck;
    public String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isMspCheck() {
        return mspCheck;
    }

    public void setMspCheck(boolean mspCheck) {
        this.mspCheck = mspCheck;
    }

    public boolean isIspCheck() {
        return ispCheck;
    }

    public void setIspCheck(boolean ispCheck) {
        this.ispCheck = ispCheck;
    }

    public boolean isStudentListCheck() {
        return studentListCheck;
    }

    public void setStudentListCheck(boolean studentListCheck) {
        this.studentListCheck = studentListCheck;
    }

    public boolean isBatteryCheck() {
        return batteryCheck;
    }

    public void setBatteryCheck(boolean batteryCheck) {
        this.batteryCheck = batteryCheck;
    }
}

