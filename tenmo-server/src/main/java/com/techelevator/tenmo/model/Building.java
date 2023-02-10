package com.techelevator.tenmo.model;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Building {
    private int buildingId;
    private String address;
    private String contract;
    private String status;
    private BigDecimal laborRate;
    private String phoneNumber;
    private String email;
    private ArrayList<Integer> employees = new ArrayList<Integer>();
    
    public Building () {};

    public Building (int buildingId, String address, String contract, String phoneNumber, String email) {
        this.buildingId = buildingId;
        this.address = address;
        this.contract = contract;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public int getBuildingId() {
        return this.buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContract() {
        return this.contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLaborRate() {
        return this.laborRate;
    }

    public void setLaborRate(BigDecimal laborRate) {
        this.laborRate = laborRate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getEmployees() {
        return this.employees;
    }

    public void setEmployees(ArrayList<Integer> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "{" +
            " buildingId='" + getBuildingId() + "'" +
            ", address='" + getAddress() + "'" +
            ", contract='" + getContract() + "'" +
            ", status='" + getStatus() + "'" +
            ", laborRate='" + getLaborRate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", employees='" + getEmployees() + "'" +
            "}";
    }


}
