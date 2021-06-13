package com.theoretics.mobilepos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.theoretics.mobilepos.R;
import com.theoretics.mobilepos.adapter.OperatorInfoAdapter;
import com.theoretics.mobilepos.bean.OperatorInfo;
import com.theoretics.mobilepos.util.BaseUtils;
import com.theoretics.mobilepos.util.DecodeUtils;
import com.theoretics.mobilepos.util.HexUtil;
import com.theoretics.mobilepos.util.PBOCLIB;
import com.theoretics.mobilepos.util.QRCodeUtil;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.buzzer.AidlBuzzer;
import com.topwise.cloudpos.aidl.camera.AidlCameraScanCode;
import com.topwise.cloudpos.aidl.camera.AidlCameraScanCodeListener;
import com.topwise.cloudpos.aidl.decoder.AidlDecoderManager;
import com.topwise.cloudpos.aidl.emv.EmvTransData;

import com.topwise.cloudpos.aidl.led.AidlLed;

import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.PrintItemObj;
import com.topwise.cloudpos.aidl.psam.AidlPsam;
import com.topwise.cloudpos.data.AidlScanParam;
import com.topwise.cloudpos.data.PrinterConstant;
import com.topwise.cloudpos.data.PsamConstant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Com.String2Bitmap.BitmapUtil;
import Com.String2Bitmap.StringBitmapParameter;

public class SwipeCardActivity extends BaseActivity {
    private ListView listView;
    private List<OperatorInfo> operatorInfoList = new ArrayList<>();
    private OperatorInfoAdapter adapter = null;

    private TextView txt_log = null;

    private final int SHOW_MESSAGE = 0;
    private String TAG = "SwipeCardActivity";
    private String strmess = "";
    private boolean isSwipeCard = false;

    private String activity_name = "";


    //pboc
    private PBOCLIB pboclib = new PBOCLIB();
    byte[] lastapdu = null;
    private boolean isReadCardId = false;



    private EmvTransData transData = null;

    //打印
    private AidlPrinter printerDev = null;

    //PSAM
    private AidlPsam psam = null;
    private AidlDeviceService deviceManager = null;


    private AidlCameraScanCode iScanner = null;


    private AidlDecoderManager iDecoder;
    private static final String QR_DECODE_DRAWABLE_NAME = "tp_decode_check";


    private AidlBuzzer iBeeper;

