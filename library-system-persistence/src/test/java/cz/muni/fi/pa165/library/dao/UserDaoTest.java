package cz.muni.fi.pa165.library.dao;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Lenka (433591)
 * @version 29.10.2016
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserDao userDao;

    private User user1;
    private User user2;

    @BeforeMethod
    public void setUp() throws Exception {
        user1 = new User("Jan", "Novak", "janko@aaa.com", "Brno", UserRole.MEMBER);
        user1.setPasswordHash("dskf390cmklsmd2");
        user2 = new User("Peter", "Hrivnak", "petoo@abc.com", "Praha", UserRole.ADMIN);
        user2.setPasswordHash("ajsdlasldjl34mlk4");
    }

    @Test
    public void testCreate() throws Exception {
        userDao.create(user2);
        assertNotNull(user2.getId());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNullUser() throws Exception {
        userDao.create(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateWithSetId() throws Exception {
        user1.setId(2L);
        userDao.create(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateUserWithNullName() throws Exception {
        user1.setGivenName(null);
        userDao.create(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateUserWithNullSurName() throws Exception {
        user1.setSurname(null);
        userDao.create(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateUserWithNullEmail() throws Exception {
        user1.setEmail(null);
        userDao.create(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateUserWithNullAddress() throws Exception {
        user1.setAddress(null);
        userDao.create(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateUserWithInvalidEmail() throws Exception {
        user1.setEmail("lalala@");
        userDao.create(user1);
    }

    @Test
    public void testUpdate() throws Exception {
        userDao.create(user1);
        user1.setGivenName("Jozef");
        user1.setSurname("Maly");
        user1.setEmail("jozko@abc.com");
        user1.setAddress("Bratislava");
        user1.setUserRole(UserRole.ADMIN);
        user1.setPasswordHash("abj897asdbddu3");

        userDao.update(user1);

        User updated = userDao.findById(user1.getId());
        assertDeepEquals(updated, user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateNonExistingUser() throws Exception {
        user1.setId(1L);
        userDao.update(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateUserWithNullId() throws Exception {
        userDao.update(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateNullUser() throws Exception {
        userDao.update(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateInvalidUser() throws Exception {
        userDao.create(user1);
        user1.setGivenName(null);
        user1.setEmail("@sas");
        user1.setUserRole(UserRole.ADMIN);

        userDao.update(user1);

    }

    @Test
    public void testDelete() throws Exception {
        userDao.create(user1);
        userDao.create(user2);
        userDao.delete(user1);
        assertNull(userDao.findById(user1.getId()));
        assertNotNull(userDao.findById(user2.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteUserWithNullId() throws Exception {
        userDao.delete(user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNullUser() throws Exception {
        userDao.delete(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNonExistingUser() throws Exception {
        user1.setId(2L);
        userDao.delete(user1);
    }

    @Test
    public void testFindById() throws Exception {
        userDao.create(user1);
        User created = userDao.findById(user1.getId());
        assertDeepEquals(created, user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByNullId() throws Exception {
        userDao.findById(null);
    }

    @Test
    public void testFindNonExistingUser() throws Exception {
        assertNull(userDao.findById(2L));
    }

    @Test
    public void testFindByEmail() throws Exception {
        userDao.create(user1);
        User created = userDao.findByEmail(user1.getEmail());
        assertDeepEquals(created, user1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByNullEmail() throws Exception {
        userDao.findByEmail(null);
    }

    @Test
    public void testFindByNonExistingEmail() throws Exception {
        assertNull(userDao.findByEmail("lalala"));
    }

    @Test
    public void testFindAll() throws Exception {
        userDao.create(user1);
        userDao.create(user2);

        List<User> users = userDao.findAll();
        assertNotNull(users);
        assertTrue(users.size() == 2);
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }


    private void assertDeepEquals(User actual, User expected) {
        assertNotNull(actual);
        assertNotNull(expected);
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getGivenName(), expected.getGivenName());
        assertEquals(actual.getSurname(), expected.getSurname());
        assertEquals(actual.getAddress(), expected.getAddress());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getUserRole(), expected.getUserRole());
        assertEquals(actual.getPasswordHash(), expected.getPasswordHash());

    }












}