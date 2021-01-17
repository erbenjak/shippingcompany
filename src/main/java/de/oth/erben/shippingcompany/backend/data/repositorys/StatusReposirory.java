package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusReposirory extends CrudRepository<Status,Long> { }
