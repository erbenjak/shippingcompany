package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.data.entities.AbstractOrder;
import de.oth.erben.shippingcompany.backend.data.entities.Letter;
import de.oth.erben.shippingcompany.backend.data.entities.Shipment;
import de.oth.erben.shippingcompany.backend.data.entities.StatusDescription;
import de.oth.erben.shippingcompany.backend.services.order.IOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    IOrderManagementService orderManagementService;

    @RequestMapping(value="/customer/cancel/{trackingId}/{confirmed}")
    public  String routCancelOrder(Model model,@PathVariable long trackingId,@PathVariable boolean confirmed){
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

            if(order.isEmpty()){
                return "redirect:/index";
            }

            if(!order.get().getStatus().isEditable()){
                return "redirect:/customer/overview";
            }


            if(confirmed){
                orderManagementService.cancelOrder(order.get());
                return "redirect:/customer/overview";
            }

            return "customer/cancelPage";
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:/index";
        }
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

    @RequestMapping(value="/customer/order/details/{trackingId}",method = RequestMethod.GET)
    public String directDetailView(Model model,@PathVariable Long trackingId){
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        if(!order.isPresent()){
            throw new IllegalStateException("Search for non existing order caused an error");
        }

        if(order.get() instanceof Shipment){
            return "redirect:/customer/order/details/shipment/"+trackingId;
        }else if(order.get() instanceof Letter){
            return "redirect:/customer/order/details/letter/"+trackingId;
        }else {
            //cautionary exception to detect wrong values when expanding the offered services later on
            throw new IllegalStateException("The datatype of the order is not supported for detail-viewing: "+order.getClass().toGenericString());
        }
    }


    /*
    * ----------------------------------------------------------------
    * Letter - Editing
    * ----------------------------------------------------------------
    */

    @RequestMapping(value="/customer/order/details/letter/{trackingId}",method = RequestMethod.GET)
    public String showLetterDetailView(Model model,@PathVariable Long trackingId){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        //check if order belongs to logedInUser
        if(!orderManagementService.checkIfOrderBelongsToCustomer(SecurityContextHolder.getContext().getAuthentication().getName(),trackingId)){
            return "redirect:/";
        }

        Letter letter = (Letter) order.get();
        model.addAttribute("letter",letter);
        model.addAttribute("inputDisabled","disabled");

        if(letter.getStatus().isEditable()){
            model.addAttribute("edit", null);
        }else{
            model.addAttribute("edit", "disabled");
            //just to overwrite editing
            model.addAttribute("inputDisabled","disabled");
        }
        return "customer/letterDetailView";
    }

    @RequestMapping(value="/customer/order/details/letter/{trackingId}/{edit}",method = RequestMethod.GET)
    public String showLetterEditingView(Model model,@PathVariable Long trackingId, @PathVariable boolean edit){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        //check if order belongs to logedInUser
        if(!orderManagementService.checkIfOrderBelongsToCustomer(SecurityContextHolder.getContext().getAuthentication().getName(),trackingId)){
            return "redirect:/";
        }

        Letter letter = (Letter) order.get();
        model.addAttribute("letter",letter);
        if(edit) {
            model.addAttribute("inputDisabled", null);
        }else{
            model.addAttribute("inputDisabled","disabled");
        }

        if(letter.getStatus().isEditable()){
            model.addAttribute("edit", null);
        }else{
            model.addAttribute("edit", "disabled");
            //just to overwrite editing
            model.addAttribute("inputDisabled","disabled");
        }
        return "customer/letterDetailView";
    }

    @RequestMapping(value="/edit/letter/save/{trackingId}")
    public String saveLetterEdit(Model model, @ModelAttribute Letter letter,@PathVariable Long trackingId){
        Optional<AbstractOrder> oldOrder = orderManagementService.findByTrackingId(trackingId);
        if(oldOrder.isEmpty()){
            String message ="No order to update found";
            return  "redirect:/customer/order/details/letter/"+trackingId+"/after/edit/"+message;
        }

        Letter oldLetter = (Letter) oldOrder.get();

        if(oldLetter.getStatus().getDescription().equals(StatusDescription.IN_DELIVERY.getDescriptionString())
                ||oldLetter.getStatus().getDescription().equals(StatusDescription.DELIVERED.getDescriptionString())
                ||oldLetter.getStatus().getDescription().equals(StatusDescription.CANCELED.getDescriptionString())){

            String message = "A delivered letter or one being delivered right now can't be edited. Also canceled orders can not be edited.";

            return  "redirect:/customer/order/details/letter/"+trackingId+"/after/edit/"+message;
        }

        orderManagementService.editOrderLetter(oldLetter,letter);
        String message = "Edited successfully";
        return  "redirect:/customer/order/details/letter/"+trackingId+"/after/edit/"+message;
    }

    @RequestMapping(value="/customer/order/details/letter/{trackingId}/after/edit/{message}")
    public String showLetterDetailViewAfterEdit(Model model,@PathVariable Long trackingId,@PathVariable String message){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        //check if order belongs to logedInUser
        if(!orderManagementService.checkIfOrderBelongsToCustomer(SecurityContextHolder.getContext().getAuthentication().getName(),trackingId)){
            return "redirect:/";
        }

        Letter letter = (Letter) order.get();
        model.addAttribute("letter",letter);
        model.addAttribute("inputDisabled","disabled");
        model.addAttribute("message",message);
        return "customer/letterDetailView";
    }


    /*
     * ----------------------------------------------------------------
     * Shipment - Editing
     * ----------------------------------------------------------------
     */

    @RequestMapping(value="/customer/order/details/shipment/{trackingId}",method = RequestMethod.GET)
    public String showShipmentDetailView(Model model,@PathVariable Long trackingId){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        //check if order belongs to logedInUser
        if(!orderManagementService.checkIfOrderBelongsToCustomer(SecurityContextHolder.getContext().getAuthentication().getName(),trackingId)){
            return "redirect:/";
        }

        Shipment shipment = (Shipment) order.get();
        model.addAttribute("shipment",shipment);
        model.addAttribute("inputDisabled","disabled");

        if(shipment.getStatus().isEditable()){
            model.addAttribute("edit", null);
        }else{
            model.addAttribute("edit", "disabled");
            //just to overwrite editing
            model.addAttribute("inputDisabled","disabled");
        }

        return "customer/shipmentDetailView";
    }

    @RequestMapping(value="/customer/order/details/shipment/{trackingId}/{edit}",method = RequestMethod.GET)
    public String showShipmentEditingView(Model model,@PathVariable Long trackingId, @PathVariable boolean edit){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        //check if order belongs to logedInUser
        if(!orderManagementService.checkIfOrderBelongsToCustomer(SecurityContextHolder.getContext().getAuthentication().getName(),trackingId)){
            return "redirect:/";
        }

        Shipment shipment = (Shipment) order.get();
        model.addAttribute("shipment",shipment);
        if(edit) {
            model.addAttribute("inputDisabled", null);
        }else{
            model.addAttribute("inputDisabled","disabled");
        }

        if(shipment.getStatus().isEditable()){
            model.addAttribute("edit", null);
        }else{
            model.addAttribute("edit", "disabled");
            //just to overwrite editing
            model.addAttribute("inputDisabled","disabled");
        }

        return "customer/shipmentDetailView";
    }

    @RequestMapping(value="/edit/shipment/save/{trackingId}")
    public String saveShipmentEdit(Model model, @ModelAttribute Shipment shipment,@PathVariable Long trackingId){
        Optional<AbstractOrder> oldOrder = orderManagementService.findByTrackingId(trackingId);
        if(oldOrder.isEmpty()){
            String message ="No order to update found";
            return  "redirect:/customer/order/details/shipment/"+trackingId+"/after/edit/"+message;
        }

        Shipment oldShipment = (Shipment) oldOrder.get();

        if(oldShipment.getStatus().getDescription().equals(StatusDescription.IN_DELIVERY.getDescriptionString())
                ||oldShipment.getStatus().getDescription().equals(StatusDescription.DELIVERED.getDescriptionString())
                ||oldShipment.getStatus().getDescription().equals(StatusDescription.CANCELED.getDescriptionString())){

            String message = "A delivered shipment or one being delivered right now can't be edited. Canceled orders can also not be edited";

            return  "redirect:/customer/order/details/shipment/"+trackingId+"/after/edit/"+message;
        }

        orderManagementService.editOrderShipment(oldShipment,shipment);
        String message = "Edited successfully";
        return  "redirect:/customer/order/details/shipment/"+trackingId+"/after/edit/"+message;
    }

    @RequestMapping(value="/customer/order/details/shipment/{trackingId}/after/edit/{message}")
    public String showShipmentDetailViewAfterEdit(Model model,@PathVariable Long trackingId,@PathVariable String message){
        //check if order belongs to authenticated Customer
        Optional<AbstractOrder> order = orderManagementService.findByTrackingId(trackingId);

        //check if order belongs to logedInUser
        if(!orderManagementService.checkIfOrderBelongsToCustomer(SecurityContextHolder.getContext().getAuthentication().getName(),trackingId)){
            return "redirect:/";
        }

        Shipment shipment = (Shipment) order.get();
        model.addAttribute("shipment",shipment);
        model.addAttribute("inputDisabled","disabled");
        model.addAttribute("message",message);
        return "customer/shipmentDetailView";
    }

}
