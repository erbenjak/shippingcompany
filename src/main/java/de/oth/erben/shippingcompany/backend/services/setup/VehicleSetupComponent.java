package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.data.entities.Vehicle;
import de.oth.erben.shippingcompany.backend.data.repositorys.IVehicleRepository;
import de.oth.erben.shippingcompany.backend.exceptions.SetupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Sets up a bunch of the companies vehicles for delivering
 */
@Service
public class VehicleSetupComponent extends AbstractSetupComponent {

    @Autowired
    IVehicleRepository vehicleRepository;

    @Override
    boolean setup() throws SetupException {
        try{
            if(StreamSupport.stream(vehicleRepository.findAll().spliterator(),false).collect(Collectors.toList()).size()==6){
                //6 vehicles meaning the setUp is already completed
                return  true;
            }

            vehicleRepository.save(new Vehicle(UUID.randomUUID().getMostSignificantBits(),1,9000.00, 36.60));
            vehicleRepository.save(new Vehicle(UUID.randomUUID().getMostSignificantBits(),2,24000.00, 85.30));
            vehicleRepository.save(new Vehicle(UUID.randomUUID().getMostSignificantBits(),1,9000.00, 36.60));
            vehicleRepository.save(new Vehicle(UUID.randomUUID().getMostSignificantBits(),1,4500.00, 18.20));

            //bicycles for letter delivery
            vehicleRepository.save(new Vehicle(UUID.randomUUID().getMostSignificantBits(),1,20.00, 0.03));
            vehicleRepository.save(new Vehicle(UUID.randomUUID().getMostSignificantBits(),1,20.00, 0.03));
            return true;
        }catch(Exception e){
            throw new SetupException("Vehicle Setup failed.",e);
        }
    }

    @Override
    String getSetupConfirmation() {
        return ("INFO------------- Completes Vehicle Setup.");
    }
}
