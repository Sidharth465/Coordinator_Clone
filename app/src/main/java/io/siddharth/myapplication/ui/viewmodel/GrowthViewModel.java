package io.siddharth.myapplication.ui.viewmodel;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.siddharth.myapplication.domain.model.GrowthAssessmentModel;

public class GrowthViewModel extends ViewModel {

    public final MutableLiveData<String> height = new MutableLiveData<>("");
    public final MutableLiveData<String> weight = new MutableLiveData<>("");
    public final MutableLiveData<String> bmiResult = new MutableLiveData<>("BMI: --");

    public void calculateBmi() {
        try {
            double h = Double.parseDouble(height.getValue()) / 100.0;
            double w = Double.parseDouble(weight.getValue());
            if (h > 0 && w > 0) {
                double bmi = w / (h * h);
                bmiResult.setValue(String.format("BMI: %.2f", bmi));
            }
        } catch (Exception e) {
            bmiResult.setValue("BMI: --");
        }
    }
}