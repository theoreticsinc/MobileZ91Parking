package com.theoretics.mobilepos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText tenderInput = null;
    private TextView changeDueTV = null;

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
        tenderInput = (EditText) findViewById(R.id.tenderInput);
        changeDueTV = findViewById(R.id.change);
        amountDueTV = (TextView) findViewById(R.id.amountDueTV);
        confirmBtn = findViewById(R.id.printBtn);

        tenderInput.setFocusable(true);


        Intent myIntent = getIntent();
        isDiscounted = myIntent.getBooleanExtra("isDiscounted", false);
        TRType = myIntent.getStringExtra("TRType");
        GLOBALS.getInstance().setpType(TRType);
        int daysElapsed = myIntent.getIntExtra("daysElapsed", 0);
        int hrsRemaining = myIntent.getIntExtra("hrsRemaining", 0);
        int minsRemaining = myIntent.getIntExtra("minsRemaining", 0);

        String cardNum = myIntent.getStringExtra("cardNum");
        GLOBALS.getInstance().setCardNumber(cardNum);
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
        GLOBALS.getInstance().setHoursElapsed(hrsRemaining + "");
        GLOBALS.getInstance().setMinutesElapsed(minsRemaining + "");
        RECEIPT.getInstance().setHoursElapsed(hrsRemaining + "");
        RECEIPT.getInstance().setMinutesElapsed(minsRemaining + "");
        GLOBALS.getInstance().setExitID(CONSTANTS.getInstance().getExitID());
        RECEIPT.getInstance().setExitID(CONSTANTS.getInstance().getExitID());
        RECEIPT.getInstance().setPlateNum(GLOBALS.getInstance().getPlateNum());
        RECEIPT.getInstance().setCardNumber(GLOBALS.getInstance().getCardNumber());

        durationTV.setText(GLOBALS.getInstance().getDuration());
        //durationTV.setText(GLOBALS.getInstance().getDaysElapsed() + "days " + GLOBALS.getInstance().getHoursElapsed() + "hours " + GLOBALS.getInstance().getMinutesElapsed() + "mins");
        TimeINTV.setText(GLOBALS.getInstance().getDatetimeIN());
        TimeOUTTV.setText(GLOBALS.getInstance().getDatetimeOUT());
        if (TRType.compareToIgnoreCase("R") == 0) {
            GLOBALS.getInstance().setpTypeName("Regular");
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
        } else if (TRType.compareToIgnoreCase("RM") == 0) {
            GLOBALS.getInstance().setpTypeName("MAB Regular");
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
            GLOBALS.getInstance().setpTypeName("Senior");
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
        tenderInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        tenderInput.addTextChangedListener(new TextWatcher() {
            double tender;
            double changeDue;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    DecimalFormat df2 = new DecimalFormat("#0.00");
                    tender = Double.valueOf(tenderInput.getText().toString());
                    changeDue = tender - GLOBALS.getInstance().getAmountDue();
                    changeDueTV.setText(df2.format(changeDue));
                    changeDue = Double.parseDouble(df2.format(changeDue));

                } catch (Exception ex) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                RECEIPT.getInstance().setTendered(tender + "");
                RECEIPT.getInstance().setChangeDue(changeDue + "");
            }
        });
        tenderInput.requestFocus();
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
        obj2Print.clear();
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
                printOnly("Location     : " + RECEIPT.getInstance().getExitID(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Plate Number : " + RECEIPT.getInstance().getPlateNum(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
                printOnly("Parker Type  : " + RECEIPT.getInstance().getpTypeName(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
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
            printNsave("Location     : " + CONSTANTS.getInstance().getExitID(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Plate Number : " + GLOBALS.getInstance().getPlateNum(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
            printNsave("Parker Type  : " + GLOBALS.getInstance().getpTypeName(),PrinterConstant.FontSize.NORMAL,false, PrintItemObj.ALIGN.LEFT);
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
                    saveTransaction(RECEIPT.getInstance().getReceiptNum(),
                            RECEIPT.getInstance().getCashierName(),
                            RECEIPT.getInstance().getEntID(),
                            RECEIPT.getInstance().getExitID(),
                            RECEIPT.getInstance().getCardNumber(),
                            RECEIPT.getInstance().getPlateNum(),
                            RECEIPT.getInstance().getpType(),
                            RECEIPT.getInstance().getNetOfDiscount(),
                            RECEIPT.getInstance().getAmountDue()+ "",
                            RECEIPT.getInstance().getAmountGross(),
                            RECEIPT.getInstance().getDiscount(),
                            RECEIPT.getInstance().getVatAdjustment(),
                            RECEIPT.getInstance().getVat12(),
                            RECEIPT.getInstance().getVatsale(),
                            RECEIPT.getInstance().getVatExemptedSales(),
                            RECEIPT.getInstance().getTendered(),
                            RECEIPT.getInstance().getChangeDue(),
                            RECEIPT.getInstance().getDatetimeIN(),
                            RECEIPT.getInstance().getDatetimeOUT(),
                            RECEIPT.getInstance().getHoursElapsed(),
                            RECEIPT.getInstance().getMinutesElapsed(),
                            RECEIPT.getInstance().getSettlementRef(),
                            RECEIPT.getInstance().getSettlementName(),
                            RECEIPT.getInstance().getSettlementAddr(),
                            RECEIPT.getInstance().getSettlementTIN(),
                            RECEIPT.getInstance().getSettlementBusStyle());

                    updateXRead();
                    updateGrandTotal(GLOBALS.getInstance().getAmountDue());
                    updateGrossTotal(GLOBALS.getInstance().getAmountGross());

                }

                @Override
                public void onError(int arg0) throws RemoteException {
                    //sendmessage(getStringByid(R.string.print_faile_errcode) + arg0);
                    updateReceiptNumber();
                    GLOBALS.getInstance().setNewCard(false);
                    saveTransaction(RECEIPT.getInstance().getReceiptNum(),
                            RECEIPT.getInstance().getCashierName(),
                            RECEIPT.getInstance().getEntID(),
                            RECEIPT.getInstance().getExitID(),
                            RECEIPT.getInstance().getCardNumber(),
                            RECEIPT.getInstance().getPlateNum(),
                            RECEIPT.getInstance().getpType(),
                            RECEIPT.getInstance().getNetOfDiscount(),
                            RECEIPT.getInstance().getAmountDue()+ "",
                            RECEIPT.getInstance().getAmountGross(),
                            RECEIPT.getInstance().getDiscount(),
                            RECEIPT.getInstance().getVatAdjustment(),
                            RECEIPT.getInstance().getVat12(),
                            RECEIPT.getInstance().getVatsale(),
                            RECEIPT.getInstance().getVatExemptedSales(),
                            RECEIPT.getInstance().getTendered(),
                            RECEIPT.getInstance().getChangeDue(),
                            RECEIPT.getInstance().getDatetimeIN(),
                            RECEIPT.getInstance().getDatetimeOUT(),
                            RECEIPT.getInstance().getHoursElapsed(),
                            RECEIPT.getInstance().getMinutesElapsed(),
                            RECEIPT.getInstance().getSettlementRef(),
                            RECEIPT.getInstance().getSettlementName(),
                            RECEIPT.getInstance().getSettlementAddr(),
                            RECEIPT.getInstance().getSettlementTIN(),
                            RECEIPT.getInstance().getSettlementBusStyle());

                    updateXRead();
                    updateGrandTotal(GLOBALS.getInstance().getAmountDue());
                    updateGrossTotal(GLOBALS.getInstance().getAmountGross());

                }
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateXRead () {
        DBHelper dbh = new DBHelper(getApplicationContext());

        //=================================Count==============================
        int currentCarServed = dbh.getImptCount("carServed");
        dbh.setImptCount("carServed", currentCarServed);

        int grossCount = dbh.getImptCount("grossCount");
        dbh.setImptCount("grossCount", grossCount);

        int vat12Count = dbh.getImptCount("vat12Count");
        dbh.setImptCount("vat12Count", vat12Count);

        int vatsaleCount = dbh.getImptCount("vatsaleCount");
        dbh.setImptCount("vatsaleCount", vatsaleCount);

        if (isDiscounted) {
            if (GLOBALS.getInstance().getpType().compareToIgnoreCase("S") == 0) {
                int vatExemptedSalesCount = dbh.getImptCount("vatExemptedSalesCount");
                dbh.setImptCount("vatExemptedSalesCount", vatExemptedSalesCount);
                int seniorDiscountCount = dbh.getImptCount("seniorDiscountCount");
                dbh.setImptCount("seniorDiscountCount", seniorDiscountCount);
                int vatAdjSeniorCount = dbh.getImptCount("vatAdjSeniorCount");
                dbh.setImptCount("vatAdjSeniorCount", vatAdjSeniorCount);
                int vat12SeniorCount = dbh.getImptCount("vat12SeniorCount");
                dbh.setImptCount("vat12SeniorCount", vat12SeniorCount);
                int seniorCount = dbh.getImptCount("seniorCount");
                dbh.setImptCount("seniorCount", seniorCount);
            } else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("PW") == 0) {
                int vatExemptedSalesCount = dbh.getImptCount("vatExemptedSalesCount");
                dbh.setImptCount("vatExemptedSalesCount", vatsaleCount);
                int pwdDiscountCount = dbh.getImptCount("pwdDiscountCount");
                dbh.setImptCount("pwdDiscountCount", pwdDiscountCount);
                int vatAdjPWDCount = dbh.getImptCount("vatAdjPWDCount");
                dbh.setImptCount("vatAdjPWDCount", vatAdjPWDCount);
                int vat12PWDCount = dbh.getImptCount("vat12PWDCount");
                dbh.setImptCount("vat12PWDCount", vat12PWDCount);
                int pwdCount = dbh.getImptCount("pwdCount");
                dbh.setImptCount("pwdCount", pwdCount);
            }
        } else {
            if (GLOBALS.getInstance().getpType().compareToIgnoreCase("V") == 0) {
                int regularCount = dbh.getImptCount("regularCount");
                dbh.setImptCount("regularCount", regularCount);
            } else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("R") == 0) {
                int regularCount = dbh.getImptCount("regularCount");
                dbh.setImptCount("regularCount", regularCount);
            } else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("RM") == 0) {
                int mabregularCount = dbh.getImptCount("mabregularCount");
                dbh.setImptCount("mabregularCount", mabregularCount);
            }
            else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("G") == 0) {
                int graceperiodCount = dbh.getImptCount("graceperiodCount");
                dbh.setImptCount("graceperiodCount", graceperiodCount);
            }
            else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("L") == 0) {
                int graceperiodCount = dbh.getImptCount("graceperiodCount");
                dbh.setImptCount("graceperiodCount", graceperiodCount);
            }
            else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("DS") == 0) {
                int dialysisCount = dbh.getImptCount("dialysisCount");
                dbh.setImptCount("dialysisCount", dialysisCount);
            }
        }
        //=================================Amount==============================
        double currentAmount = dbh.getImptAmount("totalAmount");
        dbh.setImptAmount("totalAmount", currentAmount + GLOBALS.getInstance().getAmountDue());

        double grossAmount = dbh.getImptAmount("grossAmount");
        dbh.setImptAmount("grossAmount", grossAmount + GLOBALS.getInstance().getAmountGross());

        double vat12Amount = dbh.getImptAmount("vat12Amount");
        dbh.setImptAmount("vat12Amount", grossAmount + GLOBALS.getInstance().getVat12());

        if (isDiscounted) {
            if (GLOBALS.getInstance().getpType().compareToIgnoreCase("S") == 0) {
                int vatsaleAmount = dbh.getImptCount("vatsaleAmount");
                dbh.setImptAmount("vatsaleAmount", vatsaleAmount + GLOBALS.getInstance().getNetOfDiscount());
                double vatExemptedSalesAmount = dbh.getImptAmount("vatExemptedSalesAmount");
                dbh.setImptAmount("vatExemptedSalesAmount", vatExemptedSalesAmount + GLOBALS.getInstance().getVatExemptedSales());
                double seniorDiscountAmount = dbh.getImptAmount("seniorDiscountAmount");
                dbh.setImptAmount("seniorDiscountAmount", seniorDiscountAmount + GLOBALS.getInstance().getDiscount());
                double vatAdjSeniorAmount = dbh.getImptAmount("vatAdjSeniorAmount");
                dbh.setImptAmount("vatAdjSeniorAmount", vatAdjSeniorAmount + GLOBALS.getInstance().getVatAdjustment());
                double vat12SeniorAmount = dbh.getImptAmount("vat12SeniorAmount");
                dbh.setImptAmount("vat12SeniorAmount", vat12SeniorAmount + GLOBALS.getInstance().getVat12());
                double seniorAmount = dbh.getImptAmount("seniorAmount");
                dbh.setImptAmount("seniorAmount", seniorAmount + GLOBALS.getInstance().getAmountDue());
            } else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("PW") == 0) {
                int vatsaleAmount = dbh.getImptCount("vatsaleAmount");
                dbh.setImptAmount("vatsaleAmount", vatsaleAmount + GLOBALS.getInstance().getNetOfDiscount());
                double vatExemptedSalesAmount = dbh.getImptAmount("vatExemptedSalesAmount");
                dbh.setImptAmount("vatExemptedSalesAmount", vatExemptedSalesAmount + GLOBALS.getInstance().getVatExemptedSales());
                double pwdDiscountAmount = dbh.getImptAmount("pwdDiscountAmount");
                dbh.setImptAmount("pwdDiscountAmount", pwdDiscountAmount + GLOBALS.getInstance().getDiscount());
                double vatAdjPWDAmount = dbh.getImptAmount("vatAdjPWDAmount");
                dbh.setImptAmount("vatAdjPWDAmount", vatAdjPWDAmount + GLOBALS.getInstance().getVatAdjustment());
                double vat12PWDAmount = dbh.getImptAmount("vat12PWDAmount");
                dbh.setImptAmount("vat12PWDAmount", vat12PWDAmount + GLOBALS.getInstance().getVat12());
                double pwdAmount = dbh.getImptAmount("pwdAmount");
                dbh.setImptAmount("pwdAmount", pwdAmount + GLOBALS.getInstance().getAmountDue());
            }
        } else {

            int vatsaleAmount = dbh.getImptCount("vatsaleAmount");
            dbh.setImptAmount("vatsaleAmount", vatsaleAmount + GLOBALS.getInstance().getVatsale());

            if (GLOBALS.getInstance().getpType().compareToIgnoreCase("V") == 0) {
                double vipAmount = dbh.getImptAmount("vipAmount");
                dbh.setImptAmount("vipAmount", vipAmount);
            } else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("R") == 0) {
                double regularAmount = dbh.getImptAmount("regularAmount");
                dbh.setImptAmount("regularAmount", regularAmount + GLOBALS.getInstance().getAmountDue());
            } else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("RM") == 0) {
                double mabregularAmount = dbh.getImptAmount("mabregularAmount");
                dbh.setImptAmount("mabregularAmount", mabregularAmount + GLOBALS.getInstance().getAmountDue());
            }
            else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("G") == 0) {
                double graceperiodAmount = dbh.getImptAmount("graceperiodAmount");
                dbh.setImptAmount("graceperiodAmount", graceperiodAmount);
            }
            else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("L") == 0) {
                double lostAmount = dbh.getImptAmount("lostAmount");
                dbh.setImptAmount("lostAmount", lostAmount + GLOBALS.getInstance().getAmountDue());
            }
            else if (GLOBALS.getInstance().getpType().compareToIgnoreCase("DS") == 0) {
                double dialysisAmount = dbh.getImptAmount("dialysisAmount");
                dbh.setImptAmount("dialysisAmount", dialysisAmount + GLOBALS.getInstance().getAmountDue());
            }
        }

    }

    private void updateGrandTotal (double amountDue) {
        DBHelper dbh = new DBHelper(getApplicationContext());
        double grandTotal = dbh.getImptGrand(DBHelper.MASTER_COLUMN_GRANDTOTAL);
        dbh.setGrandTotal(amountDue + grandTotal);
    }

    private void updateGrossTotal (double amountDue) {
        DBHelper dbh = new DBHelper(getApplicationContext());
        double grossTotal = dbh.getImptGrand(DBHelper.MASTER_COLUMN_GROSSTOTAL);
        dbh.setGrossTotal(amountDue + grossTotal);
    }

    private void saveTransaction(String ReceiptNumber,String CashierName, String EntranceID,
                                 String ExitID, String CardNumber, String PlateNumber, String ParkerType,
                                 String NetOfDiscount, String Amount, String GrossAmount, String discount,
                                 String vatAdjustment, String vat12, String vatsale, String vatExemptedSales,
                                 String tendered, String changeDue, String DateTimeIN, String DateTimeOUT,
                                 String HoursParked, String MinutesParked, String SettlementRef,
                                 String SettlementName, String SettlementAddr, String SettlementTIN, String SettlementBusStyle) {
        DBHelper dbh = new DBHelper(getApplicationContext());
        dbh.saveEXTransaction(ReceiptNumber, CashierName, EntranceID, ExitID, CardNumber, PlateNumber, ParkerType, NetOfDiscount,
                Amount, GrossAmount, discount, vatAdjustment, vat12, vatsale, vatExemptedSales, tendered, changeDue,
                DateTimeIN, DateTimeOUT, HoursParked, MinutesParked,
                SettlementRef, SettlementName, SettlementAddr, SettlementTIN, SettlementBusStyle);
    }

    private void saveReceipt2Memory(String recNum) {
        RECEIPT.getInstance().setReceiptNum(recNum);
        RECEIPT.getInstance().setEntID(GLOBALS.getInstance().getEntID());
        RECEIPT.getInstance().setCashierName(GLOBALS.getInstance().getCashierName());
        RECEIPT.getInstance().setExitID(GLOBALS.getInstance().getExitID());
        RECEIPT.getInstance().setPlateNum(GLOBALS.getInstance().getPlateNum());
        RECEIPT.getInstance().setpType(GLOBALS.getInstance().getpType());
        RECEIPT.getInstance().setpTypeName(GLOBALS.getInstance().getpTypeName());
        RECEIPT.getInstance().setDatetimeIN(GLOBALS.getInstance().getDatetimeIN());
        RECEIPT.getInstance().setDatetimeOUT(GLOBALS.getInstance().getDatetimeOUT());
        RECEIPT.getInstance().setDuration(GLOBALS.getInstance().getDuration());

        RECEIPT.getInstance().setDiscounted(isDiscounted);
        RECEIPT.getInstance().setAmountGross(df2.format(ca.AmountGross));

        if (isDiscounted || TRType.compareToIgnoreCase("S") == 0) {
            RECEIPT.getInstance().setVat12(df2.format(ca.vat12));
            RECEIPT.getInstance().setVatAdjustment(df2.format(ca.vatAdjustment));
            RECEIPT.getInstance().setNetOfVAT(df2.format(ca.NetOfVAT));
            RECEIPT.getInstance().setDiscount(df2.format(ca.discount));
            RECEIPT.getInstance().setNetOfDiscount(df2.format(ca.NetOfVAT - ca.discount));

        } else if (isDiscounted == false) {
            RECEIPT.getInstance().setVatsale(df2.format(ca.vatsale));
            RECEIPT.getInstance().setVat12(df2.format(ca.vat12));
        }

        RECEIPT.getInstance().setAmountDue(ca.AmountDue + "");

    }

    private String getReceiptNumber() {
        DBHelper dbh = new DBHelper(getApplicationContext());
        String rNos = "";
        int receiptNumber = dbh.getRNosData(DBHelper.MASTER_COLUMN_RECEIPTNOS);
        rNos = formatNos("" + receiptNumber);
        GLOBALS.getInstance().setReceiptNum(receiptNumber);
        return CONSTANTS.getInstance().getExitID() + rNos;
    }

    private void updateReceiptNumber() {
        DBHelper dbh = new DBHelper(getApplicationContext());
        dbh.updateRNosData(DBHelper.MASTER_COLUMN_RECEIPTNOS, GLOBALS.getInstance().getReceiptNum() + 1);
    }

    public void Compute4Change() {

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