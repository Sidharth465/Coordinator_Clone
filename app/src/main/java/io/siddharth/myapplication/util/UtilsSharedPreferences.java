package io.siddharth.myapplication.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.siddharth.myapplication.domain.model.LoginResponseModel;
import io.siddharth.myapplication.domain.model.AnalyticsModel;
import io.siddharth.myapplication.domain.model.DoctorModel;
import io.siddharth.myapplication.domain.model.NodeModel;
import io.siddharth.myapplication.domain.model.LoginModel;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UtilsSharedPreferences {

    private static UtilsSharedPreferences instance;
    private final Gson gson; // Modern standard: Use one Gson instance

    // Keys remain exactly the same as your old project
    private static final String ASSESSMENT_DATA_PREF = "assessment_data_pref";
    private static final String ASSESSMENT_DATA_KEY = "assessment_data_key";
    private static final String NODE_PREF = "node_pref";
    private static final String NODE_KEY = "node_key";
    private static final String DOCTOR_PREF = "doctor_pref";
    private static final String DOCTOR_KEY = "doctor_key";
    private static final String LOGIN_PREF = "login_pref";
    private static final String LOGIN_KEY = "login_key";
    private static final String LOGIN_RESPONSE_PREF = "login_response_pref";
    private static final String LOGIN_RESPONSE_KEY = "login_response_key";
    private static final String SCHOOL_CONTENT_NODE_PREF = "school_content_pref";
    private static final String SCHOOL_CONTENT_NODE_KEY = "school_content_key";

    private UtilsSharedPreferences() {
        gson = new Gson();
    }

    public static synchronized UtilsSharedPreferences getInstance() {
        if (instance == null) {
            instance = new UtilsSharedPreferences();
        }
        return instance;
    }

    // --- ASSESSMENT ---
    public void saveAssessmentSharedPreference(Context context, AnalyticsModel analyticsModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASSESSMENT_DATA_PREF, Context.MODE_PRIVATE);
        String json = gson.toJson(analyticsModel);
        sharedPreferences.edit().putString(ASSESSMENT_DATA_KEY, json).apply();
    }

    public AnalyticsModel restoreAssessmentSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASSESSMENT_DATA_PREF, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ASSESSMENT_DATA_KEY, "");
        return gson.fromJson(json, AnalyticsModel.class);
    }

    // --- NODE DETAILS ---
    public void saveNodeDetailsSharedPreference(Context context, NodeModel nodeModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NODE_PREF, Context.MODE_PRIVATE);
        String json = gson.toJson(nodeModel);
        sharedPreferences.edit().putString(NODE_KEY, json).apply();
    }

    public NodeModel restoreNodeDetailsSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NODE_PREF, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(NODE_KEY, "");
        return gson.fromJson(json, NodeModel.class);
    }

    // --- DOCTOR DETAIL (LIST) ---
    public void saveDoctorDetailSharedPref(Context context, List<DoctorModel> doctorList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOCTOR_PREF, Context.MODE_PRIVATE);
        String json = gson.toJson(doctorList);
        sharedPreferences.edit().putString(DOCTOR_KEY, json).apply();
    }

    public List<DoctorModel> restoreDoctorDetailSharedPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOCTOR_PREF, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(DOCTOR_KEY, "");
        if (json.isEmpty()) return new ArrayList<>();

        Type listType = new TypeToken<ArrayList<DoctorModel>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    // --- LOGIN DETAIL ---
    public void saveLoginDetailSharedPreference(Context context, LoginModel loginModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        String json = gson.toJson(loginModel);
        sharedPreferences.edit().putString(LOGIN_KEY, json).apply();
    }

    public LoginModel restoreLoginDetailSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(LOGIN_KEY, "");
        return gson.fromJson(json, LoginModel.class);
    }

    // --- LOGIN RESPONSE ---
    public void saveLoginResponseSharedPreference(Context context, LoginResponseModel responseModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_RESPONSE_PREF, Context.MODE_PRIVATE);
        String json = gson.toJson(responseModel);
        sharedPreferences.edit().putString(LOGIN_RESPONSE_KEY, json).apply();
    }

    public LoginResponseModel restoreLoginResponseSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_RESPONSE_PREF, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(LOGIN_RESPONSE_KEY, "");
        return gson.fromJson(json, LoginResponseModel.class);
    }

    // --- SCHOOL CONTENT ---
    public void saveSchoolContentNode(Context context, String contentNode) {
        context.getSharedPreferences(SCHOOL_CONTENT_NODE_PREF, Context.MODE_PRIVATE)
                .edit().putString(SCHOOL_CONTENT_NODE_KEY, contentNode).apply();
    }

    public String restoreSchoolContentNode(Context context) {
        return context.getSharedPreferences(SCHOOL_CONTENT_NODE_PREF, Context.MODE_PRIVATE)
                .getString(SCHOOL_CONTENT_NODE_KEY, "");
    }

    // --- CLEAR ---
    public void clearSharedPreference(Context context) {
        String[] prefs = {ASSESSMENT_DATA_PREF, NODE_PREF, DOCTOR_PREF, LOGIN_PREF, LOGIN_RESPONSE_PREF, SCHOOL_CONTENT_NODE_PREF};
        for (String pref : prefs) {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().clear().apply();
        }
    }
}