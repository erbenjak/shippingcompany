package de.oth.erben.shippingcompany.backend.security.authorities;

import de.oth.erben.shippingcompany.backend.security.roles.Roles;

public class CutomerAuthority extends AbstractAuthority{

    public CutomerAuthority() {
        super(Roles.CUSTOMER);
    }

    @Override
    public String getAuthority() {
        return parentRole.getInternalIdentifier();
    }
}
