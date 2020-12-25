package de.oth.erben.shippingcompany.services;

import de.oth.erben.shippingcompany.entities.dto.OrderLetterDTO;

public interface AbstractMailingService {

    long orderLetter(OrderLetterDTO oderDetails);



}
