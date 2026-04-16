package io.siddharth.myapplication.domain.model;

import java.io.Serializable;

public class PhysicalAssessmentModel implements Serializable {
    // Hygiene
    public String Hygiene_hands_and_nails = "";
    public String Hygiene_Hair = "";

    // General Signs
    public String General_signs_Pallor = "";
    public String General_signs_Cynosis = "";
    public String General_signs_Anemia = "";
    public String General_signs_Clubbing = "";
    public String General_signs_Edema = "";
    public String General_signs_Others = "";

    // Additional fields from legacy for completeness
    public String Skin_Itching = "";
    public String Skin_Others = "";
    public String Respiratory_Others = "";
    public String Abdomen_Others = "";
    public String Physical_Doctor_Remarks = "";

    public PhysicalAssessmentModel() {}
}