package com.theoretics.mobilepos.util;

public class GLOBALS {
    private static GLOBALS instance;

    private boolean newCard = true;
    private String receiptCopyType = "";

    private String loginID = ""; //Unique ID for saving
    private String entID = "";
    private String location = "";

    private String cashierName = "";
    private String cashierID = "";
    private String loginDate = "";

    private String plateNum = "";
    private String pType = "";
    private String daysElapsed = "";
    private String hoursElapsed = "";
    private String minutesElapsed = "";
    private String datetimeIN = "";
    private String datetimeOUT = "";
    private String duration = "";

    private int receiptNum = 0;
    private int grandTotal = 0;
    private int grossTotal = 0;

    public GLOBALS(){}

    public boolean isNewCard() {
        return newCard;
    }

    public void setNewCard(boolean newCard) {
        this.newCard = newCard;
    }

    public void setLoginID(String loginID){
        this.loginID = loginID;
    }
    public String getLoginID() {
        return this.loginID;
    }

    public void setCashierName(String cashierName){
        this.cashierName = cashierName;
    }
    public String getCashierName() {
        return this.cashierName;
    }

    public void setCashierID(String cashierID){
        this.cashierID = cashierID;
    }
    public String getCashierID() {
        return this.cashierID;
    }

    public void setLoginDate(String loginDate){
        this.loginDate = loginDate;
    }
    public String getLoginDate() {
        return this.loginDate;
    }
    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getDatetimeIN() {
        return datetimeIN;
    }

    public void setDatetimeIN(String datetimeIN) {
        this.datetimeIN = datetimeIN;
    }

    public String getDatetimeOUT() {
        return datetimeOUT;
    }

    public void setDatetimeOUT(String datetimeOUT) {
        this.datetimeOUT = datetimeOUT;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDaysElapsed() {
        return daysElapsed;
    }

    public void setDaysElapsed(String daysElapsed) {
        this.daysElapsed = daysElapsed;
    }

    public String getHoursElapsed() {
        return hoursElapsed;
    }

    public void setHoursElapsed(String hoursElapsed) {
        this.hoursElapsed = hoursElapsed;
    }

    public String getMinutesElapsed() {
        return minutesElapsed;
    }

    public void setMinutesElapsed(String minutesElapsed) {
        this.minutesElapsed = minutesElapsed;
    }

    public String getEntID() {
        return entID;
    }

    public void setEntID(String entID) {
        this.entID = entID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(int receiptNum) {
        this.receiptNum = receiptNum;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(int grossTotal) {
        this.grossTotal = grossTotal;
    }

    public String getReceiptCopyType() {
        return receiptCopyType;
    }

    public void setReceiptCopyType(String receiptCopyType) {
        this.receiptCopyType = receiptCopyType;
    }

    public static synchronized GLOBALS getInstance() {
        if(instance == null) {
            instance = new GLOBALS();
        }
        return instance;
    }
}
