package com.jobaidulhasan665.Covid19app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    static int home_num,district_num;
    static  String db_name="Apps";
    static  int version=1;
    static String tb_name="Home";
    static String tb_name1="District";

    static  String sl="Sl";
    static String cases="Total_Confirmed";


    static String death="Total_death";


    static String recovered="Total_recovered";


    static String active="Total_Active";


    static String today_death="Today_Death";


    static String today_cases="Today_Confirmed";


    static String  last_update="Last_Update";


    static String Country="Country";
    static String Flag="Flag";
    static String cretical="Cretecal";
    static String datePerOnmilion="DeathPerOnMilion";
    static String caseperOnmilion="casePerOnmilion";
    static String total_test="Total_test";
    static String create_table="CREATE TABLE "+tb_name+"("+sl+" INTEGER PRIMARY KEY AUTOINCREMENT,"+cases+
            " varchar(20),"+death+" varchar(20),"+recovered+" varchar(10),"+active+" varchar(20),"+today_cases+" varchar(20),"+
             today_death+" varchar(20),"+last_update+" long(20))";
    static String dropTable="DROP TABLE IF EXISTS "+tb_name;
    static  String disply_data="SELECT * FROM "+tb_name;
    Context context;
    /////////////////Create Table-------------------?
    public DataBase(@Nullable Context context) {
        super(context, db_name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        home_num=1;
        db.execSQL(create_table);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable);
        onCreate(db);
    }
    long insert_data(String r_cases,String r_death,String r_recovered,String r_active,String r_today_cases,String r_today_death,long r_last_update)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(cases,r_cases);
        contentValues.put(death,r_death);
        contentValues.put(recovered,r_recovered);
        contentValues.put(active,r_active);
        contentValues.put(today_cases,r_today_cases);
        contentValues.put(today_death,r_today_death);
        contentValues.put(last_update,r_last_update);
        return sqLiteDatabase.insert(tb_name,null,contentValues);
    }

    long update(String r_cases,String r_death,String r_recovered,String r_active,String r_today_cases,String r_today_death,long r_last_update)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(cases,r_cases);
        contentValues.put(death,r_death);
        contentValues.put(recovered,r_recovered);
        contentValues.put(active,r_active);
        contentValues.put(today_death,r_today_death);
        contentValues.put(today_cases,r_today_cases);
        contentValues.put(last_update,r_last_update);
        return sqLiteDatabase.update(tb_name,contentValues,sl+" = ?",new String[]{"1"});
    }
    Cursor display()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(disply_data,null);

    }

}
