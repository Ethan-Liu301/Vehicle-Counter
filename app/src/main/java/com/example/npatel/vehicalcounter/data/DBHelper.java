package com.example.npatel.vehicalcounter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.npatel.vehicalcounter.data.regContract.dbinfo;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context)
    {
        super(context,dbinfo.DBNAME,null,dbinfo.DBVERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        String sql="create table " +dbinfo.TBNAME + " ("
                + dbinfo.DATE  + "' DATE',"
                + dbinfo.TWQ + "' INTEGER',"
                + dbinfo.FWQ + "' INTEGER');";
        db.execSQL(sql);

    }
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion)
    {

    }
}
