package io.siddharth.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.siddharth.myapplication.domain.model.StudentModel;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudents(List<StudentModel> students);

    @Query("SELECT * FROM students WHERE assessmentStatus IN (:statuses)")
    LiveData<List<StudentModel>> getStudentsByMultipleStatuses(int[] statuses);

    @Query("SELECT * FROM students WHERE assessmentStatus IN (:statuses) AND (name LIKE :query OR admissionNo LIKE :query)")
    LiveData<List<StudentModel>> searchStudentsByMultipleStatuses(int[] statuses, String query);

    @Query("SELECT * FROM students")
    LiveData<List<StudentModel>> getAllStudents();

    @Query("SELECT * FROM students WHERE assessmentStatus = :status")
    LiveData<List<StudentModel>> getStudentsByStatus(int status);

    @Query("SELECT * FROM students WHERE name LIKE :query OR admissionNo LIKE :query")
    LiveData<List<StudentModel>> searchAllStudents(String query);

    @Query("SELECT * FROM students WHERE assessmentStatus = :status AND (name LIKE :query OR admissionNo LIKE :query)")
    LiveData<List<StudentModel>> searchStudentsByStatus(int status, String query);

    @Update
    void updateStudent(StudentModel student);

    @Query("SELECT DISTINCT stuClass FROM students ORDER BY stuClass ASC")
    LiveData<List<String>> getClasses();

    @Query("SELECT DISTINCT stuSection FROM students ORDER BY stuSection ASC")
    LiveData<List<String>> getAllSections();

    @Query("SELECT DISTINCT stuSection FROM students WHERE stuClass = :stuClass ORDER BY stuSection ASC")
    LiveData<List<String>> getSectionsByClass(String stuClass);

    @Query("SELECT * FROM students WHERE " +
            "(:stuClass IS NULL OR stuClass = :stuClass) AND " +
            "(:stuSection IS NULL OR stuSection = :stuSection) AND " +
            "(assessmentStatus IN (:statuses)) AND " +
            "(name LIKE :query OR admissionNo LIKE :query)")
    LiveData<List<StudentModel>> filterStudents(int[] statuses, String stuClass, String stuSection, String query);

    @Query("DELETE FROM students")
    void deleteAllStudents();
}
