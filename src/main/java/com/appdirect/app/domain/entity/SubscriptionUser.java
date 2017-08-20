package com.appdirect.app.domain.entity;



import javax.persistence.*;

@Entity
public class SubscriptionUser extends BaseEntity {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String language;

    @Column
    private String email;

    @Column
    private String uuid;

    @Column
    private String openId;

    @Column
    private Boolean administrator = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Subscription subscription;


    public SubscriptionUser() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return "SubscriptionUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", language='" + language + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                ", openId='" + openId + '\'' +
                ", administrator=" + administrator +
                ", subscription=" + subscription +
                '}';
    }
}
