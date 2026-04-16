package io.siddharth.myapplication.domain.model;

import com.google.gson.annotations.SerializedName;

public class OrganizationModel {

    @SerializedName("id")
    private String id;

    @SerializedName("inOrganizationName")
    private String organizationName;

    // Getters
    public String getId() { return id; }
    public String getOrganizationName() { return organizationName; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setOrganizationName(String name) { this.organizationName = name; }
}