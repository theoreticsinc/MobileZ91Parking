package com.theoretics.mobilepos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.theoretics.mobilepos.R;
import com.theoretics.mobilepos.bean.RECEIPT;
import com.theoretics.mobilepos.util.CONSTANTS;
import com.theoretics.mobilepos.util.ComputeAPI;
import com.theoretics.mobilepos.util.DBHelper;
import com.theoretics.mobilepos.util.GLOBALS;
import com.theoretics.mobilepos.util.ReceiptUtils;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.buzzer.AidlBuzzer;
import com.topwise.cloudpos.aidl.led.AidlLed;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.PrintItemObj;
import com.topwise.cloudpos.data.PrinterConstant;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ComputationActivity extends BaseActivity {

    //打印
    private AidlPrinter printerDev = null;
    private AidlDeviceService deviceManager = null;
    private AidlBuzzer iBeeper;
    private AidlLed iLed;
    private boolean isLedOn = false;

    private TextView cardTV = null;
    private TextView plateTV = null;
    private TextView TimeINTV = null;
    private TextView TimeOUTTV = null;
    private TextView durationTV = null;

    private TextView grossSalesTV = null;
    private TextView computation1 = null;
    private TextView computation2 = null;
    private TextView computation3 = null;
    private TextView computation4 = null;
    private TextView computation5 = null;

    private TextView line1 = null;
    private TextView line2 = null;
    private TextView line3 = null;
    private TextView line4 = null;
    private TextView line5 = null;

    private TextView amountDueTV = null;
    private Button confirmBtn = null;

    private boolean isDiscounted = false;
    String TRType = "";
    private ArrayList<PrintItemObj> obj2Print;

    ComputeAPI ca = null;
    final DecimalFormat df2 = new DecimalFormat("#0.00");

    ReceiptUtils rec = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation);
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView() {

        cardTV = (TextView) findViewById(R.id.cardTV);
        plateTV = (TextView) findViewById(R.id.plateTV);
        TimeINTV = (TextView) findViewById(R.id.TimeINTV);
        TimeOUTTV = (TextView) findViewById(R.id.TimeOUTTV);
        durationTV = (TextView) findViewById(R.id.durationTV);

        grossSalesTV = (TextView) findViewById(R.id.grossSalesTV);
        computation1 = (TextView) findViewById(R.id.computation1);
        computation2 = (TextView) findViewById(R.id.computation2);
        computation3 = (TextView) findViewById(R.id.computation3);
        computation4 = (TextView) findViewById(R.id.computation4);
        computation5 = (TextView) findViewById(R.id.computation5);
        line1 = (TextView) findViewById(R.id.line1);
        line2 = (TextView) findViewById(R.id.line2);
        line3 = (TextView) findViewById(R.id.line3);
        line4 = (TextView) findViewById(R.id.line4);
        line5 = (TextView) findViewById(R.id.line5);

        amountDueTV = (TextView) findViewById(R.id.amountDueTV);

        confirmBtn = findViewById(R.id.printBtn);

        Intent myIntent = getIntent();
        isDiscounted = myIntent.getBooleanExtra("isDiscounted", false);
        TRType = myIntent.getStringExtra("TRType");
        int daysElapsed = myIntent.getIntExtra("daysElapsed", 0);
        int hrsRemaining = myIntent.getIntExtra("hrsRemaining", 0);
        int minsRemaining = myIntent.getIntExtra("minsRemaining", 0);

        String cardNum = myIntent.getStringExtra("cardNum");
        String inputPlate= myIntent.getStringExtra("inputPlate");
        String minsElapsed= myIntent.getStringExtra("minsElapsed");

        ca = new ComputeAPI(isDiscounted, TRType, daysElapsed, hrsRemaining, minsRemaining);

        plateTV.setText(GLOBALS.getInstance().getPlateNum());
        cardTV.setText(cardNum);
        if (GLOBALS.getInstance().getDaysElapsed().compareToIgnoreCase("0") == 0) {
            GLOBALS.getInstance().setDuration(GLOBALS.getInstance().getHoursElapsed() + "hours " + GLOBALS.getInstance().getMinutesElapsed() + "mins");
        } else {
            GLOBALS.getInstance().setDuration(GLOBALS.getInstance().getDaysElapsed() + "days " + GLOBALS.getInstance().getHoursElapsed() + "hours " + GLOBALS.getInstance().getMinutesElapsed() + "mins");
        }
        durationTV.setText(GLOBALS.getInstance().getDuration());
        //durationTV.setText(GLOBALS.getInstance().getDaysElapsed() + "days " + GLOBALS.getInstance().getHoursElapsed() + "hours " + GLOBALS.getInstance().getMinutesElapsed() + "mins");
        TimeINTV.setText(GLOBALS.getInstance().getDatetimeIN());
        TimeOUTTV.setText(GLOBALS.getInstance().getDatetimeOUT());
        if (TRType.compareToIgnoreCase("R") == 0) {
            GLOBALS.getInstance().setpType("Regular");
            isDiscounted = false;
            line1.setText("VATable Sales    :");
            line2.setText("VAT Amount(12%)  :");
            line3.setText("VAT Exempt Sales :");
            line4.setText("Zero-Rated Sales :");
            line5.setText("");
            grossSalesTV.setText(df2.format(ca.AmountGross) + "");
            computation1.setText(df2.format(ca.vatsale) + "");
            computation2.setText(df2.format(ca.vat12) + "");
            computation3.setText("0.00");
            computation4.setText("0.00");
            computation5.setText("");
        } else if (TRType.compareToIgnoreCase("S") == 0) {
            GLOBALS.getInstance().setpType("Senior");
            isDiscounted = true;
            line1.setText("LESS: VAT");
            line2.setText("NET OF VAT");
            line3.setText("LESS DSC");
            line4.setText("NET OF DSC");
            line5.setText("ADD VAT");
            grossSalesTV.setText(df2.format(ca.AmountGross) + "");
            computation1.setText(df2.format(ca.vat12) + "");
            computation2.setText(df2.format(ca.NetOfVAT) + "");
            computation3.setText(df2.format(ca.discount) + "");
            computation4.setText(df2.format(ca.NetOfVAT - ca.discount) + "");
            computation5.setText(df2.format(ca.vat12) + "");
        }
            amountDueTV.setText("P" + df2.format(ca.AmountDue));

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GLOBALS.getInstance().isNewCard() == true) {
                    printOriginalReceipt();
                } else {
                    printDuplicateReceipt();
                }

            }
        });

        rec = new ReceiptUtils();
        rec.initiate(getApplicationContext());
        obj2Print = new ArrayList<PrintItemObj>();
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

        try {
            iLed.setLed(false);
            isLedOn = false;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void getPrintState(){
        try {
            int printState = printerDev.getPrinterState();
            //sendmessage(getStringByid(R.string.get_print_status) + printState);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startbeep() {
        try {
            iBeeper.beep(0,10000);
            //sendmessage(getStringByid(R.string.beep_start));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopbeep() {
        try {
            iBeeper.stopBeep();
            //sendmessage(getStringByid(R.string.beep_stop));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printOnly(String text, int fontSize, boolean isBold, PrintItemObj.ALIGN align) {
        obj2Print.add(new PrintItemObj(text, fontSize,isBold, align));
        try {
            if (isLedOn) {
                iLed.setLed(false);
                isLedOn = false;
            }
            else {
                iLed.setLed(true);
                isLedOn = true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printNsave(String text, int fontSize, boolean isBold, PrintItemObj.ALIGN align) {
       obj2Print.add(new PrintItemObj(text, fontSize,isBold, align));
       rec.appendToFile(text);
        try {
            if (isLedOn) {
                iLed.setLed(false);
                isLedOn = false;
            }
            else {
                iLed.setLed(true);
                isLedOn = true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printDuplicateReceipt()
    {
        try {
            this.printerDev.setPrinterGray(12);
            printOnly("CHINESE GENERAL HOSPITAL MEDICAL CTR", PrinterConstant.FontSize.SMALL,true, PrintItemObj.ALIGN.CENTER);
            printOnly("286 BLUMENTRITT ST. STA. CRUZ MANILA", PrinterConstant.FontSize.SMALL,true, PrintItemObj.ALIGN.CENTER);
            printOnly(CONSTANTS.getInstance().getREGTIN(), PrinterConstant.FontSize.SMALL,true, PrintItemObj.ALIGN.CENTER);
            printOnly(CONSTANTS.getInstance().getMIN(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER);
            printOnly(CONSTANTS.getInstance().getPTU(),PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("OFFICIAL RECEIPT",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("ACCOUNTING COPY",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("RECEIPT NUM.  : " + RECEIPT.getInstance().getReceiptNum(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printOnly("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Ent ID.      : " + RECEIPT.getInstance().getEntID(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Cashier Name : " + RECEIPT.getInstance().getCashierName(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Location     : " + RECEIPT.getInstance().getLocation(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Plate Number : " + RECEIPT.getInstance().getPlateNum(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Parker Type  : " + RECEIPT.getInstance().getpType(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("TIME IN      : " + RECEIPT.getInstance().getDatetimeIN(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("TIME OUT     : " + RECEIPT.getInstance().getDatetimeOUT(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Duration     : " + RECEIPT.getInstance().getDuration(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("GROSS AMOUNT      " + RECEIPT.getInstance().getAmountGross(),PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT);
            if (RECEIPT.getInstance().isDiscounted()) {
                printOnly("LESS: VAT           " + RECEIPT.getInstance().getVat12(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("NET OF VAT          " + RECEIPT.getInstance().getNetOfVAT(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("LESS DSC            " + RECEIPT.getInstance().getDiscount(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("NET OF DSC          " + RECEIPT.getInstance().getNetOfDiscount(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("ADD VAT             " + RECEIPT.getInstance().getVat12(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
            } else if (isDiscounted == false){
                printOnly("VATable Sales       " + RECEIPT.getInstance().getVatsale(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("VAT Amt(12%)        " + RECEIPT.getInstance().getVat12(), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("VAT Exempt          " + "0.00", PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printOnly("Zero-Rated          " + "0.00", PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
            }
            printOnly("------------------------------------------",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("TOTAL AMOUNT DUE: " + RECEIPT.getInstance().getAmountDue(),PrinterConstant.FontSize.LARGE,true, PrintItemObj.ALIGN.CENTER);
            printOnly("------------------------------------------",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("***** CUSTOMER INFO *****",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("Customer Name:____________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);
            printOnly("Addr:_____________________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);
            printOnly("TIN :_____________________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);
            printOnly("Business Type:____________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);

            printOnly("please visit",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("www.theoretics.com.ph",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("PARKING POS SUPPLIER",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("APPLIED MODERN THEORETICS INC.",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("ACCRED: 0470083988742019071113",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("Date Issued: 02/05/2020",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("Valid Until: 02/05/2025",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly(CONSTANTS.getInstance().getPTU(),PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("Date Issued: 03/03/2020",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("Valid Until: 03/03/2025",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printOnly("THANK YOU. FOR PARKING",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);

            printOnly("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printOnly("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printOnly("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);

            printerDev.printText(obj2Print, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    //String endTime = getCurTime();
                    //sendmessage(getStringByid(R.string.print_end) + endTime);
                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    //sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printOriginalReceipt()
    {
        String recNum = getReceiptNumber();
        saveReceipt2Memory(recNum);
        try {
            this.printerDev.setPrinterGray(12);
            printNsave("CHINESE GENERAL HOSPITAL MEDICAL CTR", PrinterConstant.FontSize.SMALL,true, PrintItemObj.ALIGN.CENTER);
            printNsave("286 BLUMENTRITT ST. STA. CRUZ MANILA", PrinterConstant.FontSize.SMALL,true, PrintItemObj.ALIGN.CENTER);
            printNsave(CONSTANTS.getInstance().getREGTIN(), PrinterConstant.FontSize.SMALL,true, PrintItemObj.ALIGN.CENTER);
            printNsave(CONSTANTS.getInstance().getMIN(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER);
            printNsave(CONSTANTS.getInstance().getPTU(),PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("OFFICIAL RECEIPT",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("CUSTOMER COPY",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("RECEIPT NUM.  : " + recNum,PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Ent ID.      : " + GLOBALS.getInstance().getEntID(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Cashier Name : " + GLOBALS.getInstance().getCashierName(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Location     : " + CONSTANTS.getInstance().getLocation(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Plate Number : " + GLOBALS.getInstance().getPlateNum(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Parker Type  : " + GLOBALS.getInstance().getpType(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("TIME IN      : " + GLOBALS.getInstance().getDatetimeIN(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("TIME OUT     : " + GLOBALS.getInstance().getDatetimeOUT(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Duration     : " + GLOBALS.getInstance().getDuration(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("GROSS AMOUNT      " + df2.format(ca.AmountGross),PrinterConstant.FontSize.LARGE,false, PrintItemObj.ALIGN.LEFT);
            if (isDiscounted || TRType.compareToIgnoreCase("S") == 0) {
                printNsave("LESS: VAT           " + df2.format(ca.vat12), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("NET OF VAT          " + df2.format(ca.NetOfVAT), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("LESS DSC            " + df2.format(ca.discount), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("NET OF DSC          " + df2.format(ca.NetOfVAT - ca.discount), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("ADD VAT             " + df2.format(ca.vat12), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
            } else if (isDiscounted == false){
                printNsave("VATable Sales       " + df2.format(ca.vatsale), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("VAT Amt(12%)        " + df2.format(ca.vat12), PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("VAT Exempt          " + "0.00", PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
                printNsave("Zero-Rated          " + "0.00", PrinterConstant.FontSize.NORMAL, false, PrintItemObj.ALIGN.LEFT);
            }
            printNsave("------------------------------------------",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("TOTAL AMOUNT DUE: " + df2.format(ca.AmountDue),PrinterConstant.FontSize.LARGE,true, PrintItemObj.ALIGN.CENTER);
            printNsave("------------------------------------------",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("***** CUSTOMER INFO *****",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("Customer Name:____________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Addr:_____________________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("TIN :_____________________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Business Type:____________________________",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.LEFT);

            printNsave("please visit",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("www.theoretics.com.ph",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("PARKING POS SUPPLIER",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("APPLIED MODERN THEORETICS INC.",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("ACCRED: 0470083988742019071113",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("Date Issued: 02/05/2020",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("Valid Until: 02/05/2025",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave(CONSTANTS.getInstance().getPTU(),PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("Date Issued: 03/03/2020",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("Valid Until: 03/03/2025",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);
            printNsave("THANK YOU. FOR PARKING",PrinterConstant.FontSize.SMALL,false, PrintItemObj.ALIGN.CENTER);

            printNsave("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("",PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);

            printerDev.printText(obj2Print, new AidlPrinterListener.Stub() {

                @Override
                public void onPrintFinish() throws RemoteException {
                    //String endTime = getCurTime();
                    //sendmessage(getStringByid(R.string.print_end) + endTime);
                    updateReceiptNumber();
                    GLOBALS.getInstance().setNewCard(false);

                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    //sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                }
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void saveReceipt2Memory(String recNum) {
        RECEIPT.getInstance().setReceiptNum(recNum);
        RECEIPT.getInstance().setEntID(GLOBALS.getInstance().getEntID());
        RECEIPT.getInstance().setCashierName(GLOBALS.getInstance().getCashierName());
        RECEIPT.getInstance().setLocation(GLOBALS.getInstance().getLocation());
        RECEIPT.getInstance().setPlateNum(GLOBALS.getInstance().getPlateNum());
        RECEIPT.getInstance().setpType(GLOBALS.getInstance().getpType());
        RECEIPT.getInstance().setDatetimeIN(GLOBALS.getInstance().getDatetimeIN());
        RECEIPT.getInstance().setDatetimeOUT(GLOBALS.getInstance().getDatetimeOUT());
        RECEIPT.getInstance().setDuration(GLOBALS.getInstance().getDuration());

        RECEIPT.getInstance().setDiscounted(isDiscounted);
        RECEIPT.getInstance().setAmountGross(df2.format(ca.AmountGross));

        if (isDiscounted || TRType.compareToIgnoreCase("S") == 0) {
            RECEIPT.getInstance().setVat12(df2.format(ca.vat12));
            RECEIPT.getInstance().setNetOfVAT(df2.format(ca.NetOfVAT));
            RECEIPT.getInstance().setDiscount(df2.format(ca.discount));
            RECEIPT.getInstance().setNetOfDiscount(df2.format(ca.NetOfVAT - ca.discount));

        } else if (isDiscounted == false) {
            RECEIPT.getInstance().setVatsale(df2.format(ca.vatsale));
            RECEIPT.getInstance().setVat12(df2.format(ca.vat12));
        }

        RECEIPT.getInstance().setAmountDue(df2.format(ca.AmountDue));

    }

    private String getReceiptNumber() {
        DBHelper dbh = new DBHelper(getApplicationContext());
        String rNos = "";
        int receiptNumber = dbh.getRNosData(DBHelper.MASTER_COLUMN_RECEIPTNOS);
        rNos = formatNos("" + receiptNumber);
        GLOBALS.getInstance().setReceiptNum(receiptNumber);
        return CONSTANTS.getInstance().getLocation() + rNos;
    }

    private void updateReceiptNumber() {
        DBHelper dbh = new DBHelper(getApplicationContext());
        dbh.updateRNosData(DBHelper.MASTER_COLUMN_RECEIPTNOS, GLOBALS.getInstance().getReceiptNum() + 1);
    }

    private String formatNos(String newReceipt) {
        int stoploop = 12 - newReceipt.length();
        int i = 0;
        do {
            newReceipt = "0" + newReceipt;
            i++;
        } while (i != stoploop);

        return newReceipt;
    }


    private class PrintStateChangeListener extends AidlPrinterListener.Stub{

        @Override
        public void onError(int arg0) throws RemoteException {
            //sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
        }

        @Override
        public void onPrintFinish() throws RemoteException {
            //String endTime = getCurTime();
            //sendmessage(getStringByid(R.string.print_end) + endTime);
        }

    }

}