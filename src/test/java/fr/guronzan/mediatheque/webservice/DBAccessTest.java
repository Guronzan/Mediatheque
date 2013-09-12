package fr.guronzan.mediatheque.webservice;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import fr.guronzan.mediatheque.mappingclasses.SpringTests;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;

public class DBAccessTest extends SpringTests {

    private static final String NAME = "name";
    private static final String FOR_NAME = "forName";
    private static final String OTHER_FOR_NAME = "otherForName";
    private static final String NICK = "nick";
    private static final String PASSWORD = "password";

    @Resource
    private DBAccess dbAccess;

    @Before
    public void setUp() {
        this.dbAccess.cleanDB();
    }

    private int addUser() {
        final User user = new User(
                NAME,
                FOR_NAME,
                NICK,
                String.valueOf(DigestUtils.md5DigestAsHex(PASSWORD.getBytes())),
                new Date());
        return this.dbAccess.addUser(user);
    }

    @Test
    public void testAddUser() {
        addUser();

        final User user = this.dbAccess.getUserFromNickName(NICK);
        assertThat(user.getName(), is(NAME));
        assertThat(user.getForName(), is(FOR_NAME));
        assertThat(user.getNickName(), is(NICK));
        assertTrue(user.checkPassword(String.valueOf(DigestUtils
                .md5Digest(PASSWORD.getBytes()))));
    }

    @Test
    public void testUpdateUser() {
        addUser();
        final User user = this.dbAccess.getUserFromNickName(NICK);
        user.setForName(OTHER_FOR_NAME);
        this.dbAccess.updateUser(user);
        assertNull(this.dbAccess.getUserFromFullName(NAME, FOR_NAME));
        assertNotNull(this.dbAccess.getUserFromFullName(NAME, OTHER_FOR_NAME));

    }

    @Test
    public void testDeleteUser() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testGetUserFromID() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testGetUserFromFullName() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testGetUserFromNickName() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testGetAllUsers() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testCheckPasswordFromID() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testCheckPasswordFromFullName() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testContainsUser() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testContainsBook() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testAddBook() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testAddMovie() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testContainsMovie() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testContainsCD() {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testAddCD() {
        throw new RuntimeException("not yet implemented");
    }

}
