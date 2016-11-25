package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.dto.UserNewDTO;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.library.service.UserService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test suite for the {@link UserFacade}.
 *
 * @author Dávid Lupták
 * @version 19.11.2016
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserService userService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final UserFacade userFacade = new UserFacadeImpl();

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private User userA;
    private User userB;

    @BeforeClass
    public void setupMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public void initUsers() {
        userA = new User.UserBuilder("a@a.com")
                .setId(1L)
                .setFirstName("First A name")
                .setLastName("Last A name")
                .setAddress("Somewhere in the space")
                .setPasswordHash("a")
                .build();

        userB = new User.UserBuilder("b@b.com")
                .setId(2L)
                .setFirstName("First B name")
                .setLastName("Last B name")
                .setAddress("Somewhere in the space")
                .setPasswordHash("b")
                .build();
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(userService.findById(0L)).thenReturn(null);
        when(userService.findById(1L)).thenReturn(userA);
        when(userService.findById(2L)).thenReturn(userB);

        // findByEmail
        when(userService.findByEmail("a@a.com")).thenReturn(userA);
        when(userService.findByEmail("b@b.com")).thenReturn(userB);
        when(userService.findByEmail("x@x.x")).thenReturn(null);

        // findAll
        when(userService.findAll()).thenReturn(Arrays.asList(userA, userB));

        // findUsersWithNotReturnedLoans
        when(userService.findUsersWithNotReturnedLoans()).thenReturn(Arrays.asList(userA, userB));

        // auth
        when(userService.authenticate(any(User.class), anyString())).thenReturn(true);
    }

    @Test
    public void testCreate() {
        UserNewDTO userDTO = new UserNewDTO();
        userDTO.setEmail("bla@bla.bla");

        userFacade.register(userDTO, "bla");

        verify(userService).register(userArgumentCaptor.capture(), anyString());
        assertEquals(userArgumentCaptor.getValue().getEmail(), userDTO.getEmail());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        userFacade.register(null, "");
    }

    @Test
    public void testUpdate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userA.getId());
        userDTO.setEmail("bla@bla.com");
        userDTO.setUserRole(userA.getUserRole());

        userFacade.update(userDTO);

        verify(userService).update(userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue().getEmail(), userDTO.getEmail());
        assertEquals(userArgumentCaptor.getValue().getId(), userDTO.getId());
        assertEquals(userArgumentCaptor.getValue().getUserRole(), userDTO.getUserRole());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        userFacade.update(null);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testUpdateNonExisting() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0L);

        userFacade.update(userDTO);
    }

    @Test
    public void testDelete() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userA.getId());
        userDTO.setEmail(userA.getEmail());

        userFacade.delete(userDTO.getId());

        verify(userService).delete(userArgumentCaptor.capture());

        assertEquals(userArgumentCaptor.getValue().getId(), userDTO.getId());
        assertEquals(userArgumentCaptor.getValue().getEmail(), userDTO.getEmail());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        userFacade.delete(null);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testDeleteNonExistingUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0L);

        userFacade.delete(userDTO.getId());
    }

    @Test
    public void testFindById() {
        UserDTO userDTO = userFacade.findById(1L);
        assertDeepEquals(userDTO, userA);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdNull() {
        userFacade.findById(null);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testFindByIdNonExistingUser() {
        userFacade.findById(0L);
    }

    @Test
    public void testFindByEmail() {
        UserDTO userDTO = userFacade.findByEmail(userB.getEmail());
        assertDeepEquals(userDTO, userB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByEmailNull() {
        userFacade.findByEmail(null);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testFindByEmailNonExisting() {
        userFacade.findByEmail("x@x.x");
    }

    @Test
    public void testFindAll() {
        List<UserDTO> list = userFacade.findAll();

        assertEquals(list.size(), 2);
        assertDeepEquals(list.get(0), userA);
        assertDeepEquals(list.get(1), userB);
    }

    @Test
    public void testUserRole() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userA.getId());
        userDTO.setEmail(userA.getEmail());
        userDTO.setUserRole(userA.getUserRole());

        UserRole userRole = userFacade.userRole(userDTO);

        verify(userService).userRole(userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue().getId(), userDTO.getId());
        assertEquals(userArgumentCaptor.getValue().getEmail(), userDTO.getEmail());
        assertEquals(userArgumentCaptor.getValue().getUserRole(), userDTO.getUserRole());
        assertEquals(userRole, userDTO.getUserRole());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUserRoleNull() {
        userFacade.userRole(null);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testUserRoleNonExistingUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0L);

        userFacade.userRole(userDTO);
    }

    @Test
    public void testAuthenticate() {
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setUserId(userB.getId());
        userAuthenticateDTO.setPassword("bla");

        boolean authenticated = userFacade.authenticate(userAuthenticateDTO);

        verify(userService).authenticate(userArgumentCaptor.capture(), anyString());
        assertEquals(userArgumentCaptor.getValue().getId(), userAuthenticateDTO.getUserId());
        assertEquals(authenticated, true);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAuthenticateNull() {
        userFacade.authenticate(null);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testAuthenticateNonExisting() {
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setUserId(0L);

        userFacade.authenticate(userAuthenticateDTO);
    }

    @Test
    public void testFindUsersWithNotReturnedLoans() throws Exception {
        List<UserDTO> list = userFacade.findUsersWithNotReturnedLoans();

        assertEquals(list.size(), 2);
        assertDeepEquals(list.get(0), userA);
        assertDeepEquals(list.get(1), userB);
    }

    private void assertDeepEquals(UserDTO userDTO, User user) {
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPasswordHash(), user.getPasswordHash());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getUserRole(), user.getUserRole());
    }

}
