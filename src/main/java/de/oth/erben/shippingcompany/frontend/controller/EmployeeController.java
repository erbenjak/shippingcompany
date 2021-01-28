package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.data.entities.*;
import de.oth.erben.shippingcompany.backend.services.order.IOrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    @Autowired
    IOrderManagementService orderManagementService;

    @RequestMapping(value="/employee/overview",method= RequestMethod.GET)
    public  String routOverviewPage(Model model){
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return "redirect:/employee/overview/"+userName;
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @RequestMapping(value="/employee/overview/{username}",method=RequestMethod.GET)
    public  String showOverviewPage(Model model, @PathVariable String username){
        model.addAttribute("username",username);

        if(username==null){
            return "redirect:/";
        }

        if(username.equals("")){
            return "redirect:/";
        }

        List<Trip> trips = orderManagementService.findTripsByEmployeeUserName(username);
        model.addAttribute("trips",trips);

        return "employee/employeeOverview";
    }

    @RequestMapping(value="/employee/trip/details/{tripId}",method=RequestMethod.GET)
    public  String showOverviewPage(Model model, @PathVariable long tripId){

        Optional<Trip> trip= orderManagementService.findByTripId(tripId);

        if(trip.isEmpty()){
            return "redirect:/";
        }

        if(trip.get().getOrders().stream().findFirst().isEmpty()){
            return "redirect:/";
        }

        if(!trip.get().isCompleted()){
            model.addAttribute("tripId",tripId);
        }else{
            model.addAttribute("tripId",null);
        }

        if(trip.get().getOrders().stream().findFirst().get() instanceof Letter){
            List<Letter> letters = trip.get().getOrders().stream().map(order -> (Letter)order).collect(Collectors.toList());
            model.addAttribute("letters",letters);
        }else{
            model.addAttribute("shipment",(Shipment) trip.get().getOrders().stream().findFirst().get());
        }

        model.addAttribute("trip",trip.get());
        return "employee/employeeDetail";
    }

    @RequestMapping("/employee/trip/deliver/{tripId}")
    public  String deliverTrip(@PathVariable("tripId")long tripId){
        Optional<Trip> trip = orderManagementService.findByTripId(tripId);

        if(trip.isEmpty()){
            return "redirect:/";
        }

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Trip> employeeTrips = orderManagementService.findTripsByEmployeeUserName(currentUsername);

        if(!employeeTrips.contains(trip.get())){
            return "redirect:/";
        }

        orderManagementService.deliverTrip(trip.get());

        return "redirect:/employee/overview/"+currentUsername;
    }


    @RequestMapping("/employee/trip/details/employee/trip/deliver/{tripId}")
    public  String deliverTripRedirect(@PathVariable("tripId")long tripId){
        return "redirect:/employee/trip/deliver/"+tripId;
    }
}
