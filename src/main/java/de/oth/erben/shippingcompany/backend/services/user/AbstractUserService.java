package de.oth.erben.shippingcompany.backend.services.user;

import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import de.oth.erben.shippingcompany.backend.data.entities.Employee;
import de.oth.erben.shippingcompany.backend.exceptions.SetupException;
import de.othr.brs31213.sw.othauth.dto.responses.UserInformationResponse;

import java.util.Optional;

public interface AbstractUserService {
    /**
     *
     * @param username -username of customer
     * @return boolean
     */
    boolean checkIfCustomerExists(String username);

    /**
     *
     * @param username -username of employee
     * @return
     */
    boolean checkIfEmployeeExists(String username);

    /**
     *
     * @param username -username of customer
     * @return customer id he exists
     */
    Optional<Customer> getCustomerFromUsername(String username);

    /**
     *
     * @param username of emplyoee
     * @return emplyoee if he exists
     */
    Optional<Employee> getEmployeeFromUsername(String username);

    /**
     *
     * @param userInformation retrevied from the authentication service
     * @return true if could be created
     */
    boolean createCustomerAccount(UserInformationResponse userInformation);

    /**
     *
     * @param userInformationResponse retrieved by the authetication service
     * @return true if could be created
     */
    boolean createEmployeeAccount(UserInformationResponse userInformationResponse);

    /**
     *
     * @param customer created by the partnersetupcomponent
     * @throws SetupException
     */
    void createAccountPartnerCompany(Customer customer) throws SetupException;

    /**
     *
     * @param partnerKey
     * @return customer if one has the corresponding key
     */
    Optional<Customer> getCustomerFromPartnerKey(String partnerKey);

    /**
     *
     * @param customerKey
     * @return customer if one has the corresponding key
     */
    Optional<Customer> getCustomerWithCustomerKey(Optional<String> customerKey);
}
