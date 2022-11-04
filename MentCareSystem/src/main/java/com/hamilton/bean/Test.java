package com.hamilton.bean;

import java.util.Objects;

public class Test {

    private int testID; //Created by database
    private String testName;
    private double upperLimit;
    private double lowerLimit;
    private String unitOfMeasure;

    public Test(String testName, double upperLimit, double lowerLimit, String unitOfMeasure) {
        this.testName = testName;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.unitOfMeasure = unitOfMeasure;
    }

    //No-Argument Constructor
    public Test() {

    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return testID == test.testID && Double.compare(test.upperLimit, upperLimit) == 0 && Double.compare(test.lowerLimit, lowerLimit) == 0 && Objects.equals(testName, test.testName) && Objects.equals(unitOfMeasure, test.unitOfMeasure);
    }

}
