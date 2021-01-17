package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderShipmentDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.ShipmentOrderdDTO;

public interface AbstractMailingService {

    long orderLetter(OrderLetterDTO oderDetails);

    ShipmentOrderdDTO orderShipment(OrderShipmentDTO orderDetails);

}
