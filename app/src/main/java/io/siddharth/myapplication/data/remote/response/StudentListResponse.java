package io.siddharth.myapplication.data.remote.response;

import com.google.gson.annotations.SerializedName;
import io.siddharth.myapplication.domain.model.StudentListModel;
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
        private List<StudentListModel> students;

        public List<StudentListModel> getStudents() {
            return students;
        }

        public void setStudents(List<StudentListModel> students) {
            this.students = students;
        }
    }
}
