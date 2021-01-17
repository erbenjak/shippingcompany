package de.oth.erben.shippingcompany.backend.security.roles;

public enum Roles {
    CUSTOMER("ROLE_CUSTOMER", "SHIPPING_CUSTOMER"),
    EMPLOYEE("ROLE_EMPLOYEE", "SHIPPING_EMPLOYEE");

    private final String internalIdentifier;
    private final String externalIdentifier;

    Roles(String internalIdentifier,String externalIdentifier){
        this.externalIdentifier=externalIdentifier;
        this.internalIdentifier=internalIdentifier;
    }

    public String getInternalIdentifier() {
        return internalIdentifier;
    }

    public String getExternalIdentifier() {
        return externalIdentifier;
    }
}