    private AidlLed iLed;
    Thread thread_led = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        initView();
        initData();

//        try {
//            psam = AidlPsam.Stub.asInterface(deviceManager
//                    .getPSAMReader(PsamConstant.PSAM_DEV_ID_1));
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    private void initView()
    {
        String title = "";
        Intent intent = this.getIntent();
        if(intent!=null)
        {
            title = intent.getExtras().getString("Name");
            activity_name = intent.getExtras().getString("Val");
        }
        listView = (ListView)findViewById(R.id.list);
        listView.setOnItemClickListener(onItemClickListener);
        txt_log = (TextView)findViewById(R.id.txt_log);
        txt_log.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.header_title)).setText(title);
    }

    private void initData()
    {
        operatorInfoList.clear();
        if(activity_name.equals(BaseUtils.ACTIVITY_NAME_SWIPE)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.getTrackData), "getTrackData".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.getEncryptTrackData), "getEncryptTrackData".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.getEncryptFormatTrackData), "getEncryptFormatTrackData".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.cancelSwipe), "cancelSwipe".toLowerCase()));
        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_IC)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_open), "ic_open".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_reset), "ic_reset".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_exists), "ic_exists".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_apdu), "ic_apdu".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_halt), "ic_halt".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_pboc), "ic_pboc".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_close), "ic_close".toLowerCase()));

        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_NFC)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.nfc_open), "nfc_open".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.nfc_cardtype), "nfc_cardtype".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.nfc_reset), "nfc_reset".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.nfc_cardtype), "nfc_cardtype".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_halt), "ic_halt".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_pboc), "ic_pboc".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.ic_close), "ic_close".toLowerCase()));

        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_PRINT)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_status), "print_status".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_text), "print_text".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_Arabic), "print_Arabic".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_sabra), "print_sabra".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_code), "print_code".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_pic), "print_pic".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.print_qrcode), "print_qrcode".toLowerCase()));
        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_PSAM)) {
            findViewById(R.id.psam_slot).setVisibility(View.VISIBLE);
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.psam_open), "psam_open".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.psam_reset), "psam_reset".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.psam_apdu), "psam_apdu".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.psam_halt), "psam_halt".toLowerCase()));
            ((RadioGroup)findViewById(R.id.rgpsam_slot)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch(checkedId)
                    {
                        case R.id.psam_slot1:
                            getpsam(1);
                            break;
                        case R.id.psam_slot2:
                            getpsam(2);
                            break;
                    }
                }
            });

        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_BARCODE)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.qrcode), "qrcode".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.qrcode_stop), "qrcode_stop".toLowerCase()));
        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_DECODE)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.decode_init), "decode_init".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.decode1), "decode1".toLowerCase()));
        }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_BEEP)) {
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.beep_start), "beep_start".toLowerCase()));
            operatorInfoList.add(new OperatorInfo(this.getString(R.string.beep_stop), "beep_stop".toLowerCase()));
        }

        adapter = new OperatorInfoAdapter(this,operatorInfoList);
        adapter.setData(operatorInfoList);
        listView.setAdapter(adapter);
    }


    private final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position>operatorInfoList.size()) return;
            String operatorname = operatorInfoList.get(position).get_OperatorName();
            String operatorvalue = operatorInfoList.get(position).get_OperatorValue();
            txt_log.setText("");
            if(activity_name.equals(BaseUtils.ACTIVITY_NAME_PRINT)) {
                if (operatorvalue.toLowerCase().equals("print_status".toLowerCase())) {
                    txt_log.setText("");
                    getPrintState();
                } else if (operatorvalue.toLowerCase().equals("print_text".toLowerCase())) {
                    txt_log.setText("");
                    printText_test1();


                    //printText_Vietnamese();

                    //printQrImageTest();

                    // printText_xili();
                    //printText_test2();
                }else if (operatorvalue.toLowerCase().equals("print_Arabic".toLowerCase())) {
                    txt_log.setText("");
                    printText_Arabic();



                }else if (operatorvalue.toLowerCase().equals("print_sabra".toLowerCase())) {
                    txt_log.setText("");
                    printText_Sabra();



            }else if (operatorvalue.toLowerCase().equals("print_code".toLowerCase())) {
                    txt_log.setText("");
                    printBarCode();
                }else if (operatorvalue.toLowerCase().equals("print_pic".toLowerCase())) {
                    txt_log.setText("");
                    printBitmap();
                }else if (operatorvalue.toLowerCase().equals("print_qrcode".toLowerCase())) {
                    txt_log.setText("");
                    printQrCode();
                }
            }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_PSAM)) {
                if (operatorvalue.toLowerCase().equals("psam_open".toLowerCase())) {
                    txt_log.setText("");
                    psamopen();
                } else if (operatorvalue.toLowerCase().equals("psam_reset".toLowerCase())) {
                    txt_log.setText("");
                    psamreset();
                }else if (operatorvalue.toLowerCase().equals("psam_apdu".toLowerCase())) {
                    txt_log.setText("");
                    psamapducmd();
                }else if (operatorvalue.toLowerCase().equals("psam_halt".toLowerCase())) {
                    txt_log.setText("");
                    psamclose();
                }
            }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_BARCODE)) {
                if (operatorvalue.toLowerCase().equals("qrcode".toLowerCase())) {
                    txt_log.setText("");
                    backScan();
                } else if (operatorvalue.toLowerCase().equals("qrcode_stop".toLowerCase())) {
                    txt_log.setText("");
                    stopScan();
                }
            }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_DECODE)) {
                if (operatorvalue.toLowerCase().equals("decode_init".toLowerCase())) {
                    txt_log.setText("");
                    decode_init();
                } else if (operatorvalue.toLowerCase().equals("decode1".toLowerCase())) {
                    txt_log.setText("");
                    decoder();
                }
            }else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_BEEP)) {
                if (operatorvalue.toLowerCase().equals("beep_start".toLowerCase())) {
                    startbeep();
                } else if (operatorvalue.toLowerCase().equals("beep_stop".toLowerCase())) {
                    txt_log.setText("");
                    stopbeep();
                }
            }
        }
    };





    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SHOW_MESSAGE:
                    String mess = msg.obj.toString();
                    txt_log.append(mess + "\n");
                    int offset=txt_log.getLineCount()*txt_log.getLineHeight();
                    if(offset>txt_log.getHeight()){
                        txt_log.scrollTo(0,offset-txt_log.getHeight());
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


    private void sendmessage(String mess)
    {
        Message msg = mHandler.obtainMessage();
        msg.what =SHOW_MESSAGE;
        msg.obj=mess;
        mHandler.sendMessage(msg);
    }







    /*
    打印  print
     */

    private String getCurTime(){
        Date date =new Date(System.currentTimeMillis());
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String time = format.format(date);
        return time;
    }


    public void printQrImageTest() {
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.emv);
        Bitmap qrcodeBitmap = QRCodeUtil.createQRImage("123456789", 250, 250, null);
        Bitmap mergeBitmap = mergeBitmap_LR(logoBitmap, qrcodeBitmap, true);
        String startTime = getCurTime();
        sendmessage(getStringByid(R.string.print_begin) + startTime);
        try {
            this.printerDev.printBmp(0, mergeBitmap.getWidth(), mergeBitmap.getHeight(), mergeBitmap, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });

            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Combine two bitmap overlays into one bitmap, splicing left and right
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax Whether the bitmap with large width is used as the criterion, true is equal-ratio stretching of small graph and false is equal-ratio compression of large graph.
     * @return
     */
    public Bitmap mergeBitmap_LR(Bitmap leftBitmap, Bitmap rightBitmap, boolean isBaseMax) {

        if (leftBitmap == null || leftBitmap.isRecycled()
                || rightBitmap == null || rightBitmap.isRecycled()) {
            return null;
        }
        int height = 0; // The height after splicing should be large or small according to the parameters.
        if (isBaseMax) {
            height = leftBitmap.getHeight() > rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        } else {
            height = leftBitmap.getHeight() < rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        }

        // Bitmap after scaling
        Bitmap tempBitmapL = leftBitmap;
        Bitmap tempBitmapR = rightBitmap;

        if (leftBitmap.getHeight() != height) {
            tempBitmapL = Bitmap.createScaledBitmap(leftBitmap, (int)(leftBitmap.getWidth()*1f/leftBitmap.getHeight()*height), height, false);
        } else if (rightBitmap.getHeight() != height) {
            tempBitmapR = Bitmap.createScaledBitmap(rightBitmap, (int)(rightBitmap.getWidth()*1f/rightBitmap.getHeight()*height), height, false);
        }

        // Width after splicing
        int width = tempBitmapL.getWidth() + tempBitmapR.getWidth();

        // Output bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        Rect leftRect = new Rect(0, 0, tempBitmapL.getWidth(), tempBitmapL.getHeight());
        Rect rightRect  = new Rect(0, 0, tempBitmapR.getWidth(), tempBitmapR.getHeight());

        // The right side of the graph needs to be plotted in the same position, shifting to the right the width and height of the left side of the graph are the same.
        Rect rightRectT  = new Rect(tempBitmapL.getWidth(), 0, width, height);

        canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null);
        canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null);
        return bitmap;
    }




    public void printQrCode(){
        Bitmap qrcodeBitmap = QRCodeUtil.createQRImage("123456789",300,300,null);
        try{
            this.printerDev.printBmp(0, qrcodeBitmap.getWidth(), qrcodeBitmap.getHeight(), qrcodeBitmap, new AidlPrinterListener.Stub() {
                @Override
                public void onPrintFinish() throws RemoteException {
                    sendmessage(getStringByid(R.string.print_success));
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 获取打印机状态
     * @createtor：Administrator
     * @date:2015-8-4 下午2:18:47
     */
    public void getPrintState(){
        try {
            int printState = printerDev.getPrinterState();
            sendmessage(getStringByid(R.string.get_print_status) + printState);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 打印文本
     * @createtor：Administrator
     * @date:2015-8-4 下午2:19:28
     */
    public void printText(){
        try {
            String startTime = getCurTime();
            sendmessage(getStringByid(R.string.print_begin) + startTime);
            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("مرحبا  سمارت  الصين  أيضا في العالم"));
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("مرحبا  سمارت  الصين  أيضا في العالم",24));
//
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("در حال حاضر من در دانشگاه مطالعات بین المللی شانگهای مطالعه ، می"));
//
//
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("در حال حاضر من در دانشگاه مطالعات بین المللی شانگهای مطالعه ، می",24));
//
//                    add(new PrintItemObj("                         "));
//                    add(new PrintItemObj("                         "));

                    add(new PrintItemObj(getStringByid(R.string.print_data1)));
                    add(new PrintItemObj(getStringByid(R.string.print_data1)));

                    add(new PrintItemObj(getStringByid(R.string.print_data2),24));
                    add(new PrintItemObj(getStringByid(R.string.print_data2),24));
                    add(new PrintItemObj(getStringByid(R.string.print_data3),8,true));
                    add(new PrintItemObj(getStringByid(R.string.print_data3),8,true));

                    add(new PrintItemObj(getStringByid(R.string.print_data4),8,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj(getStringByid(R.string.print_data4),8,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj(getStringByid(R.string.print_data5),8,false, PrintItemObj.ALIGN.CENTER));
                    add(new PrintItemObj(getStringByid(R.string.print_data5),8,false, PrintItemObj.ALIGN.CENTER));
                    add(new PrintItemObj(getStringByid(R.string.print_data6),8,false, PrintItemObj.ALIGN.RIGHT));
                    add(new PrintItemObj(getStringByid(R.string.print_data6),8,false, PrintItemObj.ALIGN.RIGHT));
                    add(new PrintItemObj(getStringByid(R.string.print_data7),8,false, PrintItemObj.ALIGN.LEFT,true));
                    add(new PrintItemObj(getStringByid(R.string.print_data7),8,false, PrintItemObj.ALIGN.LEFT,true));
                    add(new PrintItemObj(getStringByid(R.string.print_data8),8,false, PrintItemObj.ALIGN.LEFT,false,true));
                    add(new PrintItemObj(getStringByid(R.string.print_data8),8,false, PrintItemObj.ALIGN.LEFT,false,false));
                    add(new PrintItemObj(getStringByid(R.string.print_data9),8,false, PrintItemObj.ALIGN.LEFT,false,true,40));
                    add(new PrintItemObj(getStringByid(R.string.print_data9),8,false, PrintItemObj.ALIGN.LEFT,false,true,83));
                    add(new PrintItemObj(getStringByid(R.string.print_data10),8,false, PrintItemObj.ALIGN.LEFT,false,true,29,25));
                    add(new PrintItemObj(getStringByid(R.string.print_data10),8,false, PrintItemObj.ALIGN.LEFT,false,true,29,25));
                    add(new PrintItemObj(getStringByid(R.string.print_data11),8,false, PrintItemObj.ALIGN.LEFT,false,true,29,0,40));
                    add(new PrintItemObj(getStringByid(R.string.print_data11),8,false, PrintItemObj.ALIGN.LEFT,false,true,29,0,40));
                }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 打印越南语
     */
    public void printText_Vietnamese()
    {
        try {
            this.printerDev.setPrinterGray(4);
            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
                    //add(new PrintItemObj("С открытием своего бизнеса все предельно ясно, знание китайского языка дает огромные конкурентные преимущества при старте и развитии собственного бизнеса: самостоятельный поиск партнеров, более выгодные условия сотрудничества (цены, сроки, качество), виденье новых возможностей. Тем не менее не всем по душе бизнес и многие предпочтут карьерный рост в успешных компаниях. Поэтому ниже мы больше поговорим именно о трудоустройстве в западных или Казахстанских компаниях. ",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                    //String str = "مخابز الأمين ذ.ذ.م";
                    String str = "Cùng một thế giới, cùng một giấc mơ.";
                    add(new PrintItemObj(str,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj(str,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));



                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj(new StringBuilder(str).reverse().toString(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj(new StringBuilder(str).reverse().toString(),PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void printText_xili()
    {
        try {
            this.printerDev.setPrinterGray(4);
            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
                    //add(new PrintItemObj("С открытием своего бизнеса все предельно ясно, знание китайского языка дает огромные конкурентные преимущества при старте и развитии собственного бизнеса: самостоятельный поиск партнеров, более выгодные условия сотрудничества (цены, сроки, качество), виденье новых возможностей. Тем не менее не всем по душе бизнес и многие предпочтут карьерный рост в успешных компаниях. Поэтому ниже мы больше поговорим именно о трудоустройстве в западных или Казахстанских компаниях. ",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                    //String str = "مخابز الأمين ذ.ذ.م";
                    String str = "éřťýúíìôpášďfģhjľžxčvbňm@#_€ &-+()/@№_&_± ()/¿¡;:'\"*©®£¥$¢√π×¶∆\\}==°^¢$¥£";
                    add(new PrintItemObj(str,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj(str,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));



                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj(new StringBuilder(str).reverse().toString(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj(new StringBuilder(str).reverse().toString(),PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void printText_test1()
    {
        try {
            this.printerDev.setPrinterGray(4);
            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
                    //add(new PrintItemObj("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

//                    add(new PrintItemObj("--------------------------------",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("Name               " + "Qty " + "Rat Amt",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("वेज चाऊमीन (नूडल्स) 7 50.0 350.0",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));


//                    add(new PrintItemObj("Dec 30,2018 19:52:53",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("Receipt Number:123456",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("Token:123-3456-7890",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                    add(new PrintItemObj("Quittung       27.02.2018 12:01",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Quittung       27.02.2018 12:01",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Fahrzeug: RA-SE 907",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Ordnungs-Nr:907",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Fanrer:   Peters Rolf",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("TAID:     18000000015",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Name:",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Name",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Name",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("name",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Name:",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Name:",PrinterConstant.FontSize.XLARGE,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("------------------------------------------",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Von:",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("------------------------------------------",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("Nach:",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("------------------------------------------",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                    add(new PrintItemObj("Total:    9,00 EUR",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("MwST  7%:  0,59 EUR(enth.)",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("MwST.  7%:  0,59 EUR(enth.)",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("MwST.  7%:  0,59 EUR(enth.)",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    private Bitmap creatImage() {
        try {
//            InputStream ins = getAssets().open("res" + File.separator + "print.bmp");
//            Bitmap imageBitmap = BitmapFactory.decodeStream(ins);
            ArrayList<StringBitmapParameter> mParameters = new ArrayList<>();
            StringBitmapParameter mParameter = new StringBitmapParameter();
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("Default font--------------------", Layout.Alignment.ALIGN_CENTER));
            mParameters.add(new StringBitmapParameter("Print Demo",Layout.Alignment.ALIGN_CENTER,50,10,true,false,false));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("لتعبئة الرصيد اضغط رقم", Layout.Alignment.ALIGN_CENTER));
            mParameters.add(new StringBitmapParameter("*122* متبوعا بالرقم السريو من ثم#", Layout.Alignment.ALIGN_CENTER));
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_CENTER));
            mParameters.add(new StringBitmapParameter("--------------------------------", Layout.Alignment.ALIGN_CENTER));
            mParameter = new StringBitmapParameter("3043010573281063333333333333");
            mParameter.setBold(true);  //Set bold default false
            mParameter.setUnderline(true); //set Underline default false
            mParameter.setItalics(true); //set Italics default false
            mParameter.setLineSpacing(10);//Set the spacing between two lines default 0
            mParameter.setFontSize(20);//Set fontsize default 20
            mParameters.add(mParameter);
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_LEFT,20,5,true,false,false));
            mParameters.add(new StringBitmapParameter("--------------------------------", Layout.Alignment.ALIGN_CENTER));
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_RIGHT,20,5,true,false,false));
            mParameters.add(new StringBitmapParameter(""));

            String fontpath = "fonts/Newfolder10/ARIALN.TTF";
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("ARIALN font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("لتعبئة الرصيد اضغط رقم", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("*122* متبوعا بالرقم السريو من ثم#", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));

            fontpath = "fonts/Newfolder10/arialbd.ttf";
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("arialbd font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("لتعبئة الرصيد اضغط رقم", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("*122* متبوعا بالرقم السريو من ثم#", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));

            fontpath = "fonts/Newfolder10/arial.ttf";
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("arial font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("لتعبئة الرصيد اضغط رقم", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("*122* متبوعا بالرقم السريو من ثم#", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));

            fontpath = "fonts/Newfolder10/ariali.ttf";
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("ariali font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("لتعبئة الرصيد اضغط رقم", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("*122* متبوعا بالرقم السريو من ثم#", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            fontpath = "fonts/Newfolder10/calibrib.ttf";
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter("calibrib font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("لتعبئة الرصيد اضغط رقم", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("*122* متبوعا بالرقم السريو من ثم#", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));

            mParameters.add(new StringBitmapParameter(""));
            fontpath = "";
            mParameters.add(new StringBitmapParameter("default font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_LEFT,30,5,true,false,false,fontpath));
            mParameters.add(new StringBitmapParameter(""));
            fontpath = "fonts/Nunito.ttf";
            mParameters.add(new StringBitmapParameter("Nunito font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_LEFT,30,5,true,false,false,fontpath));
            mParameters.add(new StringBitmapParameter(""));
            fontpath = "fonts/BrushScriptStd.otf";
            mParameters.add(new StringBitmapParameter("BrushScriptStd font---------------", Layout.Alignment.ALIGN_CENTER,30,0,false,false,false,fontpath));
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_LEFT,30,5,true,false,false,fontpath));


            mParameter = new StringBitmapParameter("3043010573281063333333333333");
            mParameter.setBold(true);  //Set bold default false
            mParameter.setUnderline(true); //set Underline default false
            mParameter.setItalics(true); //set Italics default false
            mParameter.setLineSpacing(10);//Set the spacing between two lines default 0
            mParameter.setFontSize(20);//Set fontsize default 20
            mParameters.add(mParameter);
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_LEFT,20,5,true,false,false));
            mParameters.add(new StringBitmapParameter("--------------------------------", Layout.Alignment.ALIGN_CENTER));
            mParameters.add(new StringBitmapParameter("To Top-up Your Balance Please Enter *112* Followed By Pin And #", Layout.Alignment.ALIGN_RIGHT,20,5,true,false,false));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));





            BitmapUtil bitmapUtil = new BitmapUtil();
            Bitmap textBitmap = bitmapUtil.StringListtoBitmap(this, mParameters);

            Bitmap logo1 = BitmapFactory.decodeResource(getResources(),R.drawable.logo1);
            logo1 = bitmapUtil.fitBitmap(logo1,300);
            logo1 = bitmapUtil.addTextInBitmapFoot(logo1,"Alriyada",40);
            Bitmap logo2 = BitmapFactory.decodeResource(getResources(),R.drawable.logo2);
            logo2 = bitmapUtil.fitBitmap(logo2,300);
            logo2 = bitmapUtil.addTextInBitmapFoot(logo2,"Bahe wallet",40);

            Bitmap logo = bitmapUtil.addTwoLogo(logo1,logo2);

            Bitmap result = bitmapUtil.addBitmapInHead(logo,textBitmap);


            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    打印阿拉伯语
     */
    public void printText_Arabic()
    {
        try {
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
            Bitmap bitmap = creatImage();
            String startTime = getCurTime();
            sendmessage(getStringByid(R.string.print_begin) + startTime);
            this.printerDev.printBmp(0, bitmap.getWidth(), bitmap.getHeight(), bitmap, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
//            this.printerDev.printBarCode(-1, 162, 18, 65, "23418753401", new PrintStateChangeListener());
//
//            Bitmap qrcodeBitmap = QRCodeUtil.createQRImage("123456789",300,300,null);
//            try{
//                this.printerDev.printBmp(0, qrcodeBitmap.getWidth(), qrcodeBitmap.getHeight(), qrcodeBitmap, new AidlPrinterListener.Stub() {
//                    @Override
//                    public void onPrintFinish() throws RemoteException {
//                        sendmessage(getStringByid(R.string.print_success));
//                    }
//
//                    @Override
//                    public void onError(int arg0) throws RemoteException {
//                        sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
//                    }
//                });
//            } catch (RemoteException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        try {
//            this.printerDev.setPrinterGray(4);
//            printerDev.printText(new ArrayList<PrintItemObj>(){
//                {
//                    String astr = "لتعبئة الرصيد اضغط رقم";
//
////                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj(astr,PrinterConstant.FontSize.XLARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj("----------------",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj(new StringBuilder(astr).reverse().toString(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj(new StringBuilder(astr).reverse().toString(),PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj(new StringBuilder(astr).reverse().toString(),PrinterConstant.FontSize.XLARGE,false, PrintItemObj.ALIGN.RIGHT));
//
////                    add(new PrintItemObj("----------------",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER));
////                    add(new PrintItemObj(new StringBuilder(astr).reverse().toString(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER));
////                    astr = "*122* متبوعا بالرقم السريو من ثم#";
////                    add(new PrintItemObj(new StringBuilder(astr).reverse().toString(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
////                    add(new PrintItemObj("ABCDEFGHIJKLMNABCDEFGHIJKLMNABCDEFGHIJKLMNABCDEFGHIJKLMN",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
////
////                    add(new PrintItemObj("----------------",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj("RIGHT",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj("ABCDEFGHIJKLMNABCDEFGHIJKLMNABCDEFGHIJKLMNABCDEFGHIJKLMN",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
////                    add(new PrintItemObj("--------------",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.CENTER));
////                    add(new PrintItemObj("CENTER",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.CENTER));
////                    add(new PrintItemObj("ABCDEFGHIJKLMNABCDEFGHIJKLMNABCDEFGHIJKLMNABCDEFGHIJKLMN",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.CENTER));
////
////                    add(new PrintItemObj("ABCDEFGHIJKLMN",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT));
////                    add(new PrintItemObj("ABCDEFGHIJKLMN",PrinterConstant.FontSize.LARGE,true, PrintItemObj.ALIGN.LEFT));
////
////                    add(new PrintItemObj(",سعدت بلقائك.,سعدت بلقائك.سعدت بلقائك.,سعدت بلقائك.,سعدت بلقائك.سعدت بلقائك.",PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
//
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
//
//                }
//            }, new AidlPrinterListener.Stub() {
//
//                @Override
//                public void onPrintFinish() throws RemoteException {
//                    String endTime = getCurTime();
//                    sendmessage(getStringByid(R.string.print_end) + endTime);
//                }
//
//                @Override
//                public void onError(int arg0) throws RemoteException {
//                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
//                }
//            });
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private Bitmap creatImage_Sabra() {
        // Text to picture printing
        try {

            ArrayList<StringBitmapParameter> mParameters = new ArrayList<>();
            StringBitmapParameter mParameter = new StringBitmapParameter();
            String astr = "לקוח";
            mParameters.add(new StringBitmapParameter("Text to Picture Printing",Layout.Alignment.ALIGN_CENTER,30,10,true,false,false));
            mParameters.add(new StringBitmapParameter("fontsize 20",Layout.Alignment.ALIGN_CENTER,20,10,true,false,false));
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "לקוח";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "קוד לקוח";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "אסמכתא";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "-----------";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "זמן התחלה";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "זמן סיום";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "מספר משאית";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "שם נהג";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "גליל אפור 12 מלא";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "נפרקו";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "הועמסו";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "מחיר ליחידה";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));
            astr = "סה\"כ בש\"ח";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,20,5,false,false,false));

            mParameters.add(new StringBitmapParameter("fontsize 30",Layout.Alignment.ALIGN_CENTER,30,10,true,false,false));
            astr = "לקוח";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "קוד לקוח";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "אסמכתא";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "-----------";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "זמן התחלה";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "זמן סיום";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "מספר משאית";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "שם נהג";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "גליל אפור 12 מלא";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "נפרקו";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "הועמסו";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "מחיר ליחידה";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));
            astr = "סה\"כ בש\"ח";
            mParameters.add(new StringBitmapParameter(astr, Layout.Alignment.ALIGN_RIGHT,30,5,false,false,false));


            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));
            mParameters.add(new StringBitmapParameter(""));




            BitmapUtil bitmapUtil = new BitmapUtil();
            Bitmap textBitmap = bitmapUtil.StringListtoBitmap(this, mParameters);


            return textBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void print_sabraText(){
        try {
            this.printerDev.setPrinterGray(4);
            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
                    add(new PrintItemObj("Print text",PrinterConstant.FontSize.XLARGE,false, PrintItemObj.ALIGN.CENTER));
                    add(new PrintItemObj("Normal fontsize",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER));
                    String astr = "לקוח";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "קוד לקוח";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "אסמכתא";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "-----------";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "זמן התחלה";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "זמן סיום";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "מספר משאית";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "שם נהג";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "גליל אפור 12 מלא";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "נפרקו";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "הועמסו";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "מחיר ליחידה";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "סה\"כ בש\"ח";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.RIGHT));


                    add(new PrintItemObj("LARGE fontsize",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER));
                    astr = "לקוח";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "קוד לקוח";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "אסמכתא";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "-----------";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "זמן התחלה";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "זמן סיום";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "מספר משאית";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "שם נהג";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "גליל אפור 12 מלא";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "נפרקו";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "הועמסו";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "מחיר ליחידה";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "סה\"כ בש\"ח";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));
                    astr = "";
                    add(new PrintItemObj(astr,PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.RIGHT));

                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                    add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

                }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void print_texttopicture(){
        try {
            Bitmap bitmap = creatImage_Sabra();
            String startTime = getCurTime();
            sendmessage(getStringByid(R.string.print_begin) + startTime);
            this.printerDev.printBmp(0, bitmap.getWidth(), bitmap.getHeight(), bitmap, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void printText_Sabra()
    {
        print_sabraText();  //text printing
        print_texttopicture();//text to picture printing


    }

    private String leftPadd(String str,int size,String ch)
    {
        String ret = "";
        for(int i=0;i<size;i++){
            ret+=ch;
        }
        return ret+str;
    }

    private Bitmap getbmp()
    {
        Bitmap bitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.LEFT);// 若设置为center，则文本左半部分显示不全 paint.setColor(Color.RED);
        paint.setAntiAlias(true);// 消除锯齿
        paint.setTextSize(28);

        canvas.drawText("-----------------------------------------------", 5, 30, paint) ;

        canvas.drawText("Name                     Qty Rat Amt", 5, 60, paint) ;

        canvas.drawText("वेज चाऊमीन (नूडल्स) 7 50.0 350.0", 5, 90, paint) ;
        //canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTargetDensity = 160;
        options.inDensity = 160;
        byte[] data = outputStream.toByteArray();
        Bitmap bitmapnew = BitmapFactory.decodeByteArray(data,0,data.length,options);
        return bitmapnew;
    }

    /**
     * 打印位图
     * @createtor：Administrator
     * @date:2015-8-4 下午2:39:33
     */
    public void printBitmap(){
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sxlogo380);
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo3);
            //Bitmap bitmap = getbmp();
            String startTime = getCurTime();
            sendmessage(getStringByid(R.string.print_begin) + startTime);
            this.printerDev.printBmp(0, bitmap.getWidth(), bitmap.getHeight(), bitmap, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });


            printerDev.printText(new ArrayList<PrintItemObj>(){
            {
                add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));
                add(new PrintItemObj("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT));

            }
            }, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    String endTime = getCurTime();
                    sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





    }

    private class PrintStateChangeListener extends AidlPrinterListener.Stub{

        @Override
        public void onError(int arg0) throws RemoteException {
            sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
        }

        @Override
        public void onPrintFinish() throws RemoteException {
            String endTime = getCurTime();
            sendmessage(getStringByid(R.string.print_end) + endTime);
        }

    }

    /**
     * 打印条码
     * @createtor：Administrator
     * @date:2015-8-4 下午3:02:21
     */
    public void printBarCode(){
        try {
            String startTime = getCurTime();
            sendmessage(getStringByid(R.string.print_begin) + startTime);
            this.printerDev.printBarCode(-1, 162, 18, 65, "23418753401", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 66, "03400471", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 67, "2341875340111", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 68, "23411875", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 69, "*23418*", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 70, "234187534011", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 71, "23418", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 72, "23418", new PrintStateChangeListener());
            this.printerDev.printBarCode(-1, 162, 18, 73, "{A23418", new PrintStateChangeListener());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    /*
    PSAM
     */

    public void getpsam(int val) {

        try {
            psam = AidlPsam.Stub.asInterface(deviceManager
                    .getPSAMReader(val));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 打开PSAM卡
    public void psamopen() {
        if(isNormalVelocityClick(DELAY_TIME)) {
            try {
                boolean flag = psam.open();
                if (flag) {
                    sendmessage(getStringByid(R.string.psam_open_success));
                } else {
                    sendmessage(getStringByid(R.string.psam_open_faile));
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            sendmessage(getStringByid(R.string.ic_dotnot_click_quickly));
        }
    }

    // apdu交互测试
    public void psamapducmd() {
        if(isNormalVelocityClick(DELAY_TIME)) {
            byte[] apdu = new byte[]{0x00, (byte) 0xB0, (byte) 0x96, 0x00, 0x06};
            try {
                byte[] data = psam.apduComm(apdu);
                if (null != data) {
                    sendmessage(getStringByid(R.string.result)+ HexUtil.bcd2str(data));
                } else {
                    sendmessage(getStringByid(R.string.nfc_apdu_faile));
                }

            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            sendmessage(getStringByid(R.string.ic_dotnot_click_quickly));
        }
    }

    // PSAM卡复位
    public void psamreset() {
        if(isNormalVelocityClick(DELAY_TIME)) {
            try {
                byte[] data = psam.reset(0x00);
                if (null != data) {
                    sendmessage(getStringByid(R.string.psam_reset_success)+ HexUtil.bcd2str(data));
                } else {
                    sendmessage(getStringByid(R.string.psam_reset_faile));
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            sendmessage(getStringByid(R.string.ic_dotnot_click_quickly));
        }
    }

    // 关闭
    public void psamclose() {
        if(isNormalVelocityClick(DELAY_TIME)){
            try {
                boolean flag = psam.close();
                if (flag) {
                    sendmessage(getStringByid(R.string.psam_close_success));
                } else {
                    sendmessage(getStringByid(R.string.psam_close_faile));
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            sendmessage(getStringByid(R.string.ic_dotnot_click_quickly));
        }
    }






    /*
    扫码
     */
    public void backScan() {
        Log.d(TAG,"backScan");
        sendmessage(getStringByid(R.string.qrcode_back_camera));
        if(iScanner==null) {
            try {
                iScanner = AidlCameraScanCode.Stub.asInterface(deviceManager.getCameraManager());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(iScanner==null)
        {

            sendmessage(getStringByid(R.string.qrcode_openfaile));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(AidlScanParam.SCAN_CODE,new AidlScanParam(0,10));
        try {
            iScanner.scanCode(bundle, new AidlCameraScanCodeListener.Stub() {
                @Override
                public void onResult(String s) throws RemoteException {
                    sendmessage(getStringByid(R.string.qrcode_result) + s);
                }

                @Override
                public void onCancel() throws RemoteException {
                    sendmessage(getStringByid(R.string.qrcode_cancel));
                }

                @Override
                public void onError(int i) throws RemoteException {
                    sendmessage(getStringByid(R.string.error_code) + i);
                }

                @Override
                public void onTimeout() throws RemoteException {
                    sendmessage(getStringByid(R.string.qrcode_timeout));
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopScan() {
        Log.d(TAG,"stopScan");
        try {
            iScanner.stopScan();
            sendmessage(getStringByid(R.string.qrcode_cancel));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    /*
    解码
     */
    public void decode_init() {
        try {
            if(iDecoder.init() == 0) {
                sendmessage(getStringByid(R.string.decode_init_success));
            } else {
//                sendmessage(getStringByid(R.string.decode_init_faile));
                iDecoder.exit();
                if(iDecoder.init() == 0) {
                    sendmessage(getStringByid(R.string.decode_init_success));
                } else {
                    sendmessage(getStringByid(R.string.decode_init_faile));
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void decoder() {
        try {
            DecodeUtils utils = new DecodeUtils(this);
//            Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.tp_decode_check);//  utils.getRes(QR_DECODE_DRAWABLE_NAME);
            InputStream in = getResources().getAssets().open("tp_decode_check.png");
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            if(bitmap==null) return;
            String text = iDecoder.decode(utils.decodeBarcodeYUV(bitmap),bitmap.getWidth(),bitmap.getHeight());
            if(text == null) {
                sendmessage(getStringByid(R.string.decode_data) + "NULL");
            } else if(text.length() == 0){
                sendmessage(getStringByid(R.string.decode_data_non));
            } else {
                sendmessage(getStringByid(R.string.decode_data) + text);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decode_exit() {
        try {
            iDecoder.exit();
            sendmessage(getStringByid(R.string.decode_exit));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /*
    蜂鸣器
     */
    public void startbeep() {
        try {
            iBeeper.beep(0,10000);
            sendmessage(getStringByid(R.string.beep_start));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopbeep() {
        try {
            iBeeper.stopBeep();
            sendmessage(getStringByid(R.string.beep_stop));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onDestroy(){
        if(activity_name.equals(BaseUtils.ACTIVITY_NAME_PSAM))
            psamclose();
        else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_DECODE))
            decode_exit();
        else if(activity_name.equals(BaseUtils.ACTIVITY_NAME_BEEP))
            stopbeep();
        super.onDestroy();
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        deviceManager = serviceManager;
        try {
            printerDev = AidlPrinter.Stub.asInterface(serviceManager.getPrinter());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            psam = AidlPsam.Stub.asInterface(serviceManager
                    .getPSAMReader(PsamConstant.PSAM_DEV_ID_1));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            iScanner = AidlCameraScanCode.Stub.asInterface(serviceManager.getCameraManager());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            sendmessage(e.getMessage().toString());
            e.printStackTrace();

        }
        try {
            iDecoder = AidlDecoderManager.Stub.asInterface(serviceManager.getDecoder());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            iBeeper = AidlBuzzer.Stub.asInterface(serviceManager.getBuzzer());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            Bundle bundle = new Bundle();
            bundle.putInt("LED_ID",1);
            iLed = AidlLed.Stub.asInterface(serviceManager.getLed());
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
