package de.oth.erben.shippingcompany.backend.data.repositorys;

import de.oth.erben.shippingcompany.backend.data.entities.Letter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LetterRepository extends CrudRepository<Letter,Long> {
    List<Letter> findByStatusDescription(String description);
}
