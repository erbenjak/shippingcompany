package de.oth.erben.shippingcompany.backend.data.entities.dto;

import java.util.Date;

public class ShipmentOrderdDTO {

    private Long trackingId;
    private Date pickupTime;
    private Date dropOffTime;

    public ShipmentOrderdDTO(){}

    public ShipmentOrderdDTO(Long trackingId, Date pickupTime, Date dropOffTime) {
        this.trackingId = trackingId;
        this.pickupTime = pickupTime;
        this.dropOffTime = dropOffTime;
    }

    public Long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Date getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(Date dropOffTime) {
        this.dropOffTime = dropOffTime;
    }
}
