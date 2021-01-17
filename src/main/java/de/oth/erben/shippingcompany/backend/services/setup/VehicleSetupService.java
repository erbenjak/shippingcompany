package de.oth.erben.shippingcompany.backend.services.setup;

import de.oth.erben.shippingcompany.backend.data.repositorys.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sets up a bunch of the companies vehicles for delivering
 */
@Service
public class VehicleSetupService extends AbstractSetupService {

    VehicleRepository vehicleRepository;

    @Autowired
    public VehicleSetupService(VehicleRepository vehicleRepository){

    }

    @Override
    boolean setup() {
        return true;
    }
}
