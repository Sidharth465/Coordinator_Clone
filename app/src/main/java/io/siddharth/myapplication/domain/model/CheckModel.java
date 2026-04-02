package io.siddharth.myapplication.domain.model;


public class CheckModel {

    public CheckListModel checkListModel = new CheckListModel();
    public String user;
    public String userId;
    public String doctorNature;
    public int batteryStatus;
    public String batteryStatusTime;
    public String imeiNumber;


    public CheckListModel getCheckListModel() {
        return checkListModel;
    }

    public void setCheckListModel(CheckListModel checkListModel) {
        this.checkListModel = checkListModel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getBatteryStatusTime() {
        return batteryStatusTime;
    }

    public void setBatteryStatusTime(String batteryStatusTime) {
        this.batteryStatusTime = batteryStatusTime;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getDoctorNature() {
        return doctorNature;
    }

    public void setDoctorNature(String doctorNature) {
        this.doctorNature = doctorNature;
    }
}
