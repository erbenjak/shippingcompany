package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.Letter;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.backend.data.repositorys.LetterRepository;
import de.oth.erben.shippingcompany.backend.exceptions.OrderRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Scope("singleton")
@Service
public class MailingService implements IMailingService{

    @Autowired
    IOrderManagementService orderManagementService;

    @Autowired
    LetterRepository letterRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public long orderLetter(OrderLetterDTO orderDetails) {
        Letter newlyCreatedLetter = new Letter();
        newlyCreatedLetter.setContent(orderDetails.getContentLetter());
        newlyCreatedLetter.setReceivingAddress(orderDetails.getReceivingAddress());
        newlyCreatedLetter.setPrice(0.80);

        String customerIdentifier = SecurityContextHolder.getContext().getAuthentication().getName();

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("CUSTOMER")){
            customerIdentifier = null;
        }
        try {
            orderManagementService.registerOrder(newlyCreatedLetter, Optional.ofNullable(orderDetails.getCustomerKey()));
        }catch(OrderRegistrationException exception){
            System.out.println("Could not register letter");
            return 0;
        }
        letterRepository.save(newlyCreatedLetter);
        return  newlyCreatedLetter.getTrackingId();
    }


}
