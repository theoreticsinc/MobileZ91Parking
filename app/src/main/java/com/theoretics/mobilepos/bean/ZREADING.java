package com.theoretics.mobilepos.bean;

public class ZREADING {

    private static ZREADING instance;


    private String zreadingDate = "";
    private double todaysSales = 0;
    private double todaysGross = 0;
    private double vatableSales = 0;
    private double vat12Sales = 0;
    private double vatExemptedSales = 0;

    private String endOR = "";
    private String beginOR = "";
    private String endTrans = "";
    private String beginTrans = "";

    private double beginBalance = 0;
    private double endingBalance = 0;
    private double beginGross = 0;
    private double endingGross = 0;

    private String accumulatedGrand = "0";
    private String accumulatedGross = "0";

    private String zCount = "";

    public ZREADING(){}

    public static synchronized ZREADING getInstance() {
        if(instance == null) {
            instance = new ZREADING();
        }
        return instance;
    }

    public String getEndOR() {
        return endOR;
    }

    public void setEndOR(String endOR) {
        this.endOR = endOR;
    }

    public String getBeginOR() {
        return beginOR;
    }

    public void setBeginOR(String beginOR) {
        this.beginOR = beginOR;
    }

    public String getEndTrans() {
        return endTrans;
    }

    public void setEndTrans(String endTrans) {
        this.endTrans = endTrans;
    }

    public String getBeginTrans() {
        return beginTrans;
    }

    public void setBeginTrans(String beginTrans) {
        this.beginTrans = beginTrans;
    }

    public double getBeginBalance() {
        return beginBalance;
    }

    public void setBeginBalance(double beginBalance) {
        this.beginBalance = beginBalance;
    }

    public double getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(double endingBalance) {
        this.endingBalance = endingBalance;
    }

    public double getBeginGross() {
        return beginGross;
    }

    public void setBeginGross(double beginGross) {
        this.beginGross = beginGross;
    }

    public double getEndingGross() {
        return endingGross;
    }

    public void setEndingGross(double endingGross) {
        this.endingGross = endingGross;
    }

    public String getZreadingDate() {
        return zreadingDate;
    }

    public void setZreadingDate(String zreadingDate) {
        this.zreadingDate = zreadingDate;
    }

    public double getTodaysSales() {
        return todaysSales;
    }

    public void setTodaysSales(double todaysSales) {
        this.todaysSales = todaysSales;
    }

    public double getTodaysGross() {
        return todaysGross;
    }

    public void setTodaysGross(double todaysGross) {
        this.todaysGross = todaysGross;
    }

    public double getVatableSales() {
        return vatableSales;
    }

    public void setVatableSales(double vatableSales) {
        this.vatableSales = vatableSales;
    }

    public double getVat12Sales() {
        return vat12Sales;
    }

    public void setVat12Sales(double vat12Sales) {
        this.vat12Sales = vat12Sales;
    }

    public double getVatExemptedSales() {
        return vatExemptedSales;
    }

    public void setVatExemptedSales(double vatExemptedSales) {
        this.vatExemptedSales = vatExemptedSales;
    }

    public String getzCount() {
        return zCount;
    }

    public void setzCount(String zCount) {
        this.zCount = zCount;
    }

    public String getAccumulatedGrand() {
        return accumulatedGrand;
    }

    public void setAccumulatedGrand(String accumulatedGrand) {
        this.accumulatedGrand = accumulatedGrand;
    }

    public String getAccumulatedGross() {
        return accumulatedGross;
    }

    public void setAccumulatedGross(String accumulatedGross) {
        this.accumulatedGross = accumulatedGross;
    }
}
