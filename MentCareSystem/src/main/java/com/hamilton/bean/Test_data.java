package com.hamilton.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Test_data{

    private float stats;
    private String type;
    private String time;
    private int patient_ID;
    private String unitOfMeasure;
    private String alertMessage;
    private int test_data_ID;

    public Test_data() {

    }

    public Test_data(int patient_ID, float stats, String type, String time, String unitOfMeasure, String alertMessage, int test_data_ID) {
        this.stats = stats;
        this.type = type;
        this.time = time;
        this.unitOfMeasure = unitOfMeasure;
        this.patient_ID = patient_ID;
        this.alertMessage = alertMessage;
        this.test_data_ID = test_data_ID;
    }

    public String getAlertMessage() {
        return alertMessage;
    }
    


    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public static String getTimeOne() { //used for system time, need another getTime for actual patient data
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public String getTime() { return time; }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public float getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPatient_ID() {
        return patient_ID;
    }

    public void setPatient_ID(int patient_ID) {
        this.patient_ID = patient_ID;
    }

    public int getTest_data_ID() {
        return test_data_ID;
    }

    public void setTest_data_ID(int test_data_ID) {
        this.test_data_ID = test_data_ID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test_data test_data = (Test_data) o;
        return Float.compare(test_data.stats, stats) == 0 && patient_ID == test_data.patient_ID && Objects.equals(type, test_data.type) && Objects.equals(time, test_data.time) && Objects.equals(unitOfMeasure, test_data.unitOfMeasure);
    }

}
