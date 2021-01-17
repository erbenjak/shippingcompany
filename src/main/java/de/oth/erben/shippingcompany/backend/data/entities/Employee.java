package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee extends AbstractPerson{

    @ManyToMany
    private List<AbstractOrder> deliverys = new ArrayList<>();

}
