package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.exceptions.SetupException;

public abstract class AbstractSetupComponent {
    /**
     * sets up some required data (vehicles, employees , partneraccounts, testaccount)
     *
     * @return returns if a setup was executed successfully
     * @throws SetupException - should the database be in an incorrect state
     */
    abstract boolean setup() throws SetupException;

    /**
     * @return string which confirms that the setup was completed
     */
    abstract String getSetupConfirmation();
}
