package de.oth.erben.shippingcompany.repositorys;

import de.oth.erben.shippingcompany.entities.Trip;
import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip,Long> { }
