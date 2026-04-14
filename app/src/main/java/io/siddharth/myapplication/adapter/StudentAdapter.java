package io.siddharth.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.databinding.ItemStudentBinding;
import io.siddharth.myapplication.domain.model.StudentModel;
import io.siddharth.myapplication.util.Constants;

public class StudentAdapter extends ListAdapter<StudentModel, StudentAdapter.StudentViewHolder> {

    public StudentAdapter() {
        super(new DiffUtil.ItemCallback<StudentModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull StudentModel oldItem, @NonNull StudentModel newItem) {
                return oldItem.id != null && oldItem.id.equals(newItem.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull StudentModel oldItem, @NonNull StudentModel newItem) {
                return oldItem.assessmentStatus == newItem.assessmentStatus &&
                       oldItem.name.equals(newItem.name);
            }
        });
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
        holder.bind(getItem(position));
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final ItemStudentBinding binding;

        public StudentViewHolder(ItemStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StudentModel student) {
            binding.studentName.setText(student.getName());
            binding.admissionNumber.setText("Adm: #" + student.getAdmissionNo());
            
            // Set Status Text and Color
            updateStatusBadge(student.getAssessmentStatus());
        }

        private void updateStatusBadge(int status) {
            String statusText;
            int colorRes;

            if (status == Constants.ASSESSMENT_STATUS_COMPLETE) {
                statusText = "COMPLETE";
                colorRes = R.color.colorGreen;
            } else if (status == Constants.ASSESSMENT_STATUS_ONGOING) {
                statusText = "ONGOING";
                colorRes = R.color.colorOrange;
            } else {
                statusText = "PENDING";
                colorRes = R.color.colorText;
            }

            binding.statusBadge.setText(statusText);
            binding.statusBadge.getBackground().setTint(
                    ContextCompat.getColor(itemView.getContext(), colorRes));
        }
    }
}
