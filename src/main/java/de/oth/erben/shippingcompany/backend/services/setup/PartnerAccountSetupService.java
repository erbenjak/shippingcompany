package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import de.oth.erben.shippingcompany.backend.services.AbstractUserService;
import de.oth.erben.shippingcompany.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PartnerAccountSetupService extends AbstractSetupService{

    AbstractUserService userService;

    @Autowired
    public PartnerAccountSetupService(AbstractUserService userService){
        this.userService = userService;
    }

    @Override
    boolean setup() {
        try {
            //create Account Fabian Kellner - warehouse
            if(!userService.getCustomerFromPartnerKey("b2064a03-a201-42d0-a061-4c078f85d89f").isPresent())
            userService.createAccountPartnerCompany(new Customer("sw_warehouse", "sw_warehous", "partner_warehouse", "warehouse@sw.de", "b2064a03-a201-42d0-a061-4c078f85d89f"));

            //create Account Christoph Neldner - production
            if(!userService.getCustomerFromPartnerKey("4c9889a1-848a-4f98-99d7-35d6db8b6331").isPresent())
            userService.createAccountPartnerCompany(new Customer("sw_production", "sw_production", "partner_production", "production@sw.de", "4c9889a1-848a-4f98-99d7-35d6db8b6331"));

            //create Account Stephan Brunner - authentication
            if(!userService.getCustomerFromPartnerKey("604c4d2e-9e83-42b3-b95b-2ca4bb19fad9").isPresent())
            userService.createAccountPartnerCompany(new Customer("sw_authentication", "sw_authentication", "partner_authentication", "authentication@sw.de", "604c4d2e-9e83-42b3-b95b-2ca4bb19fad9"));

            return true;

        }catch(SetupException exception){
               exception.printStackTrace();
               return false;
        }
    }
}
