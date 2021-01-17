package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.data.repositorys.VehicleRepository;
import de.oth.erben.shippingcompany.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps with creating all kind of generated data - which in a real life scenario
 * would need to be covered with admin functions -> but is done in this way for simplicity
 * also when having to test the software
 */

@Configuration
public class SetupService extends ISetupService {

    List<AbstractSetupService> registeredServices = new ArrayList<>();

    @Autowired
    public SetupService(UserService userService, VehicleRepository vehicleRepository){
        registeredServices.add(new PartnerAccountSetupService(userService));
        registeredServices.add(new VehicleSetupService(vehicleRepository));
    }

    @Override
    @PostConstruct
    boolean setUp() {
        for (AbstractSetupService setupService : registeredServices){
           if(!setupService.setup()){
               return false;
           }
        }

        return true;
    }
}
