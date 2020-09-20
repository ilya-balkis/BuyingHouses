package by.buyinghouses.repository;

import by.buyinghouses.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String username);

    User findByEmail(String email);

    User findByActivationCode(String code);

    @Modifying
    @Transactional
    @Query(value = "delete from User as us where us.userName = ?1")
    void deleteById(String userName);
}
