package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Letter extends AbstractOrder{
    @Column(columnDefinition = "TEXT")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getType() {
        return "Letter";
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

        Letter letter = (Letter) o;

        //same tracking-Id -> same order
        return this.getTrackingId()==letter.getTrackingId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrackingId());
    }
}
