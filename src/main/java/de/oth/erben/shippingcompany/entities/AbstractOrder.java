package de.oth.erben.shippingcompany.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    private Customer customer;

    @ManyToMany(mappedBy = "deliverys")
    private List<DeliveryPersonal> deliveryPersonals = new ArrayList<>();

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Address getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(Address receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public Trip getCorrespondingTrip() {
        return correspondingTrip;
    }

    public void setCorrespondingTrip(Trip correspondingTrip) {
        this.correspondingTrip = correspondingTrip;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DeliveryPersonal> getDeliveryPersonals() {
        return deliveryPersonals;
    }

    public void setDeliveryPersonals(List<DeliveryPersonal> deliveryPersonals) {
        this.deliveryPersonals = deliveryPersonals;
    }
}
