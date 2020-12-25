package de.oth.erben.shippingcompany.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String country;
    private String street;
    private String house;
    private String postalCode;
    private String receiver;

    public Address() { }

    public Address(String city, String country, String street, String house, String postalCode, String receiver) {
        this.city = city;
        this.country = country;
        this.street = street;
        this.house = house;
        this.postalCode = postalCode;
        this.receiver = receiver;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
