package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.AbstractOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AbstractOrderRepository extends CrudRepository<AbstractOrder, Long> {
        List<AbstractOrder> findByCustomerUserName(String username);
        List<AbstractOrder> findAll();
        Optional<AbstractOrder> findByTrackingId(Long trackingId);
}
