package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.Shipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipmentRepository extends CrudRepository<Shipment,Long> {
    List<Shipment> findByStatusDescription(String description);
}
