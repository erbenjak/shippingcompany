package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusId;
    private String description;
    private Date   updatedAt;

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
}
