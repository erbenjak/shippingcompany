package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.*;
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

        Customer customer = (Customer) o;

        //same tracking-Id -> same order
        return this.getPersonId()==customer.getPersonId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId());
    }

}
