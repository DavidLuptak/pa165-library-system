package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.UserDao;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
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
    private User userPersistedC;
    private User userToBePersisted;

    private long newlyPersistedId = 42L;
    private long notPersistedId = 666L;
    private long byUpdatePersistedId = 41L;
    private String alreadyPersistedEmail = "already@persisted.email";

    private Loan returnedLoan;
    private Loan notReturnedLoan;
    private Loan returnedLoan2;
    private Loan notReturnedLoan2;

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

        userPersistedC = new User.UserBuilder("c@c.com")
                .setId(3L)
                .setFirstName("First C name")
                .setLastName("Last C name")
                .setAddress("Somewhere in the space")
                .setPasswordHash("c")
                .build();

        userToBePersisted = new User.UserBuilder("ab@ab.com")
                .setFirstName("New name")
                .setLastName("New surname")
                .setAddress("Right here righ now")
                .setPasswordHash("abbaa")
                .build();

        //for harder function
        returnedLoan = new Loan();
        returnedLoan.setLoanDate(new Date(5));
        returnedLoan.setReturnDate(new Date(10));
        notReturnedLoan = new Loan();
        returnedLoan2 = new Loan();
        returnedLoan2.setLoanDate(new Date(5));
        returnedLoan2.setReturnDate(new Date(10));
        notReturnedLoan2 = new Loan();

        userPersistedA.addLoan(notReturnedLoan);
        userPersistedB.addLoan(returnedLoan);
        userPersistedC.addLoan(returnedLoan2);
        userPersistedC.addLoan(notReturnedLoan2);

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
        when(userService.findAll()).thenReturn(Arrays.asList(userPersistedA, userPersistedB, userPersistedC));

        doAnswer((InvocationOnMock invocation) -> {
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

        }).when(userDao).create(any(User.class));

        doAnswer((InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if (argument == null) {
                throw new IllegalArgumentException("Argument is null.");
            }

            User user = (User) argument;
            if (user.getId() == null) {
                user.setId(byUpdatePersistedId);
            }

            return user;

        }).when(userDao).update(any(User.class));

        doAnswer((InvocationOnMock invocation) -> {
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

        }).when(userDao).delete(any(User.class));
    }

    @Test
    public void testCreate() {
        userService.register(userToBePersisted, "bla");
        assertEquals((long) userToBePersisted.getId(), newlyPersistedId);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void testCreateAlreadyPersisted() {
        userService.register(userPersistedA, "bla");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        userService.register(null, "bla");
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void testCreateDuplicateEmail() {
        userToBePersisted.setEmail(alreadyPersistedEmail);
        userService.register(userToBePersisted, "bla");
    }

    @Test
    public void testUpdate() {
        User updated = userService.update(userPersistedA);

        verify(userDao).update(userArgumentCaptor.capture());
        assertDeepEquals(userArgumentCaptor.getValue(), userPersistedA);
        assertDeepEquals(updated, userPersistedA);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        userService.update(null);
    }

    @Test
    public void testUpdateNonExisting() {
        User updated = userService.update(userToBePersisted);
        assertDeepEquals(updated, userToBePersisted);
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

    @Test(expectedExceptions = IllegalArgumentException.class)
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

    @Test(expectedExceptions = IllegalArgumentException.class)
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

        assertEquals(list.size(), 3);
        assertDeepEquals(list.get(0), userPersistedA);
        assertDeepEquals(list.get(1), userPersistedB);
        assertDeepEquals(list.get(2), userPersistedC);
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

    @Test(expectedExceptions = IllegalArgumentException.class)
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

    @Test
    public void testFindUsersWithNotReturnedLoans() {
        List<User> list = userService.findUsersWithNotReturnedLoans();

        assertEquals(list.size(), 2);
        assertDeepEquals(list.get(0), userPersistedA);
        assertDeepEquals(list.get(1), userPersistedC);
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
