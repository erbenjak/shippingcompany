package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    private String city = "";
    private String country = "";
    private String street = "";
    private String house = "";
    private String postalCode = "";
    private String receiver = "";

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

    @Override
    public String toString(){
        return this.receiver+" - "+this.country+" - "+this.city+" - "+this.postalCode+" - "+this.street+" - "+this.house;
    }

    public boolean checkIfAddressIsEmpty(){
        if(this.city !=null){
            if(!this.receiver.equals("")){
                return false;
            }
        }

        if(this.country !=null){
            if(!this.receiver.equals("")){
                return false;
            }
        }

        if(this.street !=null){
            if(!this.receiver.equals("")){
                return false;
            }
        }

        if(this.house !=null){
            if(!this.receiver.equals("")){
                return false;
            }
        }

        if(this.postalCode !=null){
            if(!this.receiver.equals("")){
                return false;
            }
        }

        if(this.receiver !=null){
            if(!this.receiver.equals("")){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        //checking object Identity
        if(this == o){
            return true;
        }

        if(o == null){
            return false;
        }

        if(o.getClass() != this.getClass()){
            return false;
        }

        Address address = (Address) o;

        if(!address.city.equals((address.getCity()))){
            return false;
        }
        if(!address.country.equals((address.getCountry()))){
            return false;
        }
        if(!address.street.equals((address.getStreet()))){
            return false;
        }
        if(!address.house.equals((address.getHouse()))){
            return false;
        }
        if(!address.postalCode.equals((address.getPostalCode()))){
            return false;
        }
        if(!address.receiver.equals((address.getReceiver()))){
            return false;
        }

        return true;
    }

}
