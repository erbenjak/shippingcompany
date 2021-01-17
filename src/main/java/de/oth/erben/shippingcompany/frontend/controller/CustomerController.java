package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.data.entities.AbstractOrder;
import de.oth.erben.shippingcompany.backend.data.entities.Letter;
import de.oth.erben.shippingcompany.backend.data.entities.Shipment;
import de.oth.erben.shippingcompany.backend.services.order.AbstractOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    AbstractOrderManagementService orderManagementService;

    @RequestMapping("/customer/home")
    public String showStartPage(Model model) {
        return "customer/customerHome";
    }

    @RequestMapping(value="/customer/overview",method= RequestMethod.GET)
    public  String routOverviewPage(Model model){
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return "redirect:/customer/overview/"+userName;
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:/index";
        }
    }

    @RequestMapping(value="/customer/overview/{username}",method=RequestMethod.GET)
    public  String showOverviewPage(Model model, @PathVariable String username){
        model.addAttribute("username",username);

        if(username==null){
            return "redirect:/index";
        }

        if(username.equals("")){
            return "redirect:/index";
        }

        List<AbstractOrder> orders = orderManagementService.findOrdersByCustomerUserName(username);
        model.addAttribute("orders",orders);

        return "customer/customerOrderOverview";
    }

    @RequestMapping(value="/customer/order/details/{trackingId}",method=RequestMethod.GET)
    public String directDetailView(Model model,@PathVariable Long trackingId){
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        if(!order.isPresent()){
            throw new IllegalStateException("Search for non existing order caused an error");
        }

        if(order.get() instanceof Shipment){
            Shipment shipment = (Shipment) order.get();
            model.addAttribute("shipment",shipment);
            return "redirect:/customer/order/details/shipment/"+trackingId;
        }else if(order.get() instanceof Letter){

            return "redirect:/customer/order/details/letter/"+trackingId;
        }else {
            //cautionary exception to detect wrong values when expanding the offered services later on
            throw new IllegalStateException("The datatype of the order is not supported for detail-viewing: "+order.getClass().toGenericString());
        }
    }

    @RequestMapping(value="/customer/order/details/shipment/{trackingId}",method=RequestMethod.GET)
    public String showShipmentDetailView(Model model,@PathVariable Long trackingId){
        //check if order belongs to authenticated Customer

        return "customer/shipmentDetailView";
    }

    @RequestMapping(value="/customer/order/details/letter/{trackingId}",method = RequestMethod.GET)
    public String showLetterDetailView(Model model,@PathVariable Long trackingId){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        if(order.get().getCustomer()==null){
            //go tracking view - which is the same but without editing options
        }

        if(!order.get().getCustomer().getUserName().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            //go to home view - as this is not the correct account for viewing this order
        }

        Letter letter = (Letter) order.get();
        model.addAttribute("letter",letter);
        return "customer/letterDetailView";
    }
}
