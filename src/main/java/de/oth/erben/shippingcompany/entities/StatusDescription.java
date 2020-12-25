package de.oth.erben.shippingcompany.entities;

public enum StatusDescription {
    REGISTERED("The order has been registered with the Shipping company."),
    PLANNED ("The delivery of your order has been planned"),
    CANCELED ("Your order has been cancelled"),
    IN_DELIVERY ("Your order is being delivered"),
    DELIVERED("Your order has been delivered");

    private String descriptionString;

    StatusDescription(String descriptionString){
        this.descriptionString=descriptionString;
    }

    public String getDescriptionString(){
        return  this.descriptionString;
    }
}
