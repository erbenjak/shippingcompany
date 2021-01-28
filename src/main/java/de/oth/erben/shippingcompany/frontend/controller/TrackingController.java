package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.data.entities.AbstractOrder;
import de.oth.erben.shippingcompany.backend.services.order.IOrderManagementService;
import de.oth.erben.shippingcompany.frontend.breadCrumbs.TrackingCrumb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class TrackingController {

    @Autowired
    IOrderManagementService orderManagementService;

    @RequestMapping(value = "/track",method = RequestMethod.POST)
    public String pathToTrackingSite(@ModelAttribute("trackingCrumb")TrackingCrumb crumb){

        if(crumb.getTrackingId() == null){
            return "redirect:/";
        }

        if(crumb.getTrackingId().equals("")){
            return "redirect:/";
        }

        long trackingId = 0;

        try {
             trackingId = Long.parseLong(crumb.getTrackingId());
        }catch(Exception e){
            System.out.println("given id could not be casted to a long-> therefore it is invalid");
        }

        return "redirect:/track/"+trackingId;
    }

    @RequestMapping(value = "/track/{trackingId}",method = RequestMethod.GET)
    public String showTrackingSite(@PathVariable("trackingId") long trackingId, Model model){

        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        if(order.isEmpty()){
            model.addAttribute("order",null);
        }else{
            model.addAttribute("order",order.get());
        }

        return "tracking";
    }
}
