package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends AbstractPerson{

    @OneToMany(mappedBy = "customer")
    List<AbstractOrder> orders = new ArrayList<>();
}
