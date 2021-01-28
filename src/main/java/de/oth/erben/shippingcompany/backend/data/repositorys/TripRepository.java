package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.Employee;
import de.oth.erben.shippingcompany.backend.data.entities.Trip;
import de.oth.erben.shippingcompany.backend.data.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip,Long> {

    List<Trip> findByDeliveryVehicle(Vehicle deliveryVehicle);

    List<Trip> findByEmployeesContaining(Employee employee);
}
