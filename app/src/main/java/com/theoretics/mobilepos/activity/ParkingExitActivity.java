package com.theoretics.mobilepos.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.theoretics.mobilepos.R;
import com.theoretics.mobilepos.util.DBHelper;
import com.theoretics.mobilepos.bean.GLOBALS;
import com.theoretics.mobilepos.util.HexUtil;
import com.theoretics.mobilepos.util.NfcAutoCheck;
import com.theoretics.mobilepos.util.ReceiptUtils;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.rfcard.AidlRFCard;
import com.topwise.cloudpos.data.RFCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.theoretics.mobilepos.util.DBHelper.CARD_COLUMN_CARDCODE;
import static com.theoretics.mobilepos.util.DBHelper.CARD_COLUMN_LANE;
import static com.theoretics.mobilepos.util.DBHelper.CARD_COLUMN_PC;
import static com.theoretics.mobilepos.util.DBHelper.CARD_COLUMN_PLATE;
import static com.theoretics.mobilepos.util.DBHelper.CARD_COLUMN_TIMEIN;
import static com.theoretics.mobilepos.util.DBHelper.CARD_COLUMN_VEHICLE;
import static com.theoretics.mobilepos.util.DBHelper.SERVER_NAME;

public class ParkingExitActivity extends BaseActivity {

    public AidlRFCard rfcard = null;
    private NfcAutoCheck nfcAutoCheck = null;

    private Button regularBtn = null;
    private Button vipBtn = null;
    private Button dialysisBtn = null;
    private Button pwdSeniorBtn = null;

    private TextView cardNum = null;
    private EditText inputPlate = null;
    private TextView datetimeIN = null;
    private TextView datetimeOUT = null;
    private TextView durationElapsed = null;

    private DBHelper dbh;
    private String cardNumber = "";
    private String prevCardNumber = "";
    public String retStr = "";

    public long minutesElapsed = 0;
    public long hrsElapsed = 0;
    public double amountDue = 0;

    private int daysElapsed = 0;
    private int minsRemaining = 0;
    private int hrsRemaining = 0;
    ReceiptUtils rec = null;

    File internalfile;
    File sdfile;
    File sdfileCheck;
    //String root = Environment.getExternalStorageDirectory().toString();
    //String root = "sdcard/Download";
    String internalPath = "data/data/com.theoretics.mobilepos";
    String sdCardPath = "mnt/m_external_sd/Android/data/com.theoretics.mobilepos";
    String sdPath = "mnt/m_external_sd/Android/data/com.theoretics.mobilepos/files/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_exit);

        initView();
        dbh = new DBHelper(this);

