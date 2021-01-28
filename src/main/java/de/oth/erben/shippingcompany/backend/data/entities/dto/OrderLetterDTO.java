package de.oth.erben.shippingcompany.backend.data.entities.dto;

import de.oth.erben.shippingcompany.backend.data.entities.Address;
import org.hibernate.annotations.Type;

public class OrderLetterDTO {

    private Address receivingAddress;
    private String contentLetter;
    private String customerKey;

    public OrderLetterDTO(){}

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

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

}
