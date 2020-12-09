package de.oth.erben.shippingcompany.repositorys;

import de.oth.erben.shippingcompany.entities.Letter;
import org.springframework.data.repository.CrudRepository;

public interface LetterRepository extends CrudRepository<Letter,Long> {
}
