package io.siddharth.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.siddharth.myapplication.database.DatabaseConstants;
import io.siddharth.myapplication.domain.model.TempModelAssessment;
import io.siddharth.myapplication.domain.model.TempModelImage;
import io.siddharth.myapplication.domain.model.TempModelStudent;

public class DB_Path {
    private static final String TAG = "DB_Path";
    private static final String OLD_DB_PATH = "/sdcard/HSG_CO/HSG_APP_DATABASE";
    TempModelStudent tempModel;
    TempModelAssessment tempModelAssessmentl;
    TempModelImage tempModelImage;

    public List<TempModelStudent> getDataStudentData(Context context) {

        List<TempModelStudent> tempList = new ArrayList<>();
        File dbFile = new File(OLD_DB_PATH);

        if (!dbFile.exists()) return tempList;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.rawQuery("SELECT * FROM student_list", null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    tempModel = new TempModelStudent();
                    tempModel.u_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_HSG_ID));
                    tempModel.add_no = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_ADMISSION_NUMBER));
                    tempModel.reg_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_REG_TYPE));
                    tempModel.reg_status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_REG_STATUS));
                    tempModel.name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_NAME));
                    tempModel.stuClass = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_CLASS));
                    tempModel.section = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_SECTION));
                    tempModel.data = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATA));
                    tempModel.status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_STATUS));
                    tempModel.date_created = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATE_CREATED));
                    tempModel.modified = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATE_MODIFIED));
                    tempModel.assessment_date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_ASSESSMENT_DATE));
                    tempModel.data_upload = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_UPLOAD_FLAG));
                    tempList.add(tempModel);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return tempList;

    }

    public synchronized void insertStudent(Context context, ArrayList<TempModelStudent> tempModelStudentArrayList) {
        try {
            for (TempModelStudent modelStudent : tempModelStudentArrayList) {
                if (TextUtils.isEmpty(modelStudent.data)) {

                    ContentValues values = new ContentValues();
                    values.put(DatabaseConstants.COLUMN_NAME, modelStudent.name);
                    values.put(DatabaseConstants.COLUMN_HSG_ID, modelStudent.u_id);
                    values.put(DatabaseConstants.COLUMN_DATA, modelStudent.data);
                    values.put(DatabaseConstants.COLUMN_ADMISSION_NUMBER, modelStudent.add_no);
                    values.put(DatabaseConstants.COLUMN_REG_TYPE, modelStudent.reg_type);
                    values.put(DatabaseConstants.COLUMN_REG_STATUS, modelStudent.reg_status);
                    values.put(DatabaseConstants.COLUMN_CLASS, modelStudent.stuClass);
                    values.put(DatabaseConstants.COLUMN_SECTION, modelStudent.section);
                    values.put(DatabaseConstants.COLUMN_DATE_CREATED, modelStudent.date_created);
                    values.put(DatabaseConstants.COLUMN_DATE_MODIFIED, modelStudent.modified);
                    values.put(DatabaseConstants.COLUMN_ASSESSMENT_DATE, modelStudent.assessment_date);
                    values.put(DatabaseConstants.COLUMN_UPLOAD_FLAG, modelStudent.data_upload);
                    values.put(DatabaseConstants.COLUMN_STATUS, modelStudent.status);

                    Uri i = context.getContentResolver().insert(DatabaseConstants.CONTENT_URI_TABLE_REGISTRATION, values);
                    values.clear();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public synchronized void insertImage(Context mContext, ArrayList<TempModelImage> tempModelImageArrayList) {

        try {
            for (TempModelImage image : tempModelImageArrayList) {
                ContentValues values = new ContentValues();

                values.put(DatabaseConstants.COLUMN_TYPE_IMAGE, image.type_image);
                values.put(DatabaseConstants.COLUMN_HSG_ID, image.u_id);
                values.put(DatabaseConstants.COLUMN_IMAGE_OWNER_ID, image.image_owner);
                values.put(DatabaseConstants.COLUMN_IMAGE_NAME, image.image_name);
                values.put(DatabaseConstants.COLUMN_NODE, image.node);
                values.put(DatabaseConstants.COLUMN_IMAGE_AWS_LOCATION, image.aws_location);
                values.put(DatabaseConstants.COLUMN_DATE_CREATED, image.date_created);
                values.put(DatabaseConstants.COLUMN_DATE_MODIFIED, image.date_modified);
                values.put(DatabaseConstants.COLUMN_UPLOAD_FLAG, image.data_upload);

                Uri i = mContext.getContentResolver().insert(DatabaseConstants.CONTENT_URI_TABLE_IMAGE, values);
                values.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TempModelAssessment> getDataStudentAssessmentData(Context mContext) {

        ArrayList<TempModelAssessment> tempList = new ArrayList<>();

        File dbfile = new File(OLD_DB_PATH);
        if (!dbfile.exists()) return tempList;

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            db1 = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            String qr = "select * from student_assessment";
            cursor = db1.rawQuery(qr, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    tempModelAssessmentl = new TempModelAssessment();
                    tempModelAssessmentl.u_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_HSG_ID));
                    tempModelAssessmentl.doctor_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DOCTOR_ID));
                    tempModelAssessmentl.type_assessment = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_TYPE_ASSESSMENT));
                    tempModelAssessmentl.student_assessment_status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_STUDENT_ASSESSMENT_STATUS));
                    tempModelAssessmentl.student_assessment_remark = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_STUDENT_ASSESSMENT_REMARK));
                    tempModelAssessmentl.name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_NAME));
                    tempModelAssessmentl.aws_location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_IMAGE_AWS_LOCATION));

                    tempModelAssessmentl.stuClass = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_CLASS));
                    tempModelAssessmentl.section = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_SECTION));
                    tempModelAssessmentl.data = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATA));
                    tempModelAssessmentl.date_created = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATE_CREATED));
                    tempModelAssessmentl.modified = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATE_MODIFIED));
                    tempModelAssessmentl.data_upload = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_UPLOAD_FLAG));
                    tempList.add(tempModelAssessmentl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db1 != null) db1.close();
        }
        return tempList;
    }

    public ArrayList<TempModelImage> getDataImageData(Context mContext) {

        ArrayList<TempModelImage> tempList = new ArrayList<>();

        File dbfile = new File(OLD_DB_PATH);
        if (!dbfile.exists()) return tempList;

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            db1 = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            String qr = "select * from image";
            cursor = db1.rawQuery(qr, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    tempModelImage = new TempModelImage();
                    tempModelImage.u_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_HSG_ID));
                    tempModelImage.aws_location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_IMAGE_AWS_LOCATION));
                    tempModelImage.type_image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_TYPE_IMAGE));
                    tempModelImage.node = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_NODE));
                    tempModelImage.image_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_IMAGE_NAME));
                    tempModelImage.image_owner = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_IMAGE_OWNER_ID));

                    tempModelImage.date_created = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATE_CREATED));
                    tempModelImage.date_modified = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DATE_MODIFIED));
                    tempModelImage.data_upload = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_UPLOAD_FLAG));
                    tempList.add(tempModelImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db1 != null) db1.close();
        }
        return tempList;
    }

    public synchronized void insertAssessment(Context mContext, ArrayList<TempModelAssessment> tempModelAssessment) {

        try {
            for (TempModelAssessment modelAssessment : tempModelAssessment) {
                // if (!TextUtils.isEmpty(modelAssessment.data)) {
                ContentValues values = new ContentValues();

                values.put(DatabaseConstants.COLUMN_NAME, modelAssessment.name);
                values.put(DatabaseConstants.COLUMN_HSG_ID, modelAssessment.u_id);
                values.put(DatabaseConstants.COLUMN_DATA, modelAssessment.data);
                values.put(DatabaseConstants.COLUMN_DOCTOR_ID, modelAssessment.doctor_id);
                values.put(DatabaseConstants.COLUMN_TYPE_ASSESSMENT, modelAssessment.type_assessment);
                values.put(DatabaseConstants.COLUMN_IMAGE_AWS_LOCATION, modelAssessment.aws_location);
                values.put(DatabaseConstants.COLUMN_CLASS, modelAssessment.stuClass);
                values.put(DatabaseConstants.COLUMN_SECTION, modelAssessment.section);
                values.put(DatabaseConstants.COLUMN_DATE_CREATED, modelAssessment.date_created);
                values.put(DatabaseConstants.COLUMN_DATE_MODIFIED, modelAssessment.modified);
                values.put(DatabaseConstants.COLUMN_UPLOAD_FLAG, modelAssessment.data_upload);
                values.put(DatabaseConstants.COLUMN_STUDENT_ASSESSMENT_STATUS, modelAssessment.student_assessment_status);
                values.put(DatabaseConstants.COLUMN_STUDENT_ASSESSMENT_REMARK, modelAssessment.student_assessment_remark);

                Uri i = mContext.getContentResolver().insert(DatabaseConstants.CONTENT_URI_TABLE_ASSESSMENT, values);
                values.clear();
            }
            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}