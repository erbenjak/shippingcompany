package de.oth.erben.shippingcompany.services;


import de.oth.erben.shippingcompany.entities.Letter;
import de.oth.erben.shippingcompany.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.repositorys.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MailingSevice implements  AbstractMailingService{

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private AbstractOrderManagementService orderManagementService;

    @Override
    public long orderLetter(OrderLetterDTO oderDetails) {
        Letter newlyCreatedLetter = new Letter();
        newlyCreatedLetter.setContent(oderDetails.getContentLetter());
        newlyCreatedLetter.setReceivingAdress(oderDetails.getReceivingAddress());
        newlyCreatedLetter.setPrice(0.80);
        orderManagementService.registerOrder(newlyCreatedLetter);
        letterRepository.save(newlyCreatedLetter);
        return  newlyCreatedLetter.getTrackingId();
    }
}
