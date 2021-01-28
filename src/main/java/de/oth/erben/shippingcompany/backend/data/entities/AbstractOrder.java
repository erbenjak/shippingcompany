package de.oth.erben.shippingcompany.backend.data.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractOrder implements Serializable {
    //forces inheritance of attributes
    @Id
    @NotNull
    private Long trackingId;

    @NotNull
    private double price;

    //a status without a object to reference is pointless and can therefore be deleted
    @OneToOne(orphanRemoval=true, cascade = CascadeType.PERSIST)
    private Status status;

    @Embedded
    private Address receivingAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Trip correspondingTrip;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    public Long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public abstract String getType();


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

        AbstractOrder order = (AbstractOrder) o;

        //same tracking-Id -> same order
        return this.trackingId==order.trackingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingId);
    }
}
