package cz.muni.fi.pa165.library.rest.controller;

import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.UserFacade;
import cz.muni.fi.pa165.library.rest.ApiUris;
import cz.muni.fi.pa165.library.rest.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bedrich Said
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserController {

    @Inject
    private UserFacade userFacade;

    /**
     * Get list of all users corresponding to given email
     * address (if email parameter is specified).
     * <p>
     * curl -i -X GET http://localhost:8080/pa165/rest/users curl -i -X GET
     * http://localhost:8080/pa165/rest/users?email={value}
     * </p>
     *
     * @param email optional user's e-mail
     * @return list of all users corresponding to given email
     * @throws ResourceNotFoundException if the requested user not found
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> findUsers(@RequestParam(value = "email", required = false) String email) {
        if (email == null) {
            return userFacade.findAll();
        } else {
            try {
                List<UserDTO> result = new ArrayList<>();
                result.add(userFacade.findByEmail(email));
                return result;
            } catch (NoEntityFoundException | IllegalArgumentException e) {
                throw new ResourceNotFoundException(e);
            }
        }
    }
}
