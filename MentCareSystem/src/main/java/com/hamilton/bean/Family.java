package com.hamilton.bean;

import java.sql.Date;
import java.util.Objects;

public class Family {
    private int familyID;
    private String familyName;

    public Family(String familyName) {
        this.familyName = familyName;
    }

    public Family(String familyName, int familyID) {
        this.familyName = familyName;
        this.familyID = familyID;
    }

    public Family(){ //default constructor

    }

    public String getFamilyName() {return familyName; }

    public void setFamilyID(String familyName) { this.familyName = familyName; }

    public int getFamilyID() {return familyID; }

    public void setFamilyID(int familyID) {this.familyID = familyID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Family family = (Family) o;
        return familyID == family.familyID && Objects.equals(familyName, family.familyName);
    }

}
