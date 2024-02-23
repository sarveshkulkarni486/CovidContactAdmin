package com.spam.maddy.covidcontactadmin.Model;

public class Vaccine {

    private String VaccineCenterName;
    private String ContactNo;
    private String Address;
    private String VaccineName;
    private String VaccineQty;
    private String VaccinePrice;
    private String VaccineCenterImageUrl;

    public Vaccine() {
    }

    public Vaccine(String vaccineCenterName, String contactNo, String address, String vaccineName, String vaccineQty, String vaccinePrice, String vaccineCenterImageUrl) {
        VaccineCenterName = vaccineCenterName;
        ContactNo = contactNo;
        Address = address;
        VaccineName = vaccineName;
        VaccineQty = vaccineQty;
        VaccinePrice = vaccinePrice;
        VaccineCenterImageUrl = vaccineCenterImageUrl;
    }

    public String getVaccineCenterName() {
        return VaccineCenterName;
    }

    public void setVaccineCenterName(String vaccineCenterName) {
        VaccineCenterName = vaccineCenterName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getVaccineName() {
        return VaccineName;
    }

    public void setVaccineName(String vaccineName) {
        VaccineName = vaccineName;
    }

    public String getVaccineQty() {
        return VaccineQty;
    }

    public void setVaccineQty(String vaccineQty) {
        VaccineQty = vaccineQty;
    }

    public String getVaccinePrice() {
        return VaccinePrice;
    }

    public void setVaccinePrice(String vaccinePrice) {
        VaccinePrice = vaccinePrice;
    }

    public String getVaccineCenterImageUrl() {
        return VaccineCenterImageUrl;
    }

    public void setVaccineCenterImageUrl(String vaccineCenterImageUrl) {
        VaccineCenterImageUrl = vaccineCenterImageUrl;
    }
}
