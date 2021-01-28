package de.oth.erben.shippingcompany.backend.data.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Employee extends AbstractPerson implements Comparable<Employee>{

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Trip> deliveries = new ArrayList<>();

    public Employee(){}

    public Employee(String firstName, String lastName, String userName, String email) {
        super(firstName, lastName, userName, email);
    }

    public List<Trip> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Trip> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Trip> getDeliveriesStillOpen(){
        return  deliveries.stream().filter(delivery -> !delivery.isCompleted()).collect(Collectors.toList());
    }

    @Override
    public int compareTo(@NotNull Employee o) {
        if(o.getDeliveriesStillOpen().size() == this.getDeliveriesStillOpen().size()){
            return 0;
        }else if(o.getDeliveriesStillOpen().size()<this.getDeliveriesStillOpen().size()){
            return -1;
        }else{
            return 1;
        }
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

        Employee employee = (Employee) o;

        //same tracking-Id -> same order
        return this.getPersonId()==employee.getPersonId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId());
    }

}
