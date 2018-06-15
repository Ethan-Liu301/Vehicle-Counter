package com.example.npatel.vehicalcounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npatel.vehicalcounter.data.DBHelper;
import com.example.npatel.vehicalcounter.data.regContract;
import com.example.npatel.vehicalcounter.counter_Summry.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class vehicaladapter extends ArrayAdapter<vehical_count> {
    View listItemView;
    Context context;

    public vehicaladapter(Context mcontext, ArrayList<vehical_count> vehical_counts) {
        super(mcontext, 0, vehical_counts);
        context = mcontext;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) { View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.customadapter, parent, false);
        }
        final TextView date = (TextView) listItemView.findViewById(R.id.vdate);
        TextView vtwc = (TextView) listItemView.findViewById(R.id.vtwc);
        TextView vfwc = (TextView) listItemView.findViewById(R.id.vfwc);
        ImageView delete = (ImageView) listItemView.findViewById(R.id.delete);
        ImageView share = (ImageView) listItemView.findViewById(R.id.share);

        final vehical_count currentpostion = getItem(position);
        if (currentpostion != null) {
            date.setText(currentpostion.gDate());
            //        date.setText("Date");
            vtwc.setText(currentpostion.gtwc() + "");
            vfwc.setText(currentpostion.gfwc() + "");

            if(delete!=null) {
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Are you sure Want To Reset");
                        alertDialog.setPositiveButton("Delete",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String dateid = currentpostion.gDate();
                                int twc = currentpostion.gtwc();
                                int fwc = currentpostion.gfwc();
                                delete(dateid);
                                 vehical_count number1 = new vehical_count(dateid,twc,fwc);
                                 counter_Summry.wc.remove(currentpostion);
                                  Toast.makeText(getContext(),"Data Succesfully Removed ",Toast.LENGTH_SHORT).show();
                                  notifyDataSetChanged();

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        alertDialog.show();
                        }
                });

            }
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ddate = currentpostion.gDate();
                    String dtwc = currentpostion.gtwc()+"";
                    String dfwc = currentpostion.gfwc()+"";
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra(intent.EXTRA_TEXT," Date : " + ddate + "\n Total Two Wheeler Vehicle : "+ dtwc + "\n Total Four Wheeler Vehicle: "+dfwc);
                    context.startActivity(intent);
                }
            });
        }


        return listItemView;
    }
    public void delete(String ddate)
    {
        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(regContract.dbinfo.TBNAME,regContract.dbinfo.DATE + "= ?",new String[]{ddate});



}

}

