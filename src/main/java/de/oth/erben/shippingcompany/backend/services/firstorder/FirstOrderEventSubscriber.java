package de.oth.erben.shippingcompany.backend.services.firstorder;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class FirstOrderEventSubscriber implements ApplicationListener<FirstOrderEvent> {

    private boolean firstOrderBonusCompleted = false;
    private long trackingId = 0;

    @Override
    public void onApplicationEvent(FirstOrderEvent firstOrderEvent) {
        this.firstOrderBonusCompleted = true;
        this.trackingId =(Long) firstOrderEvent.getSource();
    }

    public boolean isFirstOrderBonusCompleted() {
        if(firstOrderBonusCompleted){
            firstOrderBonusCompleted = false;
            return true;
        }else{
            return false;
        }
    }

    public long getTrackingId() {
        return trackingId;
    }
}
