package com.example.npatel.vehicalcounter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npatel.vehicalcounter.data.DBHelper;
import com.example.npatel.vehicalcounter.data.regContract;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    public MainActivity()
    {
        twc=0;
        fwc=0;
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        twd = (TextView) findViewById(R.id.twc);
        fwd =(TextView) findViewById(R.id.fwc);
        twp = (Button) findViewById(R.id.twp);
        fwp = (Button) findViewById(R.id.fwp);
        twm = (Button) findViewById(R.id.twm);
        fwm = (Button) findViewById(R.id.fwm);
        twrst = (Button) findViewById(R.id.twrst);
        fwrst = (Button) findViewById(R.id.fwrst);
        view = (Button) findViewById(R.id.view);


        init();
        count();
    }
    TextView twd,fwd;
    Button twp,fwp,twm,fwm,twrst,fwrst,save,view;
    int twc,fwc;

public void init() {

    boolean chk = chk_rec();
    if (chk) {
         String current_date;

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
        current_date = dateFormat.format(date);
         DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + regContract.dbinfo.TBNAME, null);
        try {
            int cdatei = cursor.getColumnIndex(regContract.dbinfo.DATE);

            String cdate;
            int dtwc, dfwc;
            int twi = cursor.getColumnIndex(regContract.dbinfo.TWQ);
            int fwi = cursor.getColumnIndex(regContract.dbinfo.FWQ);
            while (cursor.moveToNext()) {
                dtwc = Integer.parseInt(cursor.getString(twi));
                dfwc = Integer.parseInt(cursor.getString(fwi));
                cdate = cursor.getString(cdatei);
                int datechk = cdate.compareToIgnoreCase(current_date);
                if (datechk == 0) {
                    twc = dtwc;
                    fwc = dfwc;
                }
            }
        } finally {
            cursor.close();
        }

    } else {
        twc = 0;
        fwc = 0;
    }

    twcdis();
    fwcdis();

}

public void count() {

    twp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (chk_rec()) {
                update(get_date(), twc += 1, fwc);
                twcdis();
            } else {
                twc++;
                add_record();
                twcdis();
            }
        }
    });
    twm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (twc >= 1) {
                if (chk_rec()) {
                    update(get_date(), twc -= 1, fwc);
                    twcdis();
                }
            }
             else {
                Toast.makeText(getApplicationContext(), "Count must be 1 or Greater ", Toast.LENGTH_SHORT).show();
            }
        }
    });
    fwp.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            if (chk_rec()) {
                update(get_date(), twc, fwc += 1);
                 fwcdis();
            } else {
                fwc++;
                add_record();
                fwcdis();
            }
        }
    });
    fwm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (fwc >= 1) {
                if (chk_rec()) {
                    update(get_date(), twc, fwc -=1);
                    fwcdis();
                }
            } else
            {
                Toast.makeText(getApplicationContext(), "Count must be 1 or Greater ", Toast.LENGTH_SHORT).show();
        }

        }
    });
    twrst.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Are you sure Want To Reset Count ");
            alertDialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    update(get_date(), 0, fwc);
                    twcdis();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            alertDialog.show();
        }
    });
    fwrst.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Are you sure Want To Reset");
            alertDialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    update(get_date(), twc, 0);
                    fwcdis();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            alertDialog.show();

        }
    });

    view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
    {
        Intent intent = new Intent(MainActivity.this,counter_Summry.class);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }


    });


}


public void twcdis() {
    String cdate,current_date=get_date();
    DBHelper dbHelper = new DBHelper(getApplicationContext());
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from "+ regContract.dbinfo.TBNAME,null);

    try
    {

      int cdatei= cursor.getColumnIndex(regContract.dbinfo.DATE);
       while(cursor.moveToNext()) {
        cdate = cursor.getString(cdatei);
        int chk = cdate.compareToIgnoreCase(current_date);
        if (chk==0) {
            int twci=cursor.getColumnIndex(regContract.dbinfo.TWQ);
            twc=cursor.getInt(twci);

        }

   }
}
    finally {
        cursor.close();
    }
    twd.setText(String.valueOf(twc));
}
public void fwcdis()
{
    String cdate,current_date=get_date();
    DBHelper dbHelper = new DBHelper(getApplicationContext());
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from "+ regContract.dbinfo.TBNAME,null);

    try
    {

        int cdatei= cursor.getColumnIndex(regContract.dbinfo.DATE);
        while(cursor.moveToNext()) {
            cdate = cursor.getString(cdatei);
            int chk = cdate.compareToIgnoreCase(current_date);
            if (chk==0) {
                int fwci=cursor.getColumnIndex(regContract.dbinfo.FWQ);
                fwc=cursor.getInt(fwci);

            }

        }
    }
    finally {
        cursor.close();
    }
    fwd.setText(String.valueOf(fwc));

}
public void add_record()
{
    DBHelper dbHelper = new DBHelper(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues cv = new ContentValues();
    Date date = new Date();

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");

   String cdate = dateFormat.format(date);
     cv.put(regContract.dbinfo.DATE,cdate);
    cv.put(regContract.dbinfo.TWQ,twc);
    cv.put(regContract.dbinfo.FWQ,fwc);
   long id = db.insert(regContract.dbinfo.TBNAME,null,cv);

}
public  Boolean chk_rec()
{
    String current_date;

            current_date = get_date();

    DBHelper dbHelper = new DBHelper(getApplicationContext());
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from "+ regContract.dbinfo.TBNAME,null);
    try
    {
        int cdatei = cursor.getColumnIndex(regContract.dbinfo.DATE);
        String cdate;
        while(cursor.moveToNext()) {
            cdate = cursor.getString(cdatei);
            int chk = cdate.compareToIgnoreCase(current_date);
            if (chk==0) {

                 return Boolean.TRUE;
            }




        }
    }
    finally {
        cursor.close();
    }

    return Boolean.FALSE;


}

public void update(String udate,int utwc,int ufwc)
{
    DBHelper dbHelper = new DBHelper(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put(regContract.dbinfo.TWQ,utwc);
    cv.put(regContract.dbinfo.FWQ,ufwc);

    db.update(regContract.dbinfo.TBNAME,cv,regContract.dbinfo.DATE + "= ?",new String[]{udate});
}

public String get_date()
{
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
    return dateFormat.format(date);
   }
}
