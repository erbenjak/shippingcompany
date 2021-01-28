package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
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

    public Vehicle(Long vehicleId, int amntPersonsRequired, double maxWeight, double maxVolume) {
        this.vehicleId = vehicleId;
        this.amntPersonsRequired = amntPersonsRequired;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
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

        Vehicle vehicle = (Vehicle) o;

        //same tracking-Id -> same order
        return this.getVehicleId()==vehicle.getVehicleId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVehicleId());
    }
}
