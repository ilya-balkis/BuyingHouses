package by.buyinghouses.repository;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {

    Accommodation findByName(String name);
    List<Accommodation> findByOwner(User user);

    @Modifying
    @Transactional
    @Query("update Accommodation as acc set acc.cost = ?1 where acc.name = ?2")
    void updateCost(BigDecimal cost, String name);
}
