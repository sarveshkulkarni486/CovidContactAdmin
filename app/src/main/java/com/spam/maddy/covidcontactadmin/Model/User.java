package com.spam.maddy.covidcontactadmin.Model;

public class User {
    private String Name;
    private String Password;
    private String Dob;
    private String Email;
    private String homeAddress;
    private String CovidTest;
    private String CovidTestDate;

    public User(){

    }

    public User(String name, String password, String dob, String email, String homeAddress, String covidTest, String covidTestDate) {
        Name = name;
        Password = password;
        Dob = dob;
        Email = email;
        this.homeAddress = homeAddress;
        CovidTest = covidTest;
        CovidTestDate = covidTestDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCovidTest() {
        return CovidTest;
    }

    public void setCovidTest(String covidTest) {
        CovidTest = covidTest;
    }

    public String getCovidTestDate() {
        return CovidTestDate;
    }

    public void setCovidTestDate(String covidTestDate) {
        CovidTestDate = covidTestDate;
    }
}
