package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.exceptions.SetupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SetupExecutor {

    private List<AbstractSetupComponent> setupServices = new ArrayList<>();

    @Autowired @Qualifier("customer")
    AbstractCustomerSetup customerSetup;

    @Autowired @Qualifier("partner")
    AbstractCustomerSetup partnerSetup;

    @Autowired
    VehicleSetupComponent vehicleSetupComponent;

    @Autowired
    EmployeeSetupComponent employeeSetupService;


    @PostConstruct
    public void executeSetup(){
        setupServices.add(employeeSetupService);
        setupServices.add(vehicleSetupComponent);
        setupServices.add(partnerSetup);
        setupServices.add(customerSetup);

        for(AbstractSetupComponent setupService:setupServices){
            try {
                setupService.setup();
                System.out.println(setupService.getSetupConfirmation());
            }catch(SetupException exception){
                System.out.println("Setup failed!");
            }

        }
    }

}
