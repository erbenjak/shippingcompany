package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tripId;

    @OneToMany(mappedBy="correspondingTrip",cascade = CascadeType.PERSIST)
    private Collection<AbstractOrder> orders;

    private boolean completed = false;
    //to be expected dropOffTime
    private Date dropOffTime;

    @ManyToOne()
    private Vehicle deliveryVehicle;

    @ManyToMany(mappedBy = "deliveries",cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();

    public Trip() {
    }

    public Trip(Date dropOffTime, Vehicle deliveryVehicle, List<AbstractOrder> orders) {
        this.dropOffTime = dropOffTime;
        this.deliveryVehicle = deliveryVehicle;
        this.orders = orders;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Vehicle getDeliveryVehicle() {
        return deliveryVehicle;
    }

    public void setDeliveryVehicle(Vehicle deliveryVehicle) {
        this.deliveryVehicle = deliveryVehicle;
    }

    public Collection<AbstractOrder> getOrders() {
        return orders;
    }

    public void setOrders(Collection<AbstractOrder> orders) {
        this.orders = orders;
    }

    public Date getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(Date dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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

        Trip trip = (Trip) o;

        //same tracking-Id -> same order
        return this.getTripId()==trip.getTripId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId);
    }

}
