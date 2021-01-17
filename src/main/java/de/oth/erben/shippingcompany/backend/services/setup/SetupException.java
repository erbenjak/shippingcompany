package de.oth.erben.shippingcompany.backend.services.setup;

public class SetupException extends Exception{
    public SetupException(String description,Exception cause){
        super(description,cause);
    }
}
