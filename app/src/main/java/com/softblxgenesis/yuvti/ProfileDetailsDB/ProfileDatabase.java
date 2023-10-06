package com.softblxgenesis.yuvti.ProfileDetailsDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProfileDatabase extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "YuvProfile";
        public static final String TABLE_YUV = "yuv";
        public static final String ID = "id";
        private static final String NAME = "name";
        private static final String PIN = "pin";
        private static final String MSG = "message";



        public ProfileDatabase(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_STUDENT_TABLE = " CREATE TABLE " + TABLE_YUV + "(" + ID + " INTEGER PRIMARY KEY, "
                    + NAME + " TEXT, " + PIN + " TEXT, " + MSG + " TEXT " + ")";

            db.execSQL(CREATE_STUDENT_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_YUV);

            onCreate(db);
        }


        public void addProfile(YuvAttr yuvAttr){
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME,yuvAttr.getName());
            contentValues.put(PIN,yuvAttr.getPin());
            contentValues.put(MSG,yuvAttr.getMessage());
            db.insert(TABLE_YUV,null,contentValues);
            db.close();
        }

        // code to get the single data

        public YuvAttr getProfile(int id){
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from " + TABLE_YUV + " where id="+id+"", null);


            if(cursor != null)
                cursor.moveToFirst();
            YuvAttr yuvAttr  = new YuvAttr(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)),cursor.getString(3));

            return yuvAttr;
        }

//        // code to get all contacts in a list view
//
//        public List getAllData(){
//            List data = new ArrayList<>();
//            String selectQuery = "SELECT * FROM " + TABLE_YUV;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery,null);
//            if(cursor.moveToFirst()){
//                do{
//                    YuvAttr yuvAttr = new YuvAttr();
//                    yuvAttr.setId(Integer.parseInt(cursor.getString(0)));
//                    yuvAttr.setName(cursor.getString(1));
//                    yuvAttr.setPin(Integer.parseInt(cursor.getString(2)));
//                    yuvAttr.setMessage(cursor.getString(3));
//                    data.add(yuvAttr);
//                }while(cursor.moveToNext());
//            }
//            return data;
//        }

        // code to update the single data
        public boolean updateProfile(int id, String name, int pin, String message){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME,name);
            contentValues.put(PIN,pin);
            contentValues.put(MSG,message);
            db.update(TABLE_YUV, contentValues, ID + " = ? " ,
                    new String[]{String.valueOf(id)});

            return true;
        }


        // Deleting single data
        public void deleteProfile(YuvAttr yuvAttr){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_YUV, ID + " = ? ",
                    new String[]{String.valueOf(yuvAttr.getId())});
            db.close();
        }


        // Getting data Count
        public int getProfileCount() {
            int count=0;
            String countQuery = "SELECT  * FROM " + TABLE_YUV;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);

            if(cursor != null && !cursor.isClosed()){
                count = cursor.getCount();
                cursor.close();
            }

            return count;
        }

        public int getLastYuvId(){
            int count = 0;
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_YUV;
            Cursor cursor = db.rawQuery(query,null);
            if(cursor != null && !cursor.isClosed()){
                cursor.moveToLast();

                if(cursor.getCount() == 0) {
                    count = 1;
                }else{
                    count = cursor.getInt(cursor.getColumnIndex(ID));
                }
            }

            return count;

        }
    }