package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderShipmentDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.ShipmentOrderdDTO;

import java.util.Date;

public interface IShipmentService {
    /**
     *
     * @param orderDetails details needed for creation of a shipmen
     *                     customer is derived by the partnerkey or the security contect
     * @return basic facts about the shipment
     */
    ShipmentOrderdDTO orderShipment(OrderShipmentDTO orderDetails);
}
