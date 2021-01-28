package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import de.oth.erben.shippingcompany.backend.exceptions.SetupException;

public class CustomerAccountSetup extends AbstractCustomerSetup{
    @Override
    boolean setup() {
        try {
            //create Account example customer - warehouse
            if(userService.getCustomerFromUsername("!customer-test!").isEmpty())
                userService.createAccountPartnerCompany(new Customer("!test!", "!test!", "!customer-test!", "test@us.com",""));

            return true;

        }catch(SetupException exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    String getSetupConfirmation() {
        return ("INFO------------- Completed Customer Setup.");
    }
}
