package com.appdirect.app.domain.entity;



import javax.persistence.*;

@Entity
public class Company extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String companyUuid;

    @Column
    private String companyCountry;

    @Column
    private String companyName;

    @Column
    private String companyPhoneNumber;

    @Column
    private String companyWebsite;

    public Company() {
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyUuid='" + companyUuid + '\'' +
                ", companyCountry='" + companyCountry + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyPhoneNumber='" + companyPhoneNumber + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                '}';
    }
}
