package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Shipment extends AbstractOrder{
    private Date pickupTime;
    private int amountBoundle;

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }
}
