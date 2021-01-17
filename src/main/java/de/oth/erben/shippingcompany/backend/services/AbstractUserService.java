package de.oth.erben.shippingcompany.backend.services;

import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import de.oth.erben.shippingcompany.backend.data.entities.Employee;
import de.oth.erben.shippingcompany.backend.services.setup.SetupException;
import de.othr.brs31213.sw.othauth.dto.responses.UserInformationResponse;

import java.util.Optional;

public interface AbstractUserService {
    boolean checkIfCustomerExists(String username);
    boolean checkIfEmployeeExists(String username);
    Optional<Customer> getCustomerFromUsername(String username);
    Optional<Employee> getEmployeeFromUsername(String username);
    boolean createCustomerAccount(UserInformationResponse userInformation);
    boolean createEmployeeAccount(UserInformationResponse userInformationResponse);
    void createAccountPartnerCompany(Customer customer) throws SetupException;
    Optional<Customer> getCustomerFromPartnerKey(String partnerKey);
}
