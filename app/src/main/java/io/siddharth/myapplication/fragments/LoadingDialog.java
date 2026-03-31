package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import io.siddharth.myapplication.R;

public class LoadingDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Make the background dim/dark to "block" the screen visually
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            // Prevent user from cancelling by clicking outside
            setCancelable(false);
        }
    }

    // Static method to easily show the dialog
    public static LoadingDialog show(FragmentManager fragmentManager) {
        LoadingDialog dialog = new LoadingDialog();
        dialog.show(fragmentManager, "loading");
        return dialog;
    }
}