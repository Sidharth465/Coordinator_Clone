package io.siddharth.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.siddharth.myapplication.databinding.ItemStudentBinding;
import io.siddharth.myapplication.domain.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private List<StudentModel> students = new ArrayList<>();
    private OnStudentClickListener listener;

    public interface OnStudentClickListener {
        void onStudentClick(StudentModel student);
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding binding = ItemStudentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel student = students.get(position);
        holder.binding.studentName.setText(student.name);
        holder.binding.admissionNumber.setText("ID: " + student.admissionNo);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.onStudentClick(student);
        });
    }

    @Override
    public int getItemCount() { return students.size(); }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        final ItemStudentBinding binding;
        StudentViewHolder(ItemStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}