package de.oth.erben.shippingcompany.backend.services.firstorder;

import de.oth.erben.shippingcompany.backend.data.entities.Address;
import de.oth.erben.shippingcompany.backend.data.entities.Customer;

public interface IFirstOrderService {

    /**
     * Upon the first order of a shipment a customer will receive a bonus
     *
     * @param customer who will receive the bonus
     * @param receivingAddress address the bonus will be delivered to
     */
    abstract void orderBonusAndSendLetterConfirmation(Customer customer, Address receivingAddress);
}
