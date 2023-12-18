package com.example.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "StudentApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_student";
    private static final String COLUMN_ID = "_ID";
    private static final String COLUMN_FIRST_NAME = "FirstName";
    private static final String COLUMN_LAST_NAME = "LastName";
    private static final String COLUMN_DATE_OF_BIRTH = "DateOfBirth";
    private static final String COLUMN_PHONE_NUMBER = "PhoneNumber";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_DATE_OF_BIRTH + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addStudent(String firstName, String lastName, String birthDay, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, firstName);
        cv.put(COLUMN_LAST_NAME, lastName);
        cv.put(COLUMN_DATE_OF_BIRTH, birthDay);
        cv.put(COLUMN_PHONE_NUMBER, phone);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Student Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String firstName, String lastName, String birthDay, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, firstName);
        cv.put(COLUMN_LAST_NAME, lastName);
        cv.put(COLUMN_DATE_OF_BIRTH, birthDay);
        cv.put(COLUMN_PHONE_NUMBER, phone);

        long result = db.update(TABLE_NAME, cv, "_ID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed To Update Student!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Student Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_ID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed To Delete Student!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Student Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor searchStudents(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_DATE_OF_BIRTH, COLUMN_PHONE_NUMBER};
        String selection = COLUMN_FIRST_NAME + " LIKE ? AND " + COLUMN_LAST_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + firstName + "%", "%" + lastName + "%"};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        return cursor;
    }
}
