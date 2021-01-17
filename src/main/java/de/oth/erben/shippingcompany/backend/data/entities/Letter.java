package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.Entity;

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
