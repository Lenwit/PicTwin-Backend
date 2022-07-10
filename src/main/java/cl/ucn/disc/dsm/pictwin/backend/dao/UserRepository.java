package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.model.User;
import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The Repository of User.
 *
 * @author Mack Díaz Vilches.
 */
@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    /**
     * Return the User with the email
     * @param email to use.
     * @return the Optional of User.
     */
    Optional<User> findOneByEmail(@NonNull String email);
}
