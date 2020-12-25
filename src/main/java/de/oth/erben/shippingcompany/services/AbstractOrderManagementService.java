package de.oth.erben.shippingcompany.services;

import de.oth.erben.shippingcompany.entities.AbstractOrder;
import de.oth.erben.shippingcompany.entities.Status;

public interface AbstractOrderManagementService {

   boolean registerOrder(AbstractOrder newlyCreatedOrder);
}
