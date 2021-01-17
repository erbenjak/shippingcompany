package de.oth.erben.shippingcompany.backend.services;

import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import de.oth.erben.shippingcompany.backend.data.entities.Employee;
import de.oth.erben.shippingcompany.backend.data.repositorys.CustomerRepository;
import de.oth.erben.shippingcompany.backend.data.repositorys.EmployeeRepository;
import de.oth.erben.shippingcompany.backend.services.setup.SetupException;
import de.othr.brs31213.sw.othauth.dto.responses.UserInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements AbstractUserService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public boolean checkIfCustomerExists(String username) {
        return customerRepository.findByUserName(username).isPresent();
    }

    @Override
    public boolean checkIfEmployeeExists(String username) {
        return employeeRepository.findByUserName(username).isPresent();
    }

    @Override
    public Optional<Customer> getCustomerFromUsername(String username) {
        return customerRepository.findByUserName(username);
    }

    @Override
    public Optional<Employee> getEmployeeFromUsername(String username) {
        return employeeRepository.findByUserName(username);
    }

    @Override
    public boolean createCustomerAccount(UserInformationResponse userInformation) {
        Customer newCustomer = new Customer();

        newCustomer.setEmail(userInformation.getEMail());
        newCustomer.setFirstName(userInformation.getFirstName());
        newCustomer.setLastName(userInformation.getLastName());
        newCustomer.setUserName(userInformation.getUsername());

        customerRepository.save(newCustomer);
        return true;
    }

    @Override
    public boolean createEmployeeAccount(UserInformationResponse userInformation) {
        Employee newEmployee = new Employee();

        newEmployee.setEmail(userInformation.getEMail());
        newEmployee.setFirstName(userInformation.getFirstName());
        newEmployee.setLastName(userInformation.getLastName());
        newEmployee.setUserName(userInformation.getUsername());

        employeeRepository.save(newEmployee);
        return true;
    }

    @Override
    public void createAccountPartnerCompany(Customer customer) throws SetupException {
        try{
            customerRepository.save(customer);
        }catch(Exception e){
            throw new SetupException("Could not create partner-account",e);
        }
    }

    @Override
    public Optional<Customer> getCustomerFromPartnerKey(String partnerKey) {
        if(partnerKey==null||partnerKey.equals("")){
            throw new IllegalArgumentException("partnerKey have a value");
        }

        return customerRepository.findByPartnerKey(partnerKey);
    }
}
