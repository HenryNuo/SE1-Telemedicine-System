package com.hamilton.bean;

import java.sql.Date;
import java.util.Objects;

public class CommunityOwner extends User{
    private int communityOwnerID; // created by Database
    private String communityName;
    private int communityID;



    public CommunityOwner(){ //default constructor

    }
    public CommunityOwner(User user, int communityID, int communityOwnerID, String communityName) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getBirthdate(), user.getAddress(), user.getSocialSecurityNumber());
        this.communityName = communityName;
        this.communityID = communityID;

    }
    public CommunityOwner(User user, int communityID) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getBirthdate(), user.getAddress(), user.getSocialSecurityNumber());
        this.communityID = communityID;
    }

    public CommunityOwner( int communityOwnerID,String name, String email, String password, Date birthdate, String address, String socialSecurityNumber, int communityID, String communityName) {
        super(name, email, password, birthdate, address, socialSecurityNumber);
        this.communityName = communityName;
        this.communityID = communityID;
        this.communityOwnerID = communityOwnerID;

    }

    public int getCommunityOwnerID() {
        return communityOwnerID;
    }

    public void setCommunityOwnerID(int communityOwnerID) {
        this.communityOwnerID = communityOwnerID;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
    public int getCommunityID() {
        return communityID;
    }

    public void setCommunityID(int communityID) {
        this.communityID = communityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityOwner that = (CommunityOwner) o;
        return communityOwnerID == that.communityOwnerID && communityID == that.communityID && Objects.equals(communityName, that.communityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(communityOwnerID, communityName, communityID);
    }
}
