package io.siddharth.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

// Change this import to match your new project's package name
import io.siddharth.myapplication.databinding.ItemDoctorBinding;
import io.siddharth.myapplication.domain.model.DoctorModel;

public class DoctorListAdapter extends ListAdapter<DoctorModel, DoctorListAdapter.ViewHolder> {

    private final OnDoctorClickListener listener;

    public interface OnDoctorClickListener {
        void onDoctorClick(DoctorModel doctor);
    }

    public DoctorListAdapter(OnDoctorClickListener listener) {
        super(new DiffUtil.ItemCallback<DoctorModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull DoctorModel oldItem, @NonNull DoctorModel newItem) {
                return oldItem.docId != null && oldItem.docId.equals(newItem.docId);
            }

            @Override
            public boolean areContentsTheSame(@NonNull DoctorModel oldItem, @NonNull DoctorModel newItem) {
                return oldItem.docName.equals(newItem.docName) &&
                        oldItem.docNatureOfAssessment.equals(newItem.docNatureOfAssessment);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorBinding binding = ItemDoctorBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorModel doctor = getItem(position);

        holder.binding.name.setText(doctor.docName);
        holder.binding.specialization.setText(doctor.docNatureOfAssessment);

        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.onDoctorClick(doctor);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemDoctorBinding binding;

        ViewHolder(ItemDoctorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}