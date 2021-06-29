package com.theoretics.mobilepos.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.theoretics.mobilepos.bean.CONSTANTS;
import com.theoretics.mobilepos.bean.GLOBALS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    //public static final String SERVER_NAME = "http://192.168.100.240";
    public static final String SERVER_NAME = "http://192.168.1.80";
    public static final String DATABASE_NAME = "mobilepos.db";

    public static final String NET_MANAGER_TABLE_NAME = "netmanager";
    public static final String NET_MANAGER_COLUMN_ID = "id";
    public static final String NET_MANAGER_COL_TABLETYPE = "tableName";
    public static final String NET_MANAGER_COLUMN_LDC = "LastDateCreated";
    public static final String NET_MANAGER_COLUMN_LDM = "LastDateModified";

    public static final String MASTER_TABLE_NAME = "master";
    public static final String MASTER_COLUMN_ID = "pkid";
    public static final String MASTER_COLUMN_GRANDTOTAL = "grandTotal";
    public static final String MASTER_COLUMN_GROSSTOTAL = "grossTotal";
    public static final String MASTER_COLUMN_RECEIPTNOS = "receiptNos";
    public static final String MASTER_COLUMN_DATETIMERECORDED = "datatimeRecorded";

    public static final String CARD_TABLE_NAME = "timeindb";
    public static final String CARD_COLUMN_VEHICLE = "Vehicle";
    public static final String CARD_COLUMN_TIMEIN = "Timein";
    public static final String CARD_COLUMN_PC = "PC";
    public static final String CARD_COLUMN_PLATE = "Plate";
    public static final String CARD_COLUMN_CARDCODE = "CardCode";
    public static final String CARD_COLUMN_LANE = "Lane";

    public static final String EXIT_TABLE_NAME = "exit_trans";

    public static final String XREAD_TABLE_NAME = "colltrain";
    public static final String ZREAD_TABLE_NAME = "zread";
    public static final String XREAD_COLUMN_LOGINID = "logINID";
    public static final String ZREAD_COLUMN_LOGINID = "logINID";


    public static final String GIN_TABLE_NAME = "gin";
    public static final String GIN_COLUMN_CASHIERID = "cashierID";
    public static final String GIN_COLUMN_LOGID = "logID";
    public static final String GIN_COLUMN_CASHIERNAME = "cashierName";
    public static final String GIN_COLUMN_LOGINDATE = "loginDate";

    public static final String POS_TABLE_NAME = "posusers";
    public static final String POS_COLUMN_USERNAME = "username";
    public static final String POS_COLUMN_PASSWORD = "password";
    public static final String POS_COLUMN_CASHIERNAME = "cashierName";

    public static final String VIP_TABLE_NAME = "vipcontacts";
    public static final String VIP_COLUMN_ID = "id";
    public static final String VIP_COLUMN_CARDID = "CardID";
    public static final String VIP_COLUMN_PARKERTYPE = "ParkerType";
    public static final String VIP_COLUMN_PLATENUMBER = "PlateNumber";
    public static final String VIP_COLUMN_NAME = "Name";
    public static final String VIP_COLUMN_CARDCODE = "CardCode";
    public static final String VIP_COLUMN_MAXUSE = "MaxUse";
    public static final String VIP_COLUMN_STATUS = "Status";
    public static final String VIP_COLUMN_DATECREATED = "DateCreated";
    public static final String VIP_COLUMN_DATEMODIFIED = "DateModified";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
           "create table " + VIP_TABLE_NAME +
                " (id integer primary key, " +
                   VIP_COLUMN_CARDID + " text," +
                   VIP_COLUMN_PARKERTYPE + " text," +
                   VIP_COLUMN_PLATENUMBER + " text, " +
                   VIP_COLUMN_NAME + " text," +
                   VIP_COLUMN_CARDCODE + " text, " +
                   VIP_COLUMN_MAXUSE + " integer, " +
                   VIP_COLUMN_STATUS + " integer, " +
                   VIP_COLUMN_DATECREATED + " DATETIME, " +
                   VIP_COLUMN_DATEMODIFIED + " DATETIME)"
        );

        db.execSQL("create table " + MASTER_TABLE_NAME +
                " ("+ MASTER_COLUMN_ID + " integer primary key, " +
                MASTER_COLUMN_GRANDTOTAL + " text, " +
                MASTER_COLUMN_GROSSTOTAL + " text, " +
                MASTER_COLUMN_RECEIPTNOS + " text, " +
                MASTER_COLUMN_DATETIMERECORDED + " DATETIME)");

        db.execSQL("CREATE TABLE exit_trans " +
                "(id integer primary key, " +
                "void integer, " +
                "voidRefID text,  " +
                "ReceiptNumber text, " +
                "CashierName text, " +
                "EntranceID text, " +
                "ExitID text, " +
                "CardNumber text, " +
                "PlateNumber text, " +
                "ParkerType text, " +
                "NetOfDiscount real, " +
                "Amount real, " +
                "GrossAmount real, " +
                "discount real, " +
                "vatAdjustment real, " +
                "vat12 real, " +
                "vatsale real, " +
                "vatExemptedSales real, " +
                "tendered real, " +
                "changeDue real, " +
                "DateTimeIN DATETIME, " +
                "DateTimeOUT DATETIME, " +
                "HoursParked integer, " +
                "MinutesParked integer, " +
                "SettlementRef text, " +
                "SettlementName text, " +
                "SettlementAddr text, " +
                "SettlementTIN text, " +
                "SettlementBusStyle text)");

        db.execSQL("CREATE TABLE " + XREAD_TABLE_NAME +
                "(logINID text primary key," +
                "SentinelID text," +
                "userCode text," +
                "userName text," +
                "loginStamp DATETIME," +
                "logoutStamp DATETIME," +
                "extendedCount integer," +
                "extendedAmount real," +
                "overnightCount integer," +
                "overnightAmount real," +
                "carServed integer," +
                "totalCount integer," +
                "totalAmount real," +
                "grossCount integer," +
                "grossAmount real," +
                "vat12Count integer," +
                "vat12Amount real," +
                "vatsaleCount integer," +
                "vatsaleAmount real," +
                "vatExemptedSalesCount integer," +
                "vatExemptedSalesAmount real," +
                "exemptedVat12Count integer," +
                "exemptedVat12Amount real," +
                "pwdDiscountCount integer," +
                "pwdDiscountAmount real," +
                "seniorDiscountCount integer," +
                "seniorDiscountAmount real," +
                "localseniorDiscountCount integer," +
                "localseniorDiscountAmount real," +
                "vatAdjPWDCount integer," +
                "vatAdjPWDAmount real," +
                "vatAdjSeniorCount integer," +
                "vatAdjSeniorAmount real," +
                "vatAdjLocalSeniorCount integer," +
                "vatAdjLocalSeniorAmount real," +
                "vat12PWDCount integer," +
                "vat12PWDAmount real," +
                "vat12SeniorCount integer," +
                "vat12SeniorAmount real," +
                "vat12LocalSeniorCount integer," +
                "vat12LocalSeniorAmount real," +
                "voidsCount integer," +
                "voidsAmount real," +
                "refundCount integer," +
                "refundAmount real," +
                "regularCount integer," +
                "regularAmount real," +
                "vipCount integer," +
                "vipAmount real," +
                "graceperiodCount integer," +
                "graceperiodAmount real," +
                "lostCount integer," +
                "lostAmount real," +
                "promoCount integer," +
                "promoAmount real," +
                "localseniorCount integer," +
                "localseniorAmount real," +
                "seniorCount integer," +
                "seniorAmount real," +
                "pwdCount integer," +
                "pwdAmount real," +
                "motorcycleCount integer," +
                "motorcycleAmount real," +
                "jeepCount integer," +
                "jeepAmount real," +
                "tricycleCount integer," +
                "tricycleAmount real," +
                "deliveryCount integer," +
                "deliveryAmount real," +
                "ambulanceCount integer," +
                "ambulanceAmount real," +
                "ambulatoryCount integer," +
                "ambulatoryAmount real," +
                "bpoemployeeCount integer," +
                "bpoemployeeAmount real," +
                "employeesCount integer," +
                "employeesAmount real," +
                "tenantsCount integer," +
                "tenantsAmount real," +
                "mabregularCount integer," +
                "mabregularAmount real," +
                "seniormotorCount integer," +
                "seniormotorAmount real," +
                "inpatientCount integer," +
                "inpatientAmount real," +
                "dialysisCount integer," +
                "dialysisAmount real)");

        db.execSQL("CREATE TABLE lastdate " +
                "  (pkid integer primary key," +
                "  sentinelID text," +
                "  dt DATETIME)");

        db.execSQL("CREATE TABLE " + ZREAD_TABLE_NAME +
                "  (terminalnum text," +
                "  datetimeOut datetime ," +
                "  datetimeIn datetime," +
                "  todaysale real ," +
                "  todaysGross real," +
                "  vatablesale real ," +
                "  vat real ," +
                "  vatExemptedSales real," +
                "  beginOR integer," +
                "  endOR integer," +
                "  beginTrans text," +
                "  endTrans text," +
                "  beginningVoidNo text," +
                "  endingVoidNo text," +
                "  oldGrand double ," +
                "  newGrand double ," +
                "  oldGrossTotal real," +
                "  newGrossTotal real," +
                "  discounts real," +
                "  voids real," +
                "  manualColl real," +
                "  overrage real," +
                "  zCount integer," +
                "  tellerCode text," +
                "  logINID text primary key" +
                ")");
        
        db.execSQL("create table " + NET_MANAGER_TABLE_NAME +
                " (id integer primary key, " +
                NET_MANAGER_COL_TABLETYPE + " text, " +
                NET_MANAGER_COLUMN_LDC + " DATETIME, " +
                NET_MANAGER_COLUMN_LDM + " DATETIME)");

        db.execSQL("create table " + POS_TABLE_NAME +
                " (id integer primary key, " +
                POS_COLUMN_USERNAME + " text, " +
                POS_COLUMN_PASSWORD + " text, " +
                POS_COLUMN_CASHIERNAME + " text)");

        db.execSQL("create table " + GIN_TABLE_NAME +
                " (" + GIN_COLUMN_LOGID + " text primary key, " +
                GIN_COLUMN_CASHIERID + " text, " +
                GIN_COLUMN_CASHIERNAME + " text, " +
                GIN_COLUMN_LOGINDATE + " DATETIME)");

        db.execSQL("create table " + CARD_TABLE_NAME +
                " (" + CARD_COLUMN_CARDCODE + " text primary key, " +
                CARD_COLUMN_PLATE + " tesxt, " +
                CARD_COLUMN_PC + " text, " +
                CARD_COLUMN_LANE + " text, " +
                CARD_COLUMN_VEHICLE + " text, " +
                CARD_COLUMN_TIMEIN + " DATETIME)");

        fillPOSDB(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //db.execSQL("DROP TABLE IF EXISTS "+ VIP_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ GIN_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ MASTER_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ POS_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ CARD_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ NET_MANAGER_TABLE_NAME);
        //onCreate(db);
    }

    public void saveEXTransaction(String ReceiptNumber,String CashierName, String EntranceID,
                                  String ExitID, String CardNumber, String PlateNumber, String ParkerType,
                                  String NetOfDiscount, String Amount, String GrossAmount, String discount,
                                  String vatAdjustment, String vat12, String vatsale, String vatExemptedSales,
                                  String tendered, String changeDue, String DateTimeIN, String DateTimeOUT,
                                  String HoursParked, String MinutesParked, String SettlementRef,
                                  String SettlementName, String SettlementAddr, String SettlementTIN, String SettlementBusStyle) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ReceiptNumber", ReceiptNumber);
        contentValues.put("CashierName", CashierName);
        contentValues.put("EntranceID", EntranceID);
        contentValues.put("ExitID", ExitID);
        contentValues.put("CardNumber", CardNumber);
        contentValues.put("PlateNumber", PlateNumber);
        contentValues.put("ParkerType", ParkerType);
        contentValues.put("NetOfDiscount", NetOfDiscount);
        contentValues.put("Amount", Amount);
        contentValues.put("GrossAmount", GrossAmount);
        contentValues.put("discount", discount);
        contentValues.put("vatAdjustment", vatAdjustment);
        contentValues.put("vat12", vat12);
        contentValues.put("vatsale", vatsale);
        contentValues.put("vatExemptedSales", vatExemptedSales);
        contentValues.put("tendered", tendered);
        contentValues.put("changeDue", changeDue);
        contentValues.put("DateTimeIN", DateTimeIN);
        contentValues.put("DateTimeOUT", DateTimeOUT);
        contentValues.put("HoursParked", HoursParked);
        contentValues.put("MinutesParked", MinutesParked);
        contentValues.put("SettlementRef", SettlementRef);
        contentValues.put("SettlementName", SettlementName);
        contentValues.put("SettlementAddr", SettlementAddr);
        contentValues.put("SettlementTIN", SettlementTIN);
        contentValues.put("SettlementBusStyle", SettlementBusStyle);

        db.insert(EXIT_TABLE_NAME, null, contentValues);
    }

    public boolean createXRead () {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(VIP_COLUMN_ID, NULL);
       contentValues.put("logINID", GLOBALS.getInstance().getLoginID());
        contentValues.put("SentinelID", CONSTANTS.getInstance().getExitID());
        contentValues.put("userCode", GLOBALS.getInstance().getCashierID());
        contentValues.put("userName", GLOBALS.getInstance().getCashierName());
        contentValues.put("loginStamp", sdf.format(now));

        long res = db.insert(XREAD_TABLE_NAME, null, contentValues);
        if (res >= 0) {
            return true;
        }
        return false;
    }

    public boolean createZRead (String beginOR, String beginTrans, String beginGross, String beginGrand) {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(VIP_COLUMN_ID, NULL);
        contentValues.put("beginOR", beginOR);
        contentValues.put("beginTrans", beginTrans);
        contentValues.put("oldGrossTotal", beginGross);
        contentValues.put("oldGrand", beginGrand);
        contentValues.put("logINID", GLOBALS.getInstance().getLoginID());
        contentValues.put("terminalnum", CONSTANTS.getInstance().getExitID());
        contentValues.put("tellerCode", GLOBALS.getInstance().getCashierID());
        contentValues.put("datetimeIn", sdf.format(now));

        long res = db.insert(ZREAD_TABLE_NAME, null, contentValues);
        if (res >= 0) {
            return true;
        }
        return false;
    }

    public void updateXRead (String key, String value) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.update(XREAD_TABLE_NAME, contentValues, "logINID = ? ", new String[] { GLOBALS.getInstance().getLoginID() }  );

    }


    public void updateZRead (String key, String value) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.update(ZREAD_TABLE_NAME, contentValues, "logINID = ? ", new String[] { GLOBALS.getInstance().getLoginID() }  );

    }

    public String getOneZRead(String sql, String column) {
        String data = "0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( sql , null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(column));
            res.moveToNext();
        }
        if (null == data) {
            data = "0";
        }
        return data;
    }

    public String getImptString(String fieldName) {
        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT " + fieldName + " FROM "+ XREAD_TABLE_NAME + " WHERE " + XREAD_COLUMN_LOGINID + " = '" + GLOBALS.getInstance().getLoginID() + "'";
        Cursor res =  db.rawQuery( SQL, null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(fieldName));
            res.moveToNext();
        }
        return data;
    }

    public int getImptCount(String fieldName) {
        int data = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT " + fieldName + " FROM "+ XREAD_TABLE_NAME + " WHERE " + XREAD_COLUMN_LOGINID + " = '" + GLOBALS.getInstance().getLoginID() + "'";
        Cursor res =  db.rawQuery( SQL, null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getInt(res.getColumnIndex(fieldName));
            res.moveToNext();
        }
        return data;
    }

    public void setImptCount(String fieldName, int Count) {
        SQLiteDatabase db = this.getReadableDatabase();
        Count++;
        ContentValues contentValues = new ContentValues();
        contentValues.put(fieldName, Count);
        db.update(XREAD_TABLE_NAME, contentValues, XREAD_COLUMN_LOGINID + " = ? ", new String[] { GLOBALS.getInstance().getLoginID() }  );

    }

    public double getImptAmount(String fieldName) {
        double data = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select " + fieldName + " from "+ XREAD_TABLE_NAME + " WHERE " + XREAD_COLUMN_LOGINID + " = '" + GLOBALS.getInstance().getLoginID() + "'", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getDouble(res.getColumnIndex(fieldName));
            res.moveToNext();
        }
        return data;
    }

    public void setImptAmount(String fieldName, double Amount) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(fieldName, Amount);
        db.update(XREAD_TABLE_NAME, contentValues, XREAD_COLUMN_LOGINID + " = ? ", new String[] { GLOBALS.getInstance().getLoginID() }  );

    }

    public double getImptGrand(String fieldName) {
        double data = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select " + fieldName + " from "+ MASTER_TABLE_NAME + " WHERE " + MASTER_COLUMN_ID + " = 1", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getInt(res.getColumnIndex(fieldName));
            res.moveToNext();
        }
        return data;
    }

    public void setGrandTotal(double Amount) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MASTER_COLUMN_GRANDTOTAL, Amount);
        db.update(MASTER_TABLE_NAME, contentValues, MASTER_COLUMN_ID + " = ? ", new String[] { Integer.toString(1) }  );

    }

    public void setGrossTotal(double Amount) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MASTER_COLUMN_GROSSTOTAL, Amount);
        db.update(MASTER_TABLE_NAME, contentValues, MASTER_COLUMN_ID + " = ? ", new String[] { Integer.toString(1) }  );

    }

    public boolean insertContact (String cardID, String parkerType, String plateNumber, String name, String cardNumber, String maxUse, String status, String ldc, String ldm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(VIP_COLUMN_ID, NULL);
        contentValues.put(VIP_COLUMN_CARDID, cardID);
        contentValues.put(VIP_COLUMN_PARKERTYPE, parkerType);
        contentValues.put(VIP_COLUMN_PLATENUMBER, plateNumber);
        contentValues.put(VIP_COLUMN_NAME, name);
        contentValues.put(VIP_COLUMN_CARDCODE, cardNumber);
        contentValues.put(VIP_COLUMN_MAXUSE, maxUse);
        contentValues.put(VIP_COLUMN_STATUS, status);
        contentValues.put(VIP_COLUMN_DATECREATED, ldc);
        contentValues.put(VIP_COLUMN_DATEMODIFIED, ldm);
        long res = db.insert(VIP_TABLE_NAME, null, contentValues);
        if (res >= 0) {
            return true;
        }
        return false;
    }

    public boolean updateContact (String cardID, String parkerType, String plateNumber, String name, String cardNumber, String maxUse, String status, String ldc, String ldm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(VIP_COLUMN_ID, NULL);
        contentValues.put(VIP_COLUMN_CARDID, cardID);
        contentValues.put(VIP_COLUMN_PARKERTYPE, parkerType);
        contentValues.put(VIP_COLUMN_PLATENUMBER, plateNumber);
        contentValues.put(VIP_COLUMN_NAME, name);
        contentValues.put(VIP_COLUMN_CARDCODE, cardNumber);
        contentValues.put(VIP_COLUMN_MAXUSE, maxUse);
        contentValues.put(VIP_COLUMN_STATUS, status);
        contentValues.put(VIP_COLUMN_DATECREATED, ldc);
        contentValues.put(VIP_COLUMN_DATEMODIFIED, ldm);
        long res =  db.update(VIP_TABLE_NAME, contentValues, VIP_COLUMN_CARDCODE + " = ? " ,new String[]{String.valueOf(cardNumber)});
        //long res = db.update(VIP_TABLE_NAME, "", contentValues);
        if (res >= 0) {
            return true;
        }
        return false;
    }

    public boolean insertParkerCard (String cardCode, String vehicle, String plateNumber, String lane, String pc, String timein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(VIP_COLUMN_ID, NULL);
        contentValues.put(CARD_COLUMN_CARDCODE, cardCode);
        contentValues.put(CARD_COLUMN_PLATE, plateNumber);
        contentValues.put(CARD_COLUMN_VEHICLE, vehicle);
        contentValues.put(CARD_COLUMN_LANE, lane);
        contentValues.put(CARD_COLUMN_PC, pc);
        contentValues.put(CARD_COLUMN_TIMEIN, timein);
        long res = db.insert(CARD_TABLE_NAME, null, contentValues);
        if (res >= 0) {
            return true;
        }
        return false;
    }

    public boolean insertLogin (String logID, String parkerType, String plateNumber, String name, String cardNumber, String maxUse, String status, String ldc, String ldm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(VIP_COLUMN_ID, NULL);
        contentValues.put(VIP_COLUMN_CARDID, logID);
        contentValues.put(VIP_COLUMN_PARKERTYPE, parkerType);
        contentValues.put(VIP_COLUMN_PLATENUMBER, plateNumber);
        contentValues.put(VIP_COLUMN_NAME, name);
        contentValues.put(VIP_COLUMN_CARDCODE, cardNumber);
        contentValues.put(VIP_COLUMN_MAXUSE, maxUse);
        contentValues.put(VIP_COLUMN_STATUS, status);
        contentValues.put(VIP_COLUMN_DATECREATED, ldc);
        contentValues.put(VIP_COLUMN_DATEMODIFIED, ldm);
        long res = db.insert(VIP_TABLE_NAME, null, contentValues);
        if (res >= 0) {
            return true;
        }
        return false;
    }

    public boolean initiateMaster() {
        GLOBALS.getInstance().setReceiptNum(0);
        GLOBALS.getInstance().setGrandTotal(0);
        GLOBALS.getInstance().setGrossTotal(0);

        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MASTER_COLUMN_ID, 1);
        contentValues.put(MASTER_COLUMN_RECEIPTNOS, "1");
        contentValues.put(MASTER_COLUMN_GRANDTOTAL, "0");
        contentValues.put(MASTER_COLUMN_GROSSTOTAL, "0");
        contentValues.put(MASTER_COLUMN_DATETIMERECORDED, sdf.format(now));
        db.insert(MASTER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateMaster(String column, String value) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        db.insert(MASTER_TABLE_NAME, null, contentValues);
        return true;
    }

    public int getRNosData(String fieldName) {
        int data = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+ MASTER_TABLE_NAME , null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getInt(res.getColumnIndex(fieldName));
            res.moveToNext();
        }
        return data;
    }

    public boolean updateRNosData (String fieldName, int value) {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(fieldName, value);
        contentValues.put(MASTER_COLUMN_DATETIMERECORDED, sdf.format(now));
        db.update(MASTER_TABLE_NAME, contentValues, MASTER_COLUMN_ID + " = ? ", new String[] { Integer.toString(1) }  );
        return true;
    }


    public boolean getMaster() {
        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+ MASTER_TABLE_NAME , null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(MASTER_COLUMN_RECEIPTNOS));
            GLOBALS.getInstance().setReceiptNum(res.getInt(res.getColumnIndex(MASTER_COLUMN_RECEIPTNOS)));
            GLOBALS.getInstance().setGrandTotal(res.getInt(res.getColumnIndex(MASTER_COLUMN_GRANDTOTAL)));
            GLOBALS.getInstance().setGrossTotal(res.getInt(res.getColumnIndex(MASTER_COLUMN_GROSSTOTAL)));

            res.moveToNext();
            if(data.compareTo("") == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }

    public boolean isDeviceLoggedIN() {
        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+ GIN_TABLE_NAME , null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(GIN_COLUMN_CASHIERNAME));
            GLOBALS.getInstance().setLoginID(res.getString(res.getColumnIndex(GIN_COLUMN_LOGID)));
            GLOBALS.getInstance().setCashierID(res.getString(res.getColumnIndex(GIN_COLUMN_CASHIERID)));
            GLOBALS.getInstance().setCashierName(res.getString(res.getColumnIndex(GIN_COLUMN_CASHIERNAME)));
            GLOBALS.getInstance().setLoginDate(res.getString(res.getColumnIndex(GIN_COLUMN_LOGINDATE)));

            res.moveToNext();
            if(data.compareTo("") == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }


    public boolean validateLogout(String username, String password) {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ POS_TABLE_NAME +" where "+ POS_COLUMN_PASSWORD +"='"+ md5(password) +"' AND "
                + POS_COLUMN_USERNAME + "='"+username+"'", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(POS_COLUMN_USERNAME));

            if(data.compareTo("") == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }


    public boolean validateLogin(String username, String password) {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ POS_TABLE_NAME +" where "+ POS_COLUMN_PASSWORD +"='"+ md5(password) +"' AND "
                + POS_COLUMN_USERNAME + "='"+username+"'", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(POS_COLUMN_USERNAME));
            String nowStr = now.getTime() + "";
            String loginID = nowStr.substring(0, nowStr.length()/2)+data+ nowStr.substring(nowStr.length()/2, nowStr.length());
            String cashierID = res.getString(res.getColumnIndex(POS_COLUMN_USERNAME));
            String cashierName = res.getString(res.getColumnIndex(POS_COLUMN_CASHIERNAME));
            String loginDate = sdf.format(now);
            GLOBALS.getInstance().setLoginID(loginID);
            GLOBALS.getInstance().setCashierID(cashierID);
            GLOBALS.getInstance().setCashierName(cashierName);
            GLOBALS.getInstance().setLoginDate(loginDate);
            res.moveToNext();
            if(data.compareTo("") == 0) {
                return false;
            } else {
                saveLogin(loginID,cashierID,cashierName,loginDate);
                createXRead();
                String beginOR = getOneTrans("SELECT receiptNos FROM master", "receiptNos");
                String beginTrans = getOneTrans("SELECT MAX(id) AS beginTrans FROM exit_trans", "beginTrans");
                String beginGross = getOneTrans("SELECT grossTotal FROM master", "grossTotal");
                String beginGrand = getOneTrans("SELECT grandTotal FROM master", "grandTotal");
                createZRead( CONSTANTS.getInstance().getExitID() + formatNos(beginOR), beginTrans, beginGross, beginGrand);
                return true;
            }
        }
        return false;

    }

    public String getOneTrans(String sql, String column) {
        String data = "0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( sql , null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(column));
            res.moveToNext();
        }
        if (null == data) {
            data = "0";
        }
        return data;
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getLoginUserName(String loginID) {
        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ GIN_TABLE_NAME +" where "+ GIN_COLUMN_LOGID +"='"+ loginID +"'", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            data = res.getString(res.getColumnIndex(GIN_COLUMN_CASHIERNAME));
            res.moveToNext();
        }
        return data;

    }

    public void logoutForced() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GIN_TABLE_NAME);
    }

    public void saveLogin(String loginID, String cashierid, String cashiername, String logindate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GIN_COLUMN_CASHIERID, cashierid);
        contentValues.put(GIN_COLUMN_CASHIERNAME, cashiername);
        contentValues.put(GIN_COLUMN_LOGID, loginID);
        contentValues.put(GIN_COLUMN_LOGINDATE, logindate);
        db.insert(GIN_TABLE_NAME, null, contentValues);
    }

    public Cursor getCardData(String cardNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ VIP_TABLE_NAME +" where "+ VIP_COLUMN_CARDCODE +"='"+cardNumber+"'", null );
        return res;
    }

    public Cursor getLastDateData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ NET_MANAGER_TABLE_NAME, null );
        return res;
    }

    public Cursor getLastDateData(String tableType) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ NET_MANAGER_TABLE_NAME + " WHERE " + NET_MANAGER_COL_TABLETYPE + " = '" + tableType + "'", null );
        return res;
    }

    public Cursor updateLastDateData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ NET_MANAGER_TABLE_NAME, null );
        return res;
    }

    public Cursor getExitDataForServer(String ldm) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ EXIT_TABLE_NAME + " WHERE DateTimeOUT > '" + ldm + "'", null );
        return res;
    }

    public Cursor getXReadDataForServer(String ldm) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ XREAD_TABLE_NAME + " WHERE logoutStamp > '" + ldm + "'", null );
        return res;
    }

    public boolean insertLDCandLDM (String tableType, String LDC, String LDM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NET_MANAGER_COL_TABLETYPE, tableType);
        contentValues.put(NET_MANAGER_COLUMN_LDC, LDC);
        contentValues.put(NET_MANAGER_COLUMN_LDM, LDM);
        db.insert(NET_MANAGER_TABLE_NAME, null, contentValues);
        return true;
    }


    public boolean insertLDC (String LDC) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NET_MANAGER_COLUMN_LDC, LDC);
        db.insert(NET_MANAGER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateLDC (String tableType, String LDC) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NET_MANAGER_COLUMN_LDC, LDC);
        db.update(NET_MANAGER_TABLE_NAME, contentValues, NET_MANAGER_COL_TABLETYPE + " = ? ", new String[] { tableType }  );
        return true;
    }

    public boolean insertLDM (String tableType, String LDM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NET_MANAGER_COL_TABLETYPE, tableType);
        contentValues.put(NET_MANAGER_COLUMN_LDM, LDM);
        db.insert(NET_MANAGER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateLDM (String tableType, String LDM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NET_MANAGER_COLUMN_LDM, LDM);
        db.update(NET_MANAGER_TABLE_NAME, contentValues, NET_MANAGER_COL_TABLETYPE + " = ? ", new String[] { tableType }  );
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, VIP_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update(VIP_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VIP_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllVIPContacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ VIP_TABLE_NAME , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(VIP_COLUMN_NAME)) +
                    " Plate: " + res.getString(res.getColumnIndex(VIP_COLUMN_PLATENUMBER)) +
                    " Card: " + res.getString(res.getColumnIndex(VIP_COLUMN_CARDCODE)) +
                    " P Type: " + res.getString(res.getColumnIndex(VIP_COLUMN_PARKERTYPE)));
            res.moveToNext();
        }
        return array_list;
    }

    public String formatNos(String newReceipt) {
        int stoploop = 12 - newReceipt.length();
        int i = 0;
        do {
            newReceipt = "0" + newReceipt;
            i++;
        } while (i != stoploop);

        return newReceipt;
    }

    public void fillPOSDB(SQLiteDatabase db) {
        System.out.println("ANGELO : [Checking new POS Users" + new Date().toString() + "]" );
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Roseveeh', '" + md5("Roseveeh") + "', 'Roseveeh')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Joan', '" + md5("Joan") + "', 'Joan')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'claudine', '" + md5("claudine") + "', 'Claudine')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'hazel', '" + md5("hazel") + "', 'Hazel')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Xyree', '" + md5("Xyree") + "', 'Xyree')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Carla', '" + md5("Carla") + "', 'Carla')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Cielo', '" + md5("Cielo") + "', 'Cielo')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Claudine2', '" + md5("Claudine2") + "', 'Claudine2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Carla2', '" + md5("Carla2") + "', 'Carla2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'hazel2', '" + md5("hazel2") + "', 'Hazel2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Joan2', '" + md5("Joan2") + "', 'Joan2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Cielo2', '" + md5("Cielo2") + "', 'Cielo2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Roseveeh2', '" + md5("Roseveeh2") + "', 'Roseveeh2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Xyree2', '" + md5("Xyree2") + "', 'Xyree2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'roseann2', '" + md5("roseann2") + "', 'Roseann2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'm', '" + md5("m") + "', 'm')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'manuel', '" + md5("manuel") + "', 'manuel')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'mary', '" + md5("mary") + "', 'mary')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'mary2', '" + md5("mary2") + "', 'mary2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Cindy', '" + md5("Cindy") + "', 'Cindy')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Cindy2', '" + md5("Cindy2") + "', 'Cindy2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'roseann', '" + md5("roseann") + "', 'roseann')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Joan2', '" + md5("Joan2") + "', 'Joan2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Hazel2', '" + md5("Hazel2") + "', 'Hazel2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Carla2', '" + md5("Carla2") + "', 'Carla2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'Claudine2', '" + md5("Claudine2") + "', 'Claudine2')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'teller1', '" + md5("teller1") + "', 'teller1')");
        db.execSQL("INSERT INTO " + POS_TABLE_NAME + " VALUES(null, 'angelo', '" + md5("angelo") + "', 'angelo')");
        System.out.println("ANGELO : [Uploaded new POS Users" + new Date().toString() + "]" );
    }

    public void initCardDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        //TEST DATA
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '457', 'VISITING CONSULTANT', 'ZRU966', 'ONG KIAN KOC, BRYAN BERNARD UY, M.D.', 'F8F21C1E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'ZKU701', 'ADDATU, DOMINGO', 'E8831D1E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NE4713', 'VALLES, TOMAS', 'FCE0151E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        //ACTUAL DATA
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '457', 'VISITING CONSULTANT', 'ZRU966', 'ONG KIAN KOC, BRYAN BERNARD UY, M.D.', '705B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'WTI786', 'TANSENGCO, RUSTICA..M.D.', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '304', 'DOCTOR', 'ACA6820', 'TAN, KING KING', '304A9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '209', 'DOCTOR', 'WIS933', 'NGO, PRECITA S..M.D.', 'A0439082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'YI8777', 'GARCIA JR, JOSE', '80E69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NE4713', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'VIP/CCD', 'LDW153', 'MORANA, ALVIN', '202A9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '147', 'DOCTOR', 'AAP2052', 'LEE, JEFFREY ', 'A0919982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '122', 'DOCTOR', '26153 ', 'GO, RORY..M.D.', '004D9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '229', 'DOCTOR', 'AQA6307', 'GAN-PE, PAMELA', '80CF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '229', 'DOCTOR', 'TIH808', 'GAN-PE, PAMELA', '80CF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '229', 'DOCTOR', 'TOX909', 'GAN-PE, PAMELA', '80CF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '38', 'VIP', 'AAC8014', 'JIONGCO, GRACITA', '807C9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1997', 'ACCESS CARD POS2', '111111', 'TEMPORARY VIP ACCESS CARD POS2', '107B9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '537', 'DOCTOR', 'UNQ814', 'ACOSTA, LUTHGARDO', 'B0F69382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'ZKU701', 'ADDATU, DOMINGO', 'E09A9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'NQU592', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'TJH788', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'TS2228', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'UCO956', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'UTZ875', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'WAX402', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'WRK300', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'ZNV864', 'ALBANO, GERMAN JOSE', 'A0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'DOCTOR', 'ZNU412', 'ANG, LYDIA', 'A0DD9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'DOCTOR', 'XFJ516', 'ANG, LYDIA', 'A0DD9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'DOCTOR', 'PWI723', 'ANG, LYDIA', 'A0DD9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'XNB258', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'AIR740', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'WPE683', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'UYQ902', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'ZHF693', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'XNB258', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'ZBB711', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'TKI554', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'UIK257', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'PIG610', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'PQD816', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '560', 'DOCTOR', 'XSH322', 'ARQUILLA, MARLEEN', 'D0909782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '560', 'DOCTOR', 'POA189', 'ARQUILLA, MARLEEN', 'D0909782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'ZMV861', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'TII280', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'ZMH195', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'URH880', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'XEN927', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'UPZ653', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'E0Y182', 'ASTEJADA, MINA', 'D0F19882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'ZDK770', 'ALEGRE, EMELDA', '30629882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'NLI988', 'ALEGRE, EMELDA', '30629882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'NIY126', 'ALEGRE, EMELDA', '30629882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'VI7927', 'ALEGRE, EMELDA', '30629882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'VI7927', 'ALEGRE, NATALIO', '408B9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'NIY126', 'ALEGRE, NATALIO', '408B9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'NLI988', 'ALEGRE, NATALIO', '408B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'DOCTOR', 'POO858', 'ANG, DANTE', '10BB9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'DOCTOR', 'PQG880', 'ANG, DANTE', '10BB9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'DOCTOR', 'WJW378', 'ANG, DANTE', '10BB9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'DOCTOR', 'ZJH296', 'ANG, DANTE', '10BB9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'PNO353', 'ANG, ANNE TAN', 'C02A9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'NBE5008', 'ANG, ANNE TAN', 'C02A9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'TX9561', 'ANG, ANNE TAN', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'RPA266', 'ANG, ANNE TAN', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'DOCTOR', 'NOY120', 'ANG, EVELYN', 'D0B69B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'DOCTOR', 'ZSH975', 'ANG, EVELYN', 'D0B69B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'DOCTOR', 'TB1375', 'ANG, EVELYN', 'D0B69B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'DOCTOR', 'NOI487', 'ANG, CYNTHIA', '40319382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'DOCTOR', 'WAR705', 'ANG, CYNTHIA', '40319382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'DOCTOR', 'PNG325', 'ANG, IRMINIA', '50069A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'DOCTOR', 'UKO678', 'ANG, IRMINIA', '50069A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'DOCTOR', 'XAV200', 'ANG, IRMINIA', '50069A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'DOCTOR', 'DN6325', 'ANG, IRMINIA', '50069A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '374', 'DOCTOR', 'TSI725', 'ANGTUACO, JAMES', '40B89082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '374', 'DOCTOR', 'XRT488', 'ANGTUACO, JAMES', '40B89082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'DOCTOR', 'ZSB929', 'BAGAY, MELISSA', '90ED9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'ZLT783', 'BANGAYAN, TEOFILO', '602C9582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'WHF205', 'BANGAYAN, TEOFILO', '602C9582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'PIC132', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'PIL278', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'PPI505', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', 'PTI990', 'BELEN, THELMA', '80209282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', 'UQJ767', 'BELEN, THELMA', '80209282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', 'BU8492', 'BELEN, THELMA', '80209282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', 'WMO126', 'BELEN, THELMA', '80209282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'DOCTOR', 'NQK183', 'BOLANOS, MARTHA', '30729082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'DOCTOR', 'XGL190', 'BOLANOS, MARTHA', '30729082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '696', 'DOCTOR', 'ZKT978', 'BOLONG, DAVID', 'C279BE2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '696', 'DOCTOR', 'TTO686', 'BOLONG, DAVID', 'C279BE2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '696', 'DOCTOR', 'WMD523', 'BOLONG, DAVID', 'C279BE2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '696', 'DOCTOR', 'WOY838', 'BOLONG, DAVID', 'C279BE2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '26', 'DOCTOR', 'XMF798', 'BRIOSO, EDNA FLORENCE', '50A79282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'DOCTOR', 'BPC723', 'CABOTAJE, BIENVENIDO', 'E0B48F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'DOCTOR', 'TLG548', 'CABOTAJE, BIENVENIDO', 'E0B48F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'DOCTOR', 'XAP540', 'CABOTAJE, BIENVENIDO', 'E0B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '397', 'DOCTOR', 'NLI235', 'CABERO, MARIA IMOGENE LALAINE', '10E09082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '595', 'DOCTOR', 'BV3919', 'CALALANG, EDITHA', '70BA9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '595', 'DOCTOR', 'XDH844', 'CALALANG, EDITHA', '70BA9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '595', 'DOCTOR', 'XAW327', 'CALALANG, EDITHA', '70BA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'DOCTOR', 'NIC602', 'CALIMON, WILFRIDO', 'C05D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'DOCTOR', 'WOC647', 'CALIMON, WILFRIDO', 'C05D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '30', 'DOCTOR', 'CP5878', 'CHAN, CALIXTRO JR.', 'B0089582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '30', 'DOCTOR', 'ZAW737', 'CHAN, CALIXTRO JR.', 'B0089582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'WSF119', 'CASTILLO, TERESITA', 'D0BA9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'XMZ290', 'CASTILLO, TERESITA', 'D0BA9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'ZCK618', 'CASTILLO, TERESITA', 'D0BA9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'UAX733', 'CASTILLO, TERESITA', 'D0BA9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'TBM771', 'CASTILLO, TERESITA', 'D0BA9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'TIW935', 'CASTILLO, TERESITA', 'D0BA9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '33', 'DOCTOR', 'TY9364', 'CASTRO, EUGENE', '60349182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '33', 'DOCTOR', 'XPU226', 'CASTRO, EUGENE', '60349182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '33', 'DOCTOR', 'ZFF581', 'CASTRO, EUGENE', '60349182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'DOCTOR', 'XPU226', 'CASTRO, JENNIFER', '508F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'DOCTOR', 'WAJ810', 'CAWAI, CATHERINA', '10019282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'DOCTOR', 'ZEW871', 'CAWAI, CATHERINA', '10019282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '377', 'DOCTOR', 'XHW120', 'CHAM, WILLIAM CHAN', '105D9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '377', 'DOCTOR', 'YC7773', 'CHAM, WILLIAM CHAN', '105D9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'DOCTOR', 'WDU158', 'CHAN, ALFONSO', 'C07A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'DOCTOR', 'WKS800', 'CHAN, ALFONSO', 'C07A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'DOCTOR', 'XHH238', 'CHAN, ALFONSO', 'C07A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'DOCTOR', 'ZFB501', 'CHAN, ALFONSO', 'C07A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '38', 'DOCTOR', 'PQN792', 'CHAN, AMALIA', 'B04D8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'XMG954', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'NWI418', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'TQI731', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'DN4475', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'XFD603', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'WKO515', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '41', 'DOCTOR', 'WTO877', 'CHAN, REMEDIOS DEE', 'E08D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '41', 'DOCTOR', 'AEA6402', 'CHAN, REMEDIOS DEE', 'E08D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'DOCTOR', 'THV328', 'CHAN-CUA, SIOKSOAN', 'B03D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'DOCTOR', 'WAS863', 'CHAN-CUA, SIOKSOAN', 'B03D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'DOCTOR', 'XEJ146', 'CHAN-CUA, SIOKSOAN', 'B03D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'DOCTOR', 'XMC809', 'CHAN-CUA, SIOKSOAN', 'B03D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'DOCTOR', 'XSS212', 'CHAN-CUA, SIOKSOAN', 'B03D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'DOCTOR', 'POC337', 'CHAN-CUA, SIOKSOAN', 'B03D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'PDI891', 'CHANG, ROBERT', '20989982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '43', 'DOCTOR', 'TOM302', 'CHAN-LAO, JULIET', '50029A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '637', 'DOCTOR', 'NGJ666', 'CHAN-LIM, LOLITA', 'E2104F2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '637', 'DOCTOR', 'PMA747', 'CHAN-LIM, LOLITA', 'E2104F2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '637', 'DOCTOR', 'TKV715', 'CHAN-LIM, LOLITA', 'E2104F2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'TX6197', 'CHANTE, CHARLES', 'A0509282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'ZDC717', 'CHANTE, CHARLES', 'A0509282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'ZKW149', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'POT519', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'NIU283', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'WOJ896', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'WKA408', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'XCY525', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'ZPK701', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '49', 'DOCTOR', 'WSW322', 'CHAN, ANABELLE,CHO', '20779282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '49', 'DOCTOR', 'CNC393', 'CHAN, ANABELLE,CHO', '20779282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '49', 'DOCTOR', 'WUI228', 'CHAN, ANABELLE,CHO', '20779282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '50', 'DOCTOR', 'TIA371', 'CHU, ALAN TAN', '601E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'ZMC526', 'CHUA CARL JAMES', '50F59482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'ZAM717', 'CHUA CARL JAMES', '50F59482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'WLH202', 'CHUA CARL JAMES', '50F59482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'PQE890', 'CHUA CARL JAMES', '50F59482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'DOCTOR', 'WPD308', 'CHUA, ARNEL', 'E0029082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'DOCTOR', 'ZSJ988', 'CHUA, ARNEL', 'E0029082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'DOCTOR', 'AAO3786', 'CHUA, ARNEL', 'E0029082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'WCV189', 'CHUA, DAISY ', 'E0919382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'NPQ362', 'CHUA, DAISY ', 'E0919382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'UQA988', 'CHUA, DAISY ', 'E0919382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'ZSX868', 'CHUA, DAISY ', 'E0919382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'ZCW835', 'CHUA, DAISY ', 'E0919382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'WRK213', 'CHUA, DAISY ', 'E0919382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '641', 'DOCTOR', 'EDC800', 'CHUA, EDEN', '910F1F2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '57', 'DOCTOR', 'MSJ801', 'CHUA, EDEN', '910F1F2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '641', 'DOCTOR', 'UIS483', 'CHUA, EDEN', '910F1F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '641', 'DOCTOR', 'JKP806', 'CHUA, EDEN', '910F1F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'DOCTOR', 'ZRF658', 'CHUA, ELEN', 'F00C9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1995', 'ACCESS CARD POS3', '111111', 'TEMPORARY VIP ACCESS CARD POS3', '20899482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '541', 'ACCESS CARD POS1', '111111', 'TEMPORARY VIP ACCESS CARD POS1', '002D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'UDY779', 'CHUA, JOSEFINA', '30AF9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'TCA656', 'CHUA, JOSEFINA', '30AF9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '62', 'DOCTOR', 'WMT657', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'TZX533', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'TBK792', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'PSM163', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '63', 'DOCTOR', 'XCY108', 'CHUA, MARY', '30418F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '63', 'DOCTOR', 'ZNZ670', 'CHUA, MARY', '30418F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'DOCTOR', 'NNO103', 'CHUA, PERLEN TE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'DOCTOR', 'WEJ555', 'CHUA, PERLEN TE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'DOCTOR', 'XLD378', 'CHUA, PERLEN TE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'TNQ912', 'CHUA, RACHELLE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'UOE237', 'CHUA, RACHELLE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'WJP765', 'CHUA, RACHELLE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'XGP978', 'CHUA, RACHELLE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'ZNF859', 'CHUA, RACHELLE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'DOCTOR', 'NFQ599', 'CHUA, REGINA', '00309482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'DOCTOR', 'TFO622', 'CHUA, REGINA', '00309482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'DOCTOR', 'XAW867', 'CHUA, REGINA', '00309482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'DOCTOR', 'XJJ883', 'CHUA, REGINA', '00309482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '591', 'DOCTOR', 'ZNT814', 'CO, CHUA-CHIONG VALENTINA', '709C9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '591', 'DOCTOR', 'NGO939', 'CO, CHUA-CHIONG VALENTINA', '709C9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '591', 'DOCTOR', 'ZKU405', 'CO, CHUA-CHIONG VALENTINA', '709C9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '591', 'DOCTOR', 'ZER722', 'CO, CHUA-CHIONG VALENTINA', '709C9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '382', 'DOCTOR', 'XHS553', 'CHONG, SAU KAM CHAN', 'C07A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '382', 'DOCTOR', 'ZSC237', 'CHONG, SAU KAM CHAN', 'C07A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '68', 'DOCTOR', 'ZNT814', 'CO, GEORGE JR.', 'B0BD8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'CMF474', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'PHQ216', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'WPV697', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'XME932', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'ZPX253', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'RJ2013', 'CO, CRISTINA', 'B0E89482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'NOW561', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'TIV195', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'TLZ870', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'WEJ533', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'XHH165', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'ZBY280', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'DOCTOR', 'ZKS967', 'CO, MARILYN ORTEGA', '00879182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '74', 'DOCTOR', 'ZMT188', 'CO, MARIJANE', '90909982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'DOCTOR', 'ZPP565', 'CO, YEE LAM', '700C9D82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'DOCTOR', 'XBM278', 'CO, YEE LAM', '700C9D82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'DOCTOR', 'ZHG340', 'CO, YEE LAM', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'DOCTOR', 'NDQ308', 'COOPER-TAN, ANITA', '30A59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'DOCTOR', 'ZLN551', 'CO-YAP, BETTY', '803D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '81', 'DOCTOR', 'NOM456', 'CU, ERIC', '40D69882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '649', 'DOCTOR', 'CJF941', 'CUA, LOLITA', 'C30F722D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '83', 'DOCTOR', 'UPF838', 'CUA, LOLITA', 'C30F722D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '649', 'DOCTOR', 'UCQ992', 'CUA, LOLITA', 'C30F722D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'DOCTOR', 'UOV759', 'CUASO, CHARLES', 'E0219582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '85', 'DOCTOR', 'NQT187', 'CUKINGNAN, ALEXANDER', 'C0C38F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '85', 'DOCTOR', 'TOA601', 'CUKINGNAN, ALEXANDER', 'C0C38F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '85', 'DOCTOR', 'UQV667', 'CUKINGNAN, ALEXANDER', 'C0C38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '85', 'DOCTOR', 'XKQ867', 'CUKINGNAN, ALEXANDER', 'C0C38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'WBZ678', 'CUNANAN, SAMMY', '60329282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'XTJ523', 'CUNANAN, SAMMY', '60329282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'POQ352', 'CUNANAN, SAMMY', '60329282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'PBQ418', 'CUNANAN, SAMMY', '60329282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'XHC528', 'CUNANAN, SAMMY', '60329282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'DOCTOR', 'WPO145', 'DE DIOS, ARIEL VERGEL', '700B9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'DOCTOR', 'WLU103', 'DE DIOS, ARIEL VERGEL', '700B9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'DOCTOR', 'ZCY552', 'DE DIOS, ARIEL VERGEL', '700B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'DOCTOR', 'ZCW557', 'DE DIOS, ARIEL VERGEL', '700B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'DOCTOR', 'NIC886', 'DE DIOS, ARIEL VERGEL', '700B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'DOCTOR', 'UWO439', 'DE VEYRA, ERNESTO JR.', 'C0A58F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'DOCTOR', 'XCU713', 'DE VEYRA, ERNESTO JR.', 'C0A58F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'DOCTOR', 'ZBS115', 'DE VEYRA, ERNESTO JR.', 'C0A58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'DOCTOR', 'ZKC198', 'DEL MUNDO, SANDRA', '60F69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '91', 'DOCTOR', 'ZBV253', 'DELA CRUZ, ALVIN', 'E0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '91', 'DOCTOR', 'ZRU239', 'DELA CRUZ, ALVIN', 'E0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '93', 'DOCTOR', 'ZFR308', 'DICO-GO,  JOYCE', '60C28F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '524', 'DOCTOR', 'TIM697', 'LU-DIEGO, HELENA', '80B99282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '524', 'DOCTOR', 'XCC103', 'LU-DIEGO, HELENA', '80B99282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'DOCTOR', 'ZJV391', 'DOMALAON, ROMMEL', 'D0F99182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'DOCTOR', 'PRJ983', 'DY RAGOS,  RAMON', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'DOCTOR', 'ZAV515', 'DY RAGOS,  RAMON', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'DOCTOR', 'NOK659', 'DY RAGOS,  RAMON', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'ZHG340', 'DY,  BUNYOK', 'B01A9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'XBM278', 'DY,  BUNYOK', 'B01A9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'WIP568', 'DY,  BUNYOK', 'B01A9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'XLP206', 'DY, JOHNSON', '20A99082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'WEY752', 'DY, JOHNSON', '20A99082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'XNL278', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'XPU699', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'ZKJ133', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'ZEZ263', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'PHU815', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'WYW333', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'RFC714', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'BEC285', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'BDZ261', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'PYO107', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'BDX931', 'DY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '101', 'DOCTOR', 'PIZ628', 'DY, JUN', '00869182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'DOCTOR', 'UZI462', 'DY, LIGAYA', '40D79382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'DOCTOR', 'XRK488', 'DY, SOAT TONG', 'E0779182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'DOCTOR', 'ZDY852', 'DY, SOAT TONG', 'E0779182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '104', 'DOCTOR', 'UQQ253', 'DY, TIMOTHY', '80039382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '104', 'DOCTOR', 'TXI562', 'DY, TIMOTHY', '80039382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '104', 'DOCTOR', 'NPI137', 'DY, TIMOTHY', '80039382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '105', 'DOCTOR', 'XEG987', 'DY-CO, ARLENE', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '105', 'DOCTOR', 'XSJ592', 'DY-CO, ARLENE', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'WSO320', 'EDNAVE, KERIMA ANN', '70A39382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'Z55606', 'EDNAVE, KERIMA ANN', '70A39382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'ZJJ606', 'EDNAVE, KERIMA ANN', '70A39382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'ZTB389', 'EDNAVE, KERIMA ANN', '70A39382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '107', 'DOCTOR', 'MK1934', 'TEO, FUNG LEI', '20CA9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '107', 'DOCTOR', 'PJY756', 'TEO, FUNG LEI', '20CA9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '107', 'DOCTOR', 'XHU244', 'TEO, FUNG LEI', '20CA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '107', 'DOCTOR', 'HBG450', 'TEO, FUNG LEI', '20CA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'KEL705', 'GABRIEL, MELCHOR', '50E59182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'VCX309', 'GABRIEL, MELCHOR', '50E59182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'XDN914', 'GABRIEL, MELCHOR', '50E59182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'DOCTOR', 'WBL459', 'GAN, FELISA', '109A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'DOCTOR', 'XRH606', 'GAN, FELISA', '109A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'DOCTOR', 'XSN726', 'GAN, FELISA', '109A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'DOCTOR', 'XSN786', 'GAN, FELISA', '109A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'XMG316', 'GAN, GERALDINE VILLANUEVA', '200A9D82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'NQO737', 'GAN, GERALDINE VILLANUEVA', '200A9D82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'UBQ830', 'GAN, GERALDINE VILLANUEVA', '200A9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '110', 'DOCTOR', 'PMI480', 'GARCIA, EFREN', '70CC9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '110', 'DOCTOR', 'ZCU994', 'GARCIA, EFREN', '70CC9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '382', 'DOCTOR', 'BDP454', 'GARCIA, NESTOR YU', '60B69482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'PSQ675', 'GARCIA JR, JOSE', '80E69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'UFZ629', 'GARCIA JR, JOSE', '80E69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'WBE311', 'GARCIA JR, JOSE', '80E69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'ZHX913', 'GARCIA JR, JOSE', '80E69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'ATA7712', 'GARCIA JR, JOSE', '80E69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '112', 'DOCTOR', 'UUA498', 'GAW, GRACE', '20329082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'DOCTOR', 'WLC455', 'GICABAO-BERINO, CHONA', 'A0709082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'DOCTOR', 'ZBL418', 'GICABAO-BERINO, CHONA', 'A0709082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '115', 'DOCTOR', 'POU330', 'GO-JULIO, JUSTINA', 'A0639482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '115', 'DOCTOR', 'WQK662', 'GO-JULIO, JUSTINA', 'A0639482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '118', 'DOCTOR', 'WMB680', 'GO, GRACE TAN', 'D0689282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '118', 'DOCTOR', 'ZHZ820', 'GO, GRACE TAN', 'D0689282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '118', 'DOCTOR', 'ZRS681', 'GO, GRACE TAN', 'D0689282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '119', 'DOCTOR', 'NWO167', 'GO, IRISH TU', '609D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '120', 'DOCTOR', 'KCG335', 'GO, JENNYLYN', 'D0B79382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '120', 'DOCTOR', 'UON409', 'GO, JENNYLYN', 'D0B79382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '121', 'DOCTOR', 'WEH528', 'GO, RONALD', '50699082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '121', 'DOCTOR', 'XKE588', 'GO, RONALD', '50699082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '123', 'DOCTOR', 'PQL305', 'GO,  RUBY', '206D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '123', 'DOCTOR', 'NOB231', 'GO,  RUBY', '206D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '124', 'DOCTOR', 'XCF736', 'GO,  WILLIAM', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'DOCTOR', 'WTI288', 'GO-SAY, MARY', '905A9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'DOCTOR', 'WSF999', 'GO-SAY, MARY ', '905A9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'DOCTOR', 'ZFH828', 'GO-SAY, MARY', '905A9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'DOCTOR', 'ZPX882', 'GO-SAY, MARY', '905A9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'NSI252', 'GOHU-YU, ELENA', '90459C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'POR490', 'GOHU-YU, ELENA', '90459C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'TFK410', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'UPG373', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'WDD526', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'WMB525', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'WSS398', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'XTU482', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'ZHX955', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'ZRX293', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'ZSH306', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '125', 'DOCTOR', 'ZSL510', 'GOHU-YU, ELENA', '90459C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'NMO509', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'RAP401', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'WEU661', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'PQJ593', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'BEX191', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'WEH778', 'GO, ELIZABETH SIY', '80E48F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'VCV232', 'GO, ELIZABETH SIY', '80E48F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'RAP402', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'ZJJ520', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '146', 'DOCTOR', 'LEE513', 'LEE, HELEN', '20169982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '146', 'DOCTOR', 'TMN711', 'LEE, HELEN', '20169982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '146', 'DOCTOR', 'XLD484', 'LEE, HELEN', '20169982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '146', 'DOCTOR', 'XNV193', 'LEE, HELEN', '20169982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '146', 'DOCTOR', 'ZDM609', 'LEE, HELEN', '20169982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '147', 'DOCTOR', 'NEO669', 'LEE, JEFFREY', 'A0919982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '147', 'DOCTOR', 'XCZ578', 'LEE, JEFFREY', 'A0919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '147', 'DOCTOR', 'NC6048', 'LEE, JEFFREY', 'A0919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '149', 'DOCTOR', 'ZLL720', 'LEE, PAUL', 'F0A49C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '149', 'DOCTOR', 'WES910', 'LEE, PAUL', 'F0A49C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '149', 'DOCTOR', 'BXO437', 'LEE, PAUL', 'F0A49C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '149', 'DOCTOR', 'ZFR735', 'LEE, PAUL', 'F0A49C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '149', 'DOCTOR', 'WFQ892', 'LEE, PAUL', 'F0A49C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '150', 'DOCTOR', 'ZPT102', 'LEE, RITA', '10929C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '150', 'DOCTOR', 'PLK435', 'LEE, RITA', '10929C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '150', 'DOCTOR', 'ZFF128', 'LEE, RITA', '10929C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'NZQ889', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'TLC000', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'XKD633', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'XNM876', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'VXX898', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '229', 'DOCTOR', 'WBL459', 'GAN-PE, PAMELA', '80CF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'WEU661', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'NMO509', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'RAP401', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'PQJ593', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '128', 'DOCTOR', 'UJM201', 'GOTAUCO, CARMENCITA', '80DF9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '128', 'DOCTOR', 'WCG998', 'GOTAUCO, CARMENCITA', '80DF9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'XRS580', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'XRM322', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'XAH163', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'UIV258', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'XHH565', 'KING, FELISA', '200E9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'DJB222', 'KING, FELISA', '200E9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'XEF970', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'KNG111', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'XHC551', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'WSF710', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '139', 'DOCTOR', 'XJF359', 'KOH, ABNER', '20539782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '141', 'DOCTOR', 'WGL131', 'KWONG, DAVID', 'F0339B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '141', 'DOCTOR', 'ZNR533', 'KWONG, DAVID', 'F0339B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '132', 'DOCTOR', 'ZFY290', 'LIM, JUNE TIU', '906C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '133', 'DOCTOR', 'ZDU458', 'KAW, LEONCIO', 'B0109282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '134', 'DOCTOR', 'WBU558', 'KHO, MARIANO', 'E0EF9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '134', 'DOCTOR', 'WTP462', 'KHO, MARIANO', 'E0EF9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '134', 'DOCTOR', 'XNB145', 'KHO, MARIANO', 'E0EF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '134', 'DOCTOR', 'ZBF873', 'KHO, MARIANO', 'E0EF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '135', 'DOCTOR', 'WBU558', 'KHO, ROSITA CHIONG', 'F0D59B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '135', 'DOCTOR', 'WTP462', 'KHO, ROSITA CHIONG', 'F0D59B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '135', 'DOCTOR', 'XNB145', 'KHO, ROSITA CHIONG', 'F0D59B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '135', 'DOCTOR', 'ZBF873', 'KHO, ROSITA CHIONG', 'F0D59B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '142', 'DOCTOR', 'ZAJ323', 'LAO, RAMON', '10099C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '142', 'DOCTOR', 'VXL555', 'LAO, RAMON', '10099C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '142', 'DOCTOR', 'TYI555', 'LAO, RAMON', '10099C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'ZRB293', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'XLF140', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'XJX316', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'PPO885', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'UQL773', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'DP1414', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'BT2688', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'PPO885', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'ZMF458', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'PQZ482', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'UQL773', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'DP1414', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '145', 'DOCTOR', 'CRA719', 'LEE, CRISTINA', '50649A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '145', 'DOCTOR', 'CNV411', 'LEE, CRISTINA', '50649A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '145', 'DOCTOR', 'NIY212', 'LEE, CRISTINA', '50649A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '145', 'DOCTOR', 'TQK485', 'LEE, CRISTINA', '50649A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '145', 'DOCTOR', 'WOP202', 'LEE, CRISTINA', '50649A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '561', 'DOCTOR', 'ZAW299', 'VILLANUEVA, JOHANS PLANA', '50999A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '561', 'DOCTOR', 'UAG356', 'VILLANUEVA, JOHANS PLANA', '50999A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '561', 'DOCTOR', 'XTE918', 'VILLANUEVA, JOHANS PLANA', '50999A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'EDG460', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'ZKJ130', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'WIO431', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'ED6160', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '158', 'DOCTOR', 'TAQ763', 'LETRAN, ELEANOR', '60BA9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '158', 'DOCTOR', 'UF1626', 'LETRAN, ELEANOR', '60BA9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '158', 'DOCTOR', 'HV8022', 'LETRAN, ELEANOR', '60BA9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '158', 'DOCTOR', 'WIR377', 'LETRAN, ELEANOR', '60BA9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '159', 'DOCTOR', 'TAQ763', 'LETRAN, JASON', 'B0599482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '159', 'DOCTOR', 'UFI626', 'LETRAN, JASON', 'B0599482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '160', 'DOCTOR', 'WJC751', 'LEYRITANA, MARIVIC', '804D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '161', 'DOCTOR', 'ZER273', 'LEYSA, ANGELICA', '20AE9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '163', 'DOCTOR', 'POS558', 'LIM, ANDERSON', '105A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '164', 'DOCTOR', 'ZFY290', 'LIM, EDRICK', '70CD9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '353', 'DOCTOR', 'WGP128', 'LIM, DARWIN M.D.', 'F00D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '353', 'DOCTOR', 'AQA8778', 'LIM, DARWIN M.D.', 'F00D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '165', 'DOCTOR', 'XLZ568', 'LIM, EDWARD', 'B0CF9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '165', 'DOCTOR', 'ZRM258', 'LIM, EDWARD', 'B0CF9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '166', 'DOCTOR', 'WPK139', 'LIM, ELINO', '80419482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '166', 'DOCTOR', 'WKK139', 'LIM, ELINO', '80419482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '168', 'DOCTOR', 'ZTP923', 'LIM, FREDIRICK', 'A0849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '169', 'DOCTOR', 'XNG595', 'LIM, GEORGE', '30049282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '169', 'DOCTOR', 'JEP800', 'LIM, GEORGE', '30049282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '169', 'DOCTOR', 'NOD828', 'LIM, GEORGE', '30049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '170', 'DOCTOR', 'TEL990', 'LIM, HENRISON', 'C0C69182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '170', 'DOCTOR', 'XNB220', 'LIM, HENRISON', 'C0C69182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'FGE452', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'JUL383', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'XTP553', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'JRL118', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'WJQ975', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'PQR328', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '172', 'DOCTOR', 'WPD800', 'LIM, JULIE DEL ROSARIO', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '172', 'DOCTOR', 'ZGE919', 'LIM, JULIE DEL ROSARIO', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '172', 'DOCTOR', 'ZZR777', 'LIM, JULIE DEL ROSARIO', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '173', 'DOCTOR', 'PBI923', 'CABRAL-LIM, LEONOR', '50E39082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '173', 'DOCTOR', 'XHH806', 'CABRAL-LIM, LEONOR', '50E39082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '174', 'DOCTOR', 'JEP800', 'LIM, LUCILLE', '60829482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '174', 'DOCTOR', 'XMG595', 'LIM, LUCILLE', '60829482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '174', 'DOCTOR', 'NOD828', 'LIM, LUCILLE', '60829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'TV5663', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'TV3191', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'ZRF112', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'WCO577', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'ULQ759', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '176', 'DOCTOR', 'RUB911', 'LIM, ROSARIO TAN', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '176', 'DOCTOR', 'XCY535', 'LIM, ROSARIO TAN', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '176', 'DOCTOR', 'PID870', 'LIM, ROSARIO TAN', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '176', 'DOCTOR', 'TX8730', 'LIM, ROSARIO TAN', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '176', 'DOCTOR', 'WOU986', 'LIM, ROSARIO TAN', '90F39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '177', 'DOCTOR', 'MLT 86 ', 'LIM, RUBY', 'B04B9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '177', 'DOCTOR', 'RGL268', 'LIM, RUBY', 'B04B9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'XNJ828', 'LIM, TERESITA', 'E0749482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'ZDM661', 'LIM, TERESITA', 'E0749482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'ZNC404', 'LIM, TERESITA', 'E0749482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'ZDH276', 'LIM, TERESITA', 'E0749482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'NFO418', 'LIM, TERESITA', 'E0749482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'WIK609', 'LIM, TERESITA', 'E0749482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '178', 'DOCTOR', 'UVO635', 'LIM, TERESITA', 'E0749482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'ZNL761', 'LIM, TIONG SAM', '00549482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'WKE732', 'LIM, TIONG SAM', '00549482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'ZDW523', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'ZDG135', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'THV846', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'XHH827', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'PLV897', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'NNQ163', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '180', 'DOCTOR', 'XSZ723', 'LIM, VANESSA ROSETTE', '60C79382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'NOC860', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'PQC521', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'UHS339', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'WJP155', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'WSP203', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'XHZ751', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'ZGR497', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'ZTA636', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'TTO060', 'LIM, ALBA REBECCA', 'C03D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'WWI493', 'LIM, ALBA REBECCA', 'C03D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'XCE966', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'ZDR423', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'ZMN996', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'PYO148', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'TOG518', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'TTO060', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'WBD882', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'NII821', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'ZNM362', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'XBU670', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'NEO206', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'PUQ469', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'PXI644', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'ZMX437', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '186', 'DOCTOR', 'MMC770', 'LLAMAS, MODESTO', 'F0499182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '186', 'DOCTOR', 'MMM700', 'LLAMAS, MODESTO', 'F0499182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '186', 'DOCTOR', 'WBB235', 'LLAMAS, MODESTO', 'F0499182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '187', 'DOCTOR', 'XTV671', 'LO, JOVANNI', 'A07F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '187', 'DOCTOR', 'NTO437', 'LO, JOVANNI', 'A07F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'RCU366', 'LO, VIRGILIO', 'D06F9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'RDW933', 'LO, VIRGILIO', 'D06F9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'TTE205', 'LO, VIRGILIO', 'D06F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'UFM880', 'LO, VIRGILIO', 'D06F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'UQG568', 'LO, VIRGILIO', 'D06F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '190', 'DOCTOR', 'WEJ656', 'LOKIN, JOHNNY', '80FF8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '190', 'DOCTOR', 'TMQ868', 'LOKIN, JOHNNY', '80FF8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'BDE320', 'LOO, MICHAEL ', '30B79982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'UEW632', 'LOO, MICHAEL ', '30B79982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'WJL880', 'LOO, MICHAEL ', '30B79982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'NOC751', 'LOO, MICHAEL ', '30B79982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'XEG207', 'LOO, MICHAEL ', '30B79982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'ZKZ619', 'LOO, MICHAEL ', '30B79982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '140', 'DOCTOR', 'TBO318', 'LOO, MICHAEL ', '30B79982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '189', 'DOCTOR', 'UUZ725', 'LO-YOUNG, CRISTINA', 'C09B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '189', 'DOCTOR', 'WRT502', 'LO-YOUNG, CRISTINA', 'C09B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '189', 'DOCTOR', 'TMP178', 'LO-YOUNG, CRISTINA', 'C09B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '189', 'DOCTOR', 'ZBT248', 'LO-YOUNG, CRISTINA', 'C09B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '189', 'DOCTOR', 'TZQ500', 'LO-YOUNG, CRISTINA', 'C09B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '189', 'DOCTOR', 'ZPK514', 'LO-YOUNG, CRISTINA', 'C09B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '193', 'DOCTOR', 'TFQ833', 'LU, PETERSON', '70A39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '195', 'DOCTOR', 'WLK822', 'LU, LIM JUANITA', '704A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '195', 'DOCTOR', 'XCD134', 'LU, LIM JUANITA', '704A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'UUU933', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'YQ0472', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'TMZ361', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'XKL279', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'ZBC725', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '197', 'DOCTOR', 'NDC808', 'MACALALAG-CRUZ, MYRA MICHELLE', 'E0AD9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '197', 'DOCTOR', 'PBQ894', 'MACALALAG-CRUZ, MYRA MICHELLE', 'E0AD9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '197', 'DOCTOR', 'NIE706', 'MACALALAG-CRUZ, MYRA MICHELLE', 'E0AD9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '198', 'DOCTOR', 'UNO369', 'MACEDA, JANET LAO', 'C0759A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '198', 'DOCTOR', 'UTZ729', 'MACEDA, JANET LAO', 'C0759A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '198', 'DOCTOR', 'XPZ652', 'MACEDA, JANET LAO', 'C0759A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '455', 'DOCTOR', 'PQG480', 'MENOR,  THERESA CULIS', '10AB9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '455', 'DOCTOR', 'ZKK857', 'MENOR,  THERESA CULIS', '10AB9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '202', 'DOCTOR', 'PHI711', 'MANGAHAS, CAYETANO', '30079482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '202', 'DOCTOR', 'ZCS831', 'MANGAHAS, CAYETANO', '30079482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '203', 'DOCTOR', 'ULI312', 'MONSANTO, ELMA', '20BB9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '203', 'DOCTOR', 'UVI638', 'MONSANTO, ELMA', '20BB9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '204', 'DOCTOR', 'WGE515', 'MORELOS, ANA MARIE', '30A19282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '204', 'DOCTOR', 'NDO678', 'MORELOS, ANA MARIE', '30A19282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '378', 'DOCTOR', 'BV9076', 'NAVARRO, JUDITH CLANDIO', 'E0CB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '378', 'DOCTOR', 'FJD536', 'NAVARRO, JUDITH CLANDIO', 'E0CB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '206', 'DOCTOR', 'WNB4925', 'NG, MERCY', 'B08D9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '206', 'DOCTOR', 'XGY755', 'NG, MERCY', 'B08D9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '207', 'DOCTOR', 'TYN350', 'NGO, ERLEEN', 'F0A89182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '207', 'DOCTOR', 'ERN 35', 'NGO, ERLEEN', 'F0A89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '207', 'DOCTOR', 'RNC530', 'NGO, ERLEEN', 'F0A89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '207', 'DOCTOR', 'NYI350', 'NGO, ERLEEN', 'F0A89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '208', 'DOCTOR', 'WKR249', 'NGO, JOENAVIN DY', 'E0C59282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '208', 'DOCTOR', 'ZEK652', 'NGO, JOENAVIN DY', 'E0C59282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '209', 'DOCTOR', 'NRV616', 'NGO, PRECITA S..M.D.', 'A0439082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '209', 'DOCTOR', 'THG180', 'NGO, PRECITA S..M.D.', 'A0439082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '209', 'DOCTOR', 'XFM994', 'NGO, PRECITA S..M.D.', 'A0439082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '209', 'DOCTOR', 'NBU901', 'NGO, PRECITA S..M.D.', 'A0439082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '209', 'DOCTOR', 'XBA404', 'NGO, PRECITA S..M.D.', 'A0439082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'TV5663', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'XBV587', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'WGC635', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'ZRF907', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'ZRF112', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'TV3191', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'WCO577', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '212', 'DOCTOR', 'TER776', 'OCAMPO, MARVIN', '205F9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '212', 'DOCTOR', 'ZRW876', 'OCAMPO, MARVIN', '205F9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '213', 'DOCTOR', 'ZHV499', 'OCAMPO, VICTORIANO', '00F79382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '213', 'DOCTOR', 'WDC134', 'OCAMPO, VICTORIANO', '00F79382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '213', 'DOCTOR', 'ZMD573', 'OCAMPO, VICTORIANO', '00F79382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'NOU894', 'OLPINDO-MACARAEG, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'AEG727', 'OLPINDO-MACARAEG, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '216', 'DOCTOR', 'TVI625', 'ONG, BILSON', '70B89382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '216', 'DOCTOR', 'ZCZ138', 'ONG, BILSON', '70B89382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'POP772', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'PQN412', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'ZNF601', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'POD525', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'POM935', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'WFC625', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'SKA289', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'ECAR 1', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'ECAR 2', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'AAM3508', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'WSF119', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'UAX733', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'TBM771', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'XHU422', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'TIW935', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'XMZ290', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '673', 'DOCTOR', 'UQS498', 'CHAN, HENRY', '7244172E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'AAH7724', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '43', 'DOCTOR', 'UQP625', 'CHAN-LAO, JULIET', '50029A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'XLV979', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '85', 'DOCTOR', 'XKW867', 'CUKINGNAN, ALEXANDER', 'C0C38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '85', 'DOCTOR', 'AAP3695', 'CUKINGNAN, ALEXANDER', 'C0C38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '198', 'DOCTOR', 'XMS715', 'MACEDA, JANET LAO', 'C0759A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '198', 'DOCTOR', 'IH1589', 'MACEDA, JANET LAO', 'C0759A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '690', 'DOCTOR', 'XAE753', 'TY-LEE, ADELINE', '91DE0A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '690', 'DOCTOR', 'PHI550', 'TY-LEE, ADELINE', '91DE0A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '690', 'DOCTOR', 'WOY106', 'TY-LEE, ADELINE', '91DE0A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '246', 'AMBULANCE', 'POM935', 'CGH AMBULANCE', 'C07D9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '246', 'AMBULANCE', 'PQN412', 'CGH AMBULANCE', 'C07D9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '246', 'AMBULANCE', 'AAM3508', 'CGH AMBULANCE', 'C07D9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '231', 'DOCTOR', 'EDC800', 'PANGANIBAN, JULIANO', '90E79082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '231', 'DOCTOR', 'ECP707', 'PANGANIBAN, JULIANO', '90E79082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '231', 'DOCTOR', 'UIS483', 'PANGANIBAN, JULIANO', '90E79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '442', 'DOCTOR', 'JNC 88', 'CHAN, JOHN NOEL UY', 'D0D59382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '77', 'CCD COLLECTOR', '5503TJ', 'CCD MOTOR SERVICE 1', 'C0039C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '77', 'CCD COLLECTOR', '7227NL', 'CCD MOTOR SERVICE 1', 'C0039C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '77', 'CCD COLLECTOR', '5504TJ', 'CCD MOTOR SERVICE 1', 'C0039C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '87', 'CCD COLLECTOR', '5503TJ', 'CCD MOTOR SERVICE 2', '40FB9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '87', 'CCD COLLECTOR', '7227NL', 'CCD MOTOR SERVICE 2', '40FB9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '87', 'CCD COLLECTOR', '5504TJ', 'CCD MOTOR SERVICE 2', '40FB9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '18967', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '19169', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '23286', 'DY, JAMES CEO', '50F19482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'POD511', 'DY, JAMES CEO', '50F19482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TIM757', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TZO693', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XJK988', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XTU765', 'DY, JAMES CEO', '10399982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'ZHP439', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'TX3489', 'DY, JAMESON GO', 'C07A9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'BEH520', 'DY, JAMESON GO', 'C07A9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'ULC945', 'DY, JAMESON GO', 'C07A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'VIP', 'ZPU375', 'ANG, JAIME', 'F0A89882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'VIP', 'UTO189', 'ANG, JAIME', 'F0A89882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'VIP', 'NVQ633', 'ANG, JAIME', 'F0A89882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'VIP', 'XDZ659', 'ANG, JAIME', 'F0A89882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UXQ936', 'ABAYA, ALBERT TORRES', 'D2BE435D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UIM737', 'ABAYA, ALBERT TORRES', 'D2BE435D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UDO608', 'ABAYA, ALBERT TORRES', 'D2BE435D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UVO509', 'ABAYA, ALBERT TORRES', 'D2BE435D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'XNK395', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'WB9321', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'ZOC777', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'WNW889', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'ZOC 88', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WNN636', 'CHEE-AH,  SANTIAGO', '10599982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NNI713', 'CHEE-AH,  SANTIAGO', 'F201485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'VIP', 'PEQ658', 'DINO, ANTONIO', '80F89882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'WOO828', 'CHIANPIAN, CARLOS JAO', 'D241465D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'ZTZ781', 'CHIANPIAN, CARLOS JAO', 'D241465D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'POL883', 'CHIANPIAN, CARLOS JAO', 'D241465D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'TIZ736', 'CHIANPIAN, CARLOS JAO', 'D241465D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'TQX780', 'CHIANPIAN, CARLOS JAO', 'D241465D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'EDC800', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'MSJ801', 'PANGANIBAN, SHIRLEY ', 'A0978F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'ECP707', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'UIS483', 'PANGANIBAN, SHIRLEY ', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'JKP806', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'XMU968', 'GOYOKPIN, BENITO', '80949B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'WMG303', 'GOYOKPIN, BENITO', '80949B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'WJS630', 'GOYOKPIN, BENITO', '80949B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'ZBD938', 'GOYOKPIN, BENITO', '80949B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'WSC256', 'GOYOKPIN, BENITO', '80949B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'UCZ763', 'GO, BASILIO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '57', 'VIP', 'WKM936', 'KO, MARIANO', 'E0EF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '57', 'VIP', 'YGE823', 'KO, MARIANO', 'E0EF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'ZJY923', 'LAO, GIOK CHIAO', 'B0929782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'WAG510', 'LAO, GIOK CHIAO', 'B0929782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'XGS318', 'LAO, GIOK CHIAO', 'B0929782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'ZBY108', 'LAO, GIOK CHIAO', 'B0929782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'PKP431', 'LAO, GIOK CHIAO', 'B0929782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'TDW328', 'LEE, WILLIAM', '129D475D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'AEV717', 'LEE, WILLIAM', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'UKM407', 'LEE, \" SIMON\" TIONG SENG', '10FB9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'WGK892', 'LEE, \" SIMON\" TIONG SENG', '10FB9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'XBP783', 'LEE, \" SIMON\" TIONG SENG', '10FB9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'TQY249', 'LEE, \" SIMON\" TIONG SENG', '10FB9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'WRA828', 'LEE, HENRY', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'ZSV882', 'LEE, HENRY', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'UQJ906', 'LIM, PETER', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'TIA258', 'LIM, PETER', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'WIS754', 'LIM, PETER', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'NTO910', 'LIM, CRISTINO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'TFQ835', 'LIM, CRISTINO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'POQ674', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ZLA698', 'LIM, CRISTINO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'POM275', 'NGO, PETER', 'F289485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'XTE811', 'NGO, PETER', 'F289485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'LCH888', 'NGO, PETER', 'F289485D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'TQL627', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'VIP', 'WDN210', 'ROMA, ELLANA SAPITULA', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'VIP', 'AQA6087', 'ROQUE, TERESITA SIOSON', 'A0269882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'VIP', 'UUQ613', 'ROQUE, TERESITA SIOSON', 'A0269882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'ESG228', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'GLB200', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'GPG202', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'SSS989', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'TCI636', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'TIC991', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '289', 'DOCTOR', 'YY2102', 'FERNANDO, VICTORIA SY', '90B38F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '289', 'DOCTOR', 'AAH7721', 'FERNANDO, VICTORIA SY', '90B38F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'UIS921', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'XEX183', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'XFK122', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'XRM383', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'YGH607', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'ZAL777', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'ZFJ985', 'SEE, PETER', '704D9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'NQB786', 'DY, SIOT KENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'XTU765', 'DY, SIOT KENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '26', 'VIP', 'ZBA200', 'SY KA KIENG', 'E05D9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'PQW246', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'TWQ550', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO888', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO555', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO320', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XPL303', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'JGS200', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XHR535', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'BEU812', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'ZMF520', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'UCI263', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'FAT 02', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ROS668', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', '11110', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'KHT625', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'TQO933', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '28', 'VIP', 'TQ0933', 'TAI. ROSEMARIE', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '28', 'VIP', 'FAT 02', 'TAI. ROSEMARIE', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '28', 'VIP', 'ROS668', 'TAI. ROSEMARIE', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '28', 'VIP', '11110', 'TAI. ROSEMARIE', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '28', 'VIP', 'KHT625', 'TAI. ROSEMARIE', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '28', 'VIP', 'UIC263', 'TAI. ROSEMARIE', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ATT888', 'TAN, ANTONIO', 'B0429782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'AAA 02', 'TAN, ANTONIO', 'B0429782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'PMO523', 'TAN, ANTONIO', 'B0429782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '30', 'VIP', 'PSU960', 'ONG, TEK-TIONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '30', 'VIP', 'UGN977', 'ONG, TEK-TIONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PIY632', 'UY, EDDIE', '526B475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PXI690', 'UY, EDDIE', '526B475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'UOR623', 'UY, EDDIE', '526B475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'WRW303', 'UY, EDDIE', '526B475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NSQ488', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'TOP605', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'TX9471', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '47', 'VIP', 'ZNW970', 'YANG, ROBERT', 'E01E9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'ADP997', 'ONG, YONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'WCU301', 'ONG, YONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XAH263', 'ONG, YONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XAU789', 'ONG, YONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XBC605', 'ONG, YONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XBS278', 'ONG, YONG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'VIP', 'TIZ457', 'YAYEN, CRYSTALIE', '40BF9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '42', 'VIP', 'XHP654', 'YAYEN, CRYSTALIE', '40BF9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'ADP997', 'YONG, VIC CHENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'WCU301', 'YONG, VIC CHENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XAH263', 'YONG, VIC CHENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XAU789', 'YONG, VIC CHENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XBC605', 'YONG, VIC CHENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'VIP', 'XBS278', 'YONG, VIC CHENG', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'TSQ670', 'SINDICO, EDGARDO S.', '10E29882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '411', 'VISITING DOCTOR', 'WDV743', 'CUNTAPAY, GRACE', '20899482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '412', 'VISITING DOCTOR', 'ZAM117', 'DY, GEOFFREY', 'E0659482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '413', 'VISITING DOCTOR', 'ZBA641', 'LIM, JOSEPHINE', '10869182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '414', 'VISITING DOCTOR', 'BEY668', 'KING, HARVEY', 'C04E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '414', 'VISITING DOCTOR', 'BEY678', 'KING, HARVEY', 'C04E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '420', 'VISITING DOCTOR', 'YVN677', 'MARIANO, YVONNE', '60749082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '670', 'VISITING DOCTOR', 'WLI382', 'SALVADOR, NORUEL GERARD', '71B0382D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '245', 'VISITING DOCTOR', 'PON971', 'TAN, EDEN', 'E0019C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '431', 'VISITING DOCTOR', 'UK0818', 'TAN, ROBERT', '90CA9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '431', 'VISITING DOCTOR', 'XNL135', 'TAN, ROBERT', '90CA9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '569', 'VISITING DOCTOR', 'TNI279', 'VINOYA, MICHAEL ERROL', '40109D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '446', 'VISITING DOCTOR', 'AIA8462', 'UY, ALEXANDER TAN', 'D0749482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '441', 'VISITING DOCTOR', 'FJE439', 'TING, DONATO JR.', '502E9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '441', 'VISITING DOCTOR', 'WNC139', 'TING, DONATO JR.', '502E9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'TBC753', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '332', 'DOCTOR', 'UIV682', 'ONG, BENIGNO', '90509B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '332', 'DOCTOR', 'WNY566', 'ONG, BENIGNO', '90509B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '219', 'DOCTOR', 'PMQ860', 'ONG, FLORENCIO', 'D0329582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '219', 'DOCTOR', 'XGP581', 'ONG, FLORENCIO', 'D0329582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'WKY555', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'WMS777', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'ZAN518', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'ZWR222', 'ONG, MIGUELA', 'D0329582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'NOU894', 'OLPINDO, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'WEJ875', 'OLPINDO, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'TXO236', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'UEP781', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'WJC578', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'WLW735', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'WPV900', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'XEA735', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'XKE786', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'XSN603', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'ZBB660', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'ZMV327', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'ZRA516', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'VIP', 'UKO827', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'VIP', 'WBI171', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '231', 'DOCTOR', 'MSJ801', 'PANGANIBAN, JULIANO', '90E79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '231', 'DOCTOR', 'JKP806', 'PANGANIBAN, JULIANO', '90E79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '234', 'DOCTOR', 'WEX882', 'PARDILLO, ROSITA', '20E49182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'ZFB304', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'ZJS442', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'RJO488', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'RJ4692', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'PPI970', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'RJO488', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'TIY607', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'ZFB304', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'ZPB896', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'RJ4692', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '241', 'DOCTOR', 'ZKU976', 'PERIQUET, ANTONIO', '50149182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '241', 'DOCTOR', 'NSO560', 'PERIQUET, ANTONIO', '50149182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'TV5330', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'WMF690', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'ZLH637', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'XRT125', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'NCQ583', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '258', 'DOCTOR', 'TMA136', 'REGALADO-MAGINO, MA. VERONICA', '20899482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'WDV871', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'XSA265', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'ZAH556', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'ZCB922', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '456', 'DOCTOR', 'UCI920', 'YAO, ROSALINDA SIY', '90319382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '361', 'DOCTOR', 'XPN315', 'YEE, MARY TAN', 'C08C9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '361', 'DOCTOR', 'ZCS983', 'YEE, MARY TAN', 'C08C9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '361', 'DOCTOR', 'ZFE291', 'YEE, MARY TAN', 'C08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '361', 'DOCTOR', 'NQU507', 'YEE, MARY TAN', 'C08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '362', 'DOCTOR', 'TCG921', 'YEO, ANGELINE', 'E0819082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '362', 'DOCTOR', 'URC883', 'YEO, ANGELINE', 'E0819082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '362', 'DOCTOR', 'ULK856', 'YEO, ANGELINE', 'E0819082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '363', 'DOCTOR', 'XHH307', 'YEO, MELLY', 'A0C99182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '363', 'DOCTOR', 'TKW223', 'YEO, MELLY', 'A0C99182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'WEY752', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'XNL278', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'XPU699', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'ZKJ133', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'PYO107', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'BEC285', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'RFC714', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'BDZ261', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '364', 'DOCTOR', 'ZBP165', 'YOUNG-LIM, YOLANDA', '40F99382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '380', 'DOCTOR', 'WRT502', 'YOUNG, MELISSA', 'E0F19082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '380', 'DOCTOR', 'ZBT248', 'YOUNG, MELISSA', 'E0F19082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '380', 'DOCTOR', 'ZPK514', 'YOUNG, MELISSA', 'E0F19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '380', 'DOCTOR', 'TMZ725', 'YOUNG, MELISSA', 'E0F19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '380', 'DOCTOR', 'TZQ500', 'YOUNG, MELISSA', 'E0F19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '502', 'DOCTOR', 'ZNL230', 'YU, NELSON', 'A0FE9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '502', 'DOCTOR', 'ZGX866', 'YU, NELSON', 'A0FE9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'XRS576', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'PUQ950', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'PBO469', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'ZMC526', 'YU, MAYBELLINE', 'F0149282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'WLH202', 'YU, MAYBELLINE', 'F0149282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'ZAM717', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'PQE890', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'TOF415', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'NQI531', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'ZAN348', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '526', 'DOCTOR', 'ZFR167', 'YUTIAO, MARY', 'C0F09282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '360', 'DOCTOR', 'PII160', 'ZAMORA, MA. SOCORRO', 'D0CD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '410', 'VISITING DOCTOR', 'UCO693', 'CORTEZ, CHRISTOPHER', 'D0719282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '411', 'VISITING DOCTOR', 'POQ761', 'CUNTAPAY, GRACE', '20899482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '412', 'VISITING DOCTOR', 'XHN446', 'DY, GEOFFREY', 'E0659482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '444', 'VISITING DOCTOR', 'PVI463', 'GO, JEROME LIM', '60389482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '444', 'VISITING DOCTOR', 'ZKB312', 'GO, JEROME LIM', '60389482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '444', 'VISITING DOCTOR', 'TQJ217', 'GO, JEROME LIM', '60389482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '56', 'VISITING DOCTOR', 'AAJ4533', 'HORMIGAS, ALONZO JR. T.', '70CD9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '413', 'VISITING DOCTOR', 'ZBA641', 'JIM, JOSEPHINE', '10869182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '413', 'VISITING DOCTOR', 'AQA5497', 'JIM, JOSEPHINE', '10869182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '414', 'VISITING DOCTOR', 'BDU626', 'KING, HARVEY', 'C04E9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '414', 'VISITING DOCTOR', 'BET471', 'KING, HARVEY', 'C04E9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '414', 'VISITING DOCTOR', 'BEW919', 'KING, HARVEY', 'C04E9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '415', 'VISITING DOCTOR', 'WUQ201', 'KWONG, LUZVIMINDA', '30B89182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '415', 'VISITING DOCTOR', 'ZBN519', 'KWONG, LUZVIMINDA', '30B89182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '417', 'VISITING DOCTOR', 'YJ6034', 'LIM, BENJAMIN', 'E05C9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '417', 'VISITING DOCTOR', 'ZRE532', 'LIM, BENJAMIN', 'E05C9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '447', 'VISITING DOCTOR', 'POF940', 'LIM, ANTHONY HO', 'F0659182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '447', 'VISITING DOCTOR', 'HW8989', 'LIM, ANTHONY HO', 'F0659182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '447', 'VISITING DOCTOR', 'DOCTOR', 'LIM, ANTHONY HO', 'F0659182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '418', 'VISITING DOCTOR', 'CSU155', 'LIM, GOLDEE', 'C02A9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '418', 'VISITING DOCTOR', 'MEC727', 'LIM, GOLDEE', 'C02A9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '419', 'VISITING DOCTOR', 'PRH675', 'LUA, DOROTHY LIM', '505F9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '420', 'VISITING DOCTOR', 'YVN677', 'MARIANO, YVONNE', '60749082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '550', 'VISITING DOCTOR', 'TVI625', 'ONG, CATHERINE JOIE', 'D0249382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '550', 'VISITING DOCTOR', 'ZCS138', 'ONG, CATHERINE JOIE', 'D0249382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '423', 'VISITING DOCTOR', 'PQH871', 'QUE, JOCELYN', '005D9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '424', 'VISITING DOCTOR', 'UMI513', 'REBOSA, ALBERT', '90AD8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '424', 'VISITING DOCTOR', 'ZGT609', 'REBOSA, ALBERT', '90AD8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '425', 'VISITING DOCTOR', 'POV835', 'REBOSA, ANTONIO ALEJANDRO', '409A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '425', 'VISITING DOCTOR', 'ZTR133', 'REBOSA, ANTONIO ALEJANDRO', '409A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '554', 'VISITING DOCTOR', 'PHQ285', 'SALVADOR, SUSANNAH', 'D01D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '554', 'VISITING DOCTOR', 'POK377', 'SALVADOR, SUSANNAH', 'D01D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '554', 'VISITING DOCTOR', 'PVQ762', 'SALVADOR, SUSANNAH', 'D01D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '245', 'VISITING DOCTOR', 'PON971', 'TAN, EDEN', '705A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '430', 'VISITING DOCTOR', 'ZRD895', 'TAN, IRENE PINEDA', 'E0019C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '432', 'VISITING DOCTOR', 'POJ508', 'TAN-GAW, MA. LUISA', 'B0589A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '432', 'VISITING DOCTOR', 'ZBP566', 'TAN-GAW, MA. LUISA', 'B0589A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '433', 'VISITING DOCTOR', 'WNC139', 'TING, DONATO SR.', '502E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '433', 'VISITING DOCTOR', 'XLH914', 'TING, DONATO SR.', '502E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '433', 'VISITING DOCTOR', 'YA1002', 'TING, DONATO SR.', '502E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '433', 'VISITING DOCTOR', 'AAU488', 'TING, DONATO SR.', '502E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '434', 'VISITING DOCTOR', 'RFV203', 'TORILLO, MAILA ROSE', 'F04C9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '443', 'VISITING DOCTOR', 'ZHN893', 'TIU, NANNETH T.', 'E0A59282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '436', 'VISITING DOCTOR', 'UQU958', 'WONG HIN, DABBIE QUE', 'F03F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '436', 'VISITING DOCTOR', 'ZFY120', 'WONG HIN, DABBIE QUE', 'F03F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '436', 'VISITING DOCTOR', 'ZGK831', 'WONG HIN, DABBIE QUE', 'F03F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '445', 'VISITING DOCTOR', 'POQ268', 'WONG, JENNIE ANG', '80BC9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '438', 'VISITING DOCTOR', 'WLH979', 'MARTINEZ, PATRIA CECILIA', '600F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'XGL179', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'WPE237', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'ZRU966', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '332', 'DOCTOR', 'NGT894', 'ONG, BENIGNO', '90509B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '218', 'DOCTOR', 'BEU249', 'ONG, EDWIN', 'C0B19282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '218', 'DOCTOR', 'NIC324', 'ONG, EDWIN', 'C0B19282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '218', 'DOCTOR', 'XMS778', 'ONG, EDWIN', 'C0B19282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '376', 'DOCTOR', 'TOD303', 'ORTIZ, ARLENE ONG', '70D89082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '376', 'DOCTOR', 'NPQ579', 'ORTIZ, ARLENE ONG', '70D89082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '219', 'DOCTOR', 'PMQ860', 'ONG, FLORENCIO', 'D0329582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '219', 'DOCTOR', 'XGP581', 'ONG, FLORENCIO', 'D0329582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'WKY555', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'WMS777', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'ZAN518', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '220', 'DOCTOR', 'ZWR222', 'ONG, MIGUELA', 'D0B79082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '221', 'DOCTOR', 'ZHB122', 'ONG, REMEDIOS', '804B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '221', 'DOCTOR', 'XLH625', 'ONG, REMEDIOS', '804B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '222', 'DOCTOR', 'PFI381', 'ONG, ROSA', 'A05D9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '222', 'DOCTOR', 'WKE678', 'ONG, ROSA', 'A05D9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '224', 'DOCTOR', 'WDU158', 'ONG, CHAN FLORENCE', '70109582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '224', 'DOCTOR', 'WKS800', 'ONG, CHAN FLORENCE', '70109582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '224', 'DOCTOR', 'XHH238', 'ONG, CHAN FLORENCE', '70109582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '224', 'DOCTOR', 'ZFB501', 'ONG, CHAN FLORENCE', '70109582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '225', 'DOCTOR', 'ZBV253', 'ONG-DELA CRUZ, BERNICE', 'E0938F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '225', 'DOCTOR', 'HV9794', 'ONG-DELA CRUZ, BERNICE', 'E0938F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'NOU894', 'OLPINDO, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'WEJ875', 'OLPINDO, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '226', 'DOCTOR', 'POV682', 'ONG-GO, MARY', 'D02B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '226', 'DOCTOR', 'ZJL636', 'ONG-GO, MARY', 'D02B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'TXO236', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'UEP781', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'WJC578', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'WLW735', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'WPV900', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'XEA735', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'XKE786', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'XSN603', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'ZBB660', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'ZMV327', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'ZRA516', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'UKO827', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'WBI171', 'ONGKING, PILAR', '50D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '230', 'DOCTOR', 'YF8091', 'PANGANIBAN, CINDY', 'C0B49282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '232', 'DOCTOR', 'MSJ801', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '232', 'DOCTOR', 'EDC800', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '232', 'DOCTOR', 'ECP707', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '232', 'DOCTOR', 'UIS483', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '232', 'DOCTOR', 'JKP806', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'AGP916', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'UIM217', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'ZD7167', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'ED6640', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '235', 'DOCTOR', 'NWQ411', 'PAREDES DY, ALANNA WONG', '307B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '235', 'DOCTOR', 'UNI705', 'PAREDES DY, ALANNA WONG', '307B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '235', 'DOCTOR', 'AAK4941', 'PAREDES DY, ALANNA WONG', '307B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '236', 'DOCTOR', 'XKW702', 'PARRENO, FERNANDO', 'D0E49482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '236', 'DOCTOR', 'TBB286', 'PARRENO, FERNANDO', 'D0E49482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '237', 'DOCTOR', 'ZKC131', 'PASCUA, NESTOR..M.D.', 'B0B59182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '238', 'DOCTOR', 'UBU566', 'PASTRANA, THELMA', 'F0849482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '238', 'DOCTOR', 'TNP159', 'PASTRANA, THELMA', 'F0849482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '238', 'DOCTOR', 'NIO917', 'PASTRANA, THELMA', 'F0849482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'PPI970', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'TIY607', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'ZBP896', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '243', 'DOCTOR', 'PSQ638', 'PLASABAS, NORMA', 'A0709382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '243', 'DOCTOR', 'XNY328', 'PLASABAS, NORMA', 'A0709382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '243', 'DOCTOR', 'ZJG494', 'PLASABAS, NORMA', 'A0709382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '244', 'DOCTOR', 'UBX898', 'POCSIDO, WINNIE', 'F0439182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '244', 'DOCTOR', 'UOV909', 'POCSIDO, WINNIE', 'F0439182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '244', 'DOCTOR', 'RCN669', 'POCSIDO, WINNIE', 'F0439182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '247', 'DOCTOR', 'PQY959', 'PUA-GO, VIRGINIA', '603F9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '247', 'DOCTOR', 'NOY921', 'PUA-GO, VIRGINIA', '603F9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '247', 'DOCTOR', 'NNO743', 'PUA-GO, VIRGINIA', '603F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '247', 'DOCTOR', 'ZFZ758', 'PUA-GO, VIRGINIA', '603F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '248', 'DOCTOR', 'WKM720', 'QUA, JOSEFINO', 'D0129482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '248', 'DOCTOR', 'XJX339', 'QUA, JOSEFINO', 'D0129482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '248', 'DOCTOR', 'ZMD893', 'QUA, JOSEFINO', 'D0129482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '250', 'DOCTOR', 'XSJ705', 'QUE, GEORGE', '00689882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '250', 'DOCTOR', 'PRO438', 'QUE, GEORGE', '00689882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '543', 'DOCTOR', 'XLWII9', 'QUE, TWEENKY', 'B0759382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '253', 'DOCTOR', 'NOI978', 'RABO, CARTER SANTOS', 'C0A29A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '253', 'DOCTOR', 'PMI313', 'RABO, CARTER SANTOS', 'C0A29A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '253', 'DOCTOR', 'WNW116', 'RABO, CARTER SANTOS', 'C0A29A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '253', 'DOCTOR', 'ZEF640', 'RABO, CARTER SANTOS', 'C0A29A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '253', 'DOCTOR', 'ZNT302', 'RABO, CARTER SANTOS', 'C0A29A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '254', 'DOCTOR', 'WOH138', 'RABO, CHERRY', 'A09B9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '254', 'DOCTOR', 'NOI978', 'RABO, CHERRY', 'A09B9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '254', 'DOCTOR', 'ZEF640', 'RABO, CHERRY', 'A09B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '256', 'DOCTOR', 'TOY783', 'RAGOS, EVELYN', '30169282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '256', 'DOCTOR', 'TOM986', 'RAGOS, EVELYN', '30169282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '256', 'DOCTOR', 'CJ6365', 'RAGOS, EVELYN', '30169282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '256', 'DOCTOR', 'TQM986', 'RAGOS, EVELYN', '30169282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '256', 'DOCTOR', 'UQG582', 'RAGOS, EVELYN', '30169282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '259', 'DOCTOR', 'NON784', 'REYES, TOMMY', '90969382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '260', 'DOCTOR', 'UYI402', 'RIVERA, MARIONETTE LIM', '20409482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '260', 'DOCTOR', 'ZBU261', 'RIVERA, MARIONETTE LIM', '20409482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '260', 'DOCTOR', 'ZTY539', 'RIVERA, MARIONETTE LIM', '20409482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '260', 'DOCTOR', 'M12077', 'RIVERA, MARIONETTE LIM', '20409482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '261', 'DOCTOR', 'NOM460', 'ROGANDO-TAN, LYNDA', 'F0ED8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '263', 'DOCTOR', 'WLH208', 'SALAZAR, ROMMEL', '70229182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '263', 'DOCTOR', 'TQD525', 'SALAZAR, ROMMEL', '70229182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '264', 'DOCTOR', 'BDF428', 'SANGALANG-VINOYA, OLGA', '30849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '264', 'DOCTOR', 'PLP785', 'SANGALANG-VINOYA, OLGA', '30849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '264', 'DOCTOR', 'THJ424', 'SANGALANG-VINOYA, OLGA', '30849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '264', 'DOCTOR', 'TLU151', 'SANGALANG-VINOYA, OLGA', '30849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '264', 'DOCTOR', 'PST971', 'SANGALANG-VINOYA, OLGA', '30849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'TVQ126', 'SAY, ANTONIO', '50829482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'WPI608', 'SAY, ANTONIO', '50829482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'XHL239', 'SAY, ANTONIO', '50829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'XCC512', 'SAY, ANTONIO', '50829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'ZTY623', 'SAY, ANTONIO', '50829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'ZLY500', 'SAY, ANTONIO', '50829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'ZBE583', 'SAY, ANTONIO', '50829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'TVQ126', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'WPI608', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'XCC512', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'XHL239', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'ZBE583', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'ZLY500', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'ZTY623', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'YLM858', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '267', 'DOCTOR', 'DN6123', 'SEECHEUNG, ANDY Y..M.D.', '202F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '267', 'DOCTOR', 'TXQ583', 'SEECHEUNG, ANDY Y..M.D.', '202F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '267', 'DOCTOR', 'WKI823', 'SEECHEUNG, ANDY Y..M.D.', '202F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '384', 'DOCTOR', 'UEQ434', 'SEE, MARY ANN', '40059582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '384', 'DOCTOR', 'XSC535', 'SEE, MARY ANN', '40059582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '268', 'DOCTOR', 'WGF674', 'SERRANO, HELEN', '30219082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '268', 'DOCTOR', 'ZDE951', 'SERRANO, HELEN', '30219082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '373', 'DOCTOR', 'ZHS639', 'SERAPIO, SERAFIN', 'D09F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '373', 'DOCTOR', 'TNI602', 'SERAPIO, SERAFIN', 'D09F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '373', 'DOCTOR', 'TOQ556', 'SERAPIO, SERAFIN', 'D09F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '269', 'DOCTOR', 'NOI521', 'SIA-TAN, JOSEPH', 'E0638F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', 'VEB642', 'SINDICO, EDGARDO S.', '10E29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'TR3876', 'SIA, KENDRICK GO', 'C0BA9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'TPS604', 'SIA, KENDRICK GO', 'C0BA9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'XRC337', 'SIA, KENDRICK GO', 'C0BA9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'UBO870', 'SIA, KENDRICK GO', 'C0BA9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'PWQ641', 'SIA, KENDRICK GO', 'C0BA9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'ZMN492', 'SIA, KENDRICK GO', 'C0BA9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'TQD525', 'SIA, MELISSA CO', '10049282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'WLH208', 'SIA, MELISSA CO', '10049282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'FGE452', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'JRL118', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'JUL383', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'PQR328', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'UAO669', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'WJQ975', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'XCX961', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'XTP553', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '274', 'DOCTOR', 'ZMH247', 'SIBULO, MELVIN', '008F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '274', 'DOCTOR', 'TOR626', 'SIBULO, MELVIN', '008F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '274', 'DOCTOR', 'ZSZ180', 'SIBULO, MELVIN', '008F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '275', 'DOCTOR', 'XPK399', 'SISON, MICHAEL', 'C06E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '275', 'DOCTOR', 'XRS168', 'SISON, MICHAEL', 'C06E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '275', 'DOCTOR', 'ZAE863', 'SISON, MICHAEL', 'C06E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '275', 'DOCTOR', 'ZDJ532', 'SISON, MICHAEL', 'C06E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'WPV926', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'WRM781', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'XCV232', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'XKH229', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'ZCD645', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '278', 'DOCTOR', 'WLH208', 'SIY-SALAZAR, PEARLIE', '40649382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '278', 'DOCTOR', 'TQD525', 'SIY-SALAZAR, PEARLIE', '40649382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '278', 'DOCTOR', 'UUY659', 'SIY-SALAZAR, PEARLIE', '40649382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '279', 'DOCTOR', 'UCI920', 'SIY-YAO, ROSALINDA', '90319382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '279', 'DOCTOR', 'XPK128', 'SIY-YAO, ROSALINDA', '90319382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'PIO753', 'SY, BENITO', 'E0339782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'PQF337', 'SY, BENITO', 'E0339782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'WAC819', 'SY, BENITO', 'E0339782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'WKP191', 'SY, BENITO', 'E0339782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'ZAA636', 'SY, BENITO', 'E0339782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'ZGK417', 'SY, BENITO', 'E0339782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'ZER752', 'SY, JOCELYN LIM', 'A00E9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'XPU699', 'SY, JOCELYN LIM', 'A00E9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'XNL278', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'ZEZ263', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'ZKL133', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'NZI156', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'PXI871', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'PBQ229', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '284', 'DOCTOR', 'UKU127', 'SY, JONATHAN', 'D0CB9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '284', 'DOCTOR', 'XAD969', 'SY, JONATHAN', 'D0CB9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '284', 'DOCTOR', 'ZPK566', 'SY, JONATHAN', 'D0CB9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '285', 'DOCTOR', 'NHQ567', 'SY, MARIANNE', '20369A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '285', 'DOCTOR', 'UIR681', 'SY, MARIANNE', '20369A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'XSP283', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'PII315', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'AAK3250', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'XBM228', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '540', 'DOCTOR', 'NQP297', 'SY, ROBERT', 'D0C09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '540', 'DOCTOR', 'ZHL731', 'SY, ROBERT', 'D0C09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '540', 'DOCTOR', 'MPS555', 'SY, ROBERT', 'D0C09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '540', 'DOCTOR', 'XNN821', 'SY, ROBERT', 'D0C09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '540', 'DOCTOR', 'XGP560', 'SY, ROBERT', 'D0C09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'POH597', 'SY, TONG HUE', '60769182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'XKN778', 'SY, TONG HUE', '60769182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'WTV132', 'SY-FERNANDO,  VICTORIA', '90B38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'ML2774', 'SY-FERNANDO,  VICTORIA', '90B38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'XBW128', 'TAN, ALEJANDRO', '337EE92E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'VT0949', 'TAN, ALEJANDRO', '337EE92E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'TQG722', 'TAN, ALEJANDRO', '337EE92E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'WDM400', 'TAN, ALEJANDRO', '337EE92E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'ZAT191', 'TAN, ALEJANDRO', '337EE92E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'POV588', 'TAN, ALEJANDRO', '337EE92E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'XHH889', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'XBB186', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'XNY688', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'XJY910', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '299', 'DOCTOR', 'WBN267', 'TAN, EDWIN SY', '00A19C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '299', 'DOCTOR', 'ZJP703', 'TAN, EDWIN SY', '00A19C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '299', 'DOCTOR', 'UHQ817', 'TAN, EDWIN SY', '00A19C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '293', 'DOCTOR', 'WEJ405', 'TAN, FRANCISCO SY', 'DR. FRANCISCO TAN SY', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '292', 'DOCTOR', 'ZEX495', 'TAN, PASCUAL MYRA', 'E0C79B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'XRS433', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'ZFM412', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'ZSM800', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'XPH691', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'WQN258', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '470', 'DOCTOR', 'TNJ739', 'TAN, BENITO', '101A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '470', 'DOCTOR', 'XEL119', 'TAN, BENITO', '101A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '470', 'DOCTOR', 'TV3021', 'TAN, BENITO', '101A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '470', 'DOCTOR', 'WZO456', 'TAN, BENITO', '101A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'URQ983', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '303', 'DOCTOR', 'WCX234', 'TAN, JOSE', 'E02E9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '303', 'DOCTOR', 'ZTT217', 'TAN, JOSE', 'E02E9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '303', 'DOCTOR', 'PII285', 'TAN, JOSE', 'E02E9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '303', 'DOCTOR', 'MM8307', 'TAN, JOSE', 'E02E9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '304', 'DOCTOR', 'ZJJ520', 'TAN, KING KING', '304A9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '304', 'DOCTOR', 'ZBX135', 'TAN, KING KING', '304A9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '304', 'DOCTOR', 'YH4484', 'TAN, KING KING', '304A9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '661', 'DOCTOR', 'XAC853', 'TAN, LINDA', '916AB42D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '661', 'DOCTOR', 'TVQ395', 'TAN, LINDA', '916AB42D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'ZDW586', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'PRI534', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'ZSB895', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'TIC361', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'DOCTOR', 'ZSB895', 'TAN, LUCIO', '00B59382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'DOCTOR', 'WKP791', 'TAN, LUCIO', '00B59382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '307', 'DOCTOR', 'XDH274', 'TAN, PATRICIA', 'B0269782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '307', 'DOCTOR', 'XSP618', 'TAN, PATRICIA', 'B0269782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '307', 'DOCTOR', 'ZKD152', 'TAN, PATRICIA', 'B0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '308', 'DOCTOR', 'XFW815', 'TAN, RAMON', '90DF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '308', 'DOCTOR', 'NIH206', 'TAN, RAMON', '90DF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '309', 'DOCTOR', 'ZSH753', 'TAN, RIZALINA', 'A0739A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '309', 'DOCTOR', 'ZEZ799', 'TAN, RIZALINA', 'A0739A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '309', 'DOCTOR', 'ZAH206', 'TAN, RIZALINA', 'A0739A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '310', 'DOCTOR', 'WJY546', 'TAN, STEPHANY', 'A0149982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '310', 'DOCTOR', 'ZMM530', 'TAN, STEPHANY', 'A0149982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'TX9561', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'RPA266', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'WPE266', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'PNO353', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'XGE787', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'ZAV939', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '314', 'DOCTOR', 'ZPK228', 'TANBONLIONG, SEVERINO', '20A49882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '314', 'DOCTOR', 'PLD899', 'TANBONLIONG, SEVERINO', '20A49882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '314', 'DOCTOR', 'XSC700', 'TANBONLIONG, SEVERINO', '20A49882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '315', 'DOCTOR', 'PID213', 'TANG, BENJAMIN', '90289C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'UJQ203', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'ZAE997', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'ZBJ531', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'WRU959', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'WTM753', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'XSG398', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'DBG490', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '316', 'DOCTOR', 'WTI786', 'TANSENGCO, RUSTICA', 'D08E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '312', 'DOCTOR', 'UUS962', 'TAN-SO, ANITA', 'A06D9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '312', 'DOCTOR', 'TOS629', 'TAN-SO, ANITA', 'A06D9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '312', 'DOCTOR', 'TGU139', 'TAN-SO, ANITA', 'A06D9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '312', 'DOCTOR', 'UNY132', 'TAN-SO, ANITA', 'A06D9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '313', 'DOCTOR', 'WJXII0', 'TAN-SO, LOURDES SOMA', 'B0B39782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'WKS588', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'TRR661', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'UKD635', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'TD8180', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '59', 'VIP', 'ZGZ876', 'CHANGCO, ELIZABETH CHUA', '00C68F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '450', 'DOCTOR', 'WIW540', 'CO, PATRICK LEONARD GO', 'A05E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'NOT635', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '342', 'DOCTOR', 'PNH801', 'UY,VICENTE M.D.', '405E9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '358', 'DOCTOR', 'ZEH955', 'YAP, JENNIFER SY M.D.', '30539182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '641', 'DOCTOR', 'ECP707', 'CHUA, EDEN', '910F1F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '610', 'DOCTOR', 'POB797', 'TAN-TINHAY, LORA MAY', 'C273632E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '610', 'DOCTOR', 'ZSU385', 'TAN-TINHAY, LORA MAY', 'C273632E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'ZHF693', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '300', 'DOCTOR', 'ZAF199', 'TAN, EVELYN RAMOS', '00549A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '262', 'DOCTOR', 'TJI592', 'RUALES, ALLAN B..M.D.', '30A59082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '355', 'DOCTOR', 'ZEH955', 'YAP, ELENA M.D.', '80919082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'PGO942', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'PIQ717', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'XKJ753', 'TANTUCO-QUE, EMILIE ', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'NMO850', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '309', 'DOCTOR', 'ZAN206', 'TAN, RIZALINA CO..M.D.', 'A0739A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '309', 'DOCTOR', 'ZEZ799', 'TAN, RIZALINA CO..M.D.', 'A0739A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '408', 'VISITING/DOCTOR', 'ZNC262', 'CLAUDIO, MA. MARIBETH BUTLIG', '00E29182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '300', 'DOCTOR', 'NBO997', 'TAN, EVELYN RAMOS', '00549A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '402', 'VISITING/DOCTOR', 'THT988', 'CHIONG, NENITA  SOTTO', '50CB9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '402', 'VISITING/DOCTOR', 'ULW926', 'CHIONG, NENITA  SOTTO', '50CB9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '406', 'VISITING/DOCTOR', 'PQM538', 'CHUA, MANOLITO', '601D9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '393', 'VISITING/DOCTOR', 'NIZ910', 'ANG, ARNEIL UY', 'A0F29482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '393', 'VISITING/DOCTOR', 'TQP691', 'ANG, ARNEIL UY', 'A0F29482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '33', 'DOCTOR', 'AHF295', 'CASTRO, EUGENE', '60349182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'VIP', 'WBI438', 'COTO, BERLING', 'D0099C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'VIP', 'TT8865', 'COTO, BERLING', 'D0099C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'VIP', 'RAE869', 'ESTACIO, MARILOU', '70649A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'VIP', 'ZNC539', 'ESTACIO, MARILOU', '70932381', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'VIP', 'UHP256', 'LAZARO, EMMANUEL RAMOS', '007E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '59', 'DOCTOR', 'DNO465', 'CHUA, ERIC', '80DC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '59', 'DOCTOR', 'VCZ998', 'CHUA, ERIC', '80DC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '59', 'DOCTOR', 'VFJ713', 'CHUA, ERIC', '80DC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'NCQ947', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '377', 'DOCTOR', 'AAO2543', 'CHAM, WILLIAM CHAN', '105D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'DOCTOR', 'XTE752', 'CHUA, ELEN', 'F00C9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '344', 'DOCTOR', 'WPO394', 'VERA CRUZ, MARIVIC & RAFAEL..M.D', '20D09082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '344', 'DOCTOR', 'MVC777', 'VERA CRUZ, MARIVIC & RAFAEL..M.D', '20D09082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'PQZ482', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'AAL5496', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '328', 'DOCTOR', 'UXO163', 'TONGSON, LUINIO S..M.D', 'F0D19982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'WCB374', 'LO, VIRGILIO', 'D06F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '238', 'DOCTOR', 'NI0917', 'PASTRANA, THELMA', 'F0849482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'PUQ883', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'XNY128', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '390', 'DOCTOR', 'WGI200', 'UY,  BILLY JAMES..M.D', '804E8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'ZFC722', 'SIA, KELLY HUN', 'F0DF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'PKI934', 'SIA, KELLY HUN', 'F0DF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'UIV658', 'SIA, KELLY HUN', 'F0DF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'WQO649', 'CALAHAT, SHYNNE LOPEZ', '80FA9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '53', 'DOCTOR', 'UOR623', 'UY, EDUARDO JALBUENA', '409B9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '53', 'DOCTOR', 'WOR623', 'UY, EDUARDO JALBUENA', '409B9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '53', 'DOCTOR', 'PIY632', 'UY, EDUARDO JALBUENA', '409B9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'PQM669', 'GABRIEL, MELCHOR', '50E59182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'POP772', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'PQN412', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'ZNF601', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'POD525', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'POM935', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'WFC625', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'SKA289', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'ECAR 1', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'ECAR 2', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '497', 'AMBULANCE 2', 'AAM3508', 'AMBULANCE 2', 'E0789782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'PWQ641', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'TPS604', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'XRC337', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'ZMN492', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'TR3876', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'UBO870', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '344', 'DOCTOR', 'PRD832', 'VERA CRUZ, MARIVIC & RAFAEL..M.D', '20D09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '344', 'DOCTOR', 'ZTS346', 'VERA CRUZ, MARIVIC & RAFAEL..M.D', '20D09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '605', 'DOCTOR', 'ZHK359', 'TEH, CHRISTINE M.D', 'E218CC2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '605', 'DOCTOR', 'NIU229', 'TEH, CHRISTINE M.D', 'E218CC2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'BEX181', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'BEX191', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'BEX181', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '349', 'DOCTOR', 'NKL913', 'WARREN, MARIA M.D.', '90609A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '457', 'VISITING CONSULTANT', 'BGO271', 'ONG KIAN KOC, BRYAN BERNARD UY, M.D.', '705B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'DOCTOR', 'NIC324', 'BAYSA-ONG, RUBY', '80219382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'DOCTOR', 'BEU249', 'BAYSA-ONG, RUBY', '80219382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'DOCTOR', 'XMS778', 'BAYSA-ONG, RUBY', '80219382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'ZKJ218', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'ZAM813', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'XNB851', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'TS5733', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'WIQ503', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'TX1146', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'ZJF238', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'NKQ685', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '329', 'DOCTOR', 'EDT828', 'TUAZON, EDGAR M.D.', 'F04A9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '329', 'DOCTOR', 'WSV828', 'TUAZON, EDGAR M.D.', 'F04A9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'DOCTOR', 'NWO167', 'GO, ERLINDO JOSE..M.D', '80449182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '403', 'DOCTOR', '5001SO', 'CHUA, DERRICK M.D.', 'F0199282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '230', 'DOCTOR', 'NNI793', 'PANGANIBAN, CINDY', 'C0B49282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'ZRB989', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'TZI282', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'TW4268', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'UQO662', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '206', 'DOCTOR', 'UHS760', 'NG, MERCY', 'B08D9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'VIP', 'YL3835', 'ROQUE, TERESITA SIOSON', 'A0269882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', 'ZPC991', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', '242878', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', 'ZGZ878', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', 'PHQ517', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', 'NII636', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', 'UIL559', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '60', 'VIP', 'ZKP519', 'DY, ESTEBAN', '00B69082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'WLH799', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'XNE635', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'NUO187', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '333', 'DOCTOR', 'XSE988', 'TY, GRACE M.D.', '30979982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '333', 'DOCTOR', 'ZPR402', 'TY, GRACE M.D.', '30979982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '333', 'DOCTOR', 'XSC500', 'TY, GRACE M.D.', '30979982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '333', 'DOCTOR', 'UQQ523', 'TY, GRACE M.D.', '30979982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '351', 'DOCTOR', 'UBB373', 'WONG, MENELINE L. M.D.', '70C39182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '351', 'DOCTOR', 'XEB324', 'WONG, MENELINE L. M.D.', '70C39182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '351', 'DOCTOR', 'ZEX495', 'WONG, MENELINE L. M.D.', '70C39182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '351', 'DOCTOR', 'XRE279', 'WONG, MENELINE L. M.D.', '70C39182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'ZAT282', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'ZSR383', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'ZKD787', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'LGF505', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'TOK282', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'WOG144', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'WTI888', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '53', 'DOCTOR', 'ZDU220', 'CHUA, ALBERTO M.D.', 'B06E9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '338', 'DOCTOR', 'XJF828', 'UY, ELIZABETH G.  M.D.', '609A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '338', 'DOCTOR', 'ZBF882', 'UY, ELIZABETH G.  M.D.', '609A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '338', 'DOCTOR', 'XCP575', 'UY, ELIZABETH G.  M.D.', '609A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '338', 'DOCTOR', 'UAQ653', 'UY, ELIZABETH G.  M.D.', '609A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '313', 'DOCTOR', 'WJX110', 'TAN-SO, MA. LOURDES M.D.', 'B0B39782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'EE5882', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '122', 'DOCTOR', 'WSX889', 'GO, RORY..M.D.', '004D9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '122', 'DOCTOR', 'XTY152', 'GO, RORY..M.D.', '004D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '122', 'DOCTOR', 'ZEA889', 'GO, RORY..M.D.', '004D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '122', 'DOCTOR', 'UZI173', 'GO, RORY..M.D.', '004D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'UAO669', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'DOCTOR', 'ZFF581', 'CASTRO, JENNIFER', '508F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'DOCTOR', 'TY9364', 'CASTRO, JENNIFER', '508F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'DOCTOR', 'AHF295', 'CASTRO, JENNIFER', '508F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'MVC777', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'ZTS771', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'PRD832', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'TV3735', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'PRW683', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZJC469', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'XRC896', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZAX869', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'UOE237', 'CHUA, RACHELLE', 'A0A69A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'TNQ912', 'CHUA, RACHELLE', 'A0A69A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'WJP765', 'CHUA, RACHELLE', 'A0A69A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'XGP978', 'CHUA, RACHELLE', 'A0A69A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'DOCTOR', 'ZNF859', 'CHUA, RACHELLE', 'A0A69A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'WIQ503', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'NOT635', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'ZKJ218', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'ZAM813', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'TX1146', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'TS5733', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'O0A403', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'NKQ685', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'ZJF238', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'WPF928', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'XLJ736', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'XTU567', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'NMI195', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'ZGX769', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'UVO831', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '347', 'DOCTOR', 'NIS298', 'VILLA, MARTIN ANTHONY', 'D0D39882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '347', 'DOCTOR', 'TLQ503', 'VILLA, MARTIN ANTHONY', 'D0D39882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '347', 'DOCTOR', 'AAI6902', 'VILLA, MARTIN ANTHONY', 'D0D39882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '354', 'DOCTOR', 'ZHJ211', 'YAP TAN, MARILOU M.D.', '202D9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '354', 'DOCTOR', 'ZGR706', 'YAP TAN, MARILOU M.D.', '202D9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '358', 'DOCTOR', 'WLN822', 'YAP, JENNIFER SY M.D.', '30539182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '358', 'DOCTOR', 'WTX556', 'YAP, JENNIFER SY M.D.', '30539182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '358', 'DOCTOR', 'TFG333', 'YAP, JENNIFER SY M.D.', '30539182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '319', 'DOCTOR', 'NBQ315', 'TAURO-SHIA, VICKY  M.D.', 'D08E9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '466', 'DOCTOR', 'TUO538', 'VILLANUEVA, WARREN MICHAEL LEE M.D.', '301F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'TOT298', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '326', 'DOCTOR', 'ZHM436', 'TIONGSON, CASIMIRO M.D.', 'D0659A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '359', 'DOCTOR', 'TKU731', 'YARISANTOS, CHERRI RACHEL', 'E0279082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '359', 'DOCTOR', 'WJZ313', 'YARISANTOS, CHERRI RACHEL', 'E0279082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '359', 'DOCTOR', 'ZCU818', 'YARISANTOS, CHERRI RACHEL', 'E0279082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'ZPC761', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'TOO774', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '626', 'DOCTOR', 'ZNC282', 'UMALI, JOSELITO M.D.', '81D49A2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '626', 'DOCTOR', 'YJD740', 'UMALI, JOSELITO M.D.', '81D49A2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '699', 'DOCTOR', 'ZDT715', 'TUAZON, JACQUELINE M.D.', '91956F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '699', 'DOCTOR', 'NOG438', 'TUAZON, JACQUELINE M.D.', '91956F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '699', 'DOCTOR', 'TRO927', 'TUAZON, JACQUELINE M.D.', '91956F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '699', 'DOCTOR', 'UOV370', 'TUAZON, JACQUELINE M.D.', '91956F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '331', 'DOCTOR', 'UQQ523', 'TY, EDISON', 'E0E39982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '331', 'DOCTOR', 'XSE988', 'TY, EDISON', 'E0E39982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '331', 'DOCTOR', 'XSC500', 'TY, EDISON', 'E0E39982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '331', 'DOCTOR', 'HV5563', 'TY, EDISON', 'E0E39982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'VIP', 'XTE752', 'ONG, HILDA', '50789482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '262', 'DOCTOR', 'TJ1592', 'RUALES, ALLAN B..M.D.', '30A59082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '262', 'DOCTOR', 'XMC550', 'RUALES, ALLAN B..M.D.', '30A59082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'YG5589', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '150', 'DOCTOR', 'ZTP102', 'LEE, RITA', '10929C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '82', 'DOCTOR', 'PGO924', 'CUA, LEONARDO', 'A0EF9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'UIS499', 'TIN HAY, EDUARDO M.D.', '10DD9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'ZSU385', 'TIN HAY, EDUARDO M.D.', '10DD9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'POB797', 'TIN HAY, EDUARDO M.D.', '10DD9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '561', 'DOCTOR', 'TQB845', 'VILLANUEVA, JOHANS PLANA', '50999A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'NFI329', 'CO, CRISTINA ', 'B0E89482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'POV132', 'CO, CRISTINA ', 'B0E89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'ACA7104', 'CO, CRISTINA ', 'B0E89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '128', 'DOCTOR', 'XCW499', 'GOTAUCO, CARMENCITA', '80DF9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '610', 'DOCTOR', 'UIS499', 'TAN-TINHAY, LORA MAY', 'C273632E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '384', 'DOCTOR', 'AAO3312', 'SEE, MARY ANN', '40059582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '325', 'DOCTOR', 'URQ983', 'TING-TAN, CHERRYL Q.M.D.', '405B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '458', 'DOCTOR', 'YLJ211', 'CHUA, CRISTINA CHUA', 'C04E9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '422', 'VISITING', 'ACA6974', 'ONG, ERIC ', 'C0309582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'PRW683', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'XRC896', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZAX869', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZJC469', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '82', 'DOCTOR', 'ZAA285', 'CUA, LEONARDO', 'A0EF9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '82', 'DOCTOR', 'WMF992', 'CUA, LEONARDO', 'A0EF9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'ZBD938', 'GOYOKPIN, BENITO', '72DA455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'XMU968', 'GOYOKPIN, BENITO', '72DA455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'WSC256', 'GOYOKPIN, BENITO', '72DA455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'WJS630', 'GOYOKPIN, BENITO', '72DA455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'POD511', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'POD511', 'DY, JAMES CEO', '10399982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'POD511', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '18967 ', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '18967 ', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '18967 ', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '19169 ', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '19169 ', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '19169 ', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '23286', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '23286', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '23286', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TIM757', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '438', 'VISITING DOCTOR', 'AJA8126', 'MARTINEZ, PATRIA CECILIA', '600F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TIM757', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TIM757', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TZO693', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TZO693', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TZO693', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XJK988', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XJK988', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XJK988', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XTU765', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XTU765', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XTU765', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'ZHP439', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'ZHP439', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'ZHP439', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'AAY7021', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'WSO737', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'XAE172', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'TU8695', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '407', 'DOCTOR', 'NIG161', 'CHUNG, MELIZA P.', 'F0808F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '405', 'DOCTOR', 'KKY168', 'CHUA, LISA SALUD', 'B0FC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NOB800', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'AAJ3565', 'VALLES, TOMAS', '80959C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NOB800', 'VALLES, TOMAS', '80959C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NSQ488', 'VALLES, TOMAS', '80959C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NE4713', 'VALLES, TOMAS', '80959C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'TOP605', 'VALLES, TOMAS', '80959C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'TX9471', 'VALLES, TOMAS', '80959C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '450', 'DOCTOR', 'XES173', 'CO, PATRICK LEONARD GO', 'A05E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '450', 'DOCTOR', 'XMY337', 'CO, PATRICK LEONARD GO', 'A05E9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '406', 'VISITING/DOCTOR', 'XRT883', 'CHUA, MANOLITO', '601D9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '422', 'VISITING', 'ZLM588', 'ONG, ERIC ', 'C0309582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '405', 'DOCTOR', 'LSC 03', 'CHUA, LISA SALUD', 'B0FC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '50', 'VIP', 'AHA6462', 'EVANGELISTA, RACHELL DELA CRUZ', '00029582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '50', 'VIP', 'CRS158', 'EVANGELISTA, RACHELL DELA CRUZ', '00029582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'WMG303', 'GOYOKPIN, BENITO', '72DA455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '409', 'VISITING DOCTOR', 'TQQ160', 'COBANKIAT, RADCLIFF M.D.', '80B99382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '409', 'VISITING DOCTOR', 'WEH882', 'COBANKIAT, RADCLIFF M.D.', '80B99382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NIM229', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NIM229', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'PUQ466', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'PUQ466', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NOO833', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NOO833', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZFM953', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZFM953', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NIA743', 'LUGAY, SIXTO ', 'B0F89C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NIA743', 'LUGAY, SIXTO ', '5234455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'WLW363', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'WQO662', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '315', 'DOCTOR', 'AEA4796', 'TANG, BENJAMIN', '90289C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'BEU812', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'JGS200', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO320', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO555', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO888', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'PQW246', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'TWQ550', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XHR535', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XPL303', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'ZMF520', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'NOQ757', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'WAX396', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'ACA3228', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'AIA2249', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '540', 'DOCTOR', 'UWO933', 'SY, ROBERT', 'D0C09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '303', 'DOCTOR', 'AJA2731', 'TAN, JOSE', 'E02E9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '340', 'DOCTOR', 'WRT539', 'UY, ROBERT M.D.', '60CC9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '340', 'DOCTOR', 'XNN793', 'UY, ROBERT M.D.', '60CC9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '340', 'DOCTOR', 'ZSX691', 'UY, ROBERT M.D.', '60CC9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '340', 'DOCTOR', 'PVI380', 'UY, ROBERT M.D.', '60CC9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '340', 'DOCTOR', 'NC5337', 'UY, ROBERT M.D.', '60CC9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '459', 'DOCTOR', 'NON265', 'LUTANGCO,  RAINIER YU MD.', '50209182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '459', 'DOCTOR', 'NPQ314', 'LUTANGCO,  RAINIER YU MD.', '50209182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '459', 'DOCTOR', 'WBL482', 'LUTANGCO,  RAINIER YU MD.', '50209182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'TDJ877', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '340', 'DOCTOR', 'ADA6815', 'UY, ROBERT M.D.', '60CC9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '217', 'DOCTOR', 'ALA2249', 'ONG, DANIEL', '50239D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '390', 'DOCTOR', 'NHQ882', 'UY,  BILLY JAMES..M.D', '804E8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '384', 'DOCTOR', 'BDT958', 'SEE, MARY ANN', '40059582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '180', 'DOCTOR', 'AIA8104', 'LIM, VANESSA ROSETTE', '60C79382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '176', 'DOCTOR', 'AFA5375', 'LIM, ROSARIO TAN', 'E07B9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '595', 'DOCTOR', 'WKO939', 'CALALANG, EDITHA', '70BA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '595', 'DOCTOR', 'WRQ824', 'CALALANG, EDITHA', '70BA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '369', 'CONSULTANT', 'WKE578', 'CHAN-YU, NATY', 'B0089382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'ARA6015', 'CHUA, DAISY ', 'E0919382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '327', 'DOCTOR', 'ZFL538', 'LIM, ROSIE TY', '008A9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '417', 'VISITING DOCTOR', 'AIA2727', 'LIM, BENJAMIN', 'E05C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '419', 'VISITING DOCTOR', 'WTV596', 'LUA, DOROTHY LIM', '505F9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '419', 'VISITING DOCTOR', 'WHY979', 'LUA, DOROTHY LIM', '505F9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ZLA698', 'LIM, CRISTINO', '705B9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ZBD372', 'LIM, CRISTINO', '705B9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'TFQ835', 'LIM, CRISTINO', '705B9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'NTO910', 'LIM, CRISTINO', '705B9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'AAL8291', 'DY, JAMES CEO', '320C475D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '451', 'VISITING DOCTOR', 'USH968', 'PAGLINAWAN, SHERWIN ONG', '607C9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '451', 'VISITING DOCTOR', 'WNH270', 'PAGLINAWAN, SHERWIN ONG', '607C9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'POD525', 'AMBULANCE 1', '90DA9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'POM935', 'AMBULANCE 1', '90DA9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'POP772', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'PQN412', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'SKA289', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'WFC625', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'ZNF601', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'AAM3508', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'ECAR1', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '503', 'AMBULANCE 1', 'ECAR2', 'AMBULANCE 1', '90DA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '454', 'DOCTOR', 'XHR168', 'DY, WILLY CHUA M.D.', '20BC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '454', 'DOCTOR', 'ZFE310', 'DY, WILLY CHUA M.D.', '20BC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '454', 'DOCTOR', 'XHK168', 'DY, WILLY CHUA M.D.', '20BC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '259', 'DOCTOR', 'AAH2739', 'REYES, TOMMY', '90969382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'ZBP203', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '459', 'DOCTOR', 'ZGA744', 'LUTANGCO,  RAINIER YU MD.', '50209182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'NTO910', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'TFQ835', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ZBD372', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ZLA698', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'NSS 88', 'NGO, BENITO SING-SING', 'E27F455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'PUI700', 'NGO, BENITO SING-SING', 'E27F455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'UQV806', 'NGO, BENITO SING-SING', 'E27F455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'ZBY295', 'NGO, BENITO SING-SING', 'E27F455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'AQA7073', 'NGO, BENITO SING-SING', 'E27F455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'TDW328', 'LEE, WILLIAM', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'AEV717', 'LEE, WILLIAM', '129D475D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '325', 'DOCTOR', 'AHA4301', 'TING-TAN, CHERRYL Q.M.D.', '405B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'AHA4301', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '225', 'DOCTOR', 'UOQ757', 'ONG-DELA CRUZ, BERNICE', 'E0938F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'II3503', 'CHANG, ROBERT', '20989982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'AAI7431', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '174', 'DOCTOR', 'LLL323', 'LIM, LUCILLE', '60829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '433', 'VISITING DOCTOR', 'AAN4889', 'TING, DONATO SR.', '502E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '275', 'DOCTOR', 'AQA9583', 'SISON, MICHAEL', 'C06E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '483', 'VISITING CONSULTAN', 'VEL531', 'SEVERINO, RAMON CRUZ M.D.', 'C0F89482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '699', 'DOCTOR', 'ASA2194', 'TUAZON, JACQUELINE M.D.', '91956F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'AQA4554', 'VALLES, TOMAS', '80959C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '247', 'DOCTOR', 'WQU247', 'PUA-GO, VIRGINIA', '603F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '132', 'DOCTOR', 'RJ6478', 'LIM, JUNE TIU', '906C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '384', 'DOCTOR', 'ABF9634', 'SEE, MARY ANN', '40059582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'AAJ3565', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'AQA4554', 'VALLES, TOMAS', 'E266495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '462', 'DOCTOR', 'XLZ386', 'ANG, VICTORIO NGO M.D.', '20809182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '424', 'VISITING DOCTOR', 'THI206', 'REBOSA, ALBERT', '90AD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '424', 'VISITING DOCTOR', 'THI206', 'REBOSA, ALBERT', '90AD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '132', 'DOCTOR', 'ABD7868', 'LIM, JUNE TIU', '906C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '315', 'DOCTOR', 'AAO3192', 'TANG, BENJAMIN', '90289C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '463', 'DOCTOR', 'XKT134', 'CO. ARSENIO', 'A0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '463', 'DOCTOR', 'NIU519', 'CO. ARSENIO', 'A0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '464', 'VISITING CONSULTANT', 'AVA8323', 'VIUDEZ, EMILY M.', 'E0F69382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'ZFC722', 'SIA, KELLY', 'D2B9485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '430', 'VISITING DOCTOR', 'XLP568', 'TAN, IRENE PINEDA', 'E0019C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '361', 'DOCTOR', 'AJA6742', 'YEE, MARY TAN', 'C08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'DOCTOR', 'DQ9063', 'CHUA, ELEN', 'F00C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'ABG1609', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'AQA3791', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '35', 'DOCTOR', 'ZSB707', 'CAWAI, CATHERINA', '10019282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ACA1562', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'TFQ835', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'AHA3234', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'POQ674', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'VIP', 'NJO323', 'CHUA, ARTHUR TAN', '82F8435D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'VIP', 'TOP519', 'CHUA, ARTHUR TAN', '82F8435D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'VIP', 'UOO552', 'CHUA, ARTHUR TAN', '82F8435D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'TOT331', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '393', 'VISITING/DOCTOR', 'UWQ976', 'ANG, ARNEIL UY', 'A0F29482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'DOCTOR', 'ABD6158', 'CHUA, ELEN', 'F00C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'DFM 08', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'UYQ902', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'WPE683', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'XNB258', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'ZBB711', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'AIR740', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '187', 'DOCTOR', 'ZPX510', 'LO, JOVANNI', 'A07F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'NIP832', 'ANG, ANNE TAN', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'ABP2902', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '358', 'DOCTOR', 'AAX3113', 'YAP, JENNIFER SY M.D.', '30539182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '483', 'VISITING CONSULTAN', 'XRN360', 'SEVERINO, RAMON CRUZ M.D.', 'C0F89482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'XSR973', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '411', 'VISITING DOCTOR', 'POQ761', 'ESTABILLO, GRACE M.D.', '202C9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'HW4077', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '62', 'VIP', 'ZPA300', 'LU,ELAINE MARIEL ANGLUAN', 'A0619182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '62', 'VIP', 'ZBZ524', 'LU,ELAINE MARIEL ANGLUAN', 'A0619182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '63', 'VIP/AUDIT', 'ABF3394', 'BERNABE, LUCIANA', '40F79182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '43', 'DOCTOR', 'ABN3448', 'CHAN-LAO, JULIET', '50029A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '43', 'DOCTOR', 'TAO733', 'CHAN-LAO, JULIET', '50029A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '261', 'DOCTOR', 'ZND125', 'ROGANDO-TAN, LYNDA', 'F0ED8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'NIA743', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'NIM229', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'NOO833', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'PRW683', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'PUQ466', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'XRC896', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'ZAX869', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'ZFM953', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'VIP', 'ZJC469', 'LUGAY, SIXTO ', '3258475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '30', 'DOCTOR', 'TZO696', 'CHAN, CALIXTRO JR.', 'B0089582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '314', 'DOCTOR', 'ZDU681', 'TANBONLIONG, SEVERINO', '20A49882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'DQ9830', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'ABT9122', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '526', 'DOCTOR', 'MO5461', 'YUTIAO, MARY', 'C0F09282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '219', 'DOCTOR', 'YT5749', 'ONG, FLORENCIO', 'D0329582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'WIM891', 'DY, JAMESON GO', 'C07A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'WTM891', 'DY, JAMESON GO', 'C07A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '469', 'DOCTOR', 'ABD8753', 'LORENZO, EMILIO M.D.', 'F00F9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '469', 'DOCTOR', 'ZKP959', 'LORENZO, EMILIO M.D.', 'F00F9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'ABT5424', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WIQ711', 'CHEE-AH,  SANTIAGO', 'F201485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'NNI713', 'CHEE-AH,  SANTIAGO', '10599982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WIQ711', 'CHEE-AH,  SANTIAGO', '10599982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WNN636', 'CHEE-AH,  SANTIAGO', 'F201485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'XKD468', 'CHEE-AH,  SANTIAGO', 'F201485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'XKD468', 'CHEE-AH,  SANTIAGO', '10599982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '466', 'DOCTOR', 'ABQ8482', 'VILLANUEVA, WARREN MICHAEL LEE M.D.', '301F9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '74', 'DOCTOR', 'DR4610', 'CO, MARIJANE', '90909982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '63', 'DOCTOR', 'AQA5545', 'CHUA, MARY', '30418F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'ATT 09', 'TAN, ANTONIO', 'B0429782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '394', 'VISITING DOCTOR', 'UQV850', 'ANG- DE JOYA, MADELENE', 'A0309082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '65', 'VIP/ENGRNG DEPT.', 'NQN790', 'AGUDA, MARTIN JR. TABORA', 'F0189282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'VIP/PHARMACY HEAD', 'UKV860', 'CO, KAREN DY', '506E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'EF9323', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'ZPM311', 'CO, MICHELLE ANG', 'D08D9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'JCO668', 'CO, MICHELLE ANG', 'D08D9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'JCO333', 'CO, MICHELLE ANG', 'D08D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'ZGZ806', 'CO, MICHELLE ANG', 'D08D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '402', 'VISITING/DOCTOR', 'POJ149', 'CHIONG, NENITA  SOTTO', '50CB9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '351', 'DOCTOR', 'NEQ583', 'WONG, MENELINE L. M.D.', '70C39182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '472', 'VISITING DOCTOR', 'FJE385', 'BELTRAN, JENNY LIM', '50E28F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '26', 'DOCTOR', 'AHA9360', 'BRIOSO, EDNA FLORENCE', '50A79282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '530', 'VISITING DOCTOR', 'WCO588', 'SUN, CATHERINE GO', 'E0549482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '530', 'VISITING DOCTOR', 'NIR859', 'SUN, CATHERINE GO', 'E0549482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '59', 'DOCTOR', 'CN7342', 'CHUA, ERIC', '80DC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '638', 'ASS. VISITING', 'FJC857', 'SAHAGUN, MARIE JANICE AMANTE', '423E9C2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '478', 'ASS. ACTIVE DR.', 'WYO598', 'BANGAYAN-CARVAJAL, ROWENA NG.', '50D99282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '478', 'ASS. ACTIVE DR.', 'UQP403', 'BANGAYAN-CARVAJAL, ROWENA NG.', '50D99282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '477', 'ASS. VISITING DR.', 'TEH425', 'SEGARRA, YUZHEN GARCIA ', 'C06A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '477', 'ASS. VISITING DR.', 'TPL317', 'SEGARRA, YUZHEN GARCIA ', 'C06A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '526', 'DOCTOR', 'ABD8970', 'YUTIAO, MARY', 'C0F09282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '104', 'DOCTOR', 'DXBCBI', 'DY, TIMOTHY', '80039382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '480', 'ASS.-VISITING DR.', 'WOA270', 'SO, IMEE TIU', '70E98F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '484', 'VISITING CONSULTANT', 'ZHE525', 'REYES, RICHMOND CHENG', '105F9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '484', 'VISITING CONSULTANT', 'NMI898', 'REYES, RICHMOND CHENG', '105F9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '485', 'VISITING CONSULTANT', 'ADA9551', 'RENTILLO, TIFFANY IRISH E.', 'F0959482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'ABG3772', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '487', 'VISITING CONSULTANT', 'ZSF900', 'CRUZ, EMERITO EVANGELISTA', '40649282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '487', 'VISITING CONSULTANT', 'TFI123', 'CRUZ, EMERITO EVANGELISTA', '40649282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '488', 'VISITING CONSULTANT', 'TQD988', 'VILLEGAS, CHARINA VITUG', '00C79482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '488', 'VISITING CONSULTANT', 'AAP5573', 'VILLEGAS, CHARINA VITUG', '00C79482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '489', 'VISITING CONSULTANT', 'TIF872', 'HERRERA, CARLOS ANG', '300A9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '489', 'VISITING CONSULTANT', 'ZCM568', 'HERRERA, CARLOS ANG', '300A9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '489', 'VISITING CONSULTANT', 'ZRT533', 'HERRERA, CARLOS ANG', '300A9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '342', 'DOCTOR', 'PTK378', 'UY,VICENTE M.D.', '405E9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '342', 'DOCTOR', 'XME203', 'UY,VICENTE M.D.', '405E9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '142', 'DOCTOR', 'UBO560', 'LAO, RAMON', '10099C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '413', 'VISITING DOCTOR', 'ZKP223', 'JIM, JOSEPHINE', '10869182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '164', 'DOCTOR', 'MO9928', 'LIM, EDRICK', '70CD9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '197', 'DOCTOR', 'WDD852', 'MACALALAG-CRUZ, MYRA MICHELLE', 'E0AD9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '197', 'DOCTOR', 'ADA8519', 'MACALALAG-CRUZ, MYRA MICHELLE', 'E0AD9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '207', 'DOCTOR', 'ERL628', 'NGO, ERLEEN', 'F0A89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'DOCTOR', 'AAH7060', 'CHAN, ALFONSO', 'C07A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'DOCTOR', 'PIW175', 'CHAN, ALFONSO', 'C07A9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'WMO898', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NJI898', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '410', 'VISITING DOCTOR', 'RJ9987', 'CORTEZ, CHRISTOPHER', 'D0719282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '224', 'DOCTOR', 'AAH7060', 'ONG, CHAN FLORENCE', '70109582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '224', 'DOCTOR', 'PIW175', 'ONG, CHAN FLORENCE', '70109582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '387', 'DOCTOR', 'ULY410', 'SY, ROSALINA LIM', '40CE8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '92', 'DOCTOR', 'NK3014', 'DELOS SANTOS, CYMBELLY', '90B69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '248', 'DOCTOR', 'ABG3325', 'QUA, JOSEFINO', 'D0129482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'MO0081', 'SY, BENITO', 'E0339782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PEQ658', 'DINO, ANTONIO', 'D240445D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '491', 'VISITING CONSULTANT', 'WND137', 'FILARCA, RENE LUIS FAMORCA', '30A39982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '67', 'VIP', 'YV2098', 'CABALZA, IRENE FELIZARDO', '50F89282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'DOCTOR', 'WLO584', 'CHUA, REGINA', '00309482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '68', 'VIP', 'IJ9898', 'SANTOS EDMUND', '50149382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '494', 'ASS. VISITING DOCTOR', 'POQ368', 'TAGUD, JEFFERSON ROSALES', '80DF9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '128', 'DOCTOR', 'DS000094', 'GOTAUCO, CARMENCITA', '80DF9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '495', 'ASS. VISITING DOCTOR', 'NXI791', 'TE, PATRICIA SUN', '30DD9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '470', 'DOCTOR', 'TMY419', 'TAN, BENITO', '101A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '237', 'DOCTOR', 'YX7258', 'PASCUA, NESTOR..M.D.', 'B0B59182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'DOCTOR', 'CO6631', 'BAYSA-ONG, RUBY', '80219382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '218', 'DOCTOR', 'CO6631', 'ONG, EDWIN', 'C0B19282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '415', 'VISITING DOCTOR', 'ABL8977', 'KWONG, LUZVIMINDA', '30B89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '629', 'DOCTOR', 'LDN567', 'TAN, ALEJANDRO', '337EE92E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'DR1181', 'UY, EDDIE', '526B475D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'UNO991', 'HILARIO, ANTONIO', '100A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'ZCG798', 'CO, MANUEL', '30449082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'UOQ908', 'CO, MANUEL', '069CADCE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'VIP', 'PTI478', 'LAZARO, EMMANUEL RAMOS', '007E9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'ABP4600', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'UCZ763', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NJI898', 'GO, BASILIO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'WMO898', 'GO, BASILIO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '74', 'DOCTOR', 'NQB123', 'CO, MARIJANE', '90909982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '72', 'VIP', 'HPV333', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '72', 'VIP', 'PTK552', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '72', 'VIP', 'XYZ600', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '72', 'VIP', 'BEE755', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '72', 'VIP', 'NMW994', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '72', 'VIP', 'AAI5182', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'BSS109', 'SIA, KELLY HUN', 'F0DF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'BDX931', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'BSS109', 'SIA, KELLY', 'D2B9485D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '363', 'DOCTOR', 'DQ1495', 'YEO, MELLY', 'A0C99182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'VIP', 'TOT688', 'ANG, TIAK TOY', '90039582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '74', 'VIP', 'ZKT292', 'CRUZ, JAIME TAN', '40969B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '74', 'VIP', 'PHQ105', 'CRUZ, JAIME TAN', '40969B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'DOCTOR', 'XTJ117', 'DY, SOAT TONG', 'E0779182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'NLI814', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '498', 'ASSO.VISITING DR.', 'NWO208', 'CASTILLO ,LENNIE VILLOSO', 'D0149982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '498', 'ASSO.VISITING DR.', 'NFI186', 'CASTILLO ,LENNIE VILLOSO', 'D0149982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '75', 'BOARD OF DIR.', 'ZNV163', 'LEE,CHOU TIAM', 'F0DC9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '75', 'BOARD OF DIR.', 'NTI712', 'LEE,CHOU TIAM', 'F0DC9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '75', 'BOARD OF DIR.', 'PKI377', 'LEE,CHOU TIAM', 'F0DC9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '75', 'BOARD OF DIR.', 'AQA4559', 'LEE,CHOU TIAM', 'F0DC9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '403', 'DOCTOR', 'ABG8718', 'CHUA, DERRICK M.D.', 'F0199282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'XTM728', 'CHUAYING,  RAMON', '70F09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'ZFW240', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'AAL3332', 'CHUAYING,  RAMON', '70F09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '160', 'DOCTOR', 'YW2655', 'LEYRITANA, MARIVIC', '804D9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'IK1683', 'TIN HAY, EDUARDO M.D.', '10959282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '385', 'DOCTOR', 'AIA5995', 'CHUA, DAISY ', 'E0919382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'UIV658', 'SIA, KELLY', 'D2B9485D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'PKI934', 'SIA, KELLY', 'D2B9485D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '499', 'VISITING CONSULTANT', 'PMQ275', 'TAN-HERNANDEZ, CAROLINE CU', '800A9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '396', 'VISITING CONSULTANT', 'XRP922', 'CHAN, RENE CHUA', 'E0CD9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '396', 'CONSULTANT', 'XJF266', 'CHAN, RENE CHUA', 'E0CD9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '396', 'CONSULTANT', 'UTZ639', 'CHAN, RENE CHUA', 'E0CD9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '373', 'DOCTOR', '130110', 'SERAPIO, SERAFIN', 'D09F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '165', 'DOCTOR', 'CP1373', 'LIM, EDWARD', 'B0CF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'DOCTOR', 'YY0642', 'ANG, EVELYN', 'D0B69B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '10', 'DOCTOR', 'RK1656', 'ANG, EVELYN', 'D0B69B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '162', 'DOCTOR', 'NL1814', 'LI-YU, JULIE', '50179082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '262', 'DOCTOR', 'CP0773', 'RUALES, ALLAN B..M.D.', '30A59082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'DR9933', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'DR9933', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '500', 'CONSULTANT', 'TCF831', 'QUESING, MANUEL C.', '906F9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '500', 'CONSULTANT', 'XPM855', 'QUESING, MANUEL C.', '906F9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '500', 'CONSULTANT', 'ZCK608', 'QUESING, MANUEL C.', '906F9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'IL3949', 'CO, MANUEL', '30449082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '364', 'DOCTOR', 'ASA2345', 'YOUNG-LIM, YOLANDA', '40F99382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '342', 'DOCTOR', 'NIW873', 'UY,VICENTE M.D.', '405E9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '91', 'DOCTOR', 'VB3765', 'DELA CRUZ, ALVIN', 'E0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '570', 'VISITING/DOCTOR', 'ZEB657', 'TING LAO, EMMA..M.D.', '40E39882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '570', 'VISITING/DOCTOR', 'XCG890', 'TING LAO, EMMA..M.D.', '40E39882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '570', 'VISITING/DOCTOR', 'ZPP245', 'TING LAO, EMMA..M.D.', '40E39882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'VIP', 'YX6668', 'LIM, CRISTINO', '1264495D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'VB7161', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'VB7161', 'CHUA CARL JAMES', '50F59482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'ABG9725', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'VB1833', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '136', 'DOCTOR', 'WJV302', 'KING, FELISA', '200E9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'IK1959', 'CO, MICHELLE ANG', 'D08D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'ASA9712', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'ZCG721', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'ASA9712', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XGV690', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'PA8212', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'TQO933', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ZI1234', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'FAT 02', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'III 10', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'YU6838', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'CO6283', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '501', 'VISITING DOCTOR', 'ZEF640', 'LEE-RABO, JACLYN CHERYL KATE CHUA', '00299982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '501', 'VISITING DOCTOR', 'NOI978', 'LEE-RABO, JACLYN CHERYL KATE CHUA', '00299982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '501', 'VISITING DOCTOR', 'PQB138', 'LEE-RABO, JACLYN CHERYL KATE CHUA', '00299982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '77', 'VIP', 'BEH520', 'DY, NINO MICHAEL D.', '70A29882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '504', 'VISITING CONSULTANT', 'POE859', 'SEE, CHERILLE CHENG', '50BE9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '504', 'VISITING CONSULTANT', 'UMI158', 'SEE, CHERILLE CHENG', '50BE9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '504', 'VISITING CONSULTANT', 'ZRD235', 'SEE, CHERILLE CHENG', '50BE9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '504', 'VISITING CONSULTANT', 'PGI722', 'SEE, CHERILLE CHENG', '50BE9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'AAW2255', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '41', 'DOCTOR', 'IK5262', 'CHAN, REMEDIOS DEE', 'E08D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '457', 'VISITING CONSULTANT', 'XGL179', 'ONG KIAN KOC, BRYAN BERNARD UY, M.D.', '705B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'NZQ889', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'TLC000', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'XSR973', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'TNY882', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'XKD633', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'XNM876', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'WIC866', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '388', 'DOCTOR', 'AIR740', 'ANG-TIU, CHARLENE', 'D0578F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'ZKJ133', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'ABG9725', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '509', 'CONSULTANT', 'PQX665', 'UY, PETER PAUL LAO', 'B0709B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '181', 'DOCTOR', 'PQG521', 'LIM, ABRAHAM MARYJANE', 'F0E29282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '274', 'DOCTOR', 'DS2418', 'SIBULO, MELVIN', '008F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'XCY820', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '432', 'VISITING DOCTOR', 'MP4470', 'TAN-GAW, MA. LUISA', 'B0589A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '502', 'DOCTOR', 'ABG5228', 'YU, NELSON', 'A0FE9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '502', 'DOCTOR', 'POV191', 'YU, NELSON', 'A0FE9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '502', 'DOCTOR', 'AAM9848', 'YU, NELSON', 'A0FE9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '194', 'DOCTOR', 'NDE1708', 'LU-KOH, ROSIE SUE LUAN', 'C0B99A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'VIP', 'NQX138', 'LEE, ERIC SAI YAM', 'B0CA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'VIP', 'ZBU298', 'LEE, ERIC SAI YAM', 'B0CA9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'VC9340', 'CHUAYING,  RAMON', '70F09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'ZBY295', 'NGO, BENITO SING-SING', 'C0039A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'NSS 88', 'NGO, BENITO SING-SING', 'C0039A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'PUI700', 'NGO, BENITO SING-SING', 'C0039A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'UQV806', 'NGO, BENITO SING-SING', 'C0039A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '80', 'VIP', 'ALA3609', 'CHENG, JIMMY', 'A0F69C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '190', 'DOCTOR', 'IK5358', 'LOKIN, JOHNNY', '80FF8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '373', 'DOCTOR', 'ABE2029', 'SERAPIO, SERAFIN', 'D09F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'AQA7073', 'NGO, BENITO SING-SING', 'C0039A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'YZ5889', 'NGO, BENITO SING-SING', 'E27F455D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'YZ5889', 'NGO, BENITO SING-SING', 'C0039A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '560', 'DOCTOR', 'CP0841', 'ARQUILLA, MARLEEN', 'D0909782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'EG3153', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '18967', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '19169', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '23286', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'AAL8291', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'POD511', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TIM757', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'TZO693', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XJK988', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'ZHP439', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'AAL8291', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'AAL8291', 'DY, JAMES CEO', '50F19482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'AAL8291', 'DY, JAMES CEO', '10399982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', '19169', 'DY, JAMES CEO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'XTU765', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', 'HPV333', 'DY, FLORANTE', '2A5036CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', 'NMW994', 'DY, FLORANTE', '2A5036CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', 'PTK552', 'DY, FLORANTE', '2A5036CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', 'XYZ600', 'DY, FLORANTE', '2A5036CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WB9321', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WNW889', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'XNK395', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'ZOC 88', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'ZOC777', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'ZOC999', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'ZOC999', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', 'AAI5182', 'DY, FLORANTE', '2A5036CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', 'BEE755', 'DY, FLORANTE', '2A5036CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'AEV717', 'LEE, WILLIAM', '7A9927CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'TDW328', 'LEE, WILLIAM', '7A9927CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NIA743', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NIM229', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'NOO833', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'PRW683', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'PUQ466', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'XRC896', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZAX869', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZFM953', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'VIP', 'ZJC469', 'LUGAY, SIXTO ', '7A232ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'VIP', 'AAA 02', 'TAN, ANTONIO', '7AD637CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'VIP', 'ATT 09', 'TAN, ANTONIO', '7AD637CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'VIP', 'ATT888', 'TAN, ANTONIO', '7AD637CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'VIP', 'PMO523', 'TAN, ANTONIO', '7AD637CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'UCI263', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', '11110', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'FAT 02', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'III 10', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'KHT625', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'PA8212', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'ROS668', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'TQO933', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'YU6838', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'ZI1234', 'TAI. KUO', '8AC833CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', '11110', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'III 10', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'KHT625', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'PA8212', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ROS668', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'UCI263', 'TAI. KUO', 'B0639882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'YU6838', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ZI1234', 'TAI. KUO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'TQY249', 'LEE, \" SIMON\" TIONG SENG', 'DA8D36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'UKM407', 'LEE, \" SIMON\" TIONG SENG', 'DA8D36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'WGK892', 'LEE, \" SIMON\" TIONG SENG', 'DA8D36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'XBP783', 'LEE, \" SIMON\" TIONG SENG', 'DA8D36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PEQ658', 'DINO, ANTONIO', '1A4E36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'ASA9712', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'BEU812', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'JGS200', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO320', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO555', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'PQW246', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'PQW264', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'PQW264', 'SO, MICHAEL', 'C23B495D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'PQW264', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'TWQ550', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XGV690', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XGV690', 'SO, MICHAEL', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XHR535', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'XPL303', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'MSO888', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'VIP', 'ZMF520', 'SO, MICHAEL', 'EACA2ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'AAJ3565', 'VALLES, TOMAS', '1A5B37CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'AQA4554', 'VALLES, TOMAS', '1A5B37CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NE4713', 'VALLES, TOMAS', '1A5B37CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NOB800', 'VALLES, TOMAS', '1A5B37CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NSQ488', 'VALLES, TOMAS', '1A5B37CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'TOP605', 'VALLES, TOMAS', '1A5B37CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'TX9471', 'VALLES, TOMAS', '1A5B37CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'BSS109', 'SIA, KELLY', '2A7526CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'PKI934', 'SIA, KELLY', '2A7526CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'UIV658', 'SIA, KELLY', '2A7526CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'ZFC722', 'SIA, KELLY', '2A7526CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'POL883', 'CHIANPIAN, CARLOS JAO', '2AB730CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'TIZ736', 'CHIANPIAN, CARLOS JAO', '2AB730CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'TQX780', 'CHIANPIAN, CARLOS JAO', '2AB730CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'WOO828', 'CHIANPIAN, CARLOS JAO', '2AB730CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'ZTZ781', 'CHIANPIAN, CARLOS JAO', '2AB730CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'WRA828', 'LEE, HENRY', '6A252ECD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '22', 'VIP', 'ZSV882', 'LEE, HENRY', '6A252ECD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'DR1181', 'UY, EDDIE', '7A6B32CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PIY632', 'UY, EDDIE', '7A6B32CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PXI690', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'UOR623', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'WRW303', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'POM275', 'NGO, PETER', '0A7737CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'XTE811', 'NGO, PETER', '0A7737CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'WJS630', 'GOYOKPIN, BENITO', 'FAB431CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'WSC256', 'GOYOKPIN, BENITO', 'FAB431CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'XMU968', 'GOYOKPIN, BENITO', 'FAB431CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'ZBD938', 'GOYOKPIN, BENITO', '367BB6CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'WMG303', 'GOYOKPIN, BENITO', 'FAB431CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '492', 'ASS. VISITING DOCTOR', 'VEY289', 'FAMADICO, JUSTINE TAN', '40C39982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP-PRESIDENT', 'VD3916', 'DY, JAMES GO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP-PRESIDENT', 'POD511', 'DY, JAMES GO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'ZPB228', 'DY, JAMESON GO', 'C07A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'ZBA439', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'XBB802', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'WOG144', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'VD7670', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '392', 'DOCTOR', 'AEA3229', 'BACSAL, FE', 'B0E58F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'XTW884', 'SY, TONG HUE', '60769182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'DOCTOR', 'K0G349', 'GILBUENA, AMIEL', '30D89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'MQ0793', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'UIB125', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'DOCTOR', 'ASA2412', 'CALIMON, WILFRIDO', 'C05D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'DOCTOR', 'AHA4096', 'DY, LIGAYA', '40D79382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', '11110', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'FAT 02', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'III 10', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'KHT625', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'PA8212', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '11', 'VIP', 'ROS668', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'TQO933', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'UCI263', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'YU6838', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ZI1234', 'TAI. KUO', '8A682DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'TQY249', 'LEE, \" SIMON\" TIONG SENG', 'EACE2BCD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'UKM407', 'LEE, \" SIMON\" TIONG SENG', 'EACE2BCD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'WGK892', 'LEE, \" SIMON\" TIONG SENG', 'EACE2BCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'XBP783', 'LEE, \" SIMON\" TIONG SENG', 'EACE2BCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'VIP', 'NQX138', 'LEE, ERIC SAI YAM', '7AAD38CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'VIP', 'ZBU298', 'LEE, ERIC SAI YAM', '7AAD38CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '81', 'VIP-BOD', 'ZLV879', 'WONG, ALLAN BUTLOY', '40539982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '81', 'VIP-BOD', 'WBL 88', 'WONG, ALLAN BUTLOY', '40539982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '511', 'VISITING CONSULTANT', 'CRF417', 'LIM-WANG, MARY ANN  OALLESMA', '80989982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '74', 'DOCTOR', 'ABE4051', 'CO, MARIJANE', '90909982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '82', 'VIP', 'APA2366', 'EUCARIZA, NELSON D.', '10049C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '82', 'VIP', 'AAA8839', 'EUCARIZA, NELSON D.', '10049C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '551', 'VISITING DOCTOR', 'NXQ870', 'LIM, IRENE LIM', '10F18F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '551', 'VISITING DOCTOR', 'POB170', 'LIM, IRENE LIM', '10F18F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '487', 'VISITING CONSULTANT', 'VH3076', 'CRUZ, EMERITO EVANGELISTA', '40649282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'VIP/CCD', 'PUO891', 'MORANA, ALVIN', '202A9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NL7061', 'GO, BASILIO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NL7061', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '484', 'VISITING CONSULTANT', 'VH6917', 'REYES, RICHMOND CHENG', '105F9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '219', 'DOCTOR', 'ABR5949', 'ONG, FLORENCIO', 'D0329582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'ABR4235', 'WONG, STEPHEN M.D.', '709F9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '149', 'DOCTOR', 'VE1743', 'LEE, PAUL', 'F0A49C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'DT1846', 'DY,  BUNYOK', 'B01A9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '570', 'VISITING/DOCTOR', 'NCI5662', 'TING LAO, EMMA..M.D.', '40E39882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'DOCTOR', 'VI9485', 'COOPER-TAN, ANITA', '30A59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '551', 'VISITING DOCTOR', 'VC5911', 'LIM, IRENE LIM', '10F18F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '83', 'VIP-DIRECTOR', 'ZCT537', 'TAN, ANTHONY', '10DC9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'VH8373', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'UOQ908', 'CO, MANUEL', '3ADF36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'ZCG798', 'CO, MANUEL', '3ADF36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'CO6283', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'VW2855', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '38', 'DOCTOR', 'VJ0865', 'CHAN, AMALIA', 'B04D8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '93', 'DOCTOR', 'IM7594', 'DICO-GO,  JOYCE', '60C28F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'ABD8576', 'CO, CRISTINA ', 'B0E89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'VI7736', 'LEE, \" SIMON\" TIONG SENG', 'EACE2BCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '513', 'ASSO.VISITING/DOCTOR', 'NFI563', 'WIJANGCO, LOIDA MESA', '60269C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '466', 'DOCTOR', 'UAG356', 'VILLANUEVA, WARREN MICHAEL LEE M.D.', '301F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '168', 'DOCTOR', 'DS0801', 'LIM, FREDIRICK', 'A0849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '281', 'DOCTOR', 'ABG8981', 'SY, BENITO', 'E0339782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'AEV717', 'LEE, WILLIAM', 'C0379982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'TDW328', 'LEE, WILLIAM', 'C0379982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '432', 'VISITING DOCTOR', 'VH6348', 'TAN-GAW, MA. LUISA', 'B0589A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '36', 'DOCTOR', 'ZPW518', 'CHAN HUAN, FEDERICO', '10379082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'VI7736', 'LEE, \" SIMON\" TIONG SENG', '10FB9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '14', 'VIP', 'VI7736', 'LEE, \" SIMON\" TIONG SENG', 'DA8D36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '12', 'DOCTOR', 'VI7410', 'ANG, LYDIA', 'A0DD9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'GS2178', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'ABP4600', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'ABQ3068', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '424', 'VISITING DOCTOR', 'ZCJ199', 'REBOSA, ALBERT', '90AD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '424', 'VISITING DOCTOR', 'WC8588', 'REBOSA, ALBERT', '90AD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '626', 'DOCTOR', 'CP8395', 'UMALI, JOSELITO M.D.', '81D49A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'VIP', 'ZLG728', 'ONG, WILSON T.', '703F6B1D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'VIP', 'ZLG728', 'ONG, WILSON T.', '703F6B1D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UXQ936', 'ABAYA, ALBERT TORRES', 'EAF238CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UIM737', 'ABAYA, ALBERT TORRES', 'EAF238CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UDO608', 'ABAYA, ALBERT TORRES', 'EAF238CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'UVO509', 'ABAYA, ALBERT TORRES', 'EAF238CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'VIP', 'TOP519', 'CHUA, ARTHUR TAN', '7AAA2DCD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'VIP', 'UOO552', 'CHUA, ARTHUR TAN', '7AAA2DCD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'VIP', 'NJO323', 'CHUA, ARTHUR TAN', '7AAA2DCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'UNO991', 'HILARIO, ANTONIO', '2AED36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PIY632', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PXI690', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'UOR623', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'WRW303', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'DR1181', 'UY, EDDIE', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'XKD468', 'CHEE-AH,  SANTIAGO', '5A4837CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'WNN636', 'CHEE-AH,  SANTIAGO', '5A4837CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'NNI713', 'CHEE-AH,  SANTIAGO', '5A4837CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'XTM728', 'CHEE-AH,  SANTIAGO', '9A9C2FCD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'ZFW240', 'CHEE-AH,  SANTIAGO', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'AAL3332', 'CHEE-AH,  SANTIAGO', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'UQV806', 'NGO, BENITO SING-SING', '6A5A2ACD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'NSS 88', 'NGO, BENITO SING-SING', '6A5A2ACD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'PUI700', 'NGO, BENITO SING-SING', '6A5A2ACD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'VIP', 'ZBY295', 'NGO, BENITO SING-SING', '6A5A2ACD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '47', 'VIP', 'ZNW970', 'YANG, ROBERT', '4A622ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'TX3489', 'DY, JAMESON GO', 'FAD635CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'BEH520', 'DY, JAMESON GO', 'FAD635CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'ULC945', 'DY, JAMESON GO', 'FAD635CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'ZPB228', 'DY, JAMESON GO', 'FAD635CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '442', 'DOCTOR', 'VD2352', 'CHAN, JOHN NOEL UY', 'D0D59382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '442', 'DOCTOR', 'VE6125', 'CHAN, JOHN NOEL UY', 'D0D59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '442', 'DOCTOR', 'UNI948', 'CHAN, JOHN NOEL UY', 'D0D59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '514', 'VISITING DOCTOR', 'XHS528', 'LAPID-LIM , SUSAN IRENE ESPIRITU', 'A06B9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '515', 'DOCTOR', 'ASA6411', 'SANTOS, STEWART SANTOS', '50A49982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'VIP', 'UZO772', 'COTO, BERLING', 'D0099C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'ABP3464', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'DQ9570', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'VIP', 'XKU227', 'ONG, HILDA', '50789482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '58', 'VIP', 'WNR464', 'ONG, HILDA', '50789482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '81', 'DOCTOR', 'VG5949', 'CU, ERIC', '40D69882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '79', 'DOCTOR', 'NCH5215', 'COOPER-TAN, ANITA', '30A59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'DOCTOR', 'NOI566', 'CUASO, CHARLES', 'E0219582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'VIP', 'ZLG728', 'ONG, WILSON T.', '703F6B1D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'VIP', 'ZLG728', 'ONG, WILSON T.', '60C99282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'VIP', 'ZLG728', 'ONG, WILSON T.', '60C99282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '57', 'VIP', 'ZMD700', 'KO, MARIANO', 'E0EF9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'JJC787', 'CHIANPIAN, CARLOS JAO', 'D241465D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'JJC787', 'CHIANPIAN, CARLOS JAO', '2AB730CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '661', 'DOCTOR', '130110', 'TAN, LINDA', '916AB42D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'CQ0036', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'ZDP557', 'CASTILLO, TERESITA', 'D0BA9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'ZDP557', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '524', 'DOCTOR', 'ZLS496', 'LU-DIEGO, HELENA', '80B99282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '524', 'DOCTOR', 'CP8395', 'LU-DIEGO, HELENA', '80B99282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '195', 'DOCTOR', 'NDG6534', 'LU, LIM JUANITA', '704A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'ABT9548', 'SIA, KENDRICK GO', 'C0BA9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '516', 'DOCTOR', 'TIJ358', 'SUA, ALEX SEE', '10129182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '530', 'VISITING DOCTOR', 'VG5002', 'SUN, CATHERINE GO', 'E0549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'UNO99A', 'HILARIO, ANTONIO', '2AED36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'AAL3332', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'VC9340', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'XTM728', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'ZFW240', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'ZFW240', 'CHUAYING,  RAMON', '70F09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '20', 'VIP', 'PA7929', 'ABAYA, ALBERT TORRES', 'EAF238CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '519', 'DOCTOR', 'AAY1474', 'BOLIMA, SUSAN TSAI', 'B05B8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '518', 'VISITING DOCTOR', 'ZKE820', 'WEE, IRENE TAN', '40F69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '518', 'VISITING DOCTOR', 'ZDY850', 'WEE, IRENE TAN', '40F69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '518', 'VISITING DOCTOR', 'UQI876', 'WEE, IRENE TAN', '40F69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '483', 'VISITING CONSULTAN', 'UOE658', 'SEVERINO, RAMON CRUZ M.D.', 'C0F89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '483', 'VISITING CONSULTAN', 'NL3583', 'SEVERINO, RAMON CRUZ M.D.', 'C0F89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'NQB736', 'HILARIO, ANTONIO', '100A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'MMM125', 'HILARIO, ANTONIO', '100A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'MMM125', 'HILARIO, ANTONIO', '2AED36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '70', 'PCCAI DIRECTOR', 'NQB736', 'HILARIO, ANTONIO', '2AED36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '55', 'DOCTOR', 'VJ1969', 'CHUA, ARNEL', 'E0029082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '522', 'VISITING CONSULTANT', 'AAW7913', 'YAP, MINETTE TAN', '90C09382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '522', 'VISITING CONSULTANT', 'NOP725', 'YAP, MINETTE TAN', '90C09382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'DS6737', 'CO, MICHELLE ANG', 'D08D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'ABP2678', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '351', 'DOCTOR', 'ZCH163', 'WONG, MENELINE L. M.D.', '70C39182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'VIP-ITCONSULTANT', 'WKT461', 'NAGTALON JR, REUBEN THOMAS T.', 'C0069A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '327', 'DOCTOR', 'TPA546', 'LIM, ROSIE TY', '008A9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '180', 'DOCTOR', 'VQ1267', 'LIM, VANESSA ROSETTE', '60C79382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '523', 'VISITING CONSULTANT', 'VJ7594', 'LEE, ROWENA CO', 'B09B9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'ZBH347', 'EDNAVE, KERIMA ANN', '70A39382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '362', 'DOCTOR', 'RK0628', 'YEO, ANGELINE', 'E0819082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '377', 'DOCTOR', 'VO9511', 'CHAM, WILLIAM CHAN', '105D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '315', 'DOCTOR', 'VO6919', 'TANG, BENJAMIN', '90289C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'DOCTOR', 'TQH647', 'BANGAYAN, TEOFILO', '602C9582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'UEI732', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'VL9785', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'PUQ513', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'ACD4118', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'PA7861', 'PACHECO, JUANCHO', '3A2931CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', '130106', 'PACHECO, JUANCHO', '3A2931CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', '130106', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '525', 'ASS.- ACTIVE DOCTOR', 'URC883', 'YEO, CHARLESTON  TAN', '403E9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '525', 'ASS.- ACTIVE DOCTOR', 'RK0628', 'YEO, CHARLESTON  TAN', '403E9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'AAI7431', 'PACHECO, JUANCHO', '3A2931CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '431', 'VISITING DOCTOR', 'UKO818', 'TAN, ROBERT', '90CA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'EH1583', 'WONG, STEPHEN M.D.', '709F9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'XJX101', 'CHU, JUDY', '30E09282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'GFZ374', 'CHU, JUDY', '30E09282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'UBP868', 'CHU, JUDY', '30E09282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '51', 'DOCTOR', 'WQA544', 'CHU, JUDY', '30E09282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'UOU838', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'UOU838', 'GO, BASILIO', 'F0FE2181', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'ULQ759', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'DOCTOR', 'MR7312', 'CASTRO, JENNIFER', '508F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '245', 'VISITING DOCTOR', 'HW7590', 'TAN, EDEN', '705A9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '529', 'CONSULTANT', 'AEA8161', 'LU, CARLOS', 'C08E9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '529', 'CONSULTANT', 'ZTY511', 'LU, CARLOS', 'C08E9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '529', 'CONSULTANT', 'ARA2885', 'DY, CARLOS', '10B29282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '529', 'CONSULTANT', 'NUO171', 'DY, CARLOS', '10B29282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '529', 'CONSULTANT', 'PIC413', 'DY, CARLOS', '10B29282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '516', 'DOCTOR', 'AAY6039', 'SUA, ALEX SEE', '10129182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '396', 'CONSULTANT', 'WCO248', 'CHAN, RENE CHUA', 'E0CD9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '241', 'DOCTOR', 'HW8044', 'PERIQUET, ANTONIO', '50149182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'VG2343', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'XKV306', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'ZJD493', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'ZKV306', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'ZPW518', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '481', 'ASS.-VISITING DR.', 'VH6870', 'KING, LARRY SIA', '20929082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '286', 'DOCTOR', 'VQ9048', 'SY, PETER', '10419B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '286', 'DOCTOR', 'AAY8560', 'SY, PETER', '10419B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '286', 'DOCTOR', 'ADI5466', 'SY, PETER', '10419B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '226', 'DOCTOR', 'AOA9650', 'ONG-GO, MARY', 'D02B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'JOE66', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '471', 'VISITING DR.', 'NAE3564', 'CO, MICHELLE ANG', 'D08D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '481', 'ASS.-VISITING DR.', 'PUO253', 'KING, LARRY SIA', '20929082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '416', 'VISITING DOCTOR', 'EG9540', 'LEE, ANNABELLE', '60529282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '53', 'DOCTOR', 'ABT6072', 'UY, EDUARDO JALBUENA', '409B9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '53', 'DOCTOR', 'PXI690', 'UY, EDUARDO JALBUENA', '409B9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'DOCTOR', 'AAJ3899', 'BAGAY, JAMES', '004B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'DOCTOR', 'AAJ3899', 'BAGAY, MELISSA', '90ED9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'ABT6072', 'UY, EDUARDO JALBUENA', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'PIY632', 'UY, EDUARDO JALBUENA', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'PXI690', 'UY, EDUARDO JALBUENA', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'UOR623', 'UY, EDUARDO JALBUENA', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'WOR623', 'UY, EDUARDO JALBUENA', '7A6B32CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '234', 'DOCTOR', 'PLO987', 'PARDILLO, ROSITA', '20E49182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '286', 'DOCTOR', 'VC8626', 'SY, PETER', '10419B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'NLQ513', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '537', 'DOCTOR', 'DS8290', 'ACOSTA, LUTHGARDO', 'B0F69382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'VIP', 'ZLT866', 'CABERO, CAMILO ATTY.', '80429C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'VIP', 'AAK7288', 'CABERO, CAMILO ATTY.', '80429C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '196', 'DOCTOR', 'ZPN941', 'MACALALAG, EUFEMIO MIICHAEL', '30639982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '531', 'VISITING/SENIOR MD.', 'TQY799', 'YU, EUVIGILDO PERALTA', 'B06C9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '328', 'DOCTOR', 'ABP6247', 'TONGSON, LUINIO S..M.D', 'F0D19982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'AAH9715', 'MALILAY, ZENY', 'F0C59382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'BDV230', 'MALILAY, ZENY', 'F0C59382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'RNZ111', 'MALILAY, ZENY', 'F0C59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'TPZ888', 'MALILAY, ZENY', 'F0C59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'ZAF220', 'MALILAY, ZENY', 'F0C59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'ZTL993', 'MALILAY, ZENY', 'F0C59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP', '561259', 'DY, FLORANTE', 'C0459382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '533', 'CONSULTANT', 'TRO606', 'CHUA, ANTONIO HAO', '00B39282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '533', 'CONSULTANT', 'UPK522', 'CHUA, ANTONIO HAO', '00B39282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '533', 'CONSULTANT', 'XPA860', 'CHUA, ANTONIO HAO', '00B39282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '533', 'CONSULTANT', 'TDI943', 'CHUA, ANTONIO HAO', '00B39282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '533', 'CONSULTANT', 'TOD672', 'CHUA, ANTONIO HAO', '00B39282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'ZTL993', 'MALILAY, RAY', 'B0BA9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'RNZ111', 'MALILAY, RAY', 'B0BA9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'ZAF220', 'MALILAY, RAY', 'B0BA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'TPZ888', 'MALILAY, RAY', 'B0BA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'BDV230', 'MALILAY, RAY', 'B0BA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'YF8117', 'MALILAY, RAY', 'B0BA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'AAH9715', 'MALILAY, RAY', 'B0BA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'TSQ874', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'JOE66', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '9', 'VIP', 'ABF6471', 'CASTANEDA, JOSE JR', 'E0379782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '8', 'VIP', 'ABF6471', 'CASTANEDA, JOSE JR', '2A8327CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'VO4133', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '437', 'VISITING DOCTOR', 'PQC176', 'YAP, ELEANOR MAGPAYO', 'A05A9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '179', 'DOCTOR', 'VU4396', 'LIM, TIONG SAM', '00549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '226', 'DOCTOR', 'UIB336', 'ONG-GO, MARY', 'D02B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '319', 'DOCTOR', 'NAG3767', 'TAURO-SHIA, VICKY  M.D.', 'D08E9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '437', 'VISITING DOCTOR', 'VR7366', 'YAP, ELEANOR MAGPAYO', 'A05A9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'VIP (FCGCCI DIR.)', 'XTC377', 'SIYTIAP, JONATHAN BRYAN SY', '30E09782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'VIP (FCGCCI DIR.)', 'ZCW178', 'SIYTIAP, JONATHAN BRYAN SY', '30E09782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'VIP (FCGCCI DIR.)', 'TAI405', 'SIYTIAP, JONATHAN BRYAN SY', '30E09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '87', 'VIP/CHINESE CEMETERY', 'TGO633', 'CHANG, HENRY BEGUAS', 'C06B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '277', 'DOCTOR', 'ZJW811', 'GO, ELIZABETH SIY', '80E48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'WHD910', 'BARZA, GIORGIO', '10929282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'THH549', 'BARZA, GIORGIO', '10929282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'WSW630', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'XBX967', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'TLR623', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'XTK198', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'PIO915', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'BDV318', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'WC2550', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'ATA6181', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '515', 'DOCTOR', 'IO6909', 'SANTOS, STEWART SANTOS', '50A49982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'DV0242', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '535', 'CONSULTANT', 'TOG518', 'ALBA, WILLY SEE', '00CC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'VIP', 'NQB786', 'DY, SIOTKENG GO', '10759882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '88', 'VIP', 'XTU765', 'DY, SIOTKENG GO', '10759882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'VIP', 'VR8321', 'CABERO, CAMILO ATTY.', '80429C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'DOCTOR', 'XRP781', 'CABOTAJE, BIENVENIDO', 'E0B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'VIP', 'WRK123', 'DY, KATHLEEN G.', 'B0589B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'VIP', 'ZEB601', 'DY, KATHLEEN G.', 'B0589B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'VIP', 'URQ571', 'DY, KATHLEEN G.', 'B0589B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'VIP', 'AAK1625', 'DY, KATHLEEN G.', 'B0589B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'VIP', 'AAN2503', 'LAW, SHARON D.', 'D01E9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'VIP', 'NIN505', 'LAW, SHARON D.', 'D01E9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'VIP', 'VU4276', 'LAW, SHARON D.', 'D01E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '89', 'VIP', 'PFO721', 'LAW, SHARON D.', 'D01E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'DOCTOR', 'NAF1568', 'DEL MUNDO, SANDRA', '60F69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '536', 'CONSULTANT', 'VU6054', 'REYES-ADDATU, ALMA ONG', 'C0D89182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '319', 'DOCTOR', 'NAH5706', 'TAURO-SHIA, VICKY  M.D.', 'D08E9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'RK5382', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '273', 'DOCTOR', 'RK5382', 'SIA-LIM, MARY ROSALINDA', 'B0E49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'NN7220', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'VC8592', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '67', 'VIP', 'ABT7469', 'CABALZA, IRENE FELIZARDO', '50F89282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '91', 'VIP', 'ZAV688', 'CO, JENNIFER DY', '80689A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '91', 'VIP', 'WSI663', 'CO, JENNIFER DY', '80689A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'EH1743', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '538', 'CONSULTANT', 'AOA8014', 'MACEDA, VICTOR J.', '90DD8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '538', 'CONSULTANT', 'UNO369', 'MACEDA, VICTOR J.', '90DD8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '538', 'CONSULTANT', 'XPZ652', 'MACEDA, VICTOR J.', '90DD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '538', 'CONSULTANT', 'UTZ729', 'MACEDA, VICTOR J.', '90DD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '538', 'CONSULTANT', 'XMS715', 'MACEDA, VICTOR J.', '90DD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'DFM 08', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'VY1073', 'ALEGRE, EMELDA', '30629882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '475', 'ASS,VISITING', 'TNQ241', 'AREJOLA-TAN,  ALYCE GAIL NG.', '405E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '535', 'CONSULTANT', 'VV5002', 'ALBA, WILLY SEE', '00CC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'VV5002', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '68', 'DOCTOR', 'VX7614', 'CO, GEORGE JR.', 'B0BD8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '292', 'DOCTOR', 'VD4019', 'TAN, PASCUAL MYRA', 'E0C79B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '393', 'VISITING/DOCTOR', 'AAN4808', 'ANG, ARNEIL UY', 'A0F29482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'IK6127', 'BAYDID-CRUZ, ZOLINA', '50C78F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '289', 'DOCTOR', 'ADQ8231', 'FERNANDO, VICTORIA SY', '90B38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'VY1073', 'ALEGRE, NATALIO', '408B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '472', 'VISITING DOCTOR', 'VU0655', 'BELTRAN, JENNY LIM', '50E28F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '401', 'VISITING CONSULTANT', 'POJ149', 'CHIONG, DOMINADOR', '40769082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '401', 'VISITING CONSULTANT', 'THT988', 'CHIONG, DOMINADOR', '40769082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '401', 'VISITING CONSULTANT', 'ULW926', 'CHIONG, DOMINADOR', '40769082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '418', 'VISITING DOCTOR', 'UTV585', 'LIM, GOLDEE', 'C02A9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'VQ9101', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'VQ9101', 'PACHECO, JUANCHO', '3A2931CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ULO639', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'POS853', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'VU2930', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'HBE844', 'LO, VIRGILIO', 'D06F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '39', 'VIP/HRD', 'DAE3429', 'MENDOZA, GLECY ', 'D0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '41', 'DOCTOR', 'NBY3671', 'CHAN, REMEDIOS DEE', 'E08D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '205', 'CONSULTANT', 'TJI821', 'NAVARRO, BERNARDITA', '60C79182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '205', 'CONSULTANT', 'KO0056', 'NAVARRO, BERNARDITA', '60C79182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '205', 'CONSULTANT', 'KO0086', 'NAVARRO, BERNARDITA', '60C79182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '539', 'VISITING CONSULTANT', 'VI5890', 'ARCINAS, RODERICK PO', 'D0D19082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '453', 'DOCTOR', 'OOV888', 'FERNANDO, BERLIN YU', '202C9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'VY7378', 'CHUA CARL JAMES', '50F59482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'VY7378', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '169', 'DOCTOR', 'NN6493', 'LIM, GEORGE', '30049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'DOCTOR', 'VZ4362', 'CUASO, CHARLES', 'E0219582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'VXX898', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'HW8763', 'CO, MANUEL', '3ADF36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'HW8763', 'CO, MANUEL', '30449082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '24', 'DOCTOR', 'AAP1602', 'BOLANOS, MARTHA', '30729082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'WKW703', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'XJF835', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'XTG498', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '514', 'VISITING DOCTOR', 'ABG8972', 'LAPID-LIM , SUSAN IRENE ESPIRITU', 'A06B9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '212', 'DOCTOR', 'WB4897', 'OCAMPO, MARVIN', '205F9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '139', 'DOCTOR', 'NDE1708', 'KOH, ABNER', '20539782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '542', 'VISITING DOCTOR', 'IL0772', 'BISNAR , CARLO CABREIRA', '807B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '542', 'VISITING DOCTOR', 'AFA5977', 'BISNAR , CARLO CABREIRA', '807B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '542', 'VISITING DOCTOR', 'PXO456', 'BISNAR , CARLO CABREIRA', '807B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '423', 'VISITING DOCTOR', 'ROA911', 'QUE, JOCELYN', '005D9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', '130106', 'BELEN, THELMA', '80209282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '374', 'DOCTOR', 'ROA660', 'ANGTUACO, JAMES', '40B89082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'XTG498', 'DY, JAMESON GO', 'C07A9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'XTG498', 'DY, JAMESON GO', 'FAD635CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'DOCTOR', 'BOA787', 'CHUA, PERLEN TE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'DOCTOR', 'DV5145', 'CHUA, PERLEN TE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '222', 'DOCTOR', 'NAZ9158', 'ONG, ROSA', 'A05D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'DOCTOR', 'WA9583', 'GO, ERLINDO JOSE..M.D', '80449182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '119', 'DOCTOR', 'WA9583', 'GO, IRISH TU', '609D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '413', 'VISITING DOCTOR', 'VU9633', 'JIM, JOSEPHINE', '10869182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '417', 'VISITING DOCTOR', 'WA9800', 'LIM, BENJAMIN', 'E05C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '417', 'VISITING DOCTOR', 'PKI594', 'LIM, BENJAMIN', 'E05C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '201', 'CONSULTANT', 'COH997', 'MALILAY, RAY', 'B0BA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'DOCTOR', 'TQF767', 'BAGAY, JAMES', '004B9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'DOCTOR', 'TQF767', 'BAGAY, MELISSA', '90ED9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '532', 'CONSULTANT', 'COH997', 'MALILAY, ZENY', 'F0C59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '259', 'DOCTOR', 'BOE397', 'REYES, TOMMY', '90969382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'AON666', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'AON666', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'ZPF770', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '545', 'CONSULTANT', 'PXQ358', 'LIU, HUI SING', '90E79482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '545', 'CONSULTANT', 'WOK244', 'LIU, HUI SING', '90E79482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '545', 'CONSULTANT', 'OW7966', 'LIU, HUI SING', '90E79482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'NEI537', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'TRO024', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'VG7317', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'WTP344', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'XPP316', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'ZGU110', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'UOB739', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '405', 'DOCTOR', 'HEN168', 'CHUA, LISA SALUD', 'B0FC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'XTY203', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '462', 'DOCTOR', 'TMI175', 'ANG, VICTORIO NGO M.D.', '20809182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '269', 'DOCTOR', 'AO1563', 'SIA-TAN, JOSEPH', 'E0638F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'VW1628', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', '429479', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '356', 'DOCTOR', 'NAZ8818', 'YAP, ELIZABETH CO M.D.', '600B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '638', 'ASS. VISITING', 'SMM777', 'SAHAGUN, MARIE JANICE AMANTE', '423E9C2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '393', 'VISITING/DOCTOR', 'MS1903', 'ANG, ARNEIL UY', 'A0F29482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'WYI424', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '547', 'DOCTOR', 'NBQ315', 'SHIA, JOHNNY TORRES M.D.', '20489182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '547', 'DOCTOR', 'NAH5706', 'SHIA, JOHNNY TORRES M.D.', '20489182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '547', 'DOCTOR', 'NAG3767', 'SHIA, JOHNNY TORRES M.D.', '20489182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'BDX931', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'BDZ261', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'BEC285', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'PYO107', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'RFC714', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'WEY752', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'XLP206', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'XNL278', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'XPU699', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'ZKJ133', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '496', 'AMBULANCE', 'ALA7979', 'AMBULANCE 3', '00319482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '433', 'VISITING DOCTOR', 'WA8422', 'TING, DONATO SR.', '502E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '549', 'CONSULTANT', 'HBE844', 'LO, PATRICIA GRACE SANDICO', 'D0378F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '549', 'CONSULTANT', 'RCU366', 'LO, PATRICIA GRACE SANDICO', 'D0378F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', 'A1Z589', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'A1T256', 'TIN HAY, EDUARDO M.D.', '10DD9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '610', 'DOCTOR', 'A1T256', 'TAN-TINHAY, LORA MAY', 'C273632E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '193', 'DOCTOR', 'EOA926', 'LU, PETERSON', '70A39482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '137', 'DOCTOR', 'HW8807', 'KOA, LEE ALEX', '805E9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '999', 'DOCTOR', 'TEST111', 'ISA', '7B697F21', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '159', 'DOCTOR', 'GS4320', 'LETRAN, JASON', 'B0599482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '552', 'VISITING DOCTOR', 'NOL105', 'LIQUETE DEPANO, CHERI ANN SY', '60FA9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '552', 'VISITING DOCTOR', 'WNI991', 'LIQUETE DEPANO, CHERI ANN SY', '60FA9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '101', 'DOCTOR', 'VN4223', 'DY, JUN', '00869182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '550', 'VISITING DOCTOR', 'BOY619', 'ONG, CATHERINE JOIE', 'D0249382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '216', 'DOCTOR', 'BOY619', 'ONG, BILSON', '70B89382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '31', 'DOCTOR', 'ABT9122', 'CASTILLO, TERESITA', 'D0BA9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '554', 'VISITING DOCTOR', 'II8957', 'SALVADOR, SUSANNAH', 'D01D9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'ZD9125', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '145', 'DOCTOR', 'A1P907', 'LEE, CRISTINA', '50649A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'ZD9125', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'A1W703', 'CHUA CARL JAMES', '50F59482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '555', 'DOCTOR', 'TMQ743', 'LIO,JOHANNA MARIE SARIAL', '103E9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '556', 'CONSULTANT', 'NXQ870', 'LIM, MICHAEL LOUIE ONG', 'D0BE9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '556', 'CONSULTANT', 'VC5911', 'LIM, MICHAEL LOUIE ONG', 'D0BE9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '556', 'CONSULTANT', 'PQJ188', 'LIM, MICHAEL LOUIE ONG', 'D0BE9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'ZJS442', 'PEREZ, MELCO', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '426', 'VISITING CONSULTANT', 'PHQ285', 'SALVADOR, KELLY', '90A39282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'JHN 88', 'GABRIEL, MELCHOR', '50E59182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'OW8883', 'GO, BASILIO', 'F0559882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'DOCTOR', 'B1M223', 'BAGAY, JAMES', '004B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'DOCTOR', 'ZSB929', 'BAGAY, JAMES', '004B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'DOCTOR', 'B1M223', 'BAGAY, MELISSA', '90ED9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'DOCTOR', 'ZKS805', 'BAGAY, JAMES', '004B9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '18', 'DOCTOR', 'ZKS805', 'BAGAY, MELISSA', '90ED9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'C0P619', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '33', 'DOCTOR', 'MR7312', 'CASTRO, EUGENE', '60349182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '68', 'DOCTOR', 'ZER722', 'CO, GEORGE JR.', 'B0BD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '68', 'DOCTOR', 'NGO939', 'CO, GEORGE JR.', 'B0BD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '68', 'DOCTOR', 'ZKU405', 'CO, GEORGE JR.', 'B0BD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '463', 'DOCTOR', 'ZJN976', 'CO. ARSENIO', 'A0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'C0P619', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '557', 'VISITING CONSULTANT', 'ABH2064', 'CLEMENTE, CHERIE GALLA', '10209082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'COP619', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '360', 'DOCTOR', 'VU0398', 'ZAMORA, MA. SOCORRO', 'D0CD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '359', 'DOCTOR', 'TQJ175', 'YARISANTOS, CHERRI RACHEL', 'E0279082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'AAK5262', 'CUNANAN, SAMMY', '60329282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'DOCTOR', 'EOJ817', 'CUNANAN, SAMMY', '60329282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '26', 'DOCTOR', 'EOJ786', 'BRIOSO, EDNA FLORENCE', '50A79282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '161', 'DOCTOR', 'A2V538', 'LEYSA, ANGELICA', '20AE9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '158', 'DOCTOR', 'GS4320', 'LETRAN, ELEANOR', '60BA9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '158', 'DOCTOR', 'PA8406', 'LETRAN, ELEANOR', '60BA9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '159', 'DOCTOR', 'PA8406', 'LETRAN, JASON', 'B0599482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '48', 'DOCTOR', '130107', 'CHEU, GEORGE', '505D9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'A2O184', 'PACHECO, JUANCHO', '60D09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '344', 'DOCTOR', 'DOK206', 'VERA CRUZ, MARIVIC & RAFAEL..M.D', '20D09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'ZTS346', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'ACA7155', 'NGO, PETER', '0A7737CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '465', 'DOCTOR', 'AIR740', 'ANG, BRIAN CHRISTOPHER', '70809282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'AIR740', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '221', 'DOCTOR', 'B1I293', 'ONG, REMEDIOS', '804B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'WPO394', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'DT1846', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'A3S019', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'AZX135', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'ABR4235', 'WONG, STEPHEN M.D.', '205C9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'EH1583', 'WONG, STEPHEN M.D.', '205C9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'ZHM276', 'WONG, STEPHEN M.D.', '205C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '352', 'DOCTOR', 'ABR4235', 'WONG, JENNIFER M.D.', '205C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '352', 'DOCTOR', 'DA96497', 'WONG, JENNIFER M.D.', '205C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '352', 'DOCTOR', 'EH1583', 'WONG, JENNIFER M.D.', '205C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'ZBP896', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'ZPB896', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '562', 'CONSULTANT', 'AAQ3355', 'ISMAEL,ALBERT ESTRADA', '90769882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'B1U045', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '207', 'DOCTOR', 'EOR015', 'NGO, ERLEEN', 'F0A89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'DOCTOR', 'EOL318', 'GARCIA JR, JOSE', '80E69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '661', 'DOCTOR', 'ABE1601', 'TAN, LINDA', '916AB42D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'DOCTOR', 'EOL724', 'CALIMON, WILFRIDO', 'C05D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'DR2175', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '443', 'VISITING DOCTOR', 'EOP953', 'TIU, NANNETH T.', 'E0A59282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'NP6537', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '271', 'DOCTOR', 'ABT9548', 'SIA, MELISSA CO', '10049282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'O0A403', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '556', 'CONSULTANT', 'B1M871', 'LIM, MICHAEL LOUIE ONG', 'D0BE9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '390', 'DOCTOR', 'NCL3473', 'UY,  BILLY JAMES..M.D', '804E8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '307', 'DOCTOR', 'AHA3553', 'TAN, PATRICIA', 'B0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '695', 'DOCTOR', 'NBI8082', 'NGO, ROBERTO', 'B3D53B2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '695', 'DOCTOR', 'WRF136', 'NGO, ROBERTO', 'B3D53B2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '695', 'DOCTOR', 'ZPB538', 'NGO, ROBERTO', 'B3D53B2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '94', 'VIP', 'VE2564', 'XU, HAIGANG', 'B0D49A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '94', 'VIP', 'WQS220', 'XU, HAIGANG', 'B0D49A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'VIP-ITCONSULTANT', 'TTN880', 'NAGTALON JR, REUBEN THOMAS T.', 'C0069A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '564', 'VISITING DOCTOR', 'ZDN897', 'HIZON, IAN BENJAMIN M.D.', 'A0719C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'WRI882', 'VIRGILIO SIA', 'C0239982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'ZNK140', 'VIRGILIO SIA', 'C0239982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'NQG263', 'VIRGILIO SIA', 'C0239982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'PA8182', 'VIRGILIO SIA', 'C0239982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '515', 'DOCTOR', 'AA2308', 'SANTOS, STEWART SANTOS', '50A49982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', '18967', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', '19169', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', '23286', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'AAL8291', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'POD511', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'TIM757', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'TZO693', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'XJK988', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'XTU765', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'ZHP439', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'AAL8291', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'BEH520', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'PRC155', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'VCD820', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'AAL8291', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'BEH520', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'XTG498', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '49', 'DOCTOR', 'EOM644', 'CHAN, ANABELLE,CHO', '20779282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'DOCTOR', 'OX2426', 'CO-YAP, BETTY', '803D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'DOCTOR', 'OX2426', 'CO-YAP, BETTY', '803D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '566', 'DOCTOR', 'WRF551', 'MADERA, JENNIFER', '40919782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '566', 'DOCTOR', 'XMR353', 'MADERA, JENNIFER', '40919782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '566', 'DOCTOR', 'XSN663', 'MADERA, JENNIFER', '40919782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '566', 'DOCTOR', 'ZAM595', 'MADERA, JENNIFER', '40919782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '566', 'DOCTOR', 'ZRG885', 'MADERA, JENNIFER', '40919782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '171', 'DOCTOR', 'VW2855', 'LIM, JOHN', '20BB9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '695', 'DOCTOR', 'UDI143', 'NGO, ROBERTO', 'B3D53B2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '310', 'DOCTOR', 'NAJ4775', 'TAN, STEPHANY', 'A0149982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '568', 'VISITING DOCTOR', 'XMF693', 'SOLIVEN, ALBERT  UYTANLET', '40EB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '568', 'VISITING DOCTOR', 'ABS2062', 'SOLIVEN, ALBERT  UYTANLET', '40EB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '568', 'VISITING DOCTOR', 'TNQ308', 'SOLIVEN, ALBERT  UYTANLET', '40EB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '567', 'VISITING CONSULTANT', 'OX6528', 'CHUA, ANNETTE GISELLE YU', 'F0739882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'A1Q731', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '257', 'DOCTOR', 'BOE360', 'RECTO, MERLE', '207D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '571', 'VISITING DOCTOR', 'BO0919', 'SERRANO, NOREEN CONEJOS', 'B0BA9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'TEST', '12345  ', 'LC', 'C678B3CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'VI8211', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'C1B043', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'BOARD MEMBER', 'XMW915', 'ESCOLAR,ANTHONY', '20F79982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '77', 'CCD COLLECTOR', 'CCD1', 'CCD MOTOR SERVICE 1', 'C0039C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '87', 'CCD COLLECTOR', 'CCD2', 'CCD MOTOR SERVICE 2', '40FB9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '573', 'VISITING CONSULTANT', 'TIJ325', 'SO, MARK JOSEF SY', '50E59882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '572', 'VISITING SURGERY STAFF', 'ZPV792', 'REYES-LAO HOWARD IBANEZ', '60739882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '572', 'VISITING SURGERY STAFF', 'XHA865', 'REYES-LAO HOWARD IBANEZ', '60739882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'TEST', '12345', 'SAMPLE2', 'F6BAB9CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'ZCJ107', 'SY, TONG HUE', '60769182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '574', 'DOCTOR', 'AAI7286', 'DY, ANDERSON', '90DB9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '574', 'DOCTOR', 'ARA4903', 'DY, ANDERSON', '90DB9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '574', 'DOCTOR', 'ASA7646', 'DY, ANDERSON', '90DB9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '574', 'DOCTOR', 'VI5578', 'DY, ANDERSON', '90DB9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '574', 'DOCTOR', 'YB0971', 'DY, ANDERSON', '90DB9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '574', 'DOCTOR', 'ZDB608', 'DY, ANDERSON', '90DB9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '575', 'DOCTOR', 'ZKK123', 'FAUSTO CHRISTINE GAITE', 'D0729882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '576', 'ANESTHESIOLOGY', 'AAA8184', 'INOCENCIO FLORENCE DUQUEZA', '00B79782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '577', 'PEDIATRICS', 'UQN770', 'FERNANDEZ, ALMA ELUMBA', 'F0E09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '578', 'DOCTOR', 'VG9578', 'SO, EDISON', '801C9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '579', 'VISITING STAFF', 'UEL767', 'ALEGRE JAN CARLO YU', 'C0C49882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '579', 'VISITING STAFF', 'NIY126', 'ALEGRE JAN CARLO YU', 'C0C49882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '579', 'VISITING STAFF', 'VI7927', 'ALEGRE JAN CARLO YU', 'C0C49882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '580', 'PEDIATRICS', 'DR8517', 'PEREZ MARY ANNE MANGALILI', 'B08F9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '236', 'DOCTOR', 'CS6030', 'PARRENO, FERNANDO', 'D0E49482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '236', 'DOCTOR', 'WCX137', 'PARRENO, FERNANDO', 'D0E49482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '578', 'DOCTOR', 'VG9578', 'SO, EDISON', '801C9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'VIP', 'WKM936', 'KO, MARIANO', '80D09C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'VIP', 'A3U961', 'KO, MARIANO', '80D09C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'VIP', 'ZMD700', 'KO, MARIANO', '80D09C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'VIP', 'CS2479', 'KO, MARIANO', '80D09C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'VIP', 'A3U961', 'KO, MARIANO', '80D09C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '591', 'DOCTOR', 'VX7614', 'CO, CHUA-CHIONG VALENTINA', '709C9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '101', 'PCCAI/FCGCCI B.O.D.', 'GAJ 88', 'GO, STALEY', '80559B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '325', 'DOCTOR', 'ABQ3068', 'TING-TAN, CHERRYL Q.M.D.', '405B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '325', 'DOCTOR', 'A5D535', 'TING-TAN, CHERRYL Q.M.D.', '405B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '582', 'DOCTOR/ANEST', 'NII726', 'LOO TIAN, ZIEGFRIED LIM', '60039D82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '581', 'DOCTOR/ENDOCRINOLOGY', 'VK0913', 'JOVEN,MARK HENRY YANG CHUA', 'C0D39882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'E0V132', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'E0V132', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '73', 'VIP', 'E0R025', 'ANG, TIAK TOY', '90039582', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'B.DIRECTOR', 'MT1829', 'ONG,NELSON,CUA', 'A0469A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'B.DIRECTOR', 'VDY381', 'ONG,NELSON,CUA', 'A0469A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '99', 'B.DIRECTOR', 'TON622', 'KO,ALFREDO,CHING SIN', 'D0179B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '104', 'FCGCCI/DIRECTOR', 'ZHP752', 'LAGAMAYO, GREGORIO', 'C0E89982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '104', 'FCGCCI/DIRECTOR', '130106', 'LAGAMAYO, GREGORIO', 'C0E89982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'FCGCCI/DIRECTOR', 'ZGD388', 'TAGLE , ANA GOON', 'B0DB9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'FCGCCI/DIRECTOR', 'UIR883', 'TAGLE , ANA GOON', 'B0DB9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'FCGCCI/DIRECTOR', 'AIA8452', 'TAGLE , ANA GOON', 'B0DB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'FCGCCI/DIRECTOR', '130102', 'TAGLE , ANA GOON', 'B0DB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '584', 'VISITING DOCTOR', 'UZQ517', 'TANYU,KORINA,ADA', '40CF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '584', 'VISITING DOCTOR', 'TQD519', 'TANYU,KORINA,ADA', '40CF9882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '584', 'VISITING DOCTOR', 'IAN881', 'TANYU,KORINA,ADA', '40CF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '583', 'VISITING DOCTOR', 'ASA2397', 'NG,VANESSA JAM,TANYA', 'D0159C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '583', 'VISITING DOCTOR', 'ACY4556', 'NG,VANESSA JAM,TANYA', 'D0159C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'VIP (FCGCCI DIR.)', '2NA434', 'SIYTIAP, JONATHAN BRYAN SY', '30E09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '585', 'DOCTOR', 'PVI993', 'TARCE, ANNIE CHUA M.M', '90089982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '585', 'DOCTOR', 'VCK888', 'TARCE, ANNIE CHUA M.M', '90089982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '585', 'DOCTOR', 'WTG553', 'TARCE, ANNIE CHUA M.M', '90089982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '585', 'DOCTOR', 'ZDX332', 'TARCE, ANNIE CHUA M.M', '90089982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '585', 'DOCTOR', 'ZMY426', 'TARCE, ANNIE CHUA M.M', '90089982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'CQ8806', 'OLPINDO, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', '130101', 'OLPINDO, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '360', 'DOCTOR', 'A4K955', 'ZAMORA, MA. SOCORRO', 'D0CD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '586', 'DOCTOR', 'XBN819', 'NUGUID, TEODORO', '90BB9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '586', 'DOCTOR', 'XHH577', 'NUGUID, TEODORO', '90BB9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'CQ8806', 'OLPINDO-MACARAEG, RUBY', '109A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '587', 'CONSULTANT', 'EO1352', 'TE,NIKKI DOREEN,ANGBUE ', 'E03A9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '587', 'CONSULTANT', 'LGF505', 'TE,NIKKI DOREEN,ANGBUE', 'E03A9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '587', 'CONSULTANT', 'ZSR383', 'TE,NIKKI DOREEN,ANGBUE', 'E03A9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '587', 'CONSULTANT', 'TOK282', 'TE,NIKKI DOREEN,ANGBUE ', 'E03A9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'IL3949', 'CO, MANUEL', '069CADCE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'HW8763', 'CO, MANUEL', '069CADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'ZCG798', 'CO, MANUEL', '069CADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'IL3949', 'CO, MANUEL', '3ADF36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'UOQ908', 'CO, MANUEL', '30449082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'NAK8857', 'CO, CRISTINA ', 'B0E89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '374', 'DOCTOR', 'PGO507', 'ANGTUACO, JAMES', '40B89082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '493', 'ASS. VISITNG DOCTOR', 'MQ0944', 'CU, ELLEN TAN', '10819282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'WJS630', 'GOYOKPIN, BENITO', '367BB6CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'WMG303', 'GOYOKPIN, BENITO', '367BB6CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'WSC256', 'GOYOKPIN, BENITO', '367BB6CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'XMU968', 'GOYOKPIN, BENITO', '367BB6CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '3', 'VIP', 'ZBD938', 'GOYOKPIN, BENITO', '367BB6CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '230', 'DOCTOR', 'EOQ652', 'PANGANIBAN, CINDY', 'C0B49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '230', 'DOCTOR', 'DV6253', 'PANGANIBAN, CINDY', 'C0B49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'PKP431', 'LAO, GIOK CHIAO', '368CACCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'WAG510', 'LAO, GIOK CHIAO', '368CACCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'XGS318', 'LAO, GIOK CHIAO', '368CACCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'ZBY108', 'LAO, GIOK CHIAO', '368CACCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'ZJY923', 'LAO, GIOK CHIAO', '368CACCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '105', 'VIP', 'NUC 88', 'TEE WILLIE ONG', 'F0679C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'TIO946', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '270', 'DOCTOR', 'AZX135', 'SIA, KENDRICK GO', 'C0BA9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'QMT HEAD', 'XEJ763', 'LAO , EVELYN SY', '30099982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'VIP', 'PEQ658', 'DINO, ANTONIO', '266E36CF', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'ZPW518', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'DOCTOR', 'B1T591', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'MR0339', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '589', 'DOCTOR', 'DR8389', 'MANAOIS,HERBERT,ZULUETA', '500F9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '429', 'VISITING DOCTOR', 'MU1310', 'SOSUAN, LOURDES', '805F9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '429', 'VISITING DOCTOR', 'XGZ613', 'SOSUAN, LOURDES', '805F9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'WHG243', 'HAO, ELISEO', '20FA9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'XRC198', 'HAO, ELISEO', '20FA9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'XRL198', 'HAO, ELISEO', '20FA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'XRL389', 'HAO, ELISEO', '20FA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'VG3587', 'HAO, ELISEO', '20FA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '47', 'VIP', 'MU8243', 'YANG, ROBERT', 'E01E9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '47', 'VIP', 'MU8243', 'YANG, ROBERT', '4A622ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '549', 'DOCTOR', 'WPS268', 'YRUMA,MARIA ETHEL,MIRANDA', '60B29382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '593', 'DOCTOR', 'PQP767', 'PANGAN,VIVIAN,TAN', '50D49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '235', 'DOCTOR', 'WB4780', 'PAREDES DY, ALANNA WONG', '307B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '561', 'DOCTOR', 'MT3299', 'VILLANUEVA, JOHANS PLANA', '50999A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '573', 'VISITING CONSULTANT', 'A0Z369', 'SO, MARK JOSEF SY', '50E59882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '235', 'DOCTOR', 'WPQ359', 'PAREDES DY, ALANNA WONG', '307B9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '188', 'DOCTOR', 'B2U721', 'LO, VIRGILIO', 'D06F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '320', 'DOCTOR', 'EO1352', 'TE, WILLIE A. M.D', 'D0F29882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '569', 'VISITING DOCTOR', 'A5L764', 'VINOYA, MICHAEL ERROL', '40109D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '513', 'ASSO.VISITING/DOCTOR', 'C1F497', 'WIJANGCO, LOIDA MESA', '60269C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', '130106', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'A2O184', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'AAI7431', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'ACD4118', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'PA7861', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'POS853', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'TQL627', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'ULO639', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', '123', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '27', 'VIP', 'VQ9101', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'VIP', 'VW1628', 'PACHECO, JUANCHO', 'D6F5B8CE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '38', 'DOCTOR', 'ACG7268', 'CHAN, AMALIA', 'B04D8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '80', 'DOCTOR', 'E0Q914', 'CRUZ, MELANIE', '20B59782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '355', 'DOCTOR', 'AAX3113', 'YAP, ELENA M.D.', '80919082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '690', 'DOCTOR', 'ABD8758', 'TY-LEE, ADELINE', '91DE0A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '168', 'DOCTOR', 'A5L754', 'LIM, FREDIRICK', 'A0849182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '265', 'DOCTOR', 'GON687', 'SAY, ANTONIO', '50829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '187', 'DOCTOR', 'A5T139', 'LO, JOVANNI', 'A07F9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '13', 'DOCTOR', 'ZBB711', 'ANG, SAMUEL', '50269B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '46', 'DOCTOR', 'EH1743', 'CHANTE, CHARLES', 'A0509282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '80', 'VIP', 'AAL3011', 'CHENG, JIMMY', 'A0F69C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '17', 'VIP', 'AEV717', 'LEE, WILLIAM', 'E6F3B9CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'TDW328', 'LEE, WILLIAM', 'E6F3B9CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '444', 'VISITING DOCTOR', 'FJF868', 'GO, JEROME LIM', '60389482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '163', 'DOCTOR', 'YQ3201', 'LIM, ANDERSON', '105A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '163', 'DOCTOR', 'III-19', 'LIM, ANDERSON', '105A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'VG4598', 'VALLES, TOMAS', '1A5B37CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'VG4598', 'VALLES, TOMAS', 'A63BADCE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'AAJ3565', 'VALLES, TOMAS', 'A63BADCE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'AQA4554', 'VALLES, TOMAS', 'A63BADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NE4713', 'VALLES, TOMAS', 'A63BADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NOB800', 'VALLES, TOMAS', 'A63BADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '19', 'VIP', 'NSQ488', 'VALLES, TOMAS', 'A63BADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'TOP605', 'VALLES, TOMAS', 'A63BADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '32', 'VIP', 'TX9471', 'VALLES, TOMAS', 'A63BADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '159', 'DOCTOR', 'A6V168', 'LETRAN, JASON', 'B0599482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '626', 'DOCTOR', 'AAL6974', 'UMALI, JOSELITO M.D.', '81D49A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'A3N681', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'A2J263', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'DOCTOR', 'A6U089', 'GICABAO-BERINO, CHONA', 'A0709082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'TOT331', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'XNB851', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'DOCTOR', 'TGO718', 'GILBUENA, AMIEL', '30D89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'NQG263', 'VIRGILIO SIA', '0655AECE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'PA8182', 'VIRGILIO SIA', '0655AECE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'WRI882', 'VIRGILIO SIA', '0655AECE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '95', 'BOARD OF DIRECTOR', 'ZNK140', 'VIRGILIO SIA', '0655AECE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '355', 'DOCTOR', 'A7F925', 'YAP, ELENA M.D.', '80919082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '86', 'VIP (FCGCCI DIR.)', 'ZNA434', 'SIYTIAP, JONATHAN BRYAN SY', '30E09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'VR6321', 'SIA, KELLY', '2A7526CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'VIP', 'VR6321', 'SIA, KELLY', 'D2B9485D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '597', 'VISITING DOCTOR', 'A4T151', 'SAMSON,VICTORIA,SZE', '90649482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'A6Z728', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', '173707', 'TAN JR., FRANCISCO', 'D08C9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'ACA7155', 'NGO, PETER', '66FFB6CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'LCH888', 'NGO, PETER', '66FFB6CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'POM275', 'NGO, PETER', '66FFB6CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '16', 'VIP', 'XTE811', 'NGO, PETER', '66FFB6CE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '292', 'DOCTOR', 'A6P850', 'TAN, PASCUAL MYRA', 'E0C79B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'VIP', 'A2A592', 'ONG, ROBERT', 'F0BC9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'VIP', 'LGX854', 'ONG, ROBERT', 'F0BC9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'VIP', 'WIO755', 'DEE, ROBERT DE JOYA', 'D00D9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'VIP', 'UIS311', 'DEE, ROBERT DE JOYA', 'D00D9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '23', 'DOCTOR', '130106', 'BELEN, THELMA', '80209282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'K0G519', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'NCF362', 'HAO, ELISEO', '20FA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'NCJ6499', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'WIP568', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'XBM278', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'ZHG340', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '284', 'DOCTOR', 'N0A124', 'SY, JONATHAN', 'D0CB9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '382', 'DOCTOR', 'ABQ9178', 'CHONG, SAU KAM CHAN', 'C07A9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '638', 'ASS. VISITING', 'C1M543', 'SAHAGUN, MARIE JANICE AMANTE', '423E9C2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '598', 'VISITING CONSULTANT', 'VH9126', 'APARATO,EMILY,MARQUEZ', 'F0DC9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '598', 'VISITING CONSULTANT', 'NOV430', 'APARATO,EMILY,MARQUEZ', 'F0DC9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '314', 'DOCTOR', 'UHQ153', 'TANBONLIONG, SEVERINO', '20A49882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'UKI915', 'GABRIEL, MELCHOR', '50E59182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'EOZ630', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '487', 'VISITING CONSULTANT', 'NCJ8376', 'CRUZ, EMERITO EVANGELISTA', '40649282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '518', 'VISITING DOCTOR', 'A7M532', 'WEE, IRENE TAN', '40F69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '599', 'DOCTOR ANES', 'UIY610', 'CHAN, CHRISTINE, SOMERA', '00E49482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '600', 'VISITING DOCTOR', 'WC9976', 'FLORES, KRISTINE, VELASCO', '307C9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '290', 'DOCTOR', 'NCX5253', 'TALAG-SANTOS, CORAZON M.D.', '40919982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '493', 'ASS. VISITNG DOCTOR', 'UJO971', 'CU, ELLEN TAN', '10819282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '479', 'ASS. VISITING DR.', 'MQ0944', 'CU, STEPHEN GO', '30A09782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '479', 'ASS. VISITING DR.', 'UJO971', 'CU, STEPHEN GO', '30A09782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '283', 'DOCTOR', 'NCX9358', 'SY, JOCELYN LIM', 'A00E9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '110', 'VIP/ATENEO TEACHER', 'FGA893', 'FERRER,  ANDREW DELOS REYES', '107D9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '111', 'VIP/ATENEO TEACHER', 'VF1140', 'CLARO,  PAULYN JEAN O.', '107D9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '120', 'DOCTOR', 'ADR1899', 'GO, JENNYLYN', 'D0B79382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '93', 'DOCTOR', 'NCI1206', 'DICO-GO,  JOYCE', '60C28F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '590', 'DOCTOR', 'NCF4262', 'HAO, ELISEO', '20FA9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'B.DIRECTOR', 'PLQ277', 'ONG,NELSON,CUA', 'A0469A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'B.DIRECTOR', 'THI188', 'ONG,NELSON,CUA', 'A0469A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '102', 'B.DIRECTOR', 'ZAE178', 'ONG,NELSON,CUA', 'A0469A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '302', 'DOCTOR', 'FJH332', 'TAN, HIGINIO', 'E05A9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'DOCTOR', 'NCZ6094', 'CALIMON, WILFRIDO', 'C05D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '234', 'DOCTOR', 'NAY5975', 'PARDILLO, ROSITA', '20E49182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '453', 'DOCTOR', 'B3Z609', 'FERNANDO, BERLIN YU', '90B38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '109', 'DOCTOR', 'G0P210', 'GAN, FELISA', '109A8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '45', 'DOCTOR', 'E1H042', 'CHANG, ROBERT', '20989982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '284', 'DOCTOR', 'R0C282', 'SY, JONATHAN', 'D0CB9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'WQB141', 'CO, MANUEL', '3ADF36CD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'WQB141', 'CO, MANUEL', '30449082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'WQB141', 'CO, MANUEL', '069CADCE', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '601', 'VISITING DR.', 'UWO422', 'SAY,MA. KARLA VILLANUEVA', 'C318582D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'NDB5788', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'ADB5788', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '359', 'DOCTOR', 'NCH7560', 'YARISANTOS, CHERRI RACHEL', 'E0279082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '603', 'VISITING/PEDIA', 'VL8657', 'CAPUL-SY-CHANGCO,EVANGELINE PINGOL', 'D2FEF32E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '307', 'DOCTOR', 'E1B327', 'TAN, PATRICIA', 'B0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '604', 'CGHMC/COLLEGE', 'XDN343', 'GIMENEZ, ERNESTO BELLEZ', 'E219B72E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '604', 'CGHMC/COLLEGE', 'WWW822', 'GIMENEZ, ERNESTO BELLEZ', 'E219B72E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '602', 'VISITING/SURGERY', 'C1F342', 'ROMERO, DON ARLIE, SANCHEZ', 'C311EF2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', '', 'NDG4284', '0', 'D2FEF32E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'NCZ4329', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '606', 'VISITING STAFF', 'OW2726', 'YU,BLAS ANTHONY MACAWILI', '811BA52D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '607', 'COLLEGE OF MEDICINE', 'AAL2409', 'ANDRES, STEPHANIE CORTES', '71E08A2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '40', 'DOCTOR', 'NBL4652', 'CHAN, KELVIN', '90049182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '698', 'CGHMC COL.OF MEDICINE', 'ARA1647', 'PANEM RUSSELL GAMBITO', 'C2858F2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '507', 'CGHMC COL. OF MEDICINE', 'TXQ164', 'MANALANG, JOCELYN BAGTAS', '707A9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '507', 'CGHMC COL. OF MEDICINE', 'ABP1893', 'MANALANG, JOCELYN BAGTAS', '707A9B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '506', 'CGHMC COL. OF MEDICINE', 'ARA7786', 'BUMANLAG, MA. LIEZEL VITALIANO', '30279A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '339', 'CGHMC COL. OF MEDICINE', 'UD8412', 'DE DIOS , CHRISTIAN BRYAN OBSEQUIO', '90589982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '249', 'CGHMC COL. OF MEDICINE', 'WOE102', 'ALBO, VILMA PAMBID', '80529A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '321', 'CGHMC COL. OF MEDICINE', 'UFQ137', 'FERNANDEZ  , RAQUEL MELITON', 'A02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '366', 'CGHMC COL. OF MEDICINE', 'NCO1483', 'SAMSON, EMMANUEL PALMIS', '80BD9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '597', 'VISITING DOCTOR', 'AAW1646', 'SAMSON,VICTORIA,SZE', '90649482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'NAE1683', 'ADDATU, DOMINGO', 'E09A9982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '412', 'VIP', 'PA8227', 'TING, ALFREDO', '30119882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '412', 'VIP', 'XTM202', 'TING, ALFREDO', '30119882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'VIP', 'PCO629', 'TAN, VICENTE KEH', '106B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'VIP', 'A1U873', 'TAN, VICENTE KEH', '106B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'VIP', 'A1N388', 'TAN, VICENTE KEH', '106B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'VIP', 'KWT333', 'TAN, VICENTE KEH', '106B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'VIP', 'UIO173', 'TAN, VICENTE KEH', '106B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '114', 'VIP', 'UI0173', 'TAN, VICENTE KEH', '106B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '453', 'DOCTOR', 'NBA8581', 'FERNANDO, BERLIN YU', '90B38F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '91', 'DOCTOR', 'UOQ757', 'DELA CRUZ, ALVIN', 'E0099582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '680', 'DOCTOR', 'NCH2966', 'CO, CESAR', '7119722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '611', 'COL. OF MED.', 'A6O597', 'GO, MARY ANNE GONZALES', 'C27D462E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '612', 'COL. OF MEDICINE', 'WC6298', 'GILLER, KEVIN DEAN', 'C277112E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '612', 'COL. OF MEDICINE', 'WRM781', 'GILLER, KEVIN DEAN', 'C277112E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '612', 'COL. OF MEDICINE', 'ZCD645', 'GILLER, KEVIN DEAN', 'C277112E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '612', 'COL. OF MEDICINE', 'NCF9289', 'GILLER, KEVIN DEAN', 'C277112E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '613', 'DEANS. OFFICE', 'XMT751', 'CRUZ, ANTONIO MANALINGOD', 'C27C962E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '614', 'CGHMC COLLEGE', 'ZNJ315', 'SO, IRISH CHUA', 'C3152B2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '615', 'CGHMC COLLEGE', 'THH787', 'SALES, ARIS LALATA', 'D2FE162E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '617', 'CGHMC COLLEGE', 'POA238', 'GALANG, GERALDINE ROWENA SAMPEDRO', '03CD942E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '617', 'CGHMC COLLEGE', 'CKZ160', 'GALANG, GERALDINE ROWENA SAMPEDRO', '03CD942E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '618', 'CGHMC COLLEGE', 'AAI8536', 'YU, JUNWIL CHUA', '8146402D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '619', 'CGHMC COLLEGE', 'TOM739', 'CHUA, JAMES CHRISTOPHER CHAN', 'C275212E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '620', 'CGHMC COLLEGE', 'TIK618', 'DALIPE WINNIE PEARL PIRING', 'C310032D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '620', 'CGHMC COLLEGE', 'BP8212', 'DALIPE WINNIE PEARL PIRING', 'C310032D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '621', 'CGHMC COLLEGE', 'ABE1899', 'GATBONTON RYAN REY GATCHALIAN', 'C3175F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '621', 'CGHMC COLLEGE', 'WIW148', 'GATBONTON RYAN REY GATCHALIAN', 'C3175F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '622', 'CGHMC COLLEGE', 'TFK842', 'DEVEZA, BENJAMIN CONCECPCION', '910E1F2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '623', 'CGHMC COLLEGE', 'VB7382', 'UY, GLORIA TAN', '1340412E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '623', 'CGHMC COLLEGE', 'TWI883', 'UY, GLORIA TAN', '1340412E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '624', 'CGHMC COLLEGE', 'GO4808', 'GAN, EMELITA ANG', '81A9352D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '624', 'CGHMC COLLEGE', 'UQZ504', 'GAN, EMELITA ANG', '81A9352D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '624', 'CGHMC COLLEGE', 'IL8265', 'GAN, EMELITA ANG', '81A9352D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '625', 'CGHMC COLLEGE', 'XMH495', 'BUMANGLAG, MARILYN ROSALES', '03A4A52E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '610', 'DOCTOR', 'IK1683', 'TAN-TINHAY, LORA MAY', 'C273632E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '326', 'DOCTOR', 'NCU9114', 'TIONGSON, CASIMIRO M.D.', 'D0659A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'CS2973', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'CS2973', 'CHUAYING,  RAMON', '70F09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '81', 'DOCTOR', 'NDC9944', 'CU, ERIC', '40D69882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '63', 'VIP/AUDIT', 'F1K174', 'BERNABE, LUCIANA', '40F79182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '535', 'CONSULTANT', 'NBX5377', 'ALBA, WILLY SEE', '00CC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '627', 'COLLEGE OF MEDICINE', 'AAT1615', 'POLICARPIO, JOSEFINA TOLENTINO', 'C312A52D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '627', 'COLLEGE OF MEDICINE', 'AJA2932', 'POLICARPIO, JOSEFINA TOLENTINO', 'C312A52D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '628', 'COLLEGE OF MEDICINE', 'KDR903', 'USMAN, ALMUHAIMIN  MASORONG', 'E207E02E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '630', 'COLLEGE OF MEDICINE', 'ABT7186', 'WATANABE, SPENCER SITJAR', 'E21DBF2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '630', 'COLLEGE OF MEDICINE', 'YS9023', 'WATANABE, SPENCER SITJAR', 'E21DBF2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'NCH8959', 'CHUAYING,  RAMON', '9A9C2FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '76', 'BOARD OF DIRECTOR', 'NCH8959', 'CHUAYING,  RAMON', '70F09B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '558', 'CONSULTANT', 'A8Y741', 'HAO, WILLIE  TAN', '50D69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '170', 'DOCTOR', 'ZLA376', 'LIM, HENRISON', 'C0C69182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '535', 'CONSULTANT', 'A9M549', 'ALBA, WILLY SEE', '00CC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '182', 'DOCTOR', 'NEI4972', 'LIM, ALBA REBECCA', 'C03D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'AAO1585', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'EH4867', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '632', 'COLLEGE OF MEDICINE', 'XNE242', 'DAVID IMELDA SANGALANG', 'C2748F2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '632', 'COLLEGE OF MEDICINE', 'RDN834', 'DAVID IMELDA SANGALANG', 'C2748F2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '633', 'VISITING DOCTOR', 'PLI704', 'SANTOS ADRIAN HO', '81A0022D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'NBG1256', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '511', 'VISITING CONSULTANT', 'A7Q896', 'LIM-WANG, MARY ANN  OALLESMA', '80989982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'A8Y759', 'ANG, ANNE TAN', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '509', 'CONSULTANT', 'NAV3378', 'UY, PETER PAUL LAO', 'B0709B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '64', 'DOCTOR', 'LAA7401', 'CHUA, PERLEN TE', '200F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '585', 'DOCTOR', 'UIP767', 'TARCE, ANNIE CHUA M.M', '90089982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '636', 'VISITING CONSULTANT', 'NBU6778', 'KHOO, WINNE SHARON LIM', 'E200882E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '636', 'VISITING CONSULTANT', 'ABR8520', 'KHOO, WINNE SHARON LIM', 'E200882E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '636', 'VISITING CONSULTANT', 'IL0877', 'KHOO, WINNE SHARON LIM', 'E200882E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '636', 'VISITING CONSULTANT', 'VQ8551', 'KHOO, WINNE SHARON LIM', 'E200882E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '397', 'DOCTOR', 'ZHL966', 'CABERO, MARIA IMOGENE LALAINE', '10E09082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '397', 'DOCTOR', 'ALA3292', 'CABERO, MARIA IMOGENE LALAINE', '10E09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '484', 'VISITING CONSULTANT', 'NCI3240', 'REYES, RICHMOND CHENG', '105F9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '639', 'DOCTOR', 'TOK282', 'ANGBUE-TE ANDREI PAOLO', '91520A2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '640', 'DOCTOR', 'ZMU328', 'HUANG, MARK IVAN PALMA', '81B9432D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'NAH5775', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'KOG519', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '115', 'NURSING DEPT.', 'VEG784', 'DOMINGO, CRISTOBAL JASON', '90EB9382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '509', 'CONSULTANT', 'AOA9328', 'UY, PETER PAUL LAO', 'B0709B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '226', 'DOCTOR', 'AAN1958', 'ONG-GO, MARY', 'D02B9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '442', 'DOCTOR', 'NCX5763', 'CHAN, JOHN NOEL UY', 'D0D59382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '29', 'DOCTOR', 'NAA3220', 'CALIMON, WILFRIDO', 'C05D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'UQY865', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'ULC945', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'PRC155', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'ULC945', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'UQY865', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'VCD820', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'WKW703', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'XJF835', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '523', 'VISITING CONSULTANT', 'IAA2132', 'LEE, ROWENA CO', 'B09B9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '193', 'DOCTOR', 'LAB1510', 'LU, PETERSON', '70A39482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '642', 'NURSING', 'ABF7892', 'DAYRIT ,  AUBREY ALBIAR', 'E21A092E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '643', 'MEDICINE', 'USY192', 'ECLEO, SENEN REALO', 'E21D372E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '643', 'MEDICINE', 'NAF1680', 'ECLEO, SENEN REALO', 'E21D372E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '43', 'DOCTOR', 'NBH4383', 'CHAN-LAO, JULIET', '50029A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '39', 'VIP/HRD', 'BEB2375', 'MENDOZA, GLECY ', 'D0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '644', 'VISITING DR', 'NAS5826', 'VILLAMUCHO, NEIL CHRISTIAN,DELA PEÑA', 'E20D7E2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '564', 'VISITING DOCTOR', 'NIT736', 'HIZON, IAN BENJAMIN M.D.', 'A0719C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '454', 'DOCTOR', 'ZKN690', 'DY, WILLY CHUA M.D.', '20BC9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'NAL3601', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '570', 'VISITING/DOCTOR', 'NH28819', 'TING LAO, EMMA..M.D.', '40E39882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '570', 'VISITING/DOCTOR', 'NHZ8819', 'TING LAO, EMMA..M.D.', '40E39882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'FGCCI DIRECTOR', 'ZE1695', 'LEE, ROBERT', '10169282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'FGCCI DIRECTOR', 'ABP6859', 'LEE, ROBERT', '10169282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'FGCCI DIRECTOR', 'COA603', 'LEE, ROBERT', '10169282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'FGCCI DIRECTOR', 'GS4680', 'LEE, ROBERT', '10169282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'FGCCI DIRECTOR', '130302', 'LEE, ROBERT', '10169282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '39', 'VIP/HRD', 'DEB2375', 'MENDOZA, GLECY ', 'D0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'NAL3601', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '144', 'DOCTOR', 'NBQ1808', 'LAO, LEONARD', '40CE9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '646', 'FACULTY CGHCN', 'ZNR107', 'GELACIO,FREDELINE,DOMINE', '8114112D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '203', 'DOCTOR', 'NAK8414', 'MONSANTO, ELMA', '20BB9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '615', 'CGHMC COLLEGE', 'B4T422', 'SALES, ARIS LALATA', 'D2FE162E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '648', 'DR/SURGERY', 'DS4744', 'CHUA,JOYCE HAZEL,CORTEZ', '527C612E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '647', 'VISITING DR', 'WIJ334', 'SY, ALLEN ANDREW, TANLIMCO', '03D7362E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '37', 'VIP', 'ABI5861', 'ESTACIO, MARILOU', '70649A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'NBQ1676', 'DY, JAMES', '20989082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP-CEO', 'NBQ1676', 'DY, JAMES', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'NBQ1676', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'NBQ1676', 'DY, JAMES CEO', '907D9C82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'NBQ1676', 'DY, JAMES CEO', 'E03F2481', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'NBQ1676', 'DY, JAMES CEO', '50F19482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'NBQ1676', 'DY, JAMES CEO', '10399982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '1', 'VIP', 'NBQ1676', 'DY, JAMES CEO', 'DA082FCD', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '2', 'VIP-PRESIDENT', 'NBQ1676', 'DY, JAMES GO', '907D9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'NBQ1676', 'DY, JAMESON GO', 'C07A9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '54', 'VIP', 'NBQ1676', 'DY, JAMESON GO', 'FAD635CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '544', 'DOCTOR', 'NAU5995', 'SY-YUCHONGTIAN, IRENE', '20239582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '344', 'DOCTOR', 'NBL4739', 'VERA CRUZ, MARIVIC & RAFAEL..M.D', '20D09082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '345', 'DOCTOR', 'NBL4739', 'VERA CRUZ,  RAFAEL..M.D', '50059082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '505', 'VISITING CONSULTANT', 'E1C169', 'BORBE, RENE TABLANTE', 'A0C59882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '528', 'CONSULTANT', 'NCY1698', 'LEH, JOSEPHINE', 'F08C9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'NDA7068', 'ALEGRE, NATALIO', '408B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'NCN9730', 'ALEGRE, NATALIO', '408B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'NCI2743', 'ALEGRE, NATALIO', '408B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '7', 'DOCTOR', 'NAI3602', 'ALEGRE, NATALIO', '408B9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'NDA7068', 'ALEGRE, EMELDA', '30629882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '116', 'VIP-CEO', 'NBQ1676', 'DY, JAMES', '20989082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '96', 'VIP', 'NBQ1676', 'DY, JAMES CEO', '309D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '536', 'CONSULTANT', 'NAG3883', 'REYES-ADDATU, ALMA ONG', 'C0D89182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '536', 'CONSULTANT', 'NAE1683', 'REYES-ADDATU, ALMA ONG', 'C0D89182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '0', 'DOCTOR', 'NAG3883', 'ADDATU, DOMINGO', 'E09A9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '241', 'DOCTOR', 'NCZ5957', 'PERIQUET, ANTONIO', '50149182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '230', 'DOCTOR', 'NAO2291', 'PANGANIBAN, CINDY', 'C0B49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '98', 'DOCTOR', 'NAA9236', 'DY,  BUNYOK', '700C9D82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '645', 'DR. ONCOLOGY', 'NBK3094', 'VERA CRUZ, VERONICA, TAN', 'C313D02D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'A2U430', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'NCI2743', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'NCN9730', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '6', 'DOCTOR', 'NDA7068', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'NIY126', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'NLI988', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'VI7927', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'VY1073', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'NAI3602', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '328', 'DOCTOR', 'DAA9200', 'TONGSON, LUINIO S..M.D', 'F0D19982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '650', 'DOCTOR', 'ZDK770', 'ALEGRE, EMELDA', 'C30DAB2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'NAM3121', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '118', 'VIP/PRESIDENT', 'NBQ1676', 'DY,JAMES,G', '10988F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '113', 'VIP', 'DAB8340', 'CABERO, CAMILO ATTY.', '80429C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '642', 'NURSING', 'UQE549', 'DAYRIT ,  AUBREY ALBIAR', 'E21A092E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '634', 'CGH FACULTY', 'TOS478', 'LAYGO, REDARIO CASTILLO', '33C4D62E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '634', 'CGH FACULTY', 'B2S102', 'LAYGO, REDARIO CASTILLO', '33C4D62E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '634', 'CGH FACULTY', 'EOM076', 'LAYGO, REDARIO CASTILLO', '33C4D62E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '634', 'CGH FACULTY', 'RK4638', 'LAYGO, REDARIO CASTILLO', '33C4D62E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '112', 'DOCTOR', 'NAA4817', 'GAW, GRACE', '20329082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '143', 'DOCTOR', 'NBQ1808', 'LAO, SUSANA', 'E0319B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'NAS6917', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '126', 'DOCTOR', 'NAA8898', 'GOTAMCO, LAWRENCE ONG', '00FF9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '127', 'DOCTOR', 'NAA8898', 'GOTAMCO, MARIA LUISA', '50AC9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '462', 'DOCTOR', 'B4B855', 'ANG, VICTORIO NGO M.D.', '20809182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '651', 'DR/DERMA', 'NBS7547', 'CHUA, MARY ROSE ,MABALLO', '33A34D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '568', 'VISITING DOCTOR', 'DOP435', 'SOLIVEN, ALBERT  UYTANLET', '40EB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '175', 'DOCTOR', 'HAA4361', 'LIM, NELSON', '301B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'NAF3946', 'WONG, STEPHEN M.D.', '205C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '652', 'VISITING DR/OB GYN', 'XTS795', 'DE GUZMAN, KATHLENE ELAINE, LEGASPI', '91B3F92D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '505', 'VISITING CONSULTANT', 'NBQ9327', 'BORBE, RENE TABLANTE', 'A0C59882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '321', 'CGHMC COL. OF MEDICINE', 'NBI3220', 'FERNANDEZ  , RAQUEL MELITON', 'A02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '47', 'VIP', 'NAM3342', 'YANG, ROBERT', '4A622ACD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '119', 'VIP', 'AQA8385', 'FAVIS , JAAN MICHELLE CRISOLOGO', '008C8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '119', 'VIP', 'EO1761', 'FAVIS , JAAN MICHELLE CRISOLOGO', '008C8F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '119', 'VIP', 'XGR213', 'FAVIS , JAAN MICHELLE CRISOLOGO', '008C8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '123', '', '123456', 'SAMPLE TEST', 'F9AD44B3', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '123', '', '123456', 'SAMPLE TEST', 'F9AD44B3', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'NBC5543', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '514', 'VISITING DOCTOR', 'ROF288', 'LAPID-LIM , SUSAN IRENE ESPIRITU', 'A06B9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '514', 'VISITING DOCTOR', 'NBD3818', 'LAPID-LIM , SUSAN IRENE ESPIRITU', 'A06B9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '477', 'ASS. VISITING DR.', 'NBZ7469', 'SEGARRA, YUZHEN GARCIA ', 'C06A9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '230', 'DOCTOR', 'NAV6963', 'PANGANIBAN, CINDY', 'C0B49282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '558', 'CONSULTANT', 'NBE4941', 'HAO, WILLIE  TAN', '50D69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '315', 'DOCTOR', 'NCL6353', 'TANG, BENJAMIN', '90289C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '653', 'DOCTOR', 'ZPP469', 'KWONG BUIZON SHIRLEY L.', 'E20E692E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '653', 'DOCTOR', 'A3K742', 'KWONG BUIZON SHIRLEY L.', 'E20E692E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '653', 'DOCTOR', 'WOF483', 'KWONG BUIZON SHIRLEY L.', 'E20E692E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '343', 'VIP', 'C1O681', 'VELOSO, JANUARIO D. M.D.', '70129382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '21', 'DOCTOR', 'NAR4741', 'BAYDID-CRUZ, ZOLINA', '50C78F82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '47', 'VIP', 'NAM3342', 'YANG, ROBERT', 'E01E9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'NPR8301', 'TIN HAY, EDUARDO M.D.', '10DD9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'VZ1464', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '139', 'DOCTOR', 'NAS8626', 'KOH, ABNER', '20539782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '139', 'DOCTOR', 'NBI7433', 'KOH, ABNER', '20539782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '194', 'DOCTOR', 'NBI7433', 'LU-KOH, ROSIE SUE LUAN', 'C0B99A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '194', 'DOCTOR', 'NAS8626', 'LU-KOH, ROSIE SUE LUAN', 'C0B99A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '382', 'DOCTOR', 'AAY9248', 'GARCIA, NESTOR YU', '60B69482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'E0A955', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '323', 'DOCTOR', 'NCP8301', 'TIN HAY, EDUARDO M.D.', '10DD9782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '481', 'ASS.-VISITING DR.', 'NCX6602', 'KING, LARRY SIA', '20929082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '655', 'DOCTOR', 'XOH456', 'DABU, INIGO BASTE MACLANG', '714D862D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '654', 'CGH COLLEGE', 'E1F677', 'LEGASPI,JOHANNE FLORENCE CALICA', 'E211552E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '479', 'ASS. VISITING DR.', 'NBA6438', 'CU, STEPHEN GO', '30A09782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '690', 'DOCTOR', 'GAH1674', 'TY-LEE, ADELINE', '91DE0A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '656', 'VISITING DR', 'XHH827', 'LIM, AUSTIN SAMUEL, LAIFUN', 'E205732E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '656', 'VISITING DR', 'WKE732', 'LIM, AUSTIN SAMUEL, LAIFUN', 'E205732E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '194', 'DOCTOR', 'NDK8631', 'LU-KOH, ROSIE SUE LUAN', 'C0B99A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '139', 'DOCTOR', 'NDK8631', 'KOH, ABNER', '20539782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '476', 'ASS. VISITING DR.', 'DR9444', 'LIM-LOO, MICHELLE BERNADETTEE CHING', '102D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '476', 'ASS. VISITING DR.', 'PUQ859', 'LIM-LOO, MICHELLE BERNADETTEE CHING', '102D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '476', 'ASS. VISITING DR.', 'AAS8171', 'LIM-LOO, MICHELLE BERNADETTEE CHING', '102D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '657', 'DOCTOR', 'AAO6296', 'LAGAMAYO, EVELINA NAVAL', 'E20F632E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '658', 'DOCTOR', 'O0B931', 'SUN,EDELINE YAO', '91C7672D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '658', 'DOCTOR', 'WUO511', 'SUN,EDELINE YAO', '91C7672D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '658', 'DOCTOR', 'XGL141', 'SUN,EDELINE YAO', '91C7672D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '660', 'DOCTOR', 'NAZ3412', 'BORBE, MICHELLE G.  M.D.', '81B0D92D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'DOCTOR', 'NAS8218', 'CUASO, CHARLES', 'E0219582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '534', 'CONSULTANT', 'NBZ6415', 'BARZA, GIORGIO', '10929282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '649', 'DOCTOR', 'AEA3225', 'CUA, LOLITA', 'C30F722D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '244', 'DOCTOR', 'NAC2440', 'POCSIDO, WINNIE', 'F0439182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '624', 'CGHMC COLLEGE', 'C1S312', 'GAN, EMELITA ANG', '81A9352D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '286', 'DOCTOR', 'NAD1919', 'SY, PETER', '10419B82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '15', 'DOCTOR', 'NAQ3606', 'ASTEJADA, MINA', 'D0F19882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '254', 'DOCTOR', 'RK0077', 'RABO, CHERRY', 'A09B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'ZSX138', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '662', 'VISITING DOCTOR', 'NBP5590', 'MUKARAM, BANISADAR, ADDALINO', 'C27B4E2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '564', 'VISITING DOCTOR', 'TOL309', 'HIZON, IAN BENJAMIN M.D.', 'A0719C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '231', 'DOCTOR', 'P2Z144', 'PANGANIBAN, JULIANO', '90E79082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'NAX9537', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'DOCTOR', 'NBM5811', 'CO, CRISTINA ', 'B0E89482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '232', 'DOCTOR', 'P2Z144', 'PANGANIBAN, SHIRLEY', 'A0978F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '90', 'DOCTOR', 'NAD1177', 'DEL MUNDO, SANDRA', '60F69282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '641', 'DOCTOR', 'P2Z144', 'CHUA, EDEN', '910F1F2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '100', 'DOCTOR', 'PXI871', 'SY, JOHNSON', '20A99082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '548', 'DOCTOR', 'PXI871', 'YOUNG-SY, EILEEN MAY', '10179182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '159', 'DOCTOR', 'NCW5673', 'LETRAN, JASON', 'B0599482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '556', 'CONSULTANT', 'NCL3881', 'LIM, MICHAEL LOUIE ONG', 'D0BE9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'ABG9725', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'AIR740', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'DFM 08', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'UYQ902', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'WPE683', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'XNB258', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'ZBB711', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '663', 'PATHOLOGIST', 'ZHF693', 'ANG, DAPHNE CHUA', '825EE52E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '690', 'DOCTOR', 'B4B075', 'TY-LEE, ADELINE', '91DE0A2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'NEJ3354', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'POC611', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '128', 'DOCTOR', 'NAW5356', 'GOTAUCO, CARMENCITA', '80DF9A82', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '386', 'DOCTOR', 'NDL1702', 'PANLILIO, ARISTEDES', 'F09E9382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '118', 'DOCTOR', 'P3Z785', 'GO, GRACE TAN', 'D0689282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '38', 'DOCTOR', 'NCG7268', 'CHAN, AMALIA', 'B04D8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '268', 'DOCTOR', 'AAP3495', 'SERRANO, HELEN', '30219082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '268', 'DOCTOR', 'TXQ489', 'SERRANO, HELEN', '30219082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '211', 'DOCTOR', 'HAA4361', 'NGO, LIM ROSEMARIE', '80069282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '268', 'DOCTOR', 'AHA8841', 'SERRANO, HELEN', '30219082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'ATY138', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'ATY 138', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '335', 'DOCTOR', 'NCL7189', 'TY,TRIUMPANTE ANN ANN M.D.', '80299782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '334', 'DOCTOR', 'NCL7189', 'TY, WILSON M.D.', 'E0CD9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '333', 'DOCTOR', 'NBV1331', 'TY, GRACE M.D.', '30979982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '664', 'DOCTOR', 'UOV245', 'SY, JOCELYN ONG', '62A7402E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '664', 'DOCTOR', 'WPT603', 'SY, JOCELYN ONG', '62A7402E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '664', 'DOCTOR', 'ZDX629', 'SY, JOCELYN ONG', '62A7402E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '664', 'DOCTOR', 'ZEB538', 'SY, JOCELYN ONG', '62A7402E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '664', 'DOCTOR', 'ZNW842', 'SY, JOCELYN ONG', '62A7402E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'NAW8932', 'ANG, ANNE TAN', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '311', 'DOCTOR', 'NAW8932', 'TAN-ANG, ANNE', 'C02A9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'BEX191', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'RAP401', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'WEU661', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'PQT593', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'NAA8898', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'NMO509', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '666', 'DOCTOR/VISITING', 'BEX181', 'GOTAMCO, GISELLE LAZARO', '62CFBA2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'BEX1912', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'RAP401', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'WEU661', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'PQJ593', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'NAA8898', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'NMO509', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '665', 'DOCTOR', 'BEX181', 'GOTAMCO , CAMILLE LAZARO', '62D02D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '331', 'DOCTOR', 'NBV1331', 'TY, EDISON', 'E0E39982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'NCL1321', 'EDNAVE, KERIMA ANN', '70A39382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '106', 'DOCTOR', 'B3Q764', 'EDNAVE, KERIMA ANN', '70A39382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '673', 'DOCTOR', 'NDL8016', 'CHAN, HENRY', '7244172E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '667', 'VISITING DOCTOR', 'AAH8737', 'BANUELOS ,GONZALO JR. CONTRERAS', '5272822E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '667', 'VISITING DOCTOR', 'XRC243', 'BANUELOS ,GONZALO JR. CONTRERAS', '5272822E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '667', 'VISITING DOCTOR', 'NAF2527', 'BANUELOS ,GONZALO JR. CONTRERAS', '5272822E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '668', 'VISITING DOCTOR', 'XCY820', 'SEBASTIAN, MICHAEL RAY CO', '6178F92D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '668', 'VISITING DOCTOR', 'ZEA513', 'SEBASTIAN, MICHAEL RAY CO', '6178F92D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '668', 'VISITING DOCTOR', 'NCQ947', 'SEBASTIAN, MICHAEL RAY CO', '6178F92D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '669', 'VISITING DOCTOR', 'NBU7108', 'TAN,KEVIN GABRIEL CO', '729B712E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '117', 'DOCTOR', 'NDQ2789', 'GO, ERLINDO JOSE..M.D', '80449182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '119', 'DOCTOR', 'NDQ2789', 'GO, IRISH TU', '80449182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '118', 'DOCTOR', 'GAM1557', 'GO, GRACE TAN', 'D0689282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '298', 'DOCTOR', 'NCJ5679', 'TAN-DE GUZMAN, WILSON', 'C0799A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '672', 'VISITING/DOCTOR', 'VQ0753', 'ESTRELLAS, DIANA FRANCISCA TUANO', '62A61C2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '672', 'VISITING/ DOCTOR', 'YZ7282', 'ESTRELLAS, DIANA FRANCISCA TUANO', '62A61C2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '671', 'VISITING/DOCTOR', 'NBQ4105', 'SEE JOHNSON ONG', '81CD212D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '671', 'VISITING/DOCTOR', 'AAM5988', 'SEE JOHNSON ONG', '81CD212D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '317', 'DOCTOR', 'NCT3572', 'TANTUCO-QUE, EMILIE', '601F9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '80', 'DOCTOR', 'NAX4387', 'CRUZ, MELANIE', '20B59782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'NDI3508', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '225', 'DOCTOR', 'P5C093', 'ONG-DELA CRUZ, BERNICE', 'E0938F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '675', 'DOCTOR', 'AAO2543', 'SIAO, STEPHANIE JANE ONG', 'C277432E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '674', 'VISITING /DOCTOR', 'POV509', 'LIM, JOHN HAROLD SY', '62D0C62E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '674', 'VISITING /DOCTOR', 'PNI632', 'LIM, JOHN HAROLD SY', '62D0C62E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '674', 'VISITING /DOCTOR', 'NAE6455', 'LIM, JOHN HAROLD SY', '62D0C62E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '157', 'DOCTOR', 'B61635', 'LEH, PATRICK', '90439282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'B1T591', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'MR0339', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'NCY1698', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'VG2343', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'XKV306', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'ZJD493', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'ZKV306', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '482', 'ASS. -VISITING', 'F3A669', 'DE JESUS, SOLITA DE GUZMAN', 'C0CC9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '444', 'VISITING DOCTOR', 'NCS5759', 'GO, JEROME LIM', '60389482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '546', 'COPNSULTANT', 'SZA753', 'PIMENTEL, RONNIE DE LARA', '40569282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '677', 'VISITING/DOCTOR', 'ARA2182', 'CHU, DONNABELLE MANIO', 'E2193F2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '642', 'NURSING', 'ABF7888', 'DAYRIT ,  AUBREY ALBIAR', 'E21A092E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '259', 'DOCTOR', 'NCS5991', 'REYES, TOMMY', '90969382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '103', 'DOCTOR', 'APA2227', 'DY, SOAT TONG', 'E0779182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '654', 'CGH COLLEGE', 'NBQ1753', 'LEGASPI,JOHANNE FLORENCE CALICA', 'E211552E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '34', 'DOCTOR', 'NBU1198', 'CASTRO, JENNIFER', '508F9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '33', 'DOCTOR', 'NBU1198', 'CASTRO, EUGENE', '60349182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '678', 'VISITING DR.', 'PUQ859', 'LII,LAWRENCE,CHENG', '81D0B62D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '678', 'VISITING DR.', 'PUQ859', 'LOO, LAWRENCE, CHENG', '81D0B62D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '679', 'VISITING DR.', 'TCA636', 'CHENG-LIAO, ARLENE, UY', 'C30E3E2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '679', 'VISITING DR.', 'XNL558', 'CHENG-LIAO, ARLENE, UY', 'C30E3E2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '464', 'VISITING CONSULTANT', 'CAS9125', 'VIUDEZ, EMILY M.', 'E0F69382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '84', 'VIP-ITCONSULTANT', 'XPB703', 'NAGTALON JR, REUBEN THOMAS T.', 'C0069A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '539', 'VISITING CONSULTANT', 'NDE8306', 'ARCINAS, RODERICK PO', 'D0D19082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '459', 'DOCTOR', 'PQW883', 'LUTANGCO,  RAINIER YU MD.', '50209182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '459', 'DOCTOR', 'PQW883', 'LUTANGCO,  RAINIER YU MD.', '50209182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '120', 'PSCD', 'F3H262', 'CALMA, ROWENA, MENDOZA', '20229382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '681', 'DOCTOR', 'NDL8016', 'CHAN, MARGARET GO', '5273D12E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '550', 'VISITING DOCTOR', 'NCC3433', 'ONG, CATHERINE JOIE', 'D0249382', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '568', 'VISITING DOCTOR', 'O0E066', 'SOLIVEN, ALBERT  UYTANLET', '40EB9B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '631', 'DOCTOR', 'NCP1344', 'UY, TERESITA DY M.D.', 'C302072D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '603', 'VISITING/PEDIA', 'NBG2905', 'CAPUL-SY-CHANGCO,EVANGELINE PINGOL', 'D2FEF32E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '603', 'VISITING/PEDIA', 'AAZ1592', 'CAPUL-SY-CHANGCO,EVANGELINE PINGOL', 'D2FEF32E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '553', 'DOCTOR', 'NDP1129', 'CASTILLO, WILBERTO', '00B48F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '245', 'VISITING DOCTOR', 'NBN1581', 'TAN, EDEN', '705A9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '108', 'DOCTOR', 'NCY8839', 'GABRIEL, MELCHOR', '50E59182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '682', 'VISITING DR.', 'NBM4419', 'ENRIQUEZ,JOSEPHINE,TE', '72373D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '682', 'VISITING DR.', 'NAG7139', 'ENRIQUEZ,JOSEPHINE,TE', '72373D2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '206', 'DOCTOR', 'NDN9213', 'NG, MERCY', 'B08D9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '363', 'DOCTOR', 'NDQ5535', 'YEO, MELLY', 'A0C99182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '469', 'DOCTOR', 'NDR2596', 'LORENZO, EMILIO M.D.', 'F00F9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '683', 'DOCTOR/ANES', 'R0I637', 'TAN, KRISTINE MAE, VELASCO', 'A1DFC02D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '684', 'VISITING DOCTOR', 'NCZ6953', 'SY ORTIN, TERESA, TAN', '8181852D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '684', 'VISITING DOCTOR', 'WBO127', 'SY ORTIN, TERESA, TAN', '8181852D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '605', 'DOCTOR', 'NDF4482', 'TEH, CHRISTINE M.D', 'E218CC2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'DOCTOR', 'NBQ6712', 'CO-YAP, BETTY', '803D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '566', 'DOCTOR', 'NCL3092', 'MADERA, JENNIFER', '40919782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '78', 'DOCTOR', 'NBO6712', 'CO-YAP, BETTY', '803D9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '307', 'DOCTOR', 'NBR3962', 'TAN, PATRICIA', 'B0269782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '676', 'CONSULTANT', 'NCY1598', 'LEH, FREDERICK ONG', 'B229F42E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'TZI185', 'CO, MANUEL', '3ADF36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'TQR451', 'CO, MANUEL', '3ADF36CD', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'TQR451', 'CO, MANUEL', '30449082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'TZI185', 'CO, MANUEL', '30449082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'TQR451', 'CO, MANUEL', '069CADCE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '71', 'PCCAI DIRECTOR', 'TZI185', 'CO, MANUEL', '069CADCE', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '685', 'DOCTOR', 'VD7963', 'CO, JONARD', 'D36DC42D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '685', 'DOCTOR', 'AAX8292', 'CO, JONARD ', 'D36DC42D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '685', 'DOCTOR', 'NVO325', 'CO, JONARD', 'D36DC42D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '52', 'DOCTOR', 'NCZ7001', 'CHUA CARL JAMES', '50F59482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '448', 'DOCTOR', 'NCZ7001', 'YU, MAYBELLINE', 'F0149282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '615', 'CGHMC COLLEGE', 'NEI1515', 'SALES, ARIS LALATA', 'D2FE162E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '331', 'DOCTOR', 'P6D130', 'TY, EDISON', 'E0E39982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '306', 'DOCTOR', 'P6E419', 'TAN, LOUIE T.', '70309882', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '422', 'VISITING', 'B6Y556', 'ONG, ERIC ', 'C0309582', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '237', 'DOCTOR', 'NDS5301', 'PASCUA, NESTOR..M.D.', 'B0B59182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '292', 'DOCTOR', 'NDB5383', 'TAN, PASCUAL MYRA', 'E0C79B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '292', 'DOCTOR', 'NAP8283', 'TAN, PASCUAL MYRA', 'E0C79B82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'AAJ9540', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'AAY3934', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'NAP5926', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'NDJ3348', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'AAJ9540', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'AAY3934', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'NAP5926', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '227', 'DOCTOR', 'NDJ3348', 'ONG, TANTUCO MARILOU', '50169482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '686', 'DOCTOR\\PEDIA', 'DCP3800', 'TAN, MARILOU, GO', 'E213252E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '367', 'DOCTOR', 'NBT8320', 'YU, TIMOTEO', '801F9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '121', 'VIP', 'WON708', 'RAGOS TY, RICARDO SARCON', '50CD9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '121', 'VIP', 'WIX376', 'RAGOS TY, RICARDO SARCON', '50CD9082', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '121', 'VIP', 'NBZ9681', 'RAGOS TY, RICARDO SARCON', '50CD9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '549', 'DOCTOR', 'NDD4759', 'YRUMA,MARIA ETHEL,MIRANDA', '60B29382', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '115', 'DOCTOR', 'NCD5795', 'GO-JULIO, JUSTINA', 'A0639482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '115', 'DOCTOR', 'NAC9032', 'GO-JULIO, JUSTINA', 'A0639482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '516', 'DOCTOR', 'NAE6596', 'SUA, ALEX SEE', '10129182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '636', 'VISITING CONSULTANT', 'NDS5319', 'KHOO, WINNE SHARON LIM', 'E200882E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '688', 'VISITING DOCTOR', 'XBM348', 'LI, KINGBHERLY  LU', '81A93E2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '688', 'VISITING DOCTOR', 'WOH752', 'LI, KINGBHERLY  LU', '81A93E2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '688', 'VISITING DOCTOR', 'ZAU735', 'LI, KINGBHERLY  LU', '81A93E2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '688', 'VISITING DOCTOR', 'ZFP323', 'LI, KINGBHERLY  LU', '81A93E2D', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '687', 'CGHC/FACULTY', 'NI9198', 'FEDERISO ANDREA LYN ANDRADA', 'C277532E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '129', 'DOCTOR', 'NAC6936', 'HABANA, LUIS MARTIN', 'A09C9882', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '530', 'VISITING DOCTOR', 'NCZ7172', 'SUN, CATHERINE GO', 'E0549482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '122', 'VIP', 'TIL286', 'CO,JULIAN ONG', '90349082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '147', 'DOCTOR', 'XOH135', 'LEE, JEFFREY', 'A0919982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '147', 'DOCTOR', 'XOH135', 'LEE, JEFFREY', 'A0919982', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '689', 'DOCTOR', 'NAE7785', 'VELASCO, REMI KARIS MANALANG', 'E2170F2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '691', 'DOCTOR', 'E2B067', 'LASQUITE, ANNE REICHEL MINAS', '91CA722D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '692', 'DOCTOR', 'XLU812', 'CO,JEFFREY SAMUEL,PANUGAYAN', 'B136682D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '66', 'VIP/PHARMACY HEAD', 'NQC706', 'CO, KAREN DY', '506E9282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '694', 'VISITING DR', 'TOL494', 'LADORES, CARLO, SELIBIO', '62CED92E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '693', 'DOCTOR', 'NAF4350', 'SENG, KENNY, SI', 'C127152D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '510', 'DOCTOR', 'NAT6204', 'CHUA, JOSEFINA', '30AF9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '26', 'DOCTOR', 'NBL1967', 'BRIOSO, EDNA FLORENCE', '50A79282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '655', 'DOCTOR', 'F1H277', 'DABU, INIGO BASTE MACLANG', '714D862D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '638', 'ASS. VISITING', 'C2Y754', 'SAHAGUN, MARIE JANICE AMANTE', '423E9C2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '137', 'DOCTOR', 'NAR1618', 'KOA, LEE ALEX', '805E9C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '693', 'DOCTOR', 'NAF4305', 'SENG, KENNY, SI', 'C127152D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '697', 'DOCTOR', 'UOS618', 'ONG, VIGAR UY RANOLA', '810F632D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '697', 'DOCTOR', 'AAO6823', 'ONG, VIGAR UY RANOLA', '810F632D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'NAF5754', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '508', 'DOCTOR', 'EAA4726', 'TAN-LEE, JOSEPHINE', '10D59A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'NAF5754', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '521', 'DOCTOR', 'EAA4726', 'LEE, ROMEO NELSON', 'C0D89282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '538', 'CONSULTANT', 'AAX8968', 'MACEDA, VICTOR J.', '90DD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '355', 'DOCTOR', 'GAI8453', 'YAP, ELENA M.D.', '80919082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '360', 'DOCTOR', 'NAE6720', 'ZAMORA, MA. SOCORRO', 'D0CD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '360', 'DOCTOR', 'NAM9743', 'ZAMORA, MA. SOCORRO', 'D0CD8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '174', 'DOCTOR', 'NBC5749', 'LIM, LUCILLE', '60829482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '266', 'DOCTOR', 'U8S776', 'SEBASTIAN, REYNALDO..M.D.', 'A0BE8F82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '430', 'VISITING DOCTOR', 'P7A991', 'TAN, IRENE PINEDA', 'E0019C82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '177', 'DOCTOR', 'NBN7285', 'LIM, RUBY', 'B04B9482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'NBN8488', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '381', 'DOCTOR', 'NCL7573', 'TUNG-LU , VICKY M.D.', '80E09182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '700', 'DOCTOR', 'CDJ9746', 'GATMAITAN , MARIA BENITA  TIGLAO', 'C114232D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '701', 'DOCTOR', 'ACA3228', 'SANTOS, CATHERINE TALAG', '617A6C2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '701', 'DOCTOR', 'NCX5253', 'SANTOS, CATHERINE TALAG', '617A6C2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '701', 'DOCTOR', 'ZAH556', 'SANTOS, CATHERINE TALAG', '617A6C2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '561', 'DOCTOR', 'NIK532', 'VILLANUEVA, JOHANS PLANA', '50999A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '353', 'DOCTOR', 'ASA9709', 'LIM, DARWIN M.D.', 'F00D9182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '183', 'DOCTOR', 'VFA251', 'ANG, EMELDA', 'B09D9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '469', 'DOCTOR', 'MDW305', 'LORENZO, EMILIO M.D.', 'F00F9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '469', 'DOCTOR', 'ZSA987', 'LORENZO, EMILIO M.D.', 'F00F9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '529', 'CONSULTANT', 'ZAX204', 'LU, CARLOS', 'C08E9082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '214', 'DOCTOR', 'XEM887', 'OLPINDO-MACARAEG, RUBY', '109A9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '221', 'DOCTOR', 'TQP425', 'ONG, REMEDIOS', '804B9482', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '222', 'DOCTOR', 'AOT150', 'ONG, ROSA', 'A05D9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '239', 'DOCTOR', 'AAY1980', 'PEREZ, JOCELYN', '60AA9182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '240', 'DOCTOR', 'AAY1980', 'PEREZ, MELCO', 'F0F69482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '258', 'DOCTOR', 'THC804', 'REGALADO-MAGINO, MA. VERONICA', '20899482', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '515', 'DOCTOR', 'ARA1336', 'SANTOS, STEWART SANTOS', '50A49982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '658', 'DOCTOR', 'NAW1616', 'SUN,EDELINE YAO', '91C7672D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '658', 'DOCTOR', 'NBI9047', 'SUN,EDELINE YAO', '91C7672D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '284', 'DOCTOR', 'NDO591', 'SY, JONATHAN', 'D0CB9982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '449', 'DOCTOR', 'TOZ773', 'SUN, SHARMAINE', '20E19082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '516', 'DOCTOR', 'VBA580', 'SUA, ALEX SEE', 'A0879182', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '610', 'DOCTOR', 'NPR8301', 'TAN-TINHAY, LORA MAY', 'C273632E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '291', 'DOCTOR', 'CSL835', 'TAN JR., FRANCISCO', 'D08C9782', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '301', 'DOCTOR', 'WOZ853', 'TAN, FREDERICK', '90889782', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '310', 'DOCTOR', 'EON588', 'TAN, STEPHANY', 'A0149982', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '559', 'DOCTOR', 'ZHM276', 'WONG, STEPHEN M.D.', '709F9A82', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '352', 'DOCTOR', 'ZHM276', 'WONG, JENNIFER M.D.', '205C9282', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '696', 'DOCTOR', 'PCQ858', 'BOLONG, DAVID', 'C279BE2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '262', 'DOCTOR', 'NDV1561', 'RUALES, ALLAN B..M.D.', '30A59082', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '696', 'DOCTOR', 'NGG3308', 'BOLONG, DAVID', 'C279BE2E', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '702', 'VISITING/DOCTOR', '7974UG', 'CHOI, JOSEPH TAMAYO YU', '5220C72E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '702', 'VISITING/DOCTOR', '373066', 'CHOI, JOSEPH TAMAYO YU', '5220C72E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '702', 'VISITING/DOCTOR', 'APA7922', 'CHOI, JOSEPH TAMAYO YU', '5220C72E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '288', 'DOCTOR', 'AAS7531', 'SY, TONG HUE', '60769182', '1', 0,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '703', 'DOCTOR', 'NVQ411', 'LEE, JUDY REMYLYN,TANG', '921C2E2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '703', 'DOCTOR', 'PQC816', 'LEE, JUDY REMYLYN,TANG', '921C2E2E', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '704', 'VISITING/DOCTOR', 'NCQ5518', 'CHUA ,JAMIE RONQUILLO', '711A012D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '705', 'VISITING/DOCTOR', 'NDV3239', 'HILARIO ,  ED-MARVIN CHUA', '61DFDE2D', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");
        db.execSQL("INSERT INTO " + VIP_TABLE_NAME + " VALUES(null, '92', 'DOCTOR', 'NOF6998', 'DELOS SANTOS, CYMBELLY', '90B69282', '1', 1,'2021-04-01 00:00:00', '2021-04-01 00:00:00')");

    }

}