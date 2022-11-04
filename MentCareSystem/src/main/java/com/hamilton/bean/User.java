package com.hamilton.bean;

import java.sql.Date;
import java.util.Objects;

public class User {
    private String name;
    private String email;
    private String password;
    private Date birthdate;
    private String address;
    private String socialSecurityNumber;

    public User( String name, String email, String password, Date birthdate, String address,
                 String socialSecurityNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.address = address;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public User() { // default constructor

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(birthdate, user.birthdate) && Objects.equals(address, user.address) && Objects.equals(socialSecurityNumber, user.socialSecurityNumber);
    }

}
