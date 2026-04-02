package io.siddharth.myapplication.util;



public class Constants {
    public static String DOCTOR_NATURE_ASSESSMENT = null;
    public static String PHYSICAL_ASSESSMENT = "physical";
    public static String DENTAL_ASSESSMENT = "dental";
    public static String GROWTH_ASSESSMENT = "growth";
    public static String ENT_ASSESSMENT = "ent";
    public static String EYE_ASSESSMENT = "eye";
    public static String SUPER_DOCTOR_ASSESSMENT = "superDoctor";

    public static String STATUS_COMPLETE = "Completed";
    public static String STATUS_INCOMPLETE = "Incomplete";
    public static String STATUS_PENDING = "Pending";
    public static String STATUS_ONGOING = "OnGoing";
    public static String STATUS_ABSENT = "Absent";
    public static String STATUS_NOT_STARTED = "Not Started";
    public static String STATUS_BLANK = "NA";
    public static String STATUS_NEW_REGISTRATION = "1";

    public static int ASSESSMENT_STATUS_COMPLETE = 2;
    public static int ASSESSMENT_STATUS_PENDING = 0;
    public static int ASSESSMENT_STATUS_ONGOING = 1;
    public static int ASSESSMENT_STATUS_ABSENT = 3;
    public static int ASSESSMENT_STATUS_RESEND = 4;
    public static int ASSESSMENT_STATUS_NOT_STARTED = 5;
    public static int NEW_REGISTRATION = 6;
    public static int ASSESSMENT_STATUS_INCOMPLETE = 7;
    public static int ASSESSMENT_STATUS_REASSESSMENT = 8;

    public static final String STUDENT_PROFILE_IMAGE = "1";
    public static final String DOCTOR_PROFILE_IMAGE = "2";
    public static final String DOCTOR_SIGN_IMAGE = "3";
    public static final String EVENT_IMAGE = "4";

    public static final String COORDINATOR_ID_IMAGE = "5";
    public static final String COORDINATOR_PROFILE_IMAGE = "6";

    public static final String ASSESSMENT_ONGOING = "0";
    public static final String ASSESSMENT_COMPLETE = "1";
    public static final String ASSESSMENT_INCOMPLETE = "2";
    public static final String ASSESSMENT_HIGHRISK = "3";
    public static final String ASSESSMENT_RESEND = "4";
    public static final String ASSESSMENT_COMPLETE_MANUAL = "5";


    public static final String FIRST_COORDINATOR_DAY_LOGIN = "1";
    public static final String COORDINATOR_ALL_DAY_LOGIN = "2";
    public static final String DOCTOR_DAY_LOGIN = "3";

    public static final int ISP_MESSAGE = 1001;
    public static final int MSP_MESSAGE = 1002;
    public static final int PARTNER_MESSAGE = 1003;

    public static final int CAMERA_REQUEST_SIG = 100;
    public static final int CAMERA_REQUEST_DOC_IMAGE = 101;

    public static final int CAMERA_REQUEST_UNIQUE_ID = 102;
    public static final int CAMERA_REQUEST_PROFILE_IMAGE = 103;

    public static final String EVENT_COORDINATOR_FIRST_LOGIN = "1";
    public static final String EVENT_DOCTOR_LOGIN = "2";
    public static final String EVENT_HELP_SUPPORT = "3";
    public static final String EVENT_COORDINATOR_LOGIN = "4";

    public static String UPLOAD_FLAG = "1";

    public static String NOT_UPLOAD_FLAG = "0";

    public static String[] assessmentArray = {PHYSICAL_ASSESSMENT, DENTAL_ASSESSMENT, GROWTH_ASSESSMENT, ENT_ASSESSMENT, EYE_ASSESSMENT};

    public static String[] assessmentStatusArray = {"All", String.valueOf(ASSESSMENT_STATUS_PENDING), String.valueOf(ASSESSMENT_STATUS_COMPLETE),
            String.valueOf(ASSESSMENT_STATUS_ONGOING), String.valueOf(ASSESSMENT_STATUS_ABSENT), String.valueOf(NEW_REGISTRATION), String.valueOf(ASSESSMENT_STATUS_INCOMPLETE)};


    public static final String OPTOMETRIST_STRING = "Optometrist";
    public static final String PARAMEDIC_STRING = "Paramedic";

    public static String TRAINING_CHILD_ADMISSION_NUMBER = "ADT123";
    public static String TRAINING_CHILD_NAME = "Training One";
    public static String TRAINING_CHILD_CLASS = "X";
    public static String TRAINING_CHILD_DOB = "2011-01-01";
    public static String TRAINING_CHILD_HSG_ID = "HSG-T-123";
    public static String TRAINING_CHILD_GENDER = "M";

    public static String TRAINING_CHILD_TWO_ADMISSION_NUMBER = "ADT1123";
    public static String TRAINING_CHILD_TWO_NAME = "Training Two";
    public static String TRAINING_CHILD_TWO_CLASS = "X";
    public static String TRAINING_CHILD_TWO_DOB = "2011-01-01";
    public static String TRAINING_CHILD_TWO_HSG_ID = "HSG-T-1123";
    public static String TRAINING_CHILD_TWO_GENDER = "F";

    public static String TRAINING_HSG_ID = "HSG-T-1";
    public static String TRAINING_COMPLETE = "1";

    public static String BLANK_STRING = "";
}