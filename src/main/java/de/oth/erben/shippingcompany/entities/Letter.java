package de.oth.erben.shippingcompany.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Letter extends AbstractOrder{
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
