package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DeliveryPersonal extends AbstractPerson{

    private String bankInformation;

    @ManyToMany
    private List<AbstractOrder> deliverys = new ArrayList<>();


}
