package de.oth.erben.shippingcompany.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractOrder implements Serializable {
    //forces inheritance of attributes
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long trackingId;

    @NotNull
    private double price;

    @OneToOne
    private Status status;

    @NotNull
    @Embedded
    private Address receivingAddress;

    @ManyToOne
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
