package de.oth.erben.shippingcompany.backend.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractPerson{

    public AbstractPerson(){ }

    public AbstractPerson(String firstName,String lastName,String userName,String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.email =email;
    }

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long personId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String userName;

    @Column(nullable = false)
    private String email;

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        AbstractPerson person = (AbstractPerson) o;

        //same tracking-Id -> same order
        return this.personId==person.personId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }
}
