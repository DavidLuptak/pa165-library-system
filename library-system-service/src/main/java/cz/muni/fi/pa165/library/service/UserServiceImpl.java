package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.UserDao;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin
 * @version 13.11.2016
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public void register(User user, String unencryptedPassword) {
        if (user == null) throw new IllegalArgumentException("user is null");
        if (unencryptedPassword == null || unencryptedPassword.isEmpty())
            throw new IllegalArgumentException("unencryptedPassword is null or empty");
        user.setPasswordHash(createHash(unencryptedPassword));
        try {
            userDao.create(user);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public User update(User user) {
        if (user == null) throw new IllegalArgumentException("user is null");
        try {
            return userDao.update(user);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(User user) {
        if (user == null) throw new IllegalArgumentException("user is null");
        try {
            userDao.delete(user);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public User findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        try {
            return userDao.findById(id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("email is null or empty");
        try {
            return userDao.findByEmail(email);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<User> findAll() {

        try {
            return userDao.findAll();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public UserRole userRole(User user) {
        if (user == null) throw new IllegalArgumentException("user is null");
        try {
            return user.getUserRole();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public boolean authenticate(User user, String unencryptedPassword) {
        if (user == null) throw new IllegalArgumentException("user is null");
        if (unencryptedPassword == null || unencryptedPassword.isEmpty())
            throw new IllegalArgumentException("unencryptedPassword is null or empty");
        return validatePassword(unencryptedPassword, user.getPasswordHash());
    }

    @Override
    public List<User> findUsersWithNotReturnedLoans() {
        try {
            return userDao.findAll().stream()
                    .filter(user -> user.getLoans().stream()
                            .anyMatch(loan -> !loan.isReturned()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    //see  https://crackstation.net/hashing-security.htm#javasourcecode
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if (password == null) return false;
        if (correctHash == null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    @Override
    public boolean isAdmin(User u) {
        if(u == null) throw new IllegalArgumentException("user is null");
        return findById(u.getId()).isAdmin();
    }
}
