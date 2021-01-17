package de.oth.erben.shippingcompany.backend.data.entities.dto;

import de.oth.erben.shippingcompany.backend.data.entities.Address;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrderShipmentDTO {

    private Address startingAddress;
    private Address receivingAddress;

    private String receivingAddressString;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date pickupTime;
    private int amountBundles;
    private String pickUpTimeString;
    private String customerKey;

    public OrderShipmentDTO(){}

    public OrderShipmentDTO(Address startingAddress, String receivingAddressString, Date pickupTime, int amountBundles, String customerKey) {
        this.startingAddress = startingAddress;
        this.receivingAddressString = receivingAddressString;
        this.pickupTime = pickupTime;
        this.amountBundles = amountBundles;
        this.customerKey = customerKey;
    }

    public OrderShipmentDTO(Address startingAddress, Address receivingAddress, Date pickupTime, int amountBundles, String customerKey) {
        this.startingAddress = startingAddress;
        this.receivingAddress = receivingAddress;
        this.pickupTime = pickupTime;
        this.amountBundles = amountBundles;
        this.customerKey = customerKey;
    }

    public String getPickUpTimeString() {
        return pickUpTimeString;
    }

    public void setPickUpTimeString(String pickUpTimeString) {
        this.pickUpTimeString = pickUpTimeString;
    }

    public Address getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(Address startingAddress) {
        this.startingAddress = startingAddress;
    }

    public Address getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(Address receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getAmountBundles() {
        return amountBundles;
    }

    public void setAmountBundles(int amountBundles) {
        this.amountBundles = amountBundles;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }
}
