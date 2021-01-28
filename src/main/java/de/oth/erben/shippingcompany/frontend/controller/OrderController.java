package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderShipmentDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.ShipmentOrderdDTO;
import de.oth.erben.shippingcompany.backend.services.firstorder.FirstOrderEventSubscriber;
import de.oth.erben.shippingcompany.backend.services.order.IMailingService;
import de.oth.erben.shippingcompany.backend.services.order.IShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

    @Autowired
    IMailingService mailingService;

    @Autowired
    IShipmentService shipmentService;

    @Autowired
    FirstOrderEventSubscriber eventSubscriber;

    @RequestMapping(value = "/letter", method = RequestMethod.POST)
    public String orderLetter(Model model, @ModelAttribute("orderLetter") OrderLetterDTO orderLetter){
        //creating the letter
        long trackingId = mailingService.orderLetter(orderLetter);
        return "redirect:/order/letter/confirmation/"+trackingId;
    }

    @RequestMapping(value ="/order/letter", method = RequestMethod.GET)
    public String oderLetterPage(Model model){
        OrderLetterDTO orderLetter = new OrderLetterDTO();
        model.addAttribute("orderLetter",orderLetter);
        return "order/orderLetter";
    }

    @RequestMapping(value="/order/letter/confirmation/{trackingId}",method = RequestMethod.GET)
    public String showConfirmationLetter(Model model,@PathVariable ("trackingId") long trackingId){
        model.addAttribute("trackingId",trackingId);
        model.addAttribute("logedIn", ! (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken));
        return "order/orderLetterConfirmation";
    }

    @RequestMapping(value = "/shipment", method = RequestMethod.POST)
    public String orderShipment(Model model, @ModelAttribute("orderLetter") OrderShipmentDTO orderShipment){
        //creating the shipment
        ShipmentOrderdDTO orderedDTO = shipmentService.orderShipment(orderShipment);
        return "redirect:/order/shipment/confirmation/"+orderedDTO.getTrackingId();
    }

    @RequestMapping(value ="/order/shipment", method = RequestMethod.GET)
    public String oderShipmentPage(Model model){
        OrderShipmentDTO orderShipment = new OrderShipmentDTO();
        model.addAttribute("orderShipment",orderShipment);
        return "order/orderShipment";
    }

    @RequestMapping(value="/order/shipment/confirmation/{trackingId}",method = RequestMethod.GET)
    public String showConfirmationShipment(Model model,@PathVariable ("trackingId") long trackingId){
        if(eventSubscriber.isFirstOrderBonusCompleted()){
            model.addAttribute("firstOrderBonus",eventSubscriber.getTrackingId());
        }else{
            model.addAttribute("firstOrderBonus",null);
        }

        model.addAttribute("trackingId",trackingId);
        model.addAttribute("logedIn",! (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken));
        return "order/orderShipmentConfirmation";
    }

}
