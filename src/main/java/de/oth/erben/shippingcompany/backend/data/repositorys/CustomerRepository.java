package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Long> {

    Optional<Customer> findByUserName(String username);
    Optional<Customer> findByPartnerKey(String partnerKey);
}
