package com.hamilton.bean;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Visit {
    //private List<Prescription> prescriptionList;
    //private List<Test_data> testList;

    private String prescriptionList;
    private String testList;
    private int doctor_id;
    private int patient_id;
    private int visit_id;
    private Date visitDate;
    private Time visitTime;
    private String symptomNotes;
    private String doctorSummary;
    private boolean visitFulfilled;

    public Visit(int doctor_id, int patient_id, Date visitDate, Time visitTime) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
    }

    public Visit(int visit_id, int doctor_id, int patient_id, Date visitDate, Time visitTime, String symptomNotes, String doctorSummary) {
        this.visit_id = visit_id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.symptomNotes = symptomNotes;
        this.doctorSummary = doctorSummary;
    }

    public Visit(int visit_id, int doctor_id, int patient_id, Date visitDate, Time visitTime, String symptomNotes, String doctorSummary, String prescriptionList, String testList) {
        this.visit_id = visit_id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.symptomNotes = symptomNotes;
        this.doctorSummary = doctorSummary;
        this.prescriptionList = prescriptionList;
        this.testList = testList;
    }

    public Visit(){

    }

    public String getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(String prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public String getTestList() {
        return testList;
    }

    public void setTestList(String testList) {
        this.testList = testList;
    }

    public boolean isVisitFulfilled() {
        return visitFulfilled;
    }

    public void setVisitFulfilled(boolean visitFulfilled) {
        this.visitFulfilled = visitFulfilled;
    }

    /*
        public List<Prescription> getPrescriptionList() {
            return prescriptionList;
        }

        public void setPrescriptionList(List<Prescription> prescriptionList) {
            this.prescriptionList = prescriptionList;
        }

        public List<Test_data> getTestList() {
            return testList;
        }

        public void setTestList(List<Test_data> testList) {
            this.testList = testList;
        }
    */
    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Time getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Time visitTime) {
        this.visitTime = visitTime;
    }

    public String getSymptomNotes() {
        return symptomNotes;
    }

    public void setSymptomNotes(String symptomNotes) {
        this.symptomNotes = symptomNotes;
    }

    public String getDoctorSummary() {
        return doctorSummary;
    }

    public void setDoctorSummary(String doctorSummary) {
        this.doctorSummary = doctorSummary;
    }
}
