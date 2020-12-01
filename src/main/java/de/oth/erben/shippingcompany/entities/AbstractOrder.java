package de.oth.erben.shippingcompany.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public abstract class AbstractOrder implements Serializable {
    //forces inheritance of attributes
    @Id
    private long trackingId;
    private double price;

    @OneToOne
    private Status status;

    @Embedded
    private Address receivingAddress;


    private Trip correspondingTrip;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Address getReceivingAdress() {
        return receivingAddress;
    }

    public void setReceivingAdress(Address receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(long trackingId) {
        this.trackingId = trackingId;
    }
}
