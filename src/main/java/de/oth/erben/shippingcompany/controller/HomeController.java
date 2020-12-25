package de.oth.erben.shippingcompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String showStartPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "index";
    }

    @RequestMapping("/signup")
    public String showSignUpPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "signUp";
    }

    @RequestMapping("/signin")
    public String showSignInPage(Model model) {
        model.addAttribute("today", new Date().toString());
        return "signIn";
    }
}
