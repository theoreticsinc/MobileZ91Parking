package com.theoretics.mobilepos.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptUtils {

    public String internalfileName;
    public String sdfileName;
    //public String baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator
    //        + "Android/data/";
    //File sdCard = Environment.getExternalStorageDirectory();
    //File dir = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
    File internalfile;
    File sdfile;
    File sdfileCheck;
    //String root = Environment.getExternalStorageDirectory().toString();
    //String root = "sdcard/Download";
    String internalPath = "data/data/com.theoretics.mobilepos";
    String sdCardPath = "mnt/m_external_sd/Android/data/com.theoretics.mobilepos";
    String sdPath = "mnt/m_external_sd/Android/data/com.theoretics.mobilepos/files/";
    Context context = null;

    public String initiate(Context c) {
        context = c;
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

    public void appendToFile(String data) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("MMd.yyyy");
            Date now = new Date();
            //internalPath = context.getExternalFilesDir((String) null) + "";
            //File file = new File(baseDir + File.separator + "MyResearchFile.txt");
            sdfileCheck = new File(sdCardPath);
            sdfile = new File(sdCardPath + File.separator + "R02" + sdf.format(now));
            internalfile = new File(internalPath + File.separator + "R02" + sdf.format(now));

            sdfileName = sdfile.getPath().toString();
            internalfileName = internalfile.getPath().toString();

            if (sdfileCheck.exists() == false) {
                internalfile.createNewFile();
                FileOutputStream fOS = new FileOutputStream(internalfileName, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOS);
                outputStreamWriter.write(data + "\n");
                outputStreamWriter.close();
            } else {
                sdfile.createNewFile();
                FileOutputStream fOS = new FileOutputStream(sdfileName, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOS);
                outputStreamWriter.write(data + "\n");
                outputStreamWriter.close();
            }
        }
        catch (IOException e) {
            Log.e("Exception", sdCardPath + " File write failed: " + e.toString());
        }
    }

    public String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
