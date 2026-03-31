package io.siddharth.myapplication.database;

import android.net.Uri;

public class DatabaseConstants {

    public DatabaseConstants() {
    }

    //Database Version and Name
    public static final String database_name = "HSG_APP_DATABASE";
    public static final int database_version = 2;

    //Table Names
    public static final String TABLE_REGISTRATION = "student_list";
    public static final String TABLE_ASSESSMENT = "student_assessment";
    public static final String TABLE_IMAGE = "image";
    public static final String TABLE_TABLET_DATA = "tablet_details";

    //Common Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADMISSION_NUMBER = "admission_number";
    public static final String COLUMN_HSG_ID = "u_id";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ASSESSMENT_DATE = "assessment_date";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_DATE_MODIFIED = "modified";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_ASSESSMENT_STATUS = "assessment_status";
    public static final String COLUMN_ASSESSMENT_ID = "assessment_id";
    public static final String COLUMN_UPLOAD_FLAG = "data_upload";

    public static final String COLUMN_IMAGE_NAME = "image_name";
    public static final String COLUMN_IMAGE_OWNER_ID = "image_owner";
    public static final String COLUMN_IMAGE_AWS_LOCATION = "aws_location";

    //Registration Table Column Names
    public static final String COLUMN_REG_TYPE = "reg_type";
    public static final String COLUMN_REG_STATUS = "reg_status";

    public static final String COLUMN_STUDENT_ASSESSMENT_STATUS = "student_assessment_status";
    public static final String COLUMN_STUDENT_ASSESSMENT_REMARK = "student_assessment_remark";


    //AssessmentListModel Table Column Names
    public static final String COLUMN_TYPE_ASSESSMENT = "type_assessment";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";

    public static final String COLUMN_TYPE_IMAGE = "type_image";
    public static final String COLUMN_NODE = "node";


    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_USER = "user";

    //Content Providers URI
    public static final String AUTHORITY = "com.hsg.mvvmpracticeproject.database";

    //URI's
    public static final Uri CONTENT_URI_TABLE_REGISTRATION = Uri.parse("content://" + AUTHORITY + "/" + TABLE_REGISTRATION);
    public static final Uri CONTENT_URI_TABLE_ASSESSMENT = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ASSESSMENT);
    public static final Uri CONTENT_URI_TABLE_IMAGE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_IMAGE);
    public static final Uri CONTENT_URI_TABLE_TABLET_DATA = Uri.parse("content://" + AUTHORITY + "/" + TABLE_TABLET_DATA);


    //URI ID's
    public static final int URI_REG_ID = 100;
    public static final int URI_ASSESSMENT_ID = 101;
    public static final int URI_IMAGE_ID = 102;
    public static final int URI_TABLET_ID = 103;


}