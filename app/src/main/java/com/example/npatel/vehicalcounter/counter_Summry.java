package com.example.npatel.vehicalcounter;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.npatel.vehicalcounter.data.DBHelper;
import com.example.npatel.vehicalcounter.data.regContract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class counter_Summry extends Activity {

    public static ArrayList<vehical_count> wc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_summry);
        delete = findViewById(R.id.delete);
        share = findViewById(R.id.share);
        display();


    }
    ImageView delete;
    ImageView share;

    public void display() {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + regContract.dbinfo.TBNAME, null);


               wc  = new <vehical_count>ArrayList();

        try {
            int cdatei = cursor.getColumnIndex(regContract.dbinfo.DATE);
            int twi = cursor.getColumnIndex(regContract.dbinfo.TWQ);
            int fwi = cursor.getColumnIndex(regContract.dbinfo.FWQ);
            Log.v("Main Activity", "Cdate = " + cdatei);

            for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()) {
                wc.add(new vehical_count(cursor.getString(cdatei), Integer.parseInt(cursor.getString(twi)), Integer.parseInt(cursor.getString(fwi))));
            }
        } finally {
            cursor.close();
        }

        final ListView listView = findViewById(R.id.list_item);
        vehicaladapter va = new vehicaladapter(counter_Summry.this, wc);
        listView.setAdapter(va);
        va.notifyDataSetChanged();











        }



}