package com.hamilton.bean;

import java.sql.Date;
import java.util.Objects;

public class MedicalOwner extends User{
    private int medicalOwnerID; // created by Database
    private String medicalName;
    private int clinicID;

    public MedicalOwner( String name, String email, String password, Date birthdate, String address, String socialSecurityNumber, int clinicID) {
        super( name, email, password, birthdate, address, socialSecurityNumber);
//        this.medicalName = medicalName;
        this.clinicID = clinicID;
    }

    public MedicalOwner(){ //default constructor

    }
    public MedicalOwner( User user, int clinicID) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getBirthdate(), user.getAddress(), user.getSocialSecurityNumber());
//        this.medicalName = medicalName;
        this.clinicID = clinicID;
    }

    public MedicalOwner( User user, int clinicID, int medicalOwnerID,String medicalName) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getBirthdate(), user.getAddress(), user.getSocialSecurityNumber());
        this.medicalName = medicalName;
        this.clinicID = clinicID;
        this.medicalOwnerID = medicalOwnerID;
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

    public int getClinicID() {
        return clinicID;
    }

    public void setClinicID(int clinicID) {
        this.clinicID = clinicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalOwner that = (MedicalOwner) o;
        return medicalOwnerID == that.medicalOwnerID && clinicID == that.clinicID && Objects.equals(medicalName, that.medicalName);
    }
}
