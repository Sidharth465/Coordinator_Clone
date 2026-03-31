package io.siddharth.myapplication.domain.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LoginResponseModel {

    // Using SerializedName ensures that even if you rename your Java variable,
    // the app still knows which JSON key to look for.
    @SerializedName("jwt")
    public String jwt;

    @SerializedName("token")
    public String token;

    @SerializedName("username")
    public String username;

    @SerializedName("id")
    public Integer id;

    @SerializedName("email")
    public String email;

    @SerializedName("uuid")
    public String uuid;

    @SerializedName("role")
    public List<Role> roles;

    // Helper method to handle cases where the server sends 'jwt' or 'token'
    public String getAuthToken() {
        return (jwt != null && !jwt.isEmpty()) ? jwt : token;
    }

    // Modern 2026 Inner Class for Role
    public static class Role {
        @SerializedName("id")
        public Integer id;

        @SerializedName("name")
        public String name;
    }
}