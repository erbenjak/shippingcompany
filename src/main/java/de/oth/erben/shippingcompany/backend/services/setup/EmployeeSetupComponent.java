package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.data.entities.Employee;
import de.oth.erben.shippingcompany.backend.data.repositorys.EmployeeRepository;
import de.oth.erben.shippingcompany.backend.exceptions.SetupException;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeSetupComponent extends AbstractSetupComponent {

    @Autowired
    EmployeeRepository employeeRepository=null;

    @Override
    boolean setup() throws SetupException {
        try {
            if (!employeeRepository.findByUserName("shippingSoren").isPresent()) {
                employeeRepository.save(new Employee("Soren", "Shipping", "shippingSoren", "soren@shippingCo.de"));
            }

            if (!employeeRepository.findByUserName("shippingRasmus").isPresent()) {
                employeeRepository.save(new Employee("Rasmus", "Shipping", "shippingRasmus", "rasmus@shippingCo.de"));
            }

            return true;
        } catch (Exception e) {
            throw new SetupException("Could not properly setup required employees -via the employee-repository", e);
        }

    }

    @Override
    String getSetupConfirmation() {
        return ("INFO------------- Completed Employee Setup.");
    }
}
