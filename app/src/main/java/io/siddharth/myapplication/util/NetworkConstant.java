package io.siddharth.myapplication.util;

public class NetworkConstant {
    private static final String TAG = "NetworkConstant";

    public static final int REQUEST_ID_LOGIN = 1;
    public static final int REQUEST_ID_STUDENT_DATA_LIST = 2;
    public static final int REQUEST_ID_OMR_DATA = 3;
    public static final int REQUEST_ID_STUDENT_UPLOAD = 4;
    public static final int REQUEST_ID_DOCTOR_UPLOAD = 5;
    public static final int REQUEST_ID_NODE_DATA = 6;

    public static final int REQUEST_ID_IMAGE_UPLOAD_ACCESS = 7;
    public static final int REQUEST_ID_IMAGE_UPLOAD_BUCKET = 8;
    public static final int REQUEST_ID_IMAGE_UPLOAD_CONTENT = 9;
    public static final int REQUEST_ID_IMAGE_UPLOAD_CONTENT_ASSOCIATION = 10;

    public static final int REQUEST_ID_UPDATE_USER = 11;
    public static final int REQUEST_ID_SCHOOL_CONTENT = 12;

    public static final int REQUEST_ID_DOCTOR_SIGN_IMAGE_UPLOAD_BUCKET = 13;
    public static final int REQUEST_ID_MSP_ORGANIZATION = 14;
    public static final int REQUEST_ID_EVENT_TRACK = 15;
    public static final int REQUEST_ID_STUDENT_LIST_REFRESH = 16;
    public static final int REQUEST_ID_NODE_DATA_REFRESH = 17;
    public static final int REQUEST_ID_ORGANIZATION_REFRESH = 18;
    public static final int REQUEST_ID_UPLOAD_DATA_FLAG = 19;
    public static final int REQUEST_ID_QC_FLAG = 20;
    public static final int REQUEST_ID_EVENT_REPORTING = 21;
    public static final int REQUEST_ID_MATA_DATA = 22;
    public static final int REQUEST_ID_ISP_ORGANIZATION = 23;

    public static final String url = "https://api.healthsetgo.com/";
//    public static final String url = "http://35.154.246.223/";

    // public static final String STUDENT_LIST_DOWNLOAD_URL = url + "api/v2/download/omr/flat";
    public static final String STUDENT_LIST_DOWNLOAD_URL = url + "api/v2/download/omr/flat/v2";
    public static final String STUDENT_UPLOAD_URL = url + "api/v2/upload/app/user";

    public static final String DOCTOR_UPLOAD_URL = url + "api/v2/user";
    public static final String OMR_UPLOAD_URL = url + "api/v2/upload/omr";

    public static final String LOGIN_URL = url + "login";
    public static final String NODE_URL = url + "api/v2/nodeIds";

    public static final String IMAGE_UPLOAD_ACCESSES_URL = url + "api/s3/uploadaccess";
    public static final String IMAGE_BUCKET_URL = "http://hsgbucket.s3.amazonaws.com";
    public static final String IMAGE_CONTENT_URL = url + "api/v2/content";
    public static final String IMAGE_CONTENT_ASSOCIATION_URL = url + "api/v2/content/association";

    public static final String USER_UPDATE_URL = url + "api/v2/user";
    public static final String ORGANIZATION_URL = url + "api/v2/organization";
    public static final String EVENT_TRACK_URL = url + "api/v2/event";
    public static final String EVENT_REPORTING_URL = url + "api/v2/assessment/reporting";
    public static final String META_URL = url + "api/v2/assessment/key/meta";


}
