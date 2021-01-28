package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderShipmentDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.ShipmentOrderdDTO;
import de.oth.erben.shippingcompany.backend.services.order.IMailingService;
import de.oth.erben.shippingcompany.backend.services.order.IShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestOrderingApiController {

    @Autowired
    IMailingService mailingService;

    @Autowired
    IShipmentService shipmentService;

    @RequestMapping(value = "/restapi/order/shipment", method = RequestMethod.POST)
    public ShipmentOrderdDTO orderShipment(@RequestBody OrderShipmentDTO orderShipmentDetails){
        return shipmentService.orderShipment(orderShipmentDetails);
    }

    @RequestMapping(value = "/restapi/order/letter", method = RequestMethod.POST)
    public Long orderLetter(@RequestBody OrderLetterDTO orderLetterDTO){
        return mailingService.orderLetter(orderLetterDTO);
    }
}
