package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.UserDao;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Test suite for the {@link UserService}.
 *
 * @author Dávid Lupták
 * @version 20.11.2016
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private User userPersistedA;
    private User userPersistedB;
    private User userToBePersisted;

    private long newlyPersistedId = 42L;
    private long notPersistedId = 666L;
    private String alreadyPersistedEmail = "already@persisted.email";

    @BeforeClass
    public void setupMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public void initUsers() {
        userPersistedA = new User.UserBuilder("a@a.com")
                .setId(1L)
                .setFirstName("First A name")
                .setLastName("Last A name")
                .setAddress("Somewhere in the space")
                .setPasswordHash("a")
                .build();

        userPersistedB = new User.UserBuilder("b@b.com")
                .setId(2L)
                .setFirstName("First B name")
                .setLastName("Last B name")
                .setAddress("Somewhere in the space")
                .setPasswordHash("b")
                .build();

        userToBePersisted = new User.UserBuilder("ab@ab.com")
                .setFirstName("New name")
                .setLastName("New surname")
                .setAddress("Right here righ now")
                .setPasswordHash("abbaa")
                .build();
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(userService.findById(0L)).thenReturn(null);
        when(userService.findById(1L)).thenReturn(userPersistedA);

        // findByEmail
        when(userService.findByEmail("a@a.com")).thenReturn(userPersistedA);
        when(userService.findByEmail("x@x.x")).thenReturn(null);

        // findAll
        when(userService.findAll()).thenReturn(Arrays.asList(userPersistedA, userPersistedB));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                User user = (User) argument;
                if (user.getId() != null) {
                    throw new EntityExistsException("Entity with ID=" + user.getId() + " already exists.");
                }

                if (user.getEmail().equals(alreadyPersistedEmail)) {
                    throw new ConstraintViolationException("Duplicate email attempt.", null);
                }

                user.setId(newlyPersistedId);
                return null;
            }
        }).when(userDao).create(any(User.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                User user = (User) argument;
                // TODO

                return null;
            }
        }).when(userDao).update(any(User.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                User user = (User) argument;

                if (user.getId() == null) {
                    throw new IllegalArgumentException("Delete non-persisted user attempt.");
                }

                if (user.getId() == notPersistedId) {
                    throw new IllegalArgumentException("Delete non-persisted user attempt.");
                }

                return null;
            }
        }).when(userDao).delete(any(User.class));
    }

    @Test
    public void testCreate() {
        userService.register(userToBePersisted, "bla");
        assertEquals((long) userToBePersisted.getId(), newlyPersistedId);
    }

    @Test(enabled = false)
    public void testCreateAlreadyPersisted() {
        userService.register(userPersistedA, "bla");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, enabled = false)
    public void testCreateNull() {
        userService.register(null, "bla");
    }

    @Test(enabled = false)
    public void testCreateDuplicateEmail() {
        userToBePersisted.setEmail(alreadyPersistedEmail);
        userService.register(userToBePersisted, "bla");
    }

    @Test
    public void testUpdate() {
        userService.update(userPersistedA);

        verify(userDao).update(userArgumentCaptor.capture());
        assertDeepEquals(userArgumentCaptor.getValue(), userPersistedA);
        User updated = userService.findById(userPersistedA.getId()); // preferably to be returned on update method
        assertDeepEquals(updated, userPersistedA);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        userService.update(null);
    }

    @Test
    public void testUpdateNonExisting() {
        userService.update(userToBePersisted);
        // TODO
    }

    @Test
    public void testDelete() {
        userService.delete(userPersistedA);

        verify(userDao).delete(userArgumentCaptor.capture());
        assertDeepEquals(userArgumentCaptor.getValue(), userPersistedA);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        userService.delete(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNonExistingNull() {
        userToBePersisted.setId(null);
        userService.delete(userToBePersisted);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNonExistingId() {
        userToBePersisted.setId(notPersistedId);
        userService.delete(userToBePersisted);
    }

    @Test
    public void testFindById() {
        User foundUser = userService.findById(userPersistedA.getId());
        assertDeepEquals(foundUser, userPersistedA);
    }

    @Test
    public void testFindByIdNull() {
        userService.findById(null);
    }

    @Test
    public void testFindByIdNonExisting() {
        assertNull(userService.findById(0L));
    }


    @Test
    public void testFindByEmail() {
        User foundUser = userService.findByEmail(userPersistedA.getEmail());
        assertDeepEquals(foundUser, userPersistedA);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, enabled = false)
    public void testFindByEmailNull() {
        userService.findByEmail(null);
    }

    @Test
    public void testFindByEmailNonExisting() {
        assertNull(userService.findByEmail("x@x.x"));
    }

    @Test
    public void testFindAll() {
        List<User> list = userService.findAll();

        assertEquals(list.size(), 2);
        assertDeepEquals(list.get(0), userPersistedA);
        assertDeepEquals(list.get(1), userPersistedB);
    }

    @Test
    public void testUserRole() {
        UserRole userRole = userService.userRole(userPersistedB);
        assertEquals(userRole, userPersistedB.getUserRole());
    }

    @Test
    public void testAuthenticate() {
        User pureNewUser = new User.UserBuilder("pure@new.user").build();

        userService.register(pureNewUser, "superStrongPassword");

        assertNotNull(pureNewUser);
        assertNotNull(pureNewUser.getPasswordHash());

        boolean authenticated = userService.authenticate(pureNewUser, "superStrongPassword");
        assertEquals(authenticated, true);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, enabled = false)
    public void testAuthenticateNull() {
        userService.authenticate(null, "bla");
    }

    @Test(enabled = false)
    public void testAuthenticateNonExisting() {
        User user = new User();
        user.setId(0L);
        user.setPasswordHash("alb");

        userService.authenticate(user, "bla");
    }

    private void assertDeepEquals(User userA, User userB) {
        assertEquals(userA.getId(), userB.getId());
        assertEquals(userA.getEmail(), userB.getEmail());
        assertEquals(userA.getPasswordHash(), userB.getPasswordHash());
        assertEquals(userA.getFirstName(), userB.getFirstName());
        assertEquals(userA.getLastName(), userB.getLastName());
        assertEquals(userA.getAddress(), userB.getAddress());
        assertEquals(userA.getUserRole(), userB.getUserRole());
    }
}