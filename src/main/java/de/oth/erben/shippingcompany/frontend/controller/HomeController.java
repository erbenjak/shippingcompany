package de.oth.erben.shippingcompany.frontend.controller;

import de.oth.erben.shippingcompany.backend.services.REST.ISignUrlLoader;
import de.oth.erben.shippingcompany.backend.services.user.UserService;
import de.oth.erben.shippingcompany.frontend.breadCrumbs.TrackingCrumb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    ISignUrlLoader urlLoader;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showStartPage(Model model) {
        TrackingCrumb trackingCrumb = new TrackingCrumb();
        model.addAttribute("trackingCrumb", trackingCrumb);
        return "index";
    }

    @RequestMapping("/home")
    public String showStartPageAfterSignIn(Model model) {
        return "index";
    }

    @PermitAll
    @RequestMapping(value="/sign/up")
    public ResponseEntity<Void> showSignUpPage(HttpServletResponse httpServletResponse) {
        String signInUrl = urlLoader.loadSignInUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(signInUrl)).build();
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

    @RequestMapping("/logout")
    public String logout(){
        return "authentication/signOut";
    }
}
