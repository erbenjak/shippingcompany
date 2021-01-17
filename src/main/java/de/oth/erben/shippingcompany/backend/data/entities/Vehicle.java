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

    //conversion amount bundles as follows 1=bundle=840 liter the volume of euro-palette filled with liquids
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return amntPersonsRequired == vehicle.amntPersonsRequired && Double.compare(vehicle.maxWeight, maxWeight) == 0 && Double.compare(vehicle.maxVolume, maxVolume) == 0 && vehicleId.equals(vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, amntPersonsRequired, maxWeight, maxVolume);
    }
}
