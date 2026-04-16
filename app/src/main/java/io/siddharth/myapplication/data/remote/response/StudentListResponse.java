package io.siddharth.myapplication.data.remote.response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import io.siddharth.myapplication.domain.model.StudentModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StudentListResponse {
    @SerializedName("results")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        @SerializedName("students")
        private JsonElement students;

        public List<StudentModel> getStudents() {
            if (students == null || students.isJsonNull()) {
                return new ArrayList<>();
            }
            if (students.isJsonArray()) {
                Type listType = new TypeToken<List<StudentModel>>() {}.getType();
                return new Gson().fromJson(students, listType);
            } else if (students.isJsonPrimitive() && students.getAsJsonPrimitive().isString()) {
                // Handle the case where students is a JSON string
                String jsonString = students.getAsString();
                Type listType = new TypeToken<List<StudentModel>>() {}.getType();
                try {
                    return new Gson().fromJson(jsonString, listType);
                } catch (Exception e) {
                    return new ArrayList<>();
                }
            }
            return new ArrayList<>();
        }

        public void setStudents(JsonElement students) {
            this.students = students;
        }
    }
}
