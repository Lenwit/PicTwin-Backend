package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The Repository.
 *
 * @author Mack Díaz Vilches.
 */
@Repository
public interface PicRepository extends ListCrudRepository<Pic, Long> {
}
