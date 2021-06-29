package com.theoretics.mobilepos.bean;

public class CONSTANTS {
    private static CONSTANTS instance;
    private String exitID = "AB01";

    private String REGTIN = "VAT REG TIN: 000-328-853-000";
    private String MIN = "MIN: 21060915095156961";//WT
    //private String MIN = "MIN: 21060915095156962";//FC
    private String PTU = "PTU NO: FP062021-031-0292659-00000";//HZ511SACOL350WT
    //private String PTU = "PTU NO: FP062021-031-0292660-00000";//HZ511SACOL350FC


    public CONSTANTS(){}

    public String getExitID() {
        return exitID;
    }

    public void setExitID(String exitID) {
        this.exitID = exitID;
    }

    public String getREGTIN() {
        return REGTIN;
    }

    public void setREGTIN(String REGTIN) {
        this.REGTIN = REGTIN;
    }

    public String getMIN() {
        return MIN;
    }

    public void setMIN(String MIN) {
        this.MIN = MIN;
    }

    public String getPTU() {
        return PTU;
    }

    public void setPTU(String PTU) {
        this.PTU = PTU;
    }

    public static synchronized CONSTANTS getInstance() {
        if(instance == null) {
            instance = new CONSTANTS();
        }
        return instance;
    }
}
