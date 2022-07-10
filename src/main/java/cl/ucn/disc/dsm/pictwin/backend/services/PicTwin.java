package cl.ucn.disc.dsm.pictwin.backend.services;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;

/**
 * The PicTwins Operations.
 *
 * @author Mack Díaz Vilches.
 */
public interface PicTwin {
    /**
     * Create a User with a specific password.
     *
     * @param user to create.
     * @param password to hash.
     * @return the User created.
     */
    User create(User user, String password);

    /**
     * Return the User with the email and password.
     *
     * @param email to search.
     * @param password to use.
     * @return the User.
     */
    User authenticate(String email, String password);

    /**
     * Create a Twin using the Pic from the User.
     *
     * @param pic to use.
     * @param idUser who create the Pic to use.
     * @return the Twin created.
     */
    Twin createTwin(Pic pic, Long idUser);

    /**
     * Dislike a Pic in a Twin.
     *
     * @param idTwin to dislike.
     * @param idUser who dislike the Twin.
     */
    void dislike(Long idTwin, Long idUser);

    /**
     * @return the number of users in the system.
     */
    Long getUserSize();

}
