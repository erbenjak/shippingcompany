package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String showStartPage(Model model) {
        return "index";
    }

    @RequestMapping("/home")
    public String showStartPageAfterSignIn(Model model) {
        return "index";
    }

    @RequestMapping(value="/sign/up")
    public String showSignUpPage(Model model) {
        //redirect to AuthService Stephan
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam("error") Optional<Boolean> error){

        if(error.isPresent() && error.get()){
            model.addAttribute("error",true);
        }else{
            model.addAttribute("error",false);
        }
        return "authentication/signIn";
    }

}
