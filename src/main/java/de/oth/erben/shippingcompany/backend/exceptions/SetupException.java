package de.oth.erben.shippingcompany.backend.exceptions;

public class SetupException extends Exception{
    public SetupException(String description,Exception cause){
        super(description,cause);
    }
}
