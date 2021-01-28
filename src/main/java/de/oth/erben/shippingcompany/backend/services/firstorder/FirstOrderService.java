package de.oth.erben.shippingcompany.backend.services.firstorder;

import de.oth.erben.shippingcompany.backend.data.entities.Address;
import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.backend.services.order.IMailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Scope("singleton")
@Service
public class FirstOrderService implements IFirstOrderService {

    @Value("${oth-bar.api.url}")
    private String OTH_BAR_URL;

    @Value("${oth-bar.api.value}")
    private String OTH_BAR_VALUE;

    @Autowired
    RestTemplate restClient;

    @Autowired
    IMailingService mailingService;

    @Autowired
    FirstOrderEventPublisher eventPublisher;

    @Override
    public void orderBonusAndSendLetterConfirmation(Customer customer, Address receivingAddress) {
        // first order bonus is a coupon worth 100€

        double value = Double.valueOf(OTH_BAR_VALUE);
        String response = "";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(OTH_BAR_URL).queryParam("value", 100.00);
        try {
            response = restClient.postForObject(builder.toUriString(), null, String.class);
        }catch(Exception e){
            System.out.println("could not apply first order bonus - api not available?");
        }

        orderCouponLetter(response,customer,receivingAddress);
    }


    private void orderCouponLetter(String response,Customer customer,Address receivingAddress){
        //send a letter
        OrderLetterDTO couponLetter = new OrderLetterDTO();
        if(!response.equals("")){
            couponLetter.setContentLetter("Dear "+customer.getFirstName()+" "+customer.getLastName()+", as " +
                    "a little present for becoming our partner and completing your first order we gift you this " +
                    "coupon for Bennis Bar worth: 100€" +
                    "Coupon Code : "+response);
        }else{
            couponLetter.setContentLetter("Dear "+customer.getFirstName()+" "+customer.getLastName()+", as " +
                    "a little present for becoming our partner and completing your first order we gift you this " +
                    "coupon for Bennis Bar worth: 100€ Sadly we could not automatically gernerate your coupon code. " +
                    "Please contact our customer service to receive yours.");
        }

        couponLetter.setReceivingAddress(receivingAddress);
        Long trackingId = mailingService.orderLetter(couponLetter);

        //publish a first order completed event if the user is authenticated --> using the UI
        //do not publish a event in case the usage comes from the Api
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            eventPublisher.sendFirstOrderEvent(new FirstOrderEvent(trackingId));
        }

    }
}