        checkLog();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            int EXTERNAL_STORAGE_PERMISSION_CODE = 201;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_PERMISSION_CODE);
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.READ_EXTERNAL_STORAGE};
            // Verify permissions
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //request for access
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }

        }
        initiateFiles();
        /*
        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                cardNum.setText(cardNumber);
                System.out.println("ANGELO CARD:" + cardNumber);
            }
        }).start();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // Code here will run in UI thread
                cardNum.setText(cardNumber);
                System.out.println("ANGELO CARD:" + cardNumber);
            }
        });*/

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 25:
                Intent intent = new Intent(ParkingExitActivity.this, LogoutActivity.class);
                intent.putExtra("cardNum",cardNum.getText());
                startActivity(intent);
                return true;
            default:
                System.out.println("ANGELO :" + keyCode);
                return super.onKeyUp(keyCode, event);
        }
    }

    public String initiateFiles() {
        //file = context.getExternalFilesDir((String) null);
        internalfile = new File(internalPath);
        if(internalfile.exists() == false) {
            internalfile.mkdir();
        }
        sdfile = new File(sdCardPath);
        if(sdfile.exists() == false) {
            boolean b = sdfile.mkdirs();
            System.out.println(sdCardPath + " was created = " + b);
        }
        sdfile = new File(sdPath);
        if(sdfile.exists() == false) {
            boolean b = sdfile.mkdirs();
            System.out.println(sdCardPath + " was created = " + b);
        }
        return sdfile.getPath().toString();

    }

    protected void onStart(){
        super.onStart();

//checkLog();
    }

    private void initView() {
        regularBtn = (Button) findViewById(R.id.regularBtn);
        vipBtn = (Button) findViewById(R.id.vipBtn);
        dialysisBtn = (Button) findViewById(R.id.dialysisBtn);
        regularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GLOBALS.getInstance().setPlateNum(inputPlate.getText().toString());
                GLOBALS.getInstance().setReceiptCopyType("CUSTOMER COPY");
                Intent intent = new Intent(ParkingExitActivity.this, ComputationActivity.class);
                intent.putExtra("cardNum",cardNum.getText());
                intent.putExtra("inputPlate",inputPlate.getText().toString());

                intent.putExtra("isDiscounted",false);
                intent.putExtra("TRType","RM");
                intent.putExtra("daysElapsed", daysElapsed);
                intent.putExtra("hrsRemaining", hrsRemaining);
                intent.putExtra("minsRemaining", minsRemaining);

                startActivity(intent);
            }
        });
        pwdSeniorBtn = (Button) findViewById(R.id.pwdSeniorBtn);
        pwdSeniorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GLOBALS.getInstance().setPlateNum(inputPlate.getText().toString());
                GLOBALS.getInstance().setReceiptCopyType("CUSTOMER COPY");
                Intent intent = new Intent(ParkingExitActivity.this, ComputationActivity.class);
                intent.putExtra("cardNum",cardNum.getText());
                intent.putExtra("inputPlate",inputPlate.getText().toString());

                intent.putExtra("isDiscounted",true);
                intent.putExtra("TRType","S");
                intent.putExtra("daysElapsed", daysElapsed);
                intent.putExtra("hrsRemaining", hrsRemaining);
                intent.putExtra("minsRemaining", minsRemaining);

                startActivity(intent);
            }
        });
        vipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GLOBALS.getInstance().setPlateNum(inputPlate.getText().toString());
                GLOBALS.getInstance().setReceiptCopyType("CUSTOMER COPY");
                Intent intent = new Intent(ParkingExitActivity.this, ComputationActivity.class);
                intent.putExtra("cardNum",cardNum.getText());
                intent.putExtra("inputPlate",inputPlate.getText().toString());

                intent.putExtra("isDiscounted",false);
                intent.putExtra("TRType","V");
                intent.putExtra("daysElapsed", daysElapsed);
                intent.putExtra("hrsRemaining", hrsRemaining);
                intent.putExtra("minsRemaining", minsRemaining);

                startActivity(intent);
            }
        });
        dialysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GLOBALS.getInstance().setPlateNum(inputPlate.getText().toString());
                GLOBALS.getInstance().setReceiptCopyType("CUSTOMER COPY");
                Intent intent = new Intent(ParkingExitActivity.this, ComputationActivity.class);
                intent.putExtra("cardNum",cardNum.getText());
                intent.putExtra("inputPlate",inputPlate.getText().toString());

                intent.putExtra("isDiscounted",false);
                intent.putExtra("TRType","DS");
                intent.putExtra("daysElapsed", daysElapsed);
                intent.putExtra("hrsRemaining", hrsRemaining);
                intent.putExtra("minsRemaining", minsRemaining);

                startActivity(intent);
            }
        });

        cardNum = (TextView) findViewById(R.id.cardNum);
        datetimeIN = (TextView) findViewById(R.id.datetimeIN);
        datetimeOUT = (TextView) findViewById(R.id.datetimeOUT);
        inputPlate = (EditText) findViewById(R.id.inputPlateNo);
        durationElapsed = (TextView) findViewById(R.id.durationElapsed);
        rec = new ReceiptUtils();

    }

    private void checkLog() {
        //rec.createNewFile();
        //datetimeOUT.setText(GLOBALS.getInstance().getCashierID() + GLOBALS.getInstance().getCashierName()+GLOBALS.getInstance().getLoginID());
        if(dbh.isDeviceLoggedIN() == false) {
            Intent intent = new Intent(ParkingExitActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if (dbh.getMaster() == false) {
            dbh.initiateMaster();
        }

        //durationElapsed.setText(GLOBALS.getInstance().getCashierID() + GLOBALS.getInstance().getCashierName()+GLOBALS.getInstance().getLoginID());
        //
        //durationElapsed.setText(rec.getExternalFilesDir(getApplicationContext()));
        //rec.appendToFile("ANGELO");
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            rfcard = AidlRFCard.Stub
                    .asInterface(serviceManager.getRFIDReader());
            setOnCheckListen();
            start_autoRead();

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDestroy() {
        close();
        super.onDestroy();
    }

    private String getCardType(int cardtype) {
        String ret = "";
        switch (cardtype) {
            case RFCard.RFCardType.UNSUPPORTED:
                ret = "UNSUPPORTED";
                break;
            case RFCard.RFCardType.TYPEA:
                ret = "TYPEA";
                break;
            case RFCard.RFCardType.TYPEB:
                ret = "TYPEB";
                break;
            case RFCard.RFCardType.MIFARE_ONE:
                ret = "MIFARE_ONE";
                break;
            case RFCard.RFCardType.MIFARE_S50:
                ret = "MIFARE_S50";
                break;
            case RFCard.RFCardType.MIFARE_ONE_S70:
                ret = "MIFARE_ONE_S70";
                break;
            case RFCard.RFCardType.MIFARE_ULTRALIGHT_C:
                ret = "MIFARE_ULTRALIGHT_C";
                break;
            case RFCard.RFCardType.MIFARE_PLUS:
                ret = "MIFARE_PLUS";
                break;
            case RFCard.RFCardType.MIFARE_DESFIRE:
                ret = "MIFARE_DESFIRE";
                break;
            case RFCard.RFCardType.MIFARE_CPU:
                ret = "MIFARE_CPU";
                break;
            case RFCard.RFCardType.MIFARE_PRO:
                ret = "MIFARE_PRO";
                break;
            case RFCard.RFCardType.MIFARE_S50_PRO:
                ret = "MIFARE_S50_PRO";
                break;
            case RFCard.RFCardType.MIFARE_S70_PRO:
                ret = "MIFARE_S70_PRO";
                break;

        }
        return ret;
    }

    private void start_autoRead() {
        nfcAutoCheck.startAutoCheck(60);
        System.out.println("ANGELO Start auto check card");
    }

    private void stop_autoRead() {
        if (nfcAutoCheck != null) {
            nfcAutoCheck.stopAutoCheck();
            if (rfcard != null)
                close();
        }
        System.out.println("ANGELO Stoped auto check card");

    }

    public void close() {
        try {
            boolean flag = rfcard.close();
            if (flag) {
                System.out.println("ANGELO " + getStringByid(R.string.nfc_close_success));
            } else {
                System.out.println("ANGELO " + getStringByid(R.string.nfc_close_faile));
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setOnCheckListen() {
        if (rfcard != null) {
            nfcAutoCheck = new NfcAutoCheck(rfcard);
            nfcAutoCheck.setOnDataReceiveListener(new NfcAutoCheck.OnCheckCardListener() {
                @Override
                public void onCheckCardReceive(boolean isexist, int cardtype) {
                    if (isexist) {
                        //sendmessage("card is exists" + " Card type is " + getCardType(cardtype));
                        System.out.println("ANGELO Card Present:::");
                        cardNumber = getCardId();
                        //cardNum.setText("Checked");
                        stop_autoRead();
                        nfcAutoCheck.startAutoCheck(60 * 1000);
                    } else {
                        System.out.println("ANGELO Card ABSENT:::");
                        //start_autoRead();
                        //sendmessage("card is not exists");
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    datetimeIN.setText("");
                                    datetimeOUT.setText("");
                                    durationElapsed.setText("");
                                    inputPlate.setText("");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        nfcAutoCheck.startAutoCheck(60 * 1000);
                    }

                }

                @Override
                public void onCheckCardReceive(boolean isexist, int cardtype, String uid) {

                }
            });
        }
    }

    public String getCardId() {
        try {
            Cursor res = null;
            byte[] id = rfcard.getCardCode();
            if (id != null) {
                String cardID = HexUtil.bcd2str(id);
                //sendmessage("Uid:" + cardID);
                cardNumber = cardID;
                System.out.println("ANGELO Uid:" + cardID);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (prevCardNumber.compareToIgnoreCase(cardNumber) != 0) {
                            cardNum.setText(cardNumber);
                            String cardStatus = getExitCard(SERVER_NAME + "/cardCheck.php?rfid=", cardNumber);
                            System.out.println("ANGELO getExitCard:" + retStr + cardStatus);
                        }
                        prevCardNumber = cardNumber;
                    }
                });
                return cardID;
            } else {

            }

        } catch (RemoteException e) {

        }
        return "Empty";
    }

    //this method is actually fetching the json string
    public String getExitCard(final String urlWebService, final String rfid) {

        //final String[] retStr = new String[1];
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                retStr = s;
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    System.out.println("ANGELO : " + urlWebService + rfid);
                    //creating a URL
                    String u = urlWebService + rfid;
                    URL url = new URL(u);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;
                    int i = 0;
                    //reading until we don't find null

                    while ((json = bufferedReader.readLine()) != null) {
                        i++;
                        //appending it to string builder
                        sb.append(json + "\n");
                        System.out.println("ANGELO : [" + i + "]" + json + "\n");
                        //PROCESS JSON to DB
                        HashMap<String, String> cardData = grabCardFromServer(dbh, json);
                        //dbh.updateLDM(ldm);
                    }
                    if (sb.toString().compareToIgnoreCase("") == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                datetimeIN.setText("");
                                datetimeOUT.setText("");
                                durationElapsed.setText("");
                                inputPlate.setText("");
                            }
                        });
                    } else {
                        System.out.println("ANGELO : [NO DATA FOUND]");
                    }
                    System.out.println("ANGELO : [" + i + "]" + sb.toString());
                    //finally returning the read string
                    //Date now = new Date();
                    //String n = sdf.format(now);
                    //System.out.println(n);
                    //dbh.updateLDC(ldm);
                    //retJSON[0] = readJSON(json);
                    return "EXISTS";
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
        return "";
    }

    private HashMap<String, String> grabCardFromServer(DBHelper dbh, String msg) {
        //String url = "http://api.androidhive.info/contacts/";
        //url = "http://192.168.1.116/timecheck.php";
        //HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        //String jsonStr = sh.makeServiceCall(url);
        final JSONObject c;
        try {
            c = new JSONObject(msg);

            // tmp hash map for single contact
            HashMap<String, String> cardData = new HashMap<>();

            // adding each child node to HashMap key => value
            cardData.put(CARD_COLUMN_CARDCODE, c.getString(CARD_COLUMN_CARDCODE));
            cardData.put(CARD_COLUMN_LANE, c.getString(CARD_COLUMN_LANE));
            cardData.put(CARD_COLUMN_PC, c.getString(CARD_COLUMN_PC));
            cardData.put(CARD_COLUMN_PLATE, c.getString(CARD_COLUMN_PLATE));
            cardData.put(CARD_COLUMN_VEHICLE, c.getString(CARD_COLUMN_VEHICLE));
            cardData.put(CARD_COLUMN_TIMEIN, c.getString(CARD_COLUMN_TIMEIN));
            cardData.put("TimeOut", c.getString("TimeOut"));

            // adding contact to contact
            //sendmessage(c.getString("id") + " : " + c.getString("name") + " : " + c.getString("email") + " : " + c.getString("mobile"));
            System.out.println("ANGELO: " + c.getString(CARD_COLUMN_CARDCODE) + " : " + c.getString("TimeOut") + " : " + c.getString(CARD_COLUMN_LANE)
                    + " : " + c.getString(CARD_COLUMN_TIMEIN) + " : " + c.getString(CARD_COLUMN_VEHICLE)
                    + " : " + c.getString(CARD_COLUMN_PC) + " : " + c.getString(CARD_COLUMN_PLATE));

            GLOBALS.getInstance().setEntID(c.getString(CARD_COLUMN_PC));

            String pattern = "yyyy-MM-dd HH:mm:ss";
            final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            //final Date now = new Date();
            final Date nowFromServer = sdf.parse(c.getString("TimeOut"));
            Date dtIN = sdf.parse(c.getString(CARD_COLUMN_TIMEIN));
            System.out.println("ANGELO : dtIN=" + dtIN);
            //final String n = sdf.format(c.getString("TimeOut"));
            //System.out.println("ANGELO : now=" + n);
            long diffInMills = nowFromServer.getTime() - dtIN.getTime();
            System.out.println("ANGELO : diffInMills=" + diffInMills);

            //final long diffMins = TimeUnit.MINUTES.convert(diffInMills, TimeUnit.MILLISECONDS);
           // final long diffHours = TimeUnit.HOURS.convert(diffMins, TimeUnit.MINUTES);
           // final long diffDays = TimeUnit.DAYS.convert(diffHours, TimeUnit.HOURS);

            final long secondsElapsed = diffInMills / 1000;
            int minutesElapsed = (int) (secondsElapsed / 60);
            int hrsElapsed = minutesElapsed / 60;
            daysElapsed = hrsElapsed / 24;
            minutesElapsed = minutesElapsed - (hrsElapsed * 60);
            hrsElapsed = hrsElapsed - (daysElapsed * 24);

            minsRemaining = minutesElapsed;
            hrsRemaining = hrsElapsed;

            System.out.println("ANGELO : seconds Elapsed [" + secondsElapsed + "]");
            System.out.println("ANGELO : minutes Elapsed [" + minutesElapsed + "]");

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        GLOBALS.getInstance().setNewCard(true);
                        String dtIN = c.getString(CARD_COLUMN_TIMEIN);
                        String dtOUT = sdf.format(nowFromServer);

                        datetimeIN.setText(dtIN);
                        datetimeOUT.setText(dtOUT);
                        durationElapsed.setText(daysElapsed + " days "  + hrsRemaining + " hours " + minsRemaining + " mins ");

                        GLOBALS.getInstance().setDatetimeIN(dtIN);
                        GLOBALS.getInstance().setDatetimeOUT(dtOUT);
                        GLOBALS.getInstance().setDaysElapsed(daysElapsed + "");
                        GLOBALS.getInstance().setHoursElapsed(hrsRemaining + "");
                        GLOBALS.getInstance().setMinutesElapsed(minsRemaining + "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            //For Rooted Systems only
            //changeSystemTime(nowFromServer.getYear() + "",nowFromServer.getMonth() + "",nowFromServer.getDate() + "",nowFromServer.getHours() + "",
             //       nowFromServer.getMinutes()+"",nowFromServer.getSeconds()+"");
            //String cardID, String parkerType, String plateNumber, String name, String cardNumber, int maxUse, int status, String ldc, String ldm)
            //boolean inserted = dbh.insertParkerCard(c.getString(CARD_COLUMN_CARDCODE), c.getString(CARD_COLUMN_VEHICLE), c.getString(CARD_COLUMN_PLATE),
            //        c.getString(CARD_COLUMN_LANE), c.getString(CARD_COLUMN_PC), c.getString(CARD_COLUMN_TIMEIN));
            return cardData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private void changeSystemTime(String year,String month,String day,String hour,String minute,String second){
        try {

            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            //MMddhhmmyy
            String command = "date "+month+day+hour+minute+year+"\n";
            // Log.e("command",command);
            os.writeBytes(command);
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();

        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}