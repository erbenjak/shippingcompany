package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.AbstractOrder;

import java.util.List;
import java.util.Optional;

public interface AbstractOrderManagementService {

   boolean registerOrder(AbstractOrder newlyCreatedOrder, Optional<String> customer);

   void planOrder(List<AbstractOrder> abstractOrders,Class typeOfOrder);

   List<AbstractOrder> findOrdersByCustomerUserName(String userName);

   Optional<AbstractOrder> findByTrackingId(Long trackingId);
}
