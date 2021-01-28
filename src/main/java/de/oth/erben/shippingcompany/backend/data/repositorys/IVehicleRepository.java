package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface IVehicleRepository extends CrudRepository<Vehicle, Long> {
}
