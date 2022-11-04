package com.hamilton.bean;

import java.util.Objects;

public class Prescription {
    private String dose;
    private String medication;
    private String pharmacy;
    private int id;
    private int patient_id;
    private int visit_id;

    public Prescription(String dose, String medication, String pharmacy, int patient_id, int visit_id){
        this.dose = dose;
        this.medication = medication;
        this.pharmacy = pharmacy;
        this.patient_id = patient_id;
        this.visit_id = visit_id;
    }
    public Prescription(String dose, String medication){
        this.dose = dose;
        this.medication = medication;
    }

    public Prescription(int id, String dose, String medication, String pharmacy, int patient_id,int visit_id){
        this.id = id;
        this.dose = dose;
        this.medication = medication;
        this.pharmacy = pharmacy;
        this.patient_id = patient_id;
        this.visit_id = visit_id;
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

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return id == that.id && dose.equals(that.dose) && medication.equals(that.medication) && Objects.equals(pharmacy, that.pharmacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dose, medication, pharmacy, id);
    }
}
