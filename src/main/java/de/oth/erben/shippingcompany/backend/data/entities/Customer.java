package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Customer extends AbstractPerson{

    @OneToMany(mappedBy = "customer")
    List<AbstractOrder> orders = new ArrayList<>();

    @Column(unique = true,nullable = true)
    String partnerKey;

    public Customer(){}

    public Customer(String firstName,String lastName,String userName,String email, String partnerKey){
        super(firstName,lastName,userName,email);
        this.partnerKey=partnerKey;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }

    public List<AbstractOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<AbstractOrder> orders) {
        this.orders = orders;
    }
}
