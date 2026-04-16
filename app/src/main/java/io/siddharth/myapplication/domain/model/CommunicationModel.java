package io.siddharth.myapplication.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunicationModel implements Serializable {

    public String id;
    public String type;
    public String ipAddress;
    public String response;
    public byte[] file;
    public String content;
    public boolean resend;
    public int batteryStatus;
    public String batteryStatusTime;
    public String deviceId;

}