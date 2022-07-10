package cl.ucn.disc.dsm.pictwin.backend.services;

import cl.ucn.disc.dsm.pictwin.backend.dao.PicRepository;
import cl.ucn.disc.dsm.pictwin.backend.dao.TwinRepository;
import cl.ucn.disc.dsm.pictwin.backend.dao.UserRepository;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * The {@link PicTwin} implementation.
 *
 * @author Mack Díaz Vilches.
 */
@Slf4j
@Service
public class PicTwinImpl implements PicTwin {
    /**
     * The Hasher.
     */
    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    /**
     * The Random.
     */
    private final static Random RANDOM = new Random();

    /**
     * The Pic Repository.
     */
    private final PicRepository picRepository;

    /**
     * The Twin Repository.
     */
    private final TwinRepository twinRepository;

    /**
     * The User Repository.
     */
    private final UserRepository userRepository;

    /**
     * Build the PicTwinImplementation.
     *
     * @param picRepository to use.
     * @param twinRepository to use.
     * @param userRepository to use.
     */
    @Autowired
    public PicTwinImpl(PicRepository picRepository, TwinRepository twinRepository, UserRepository userRepository){
        this.picRepository = picRepository;
        this.twinRepository = twinRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a User with a specific password.
     *
     * @param user to create.
     * @param password to hash.
     * @return the User created.
     */
    @Override
    @Transactional
    public User create(@NonNull User user, @NonNull String password){
        //If the User already exists
        if(this.userRepository.findOneByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("The User with email <" + user.getEmail() + "> it's already in the system." );
        }

        //Using the password encoder to hash
        String passwdHash = PASSWORD_ENCODER.encode(password);

        //Replace the password with the hash
        user.setPassword(passwdHash);

        //Save into the repository
        return this.userRepository.save(user);
    }

    /**
     * Return the User with the email and password.
     *
     * @param email to search.
     * @param password to use.
     * @return the User.
     */
    @Override
    @Transactional
    public Twin createTwin(@NonNull Pic pic, @NonNull Long idUser){
        //The user
        User owner = this.userRepository.findById(idUser).orElseThrow();

        log.debug("Pics: {} in User: {}", owner.getTwins().size(), owner.getEmail());

        //Set the User
        pic.setOwner(owner);

        //Store the Pic
        this.picRepository.save(pic);

        //Get all the Pics
        List<Pic> pics = this.picRepository.findAll();
        log.debug("Number of Pics in the database: {}", pics.size());

        //Remove my Owns Pics
        List<Pic> picsFiltered = pics.stream().filter(p -> !p.getOwner().getId().equals(idUser)).toList();
        if(picsFiltered.size() == 0){
            log.warn("Re-using Pics from database.");
            picsFiltered = pics;
        }

        //Select a random Pic
        //FIXME: Sort by views and select the least used
        Pic your = picsFiltered.size() == 0 ? pic : picsFiltered.get(RANDOM.nextInt(picsFiltered.size()));

        //Increment the views
        your.incrementViews();

        //Save the increment
        this.picRepository.save(your);

        //Store the Twin
        Twin twin = Twin.builder()
                .my(pic)
                .yours(your)
                .owner(owner)
                .build();

        //Save the Twin
        this.twinRepository.save(twin);

        //Add the Twin to the user
        owner.add(twin);
        this.userRepository.save(owner);

        return twin;
    }

    /**
     * Dislike a Pic in a Twin.
     *
     * @param idTwin to dislike.
     * @param idUser who dislike the Twin.
     */
    @Override
    @Transactional
    public void dislike(@NonNull Long idTwin, @NonNull Long idUser){
        //Retrieve the Twin
        Optional<Twin> oTwin = this.twinRepository.findById(idTwin);

        //Check if exists
        Twin twin = oTwin.orElseThrow(() -> new RuntimeException("Can't find Twin with id: " + idTwin));

        //Check the owner of the Pic
        if(!idUser.equals(twin.getMy().getOwner().getId())){
            throw new RuntimeException("Twin id<" + idTwin + "> not owned by User id<" + idUser + ">!");
        }

        //Set the dislike and save
        twin.setDislike(true);
        this.twinRepository.save(twin);

        //Increment the dislike of twin and save
        Pic yours = twin.getYours();
        yours.incrementDislikes();
        this.picRepository.save(yours);

        //Increment the strikes in User and save
        User user = yours.getOwner();
        yours.incrementDislikes();
        this.picRepository.save(yours);
    }

    /**
     * @return the number of users in the system.
     */
    @Override
    public Long getUserSize(){
        return this.userRepository.count();
    }
}
