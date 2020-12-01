package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Status {
    @Id
    private String statusId;
    private String description;
    private Date   updatedAt;
    private Map<Date,String> history = new HashMap<Date,String>();

    public void setId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
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

    public String getId() {
        return statusId;
    }
}
