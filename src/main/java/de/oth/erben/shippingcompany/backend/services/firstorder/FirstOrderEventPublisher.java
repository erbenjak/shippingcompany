package de.oth.erben.shippingcompany.backend.services.firstorder;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class FirstOrderEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher pub) {
        this.publisher = pub;
    }

    public void sendFirstOrderEvent(FirstOrderEvent event) {
        publisher.publishEvent(event);
    }
}
