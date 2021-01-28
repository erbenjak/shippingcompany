package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusId;
    private String description;
    private Date   updatedAt;

    //creates a filled new status
    public Status(){
        this(StatusDescription.REGISTERED);
    }

    public Status(StatusDescription description){
        this.updatedAt = new Date();
        this.description = description.getDescriptionString();
        this.history.put(this.updatedAt,this.description);
    }

    @OneToOne( mappedBy = "status")
    private AbstractOrder correspondingOrder;

    @ElementCollection
    private Map<Date,String> history = new HashMap<>();

    public void setId(long statusId) {
        this.statusId = statusId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return statusId;
    }

    public void update(StatusDescription description){
        this.updatedAt = new Date();
        this.description = description.getDescriptionString();
    }

    public boolean isEditable(){
        if(description.equals(StatusDescription.IN_DELIVERY.getDescriptionString())){
            return false;
        }

        if(description.equals((StatusDescription.DELIVERED.getDescriptionString()))){
            return  false;
        }

        if(description.equals((StatusDescription.CANCELED.getDescriptionString()))){
            return  false;
        }

        return true;
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

        Status status = (Status) o;

        //same tracking-Id -> same order
        return this.getStatusId()==status.getStatusId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusId());
    }
}
