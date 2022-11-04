package com.hamilton.bean;

import java.util.Objects;

public class Clinic{
    private int clinicID; // created by database
    private String medicalOwnerName;
    private int medicalOwnerID;
    private String medicalName;
    private int communityID;

    public Clinic(String medicalOwnerName, int medicalOwnerID, String medicalName) {
        this.medicalOwnerName = medicalOwnerName;
        this.medicalOwnerID = medicalOwnerID;
        this.medicalName = medicalName;
        this.communityID = communityID;
    }

    public Clinic(){ //default constructor

    }

    public int getClinicID() {
        return clinicID;
    }

    public void setClinicID(int clinicID) {
        this.clinicID = clinicID;
    }

    public String getMedicalOwnerName() {
        return medicalOwnerName;
    }

    public void setMedicalOwnerName(String medicalOwnerName) {
        this.medicalOwnerName = medicalOwnerName;
    }

    public int getMedicalOwnerID() {
        return medicalOwnerID;
    }

    public void setMedicalOwnerID(int medicalOwnerID) {
        this.medicalOwnerID = medicalOwnerID;
    }

    public String getMedicalName() {
        return medicalName;
    }

    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    public int getCommunityID() {
        return clinicID;
    }

    public void setCommunityID(int communityID) {
        this.communityID = communityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clinic clinic = (Clinic) o;
        return clinicID == clinic.clinicID && medicalOwnerID == clinic.medicalOwnerID && communityID == clinic.communityID && Objects.equals(medicalOwnerName, clinic.medicalOwnerName) && Objects.equals(medicalName, clinic.medicalName);
    }

}
