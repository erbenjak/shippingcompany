package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;

@Entity
public class Trip {
    @Id
    private long tripId;

    @OneToMany(mappedBy="correspondingTrip")
    private Collection<AbstractOrder> orders;

    private boolean completed = false;
    private Date dropoffTime;

    @ManyToOne()
    private Vehicle deliveryVehicle;

    public Trip() {
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

    public Date getDropoffTime() {
        return dropoffTime;
    }

    public void setDropoffTime(Date dropoffTime) {
        this.dropoffTime = dropoffTime;
    }

    public Vehicle getDeliveryVehicle() {
        return deliveryVehicle;
    }

    public void setDeliveryVehicle(Vehicle deliveryVehicle) {
        this.deliveryVehicle = deliveryVehicle;
    }
}
