package de.oth.erben.shippingcompany.entities.dto;

import de.oth.erben.shippingcompany.entities.Address;

public class OrderLetterDTO {

    private Address receivingAddress;
    private String contentLetter;

    public OrderLetterDTO(){}

    public OrderLetterDTO(Address receivingAddress, String contentLetter){
        this.contentLetter    = contentLetter;
        this.receivingAddress = receivingAddress;
    }

    public void setReceivingAddress(Address receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public void setContentLetter(String contentLetter) {
        this.contentLetter = contentLetter;
    }

    public Address getReceivingAddress() {
        return receivingAddress;
    }

    public String getContentLetter() {
        return contentLetter;
    }
}
