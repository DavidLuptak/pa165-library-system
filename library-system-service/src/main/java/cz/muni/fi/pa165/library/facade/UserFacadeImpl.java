package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Martin
 * @version 13.11.2016
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void register(UserDTO userDTO, String unencryptedPassword) {
        User user = beanMappingService.mapTo(userDTO, User.class);
        userService.register(user, unencryptedPassword);
        userDTO.setId(user.getId());
    }

    @Override
    public void update(UserDTO userDTO) {
        User user = beanMappingService.mapTo(userDTO, User.class);
        userService.update(user);
    }

    @Override
    public void delete(Long id) {
        userService.delete(userService.findById(id));
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userService.findByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public UserRole userRole(UserDTO user) {
        return userService.userRole(beanMappingService.mapTo(user, User.class));
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO user) {
        return userService.authenticate(
                userService.findById(user.getUserId()), user.getPassword());
    }
}
