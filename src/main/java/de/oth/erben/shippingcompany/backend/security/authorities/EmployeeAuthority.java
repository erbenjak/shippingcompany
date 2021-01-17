package de.oth.erben.shippingcompany.backend.security.authorities;

import de.oth.erben.shippingcompany.backend.security.roles.Roles;

public class EmployeeAuthority extends AbstractAuthority{

    public EmployeeAuthority() {
        super(Roles.EMPLOYEE);
    }

    @Override
    public String getAuthority() {
        return parentRole.getInternalIdentifier();
    }
}
