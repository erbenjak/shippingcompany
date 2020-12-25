package de.oth.erben.shippingcompany.entities;

import com.sun.istack.NotNull;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractPerson {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long personId;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;
}
