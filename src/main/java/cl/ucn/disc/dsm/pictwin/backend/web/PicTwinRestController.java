package cl.ucn.disc.dsm.pictwin.backend.web;

import cl.ucn.disc.dsm.pictwin.backend.Utils;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * The PicTwin Controller
 */
@RestController
@Slf4j
public class PicTwinRestController {
    /**
     * The PicTwin service.
     */
    private final PicTwin picTwin;

    /**
     * The Constructor.
     *
     * @param picTwin the service.
     */
    @Autowired
    public PicTwinRestController(PicTwin picTwin){
        this.picTwin = picTwin;
    }

    /**
     * Create a User.
     *
     * @param user to create.
     * @param password to use.
     * @return the User.
     */
    @RequestMapping(value = {"/users", "/users/"}, method = RequestMethod.POST)
    public User create(@Valid @RequestBody User user, @RequestParam String password){
        //Debug
        Utils.printObject("User",user);

        //Call the controller
        return this.picTwin.create(user, password);
    }

    /**
     * Authenticate a User.
     *
     * @param email to use.
     * @param password to use.
     * @return the User.
     */
    @RequestMapping(value = {"/users", "/users/"}, method = RequestMethod.GET)
    public User authenticate(@RequestParam String email, @RequestParam String password){
        return this.picTwin.authenticate(email, password);
    }

    /**
     * Create a Twin.
     *
     * @param pic to use.
     * @param idUser to use.
     * @return the Twin.
     */
    @RequestMapping(value = {"/twins", "/twins/"}, method = RequestMethod.POST)
    public Twin createTwin(@Valid @RequestBody Pic pic, @RequestParam Long idUser){
        return this.picTwin.createTwin(pic, idUser);
    }

    /**
     * Dislike a Twin.
     *
     * @param idTwin to use.
     * @param idUser to use.
     */
    @RequestMapping(value = {"/twins", "/twins/"}, method = RequestMethod.PATCH)
    public void dislike(@RequestParam Long idTwin, @RequestParam Long idUser){
        this.picTwin.dislike(idTwin, idUser);
    }

    /**
     * Show a message in {@link MethodArgumentNotValidException}.
     *
     * @param ex to catch.
     * @return the Map of errors.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Show a message in {@link IllegalArgumentException}.
     *
     * @param ex to catch.
     * @return the message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleValidaationExceptions(IllegalArgumentException ex){
        return ex.getMessage();
    }

}
