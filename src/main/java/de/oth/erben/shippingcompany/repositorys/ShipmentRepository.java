package de.oth.erben.shippingcompany.repositorys;

import de.oth.erben.shippingcompany.entities.Shipment;
import org.springframework.data.repository.CrudRepository;

public interface ShipmentRepository extends CrudRepository<Shipment,Long> {
}
