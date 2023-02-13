package com.techelevator.tenmo.model;
//IDK. declarations..?
public class Employee {
    private int employeeId;
    private String fullName;
    private String phoneNumber;
    private String email;

    public Employee () {};
//constructor
    public Employee (int employeeId, String fullName, String phoneNumber, String email) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
//getters
public int getEmployeeId() {
    return this.employeeId;
}
public String getFullName() {
    return this.fullName;
}
public String getPhoneNumber(){
    return this.phoneNumber;
}
public String getEmail(){
    return this.email;
}
//setters
public void setEmployeeId(int employeeId){
    this.employeeId = employeeId;
}
public void setFullName(String fullName){
    this.fullName = fullName;
}
public void setPhoneNumber (String phoneNumber){
    this.phoneNumber = phoneNumber;
}
public void setEmail (String email){
    this.email = email;
}
//IDK. overriders...?




    @Override
    public String toString() {
        return "{" +
            " employeeId='" + getEmployeeId() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}