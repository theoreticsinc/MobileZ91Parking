package com.theoretics.mobilepos.bean;

public class XREADING {

    private static XREADING instance;
    private int ambulanceCount = 0;
    private int gracePeriodCount = 0;
    private int inpatientCount = 0;
    private int lostCount = 0;
    private int MABRegularCount = 0;
    private int motorcycleCount = 0;
    private int pwdCount = 0;
    private int ambulatoryCount = 0;
    private int regularCount = 0;
    private int seniorCount = 0;
    private int VIPCount = 0;
    private int dialysisCount = 0;
    private int carsServed = 0;

    private double ambulanceAmount = 0;
    private double gracePeriodAmount = 0;
    private double inpatientAmount = 0;
    private double lostAmount = 0;
    private double MABRegularAmount = 0;
    private double motorcycleAmount = 0;
    private double pwdAmount = 0;
    private double ambulatoryAmount = 0;
    private double regularAmount = 0;
    private double seniorAmount = 0;
    private double VIPAmount = 0;
    private double dialysisAmount = 0;

    private double VATableSales = 0;
    private double VAT12 = 0;
    private double todaysCollection = 0;
    private double todaysGrossColl = 0;
    private double totalGrandCollection = 0;
    private double totalGrandGrossColl = 0;

    private double pwdDscCount = 0;
    private double pwdDscAmount = 0;
    private double seniorDscCount = 0;
    private double seniorDscAmount = 0;
    private double localSeniorDscCount = 0;
    private double localSeniorDscAmount = 0;

    private String teller = "";
    private String logInDateTime = "";
    private String logOutDateTime = "";

    public XREADING(){}

    public static synchronized XREADING getInstance() {
        if(instance == null) {
            instance = new XREADING();
        }
        return instance;
    }

    public int getAmbulanceCount() {
        return ambulanceCount;
    }

    public void setAmbulanceCount(int ambulanceCount) {
        this.ambulanceCount = ambulanceCount;
    }

    public int getGracePeriodCount() {
        return gracePeriodCount;
    }

    public void setGracePeriodCount(int gracePeriodCount) {
        this.gracePeriodCount = gracePeriodCount;
    }

    public int getInpatientCount() {
        return inpatientCount;
    }

