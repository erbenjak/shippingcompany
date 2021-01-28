package de.oth.erben.shippingcompany.backend.data.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Shipment extends AbstractOrder{

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="city", column = @Column(name="pickUpCity") ),
            @AttributeOverride(name="country", column = @Column(name="pickUpCountry") ),
            @AttributeOverride(name="street", column = @Column(name="pickUpStreet") ),
            @AttributeOverride(name="house", column = @Column(name="pickUpHouse") ),
            @AttributeOverride(name="postalCode", column = @Column(name="pickUpPostalCode") ),
            @AttributeOverride(name="receiver", column = @Column(name="pickUpReceiver") )
    } )
    private Address pickUpAddress;
    private int amountBundle;

    @DateTimeFormat(pattern="dd/mm/yyyy")
    private Date pickUpTime;

    @DateTimeFormat(pattern="dd/mm/yyyy")
    private Date dropOffTime;

    public Address getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(Address pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public int getAmountBundle() {
        return amountBundle;
    }

    public void setAmountBundle(int amountBundle) {
        this.amountBundle = amountBundle;
    }

    public Date getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(Date dropoffTime) {
        this.dropOffTime = dropoffTime;
    }

    @Override
    public String getType() {
        return "Shipment";
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

        Shipment shipment = (Shipment) o;

        //same tracking-Id -> same order
        return this.getTrackingId()==shipment.getTrackingId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrackingId());
    }
}
