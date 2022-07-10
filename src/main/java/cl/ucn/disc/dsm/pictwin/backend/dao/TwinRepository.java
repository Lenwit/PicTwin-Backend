package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The Repository of Twin.
 *
 * @author Mack Díaz Vilches.
 */
public interface TwinRepository extends ListCrudRepository <Twin, Long>{
}
