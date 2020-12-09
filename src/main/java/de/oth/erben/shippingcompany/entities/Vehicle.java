package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Vehicle {
    @Id
    private Long vehicleId;

    private int amntPersonsRequired;

    private double maxWeight;

    private double maxVolume;

    public Vehicle() {
        this.vehicleId = UUID.randomUUID().getMostSignificantBits();
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getAmntPersonsRequired() {
        return amntPersonsRequired;
    }

    public void setAmntPersonsRequired(int amntPersonsRequired) {
        this.amntPersonsRequired = amntPersonsRequired;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }
}
