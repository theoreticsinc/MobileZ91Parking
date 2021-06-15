package com.theoretics.mobilepos.util;

public class CONSTANTS {
    private static CONSTANTS instance;
    private String exitID = "AB01";

    private String REGTIN = "VAT REG TIN: 000-328-853-000";
    private String MIN = "MIN: 20022710505488671";
    private String PTU = "PTU NO: FP022020-031-0252249-00000";


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
