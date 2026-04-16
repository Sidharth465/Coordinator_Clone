package io.siddharth.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.siddharth.myapplication.domain.model.CheckListModel;

public class CheckListViewModel extends ViewModel {

    private final CheckListModel checkListModel = new CheckListModel();

    // LiveData to observe if the checklist is complete
    private final MutableLiveData<Boolean> _isComplete = new MutableLiveData<>(false);
    public final LiveData<Boolean> isComplete = _isComplete;

    public void setMspCheck(boolean checked) {
        checkListModel.setMspCheck(checked);
        validate();
    }

    public void setIspCheck(boolean checked) {
        checkListModel.setIspCheck(checked);
        validate();
    }

    public void setStudentListCheck(boolean checked) {
        checkListModel.setStudentListCheck(checked);
        validate();
    }

    public void setBatteryCheck(boolean checked) {
        checkListModel.setBatteryCheck(checked);
        validate();
    }

    public void setComment(String comment) {
        checkListModel.setComment(comment);
    }

    private void validate() {
        // Business logic: All checkboxes must be true to proceed
        boolean allChecked = checkListModel.isMspCheck() &&
                checkListModel.isIspCheck() &&
                checkListModel.isStudentListCheck() &&
                checkListModel.isBatteryCheck();

        _isComplete.setValue(allChecked);
    }

    public CheckListModel getModel() {
        return checkListModel;
    }
}