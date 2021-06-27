package com.theoretics.mobilepos.bean;

public class ZREADING {

    private static ZREADING instance;

    private String endOR = "";
    private String beginOR = "";
    private String endTrans = "";
    private String beginTrans = "";

    private double beginBalance = 0;
    private double endingBalance = 0;
    private double beginGross = 0;
    private double endingGross = 0;

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
}
