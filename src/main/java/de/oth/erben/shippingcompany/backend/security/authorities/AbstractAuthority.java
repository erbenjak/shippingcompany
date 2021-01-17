package de.oth.erben.shippingcompany.backend.security.authorities;

import de.oth.erben.shippingcompany.backend.security.roles.Roles;
import org.springframework.security.core.GrantedAuthority;

public abstract class AbstractAuthority implements GrantedAuthority {

    Roles parentRole;

    public AbstractAuthority(Roles parentRole){
        this.parentRole = parentRole;
    }
}
