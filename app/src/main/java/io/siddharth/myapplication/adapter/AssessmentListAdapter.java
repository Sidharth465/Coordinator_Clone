package io.siddharth.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import io.siddharth.myapplication.databinding.ItemAssessmentStudentBinding;
import io.siddharth.myapplication.domain.model.AssessmentListModel;

public class AssessmentListAdapter extends ListAdapter<AssessmentListModel, AssessmentListAdapter.ViewHolder> {

    private final OnStudentClickListener listener;

    public interface OnStudentClickListener {
        void onStudentClick(AssessmentListModel model);
    }

    public AssessmentListAdapter(OnStudentClickListener listener) {
        super(new DiffUtil.ItemCallback<AssessmentListModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull AssessmentListModel oldItem, @NonNull AssessmentListModel newItem) {
                return oldItem.studentModel.id != null && oldItem.studentModel.id.equals(newItem.studentModel.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull AssessmentListModel oldItem, @NonNull AssessmentListModel newItem) {
                return oldItem.studentModel.name.equals(newItem.studentModel.name) &&
                        oldItem.studentModel.assessmentStatus == newItem.studentModel.assessmentStatus;
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAssessmentStudentBinding binding = ItemAssessmentStudentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssessmentListModel model = getItem(position);

        // Mapping data to UI
        holder.binding.tvStudentName.setText(model.studentModel.name);
        holder.binding.tvAdmissionNo.setText("Adm No: " + model.studentModel.admissionNo);
        holder.binding.tvClassSection.setText("Class: " + model.studentModel.stuClass + " - " + model.studentModel.stuSection);

        // Handle click
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.onStudentClick(model);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemAssessmentStudentBinding binding;
        ViewHolder(ItemAssessmentStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}