    public void setInpatientCount(int inpatientCount) {
        this.inpatientCount = inpatientCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public void setLostCount(int lostCount) {
        this.lostCount = lostCount;
    }

    public int getMABRegularCount() {
        return MABRegularCount;
    }

    public void setMABRegularCount(int MABRegularCount) {
        this.MABRegularCount = MABRegularCount;
    }

    public int getMotorcycleCount() {
        return motorcycleCount;
    }

    public void setMotorcycleCount(int motorcycleCount) {
        this.motorcycleCount = motorcycleCount;
    }

    public int getPwdCount() {
        return pwdCount;
    }

    public void setPwdCount(int pwdCount) {
        this.pwdCount = pwdCount;
    }

    public int getRegularCount() {
        return regularCount;
    }

    public void setRegularCount(int regularCount) {
        this.regularCount = regularCount;
    }

    public int getSeniorCount() {
        return seniorCount;
    }

    public void setSeniorCount(int seniorCount) {
        this.seniorCount = seniorCount;
    }

    public int getVIPCount() {
        return VIPCount;
    }

    public void setVIPCount(int VIPCount) {
        this.VIPCount = VIPCount;
    }

    public double getAmbulanceAmount() {
        return ambulanceAmount;
    }

    public void setAmbulanceAmount(double ambulanceAmount) {
        this.ambulanceAmount = ambulanceAmount;
    }

    public double getGracePeriodAmount() {
        return gracePeriodAmount;
    }

    public void setGracePeriodAmount(double gracePeriodAmount) {
        this.gracePeriodAmount = gracePeriodAmount;
    }

    public double getInpatientAmount() {
        return inpatientAmount;
    }

    public void setInpatientAmount(double inpatientAmount) {
        this.inpatientAmount = inpatientAmount;
    }

    public double getLostAmount() {
        return lostAmount;
    }

    public void setLostAmount(double lostAmount) {
        this.lostAmount = lostAmount;
    }

    public double getMABRegularAmount() {
        return MABRegularAmount;
    }

    public void setMABRegularAmount(double MABRegularAmount) {
        this.MABRegularAmount = MABRegularAmount;
    }

    public double getMotorcycleAmount() {
        return motorcycleAmount;
    }

    public void setMotorcycleAmount(double motorcycleAmount) {
        this.motorcycleAmount = motorcycleAmount;
    }

    public double getPwdAmount() {
        return pwdAmount;
    }

    public void setPwdAmount(double pwdAmount) {
        this.pwdAmount = pwdAmount;
    }

    public double getRegularAmount() {
        return regularAmount;
    }

    public void setRegularAmount(double regularAmount) {
        this.regularAmount = regularAmount;
    }

    public double getSeniorAmount() {
        return seniorAmount;
    }

    public void setSeniorAmount(double seniorAmount) {
        this.seniorAmount = seniorAmount;
    }

    public double getVIPAmount() {
        return VIPAmount;
    }

    public void setVIPAmount(double VIPAmount) {
        this.VIPAmount = VIPAmount;
    }

    public double getVATableSales() {
        return VATableSales;
    }

    public void setVATableSales(double VATableSales) {
        this.VATableSales = VATableSales;
    }

    public double getVAT12() {
        return VAT12;
    }

    public void setVAT12(double VAT12) {
        this.VAT12 = VAT12;
    }

    public double getPwdDscCount() {
        return pwdDscCount;
    }

    public void setPwdDscCount(double pwdDscCount) {
        this.pwdDscCount = pwdDscCount;
    }

    public double getPwdDscAmount() {
        return pwdDscAmount;
    }

    public void setPwdDscAmount(double pwdDscAmount) {
        this.pwdDscAmount = pwdDscAmount;
    }

    public double getSeniorDscCount() {
        return seniorDscCount;
    }

    public void setSeniorDscCount(double seniorDscCount) {
        this.seniorDscCount = seniorDscCount;
    }

    public double getSeniorDscAmount() {
        return seniorDscAmount;
    }

    public void setSeniorDscAmount(double seniorDscAmount) {
        this.seniorDscAmount = seniorDscAmount;
    }

    public double getLocalSeniorDscCount() {
        return localSeniorDscCount;
    }

    public void setLocalSeniorDscCount(double localSeniorDscCount) {
        this.localSeniorDscCount = localSeniorDscCount;
    }

    public double getLocalSeniorDscAmount() {
        return localSeniorDscAmount;
    }

    public void setLocalSeniorDscAmount(double localSeniorDscAmount) {
        this.localSeniorDscAmount = localSeniorDscAmount;
    }

    public String getTeller() {
        return teller;
    }

    public void setTeller(String teller) {
        this.teller = teller;
    }

    public String getLogInDateTime() {
        return logInDateTime;
    }

    public void setLogInDateTime(String logInDateTime) {
        this.logInDateTime = logInDateTime;
    }

    public String getLogOutDateTime() {
        return logOutDateTime;
    }

    public void setLogOutDateTime(String logOutDateTime) {
        this.logOutDateTime = logOutDateTime;
    }

    public int getAmbulatoryCount() {
        return ambulatoryCount;
    }

    public void setAmbulatoryCount(int ambulatoryCount) {
        this.ambulatoryCount = ambulatoryCount;
    }

    public double getAmbulatoryAmount() {
        return ambulatoryAmount;
    }

    public void setAmbulatoryAmount(double ambulatoryAmount) {
        this.ambulatoryAmount = ambulatoryAmount;
    }

    public double getTodaysCollection() {
        return todaysCollection;
    }

    public void setTodaysCollection(double todaysCollection) {
        this.todaysCollection = todaysCollection;
    }

    public double getTodaysGrossColl() {
        return todaysGrossColl;
    }

    public void setTodaysGrossColl(double todaysGrossColl) {
        this.todaysGrossColl = todaysGrossColl;
    }

    public int getCarsServed() {
        return carsServed;
    }

    public void setCarsServed(int carsServed) {
        this.carsServed = carsServed;
    }

    public double getTotalGrandCollection() {
        return totalGrandCollection;
    }

    public void setTotalGrandCollection(double totalGrandCollection) {
        this.totalGrandCollection = totalGrandCollection;
    }

    public double getTotalGrandGrossColl() {
        return totalGrandGrossColl;
    }

    public void setTotalGrandGrossColl(double totalGrandGrossColl) {
        this.totalGrandGrossColl = totalGrandGrossColl;
    }

    public int getDialysisCount() {
        return dialysisCount;
    }

    public void setDialysisCount(int dialysisCount) {
        this.dialysisCount = dialysisCount;
    }

    public double getDialysisAmount() {
        return dialysisAmount;
    }

    public void setDialysisAmount(double dialysisAmount) {
        this.dialysisAmount = dialysisAmount;
    }
}
