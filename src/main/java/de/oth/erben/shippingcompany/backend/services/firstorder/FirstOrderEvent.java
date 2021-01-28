package de.oth.erben.shippingcompany.backend.services.firstorder;

import org.springframework.context.ApplicationEvent;

public class FirstOrderEvent extends ApplicationEvent {

    public FirstOrderEvent(Long source) {
        super(source);
    }


}
