package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.services.user.AbstractUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * manages all setup-components which create customer
 */
public abstract class AbstractCustomerSetup extends AbstractSetupComponent {

    @Autowired
    AbstractUserService userService;
}
