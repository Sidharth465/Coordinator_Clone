package io.siddharth.myapplication.domain.model;

import java.io.Serializable;

public class ResponseModel implements Serializable {

    public String assessmentType;
    public boolean assessmentVerified = false;
    public boolean assessmentReceived= false;
    public StudentModel studentModel;
    public String docId;

}
