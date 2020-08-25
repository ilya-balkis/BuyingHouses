package by.buyinghouses.repository;

import by.buyinghouses.domain.Accommodation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {

    Accommodation findByName(String name);

}
