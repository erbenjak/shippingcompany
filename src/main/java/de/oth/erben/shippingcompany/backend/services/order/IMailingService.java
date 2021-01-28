package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;

public interface IMailingService {
    /**
     *
     * @param oderDetails contains al information about a letter
     *                    if the customerkey is null the customer will be derived from the current security-context
     * @return tracking-id which can be used to track the order
     * */
    long orderLetter(OrderLetterDTO oderDetails);


}
