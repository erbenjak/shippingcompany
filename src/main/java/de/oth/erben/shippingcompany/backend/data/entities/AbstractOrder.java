package de.oth.erben.shippingcompany.backend.data.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractOrder implements Serializable {
    //forces inheritance of attributes
    @Id
    @NotNull
    private Long trackingId;

    @NotNull
    private double price;

    @OneToOne
    private Status status;

    //@NotNull
    @Embedded
    private Address receivingAddress;

    @ManyToOne
    private Trip correspondingTrip;

    @ManyToOne
    private Customer customer;

    @ManyToMany(mappedBy = "deliverys")
    private List<Employee> employees = new ArrayList<>();

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

    public List<Employee> getDeliveryPersonals() {
        return employees;
    }

    public void setDeliveryPersonals(List<Employee> employees) {
        this.employees = employees;
    }
}
