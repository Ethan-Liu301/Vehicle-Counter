package com.example.npatel.vehicalcounter.data;

import android.provider.BaseColumns;

public final class regContract {
   public static final class dbinfo implements BaseColumns
   {
       public static final String DBNAME = "Vehical_Summary";
       public static final String TBNAME = "vs";
       public static final int DBVERSION = 2;
       public static final String DATE = "Date";
       public static final String TWQ = "Two_Wheeler";
       public static final String FWQ = "Four_Wheeler";
   }
}
