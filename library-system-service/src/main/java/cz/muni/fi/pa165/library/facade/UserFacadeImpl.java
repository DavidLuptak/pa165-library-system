package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Martin
 * @version 13.11.2016
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void register(UserDTO userDTO, String unencryptedPassword) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (unencryptedPassword == null || unencryptedPassword.isEmpty()) {
            throw new IllegalArgumentException("Unencrypted password cannot be null nor empty.");
        }
        User user = beanMappingService.mapTo(userDTO, User.class);
        if (user == null) {
            throw new NoEntityFoundException("User not found during register.");
        }
        userService.register(user, unencryptedPassword);
        userDTO.setId(user.getId());
    }

    @Override
    public void update(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        User user = beanMappingService.mapTo(userDTO, User.class);
        if (user == null) {
            throw new NoEntityFoundException("User not found during update.");
        }
        userService.update(user);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        User user = userService.findById(id);
        if (user == null) {
            throw new NoEntityFoundException("User not found during delete.");
        }
        userService.delete(user);
    }

    @Override
    public UserDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        User user = userService.findById(id);
        if (user == null) {
            throw new NoEntityFoundException("User not found during findById.");
        }

        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new NoEntityFoundException("User not found during findByEmail.");
        }
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public UserRole userRole(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return userService.userRole(beanMappingService.mapTo(user, User.class));
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        User user = userService.findById(userDTO.getUserId());
        if (user == null) {
            throw new NoEntityFoundException("User not found during authenticate.");
        }
        return userService.authenticate(user, userDTO.getPassword());
    }
}