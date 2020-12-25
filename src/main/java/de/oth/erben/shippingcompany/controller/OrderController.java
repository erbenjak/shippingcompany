package de.oth.erben.shippingcompany.controller;

import de.oth.erben.shippingcompany.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.services.AbstractMailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

    @Autowired
    AbstractMailingService mailingService;

    @RequestMapping(value = "/letter", method = RequestMethod.POST)
    public String orderLetter(Model model, @ModelAttribute("orderLetter") OrderLetterDTO orderLetter){
        //creating the letter
        mailingService.orderLetter(orderLetter);

        return "index";
    }

    @RequestMapping(value ="/order/letter", method = RequestMethod.GET)
    public String oderLetterPage(Model model){
        OrderLetterDTO orderLetter = new OrderLetterDTO();
        model.addAttribute("orderLetter",orderLetter);
        return "orderLetter";
    }

}
