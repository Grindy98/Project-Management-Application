package persistent.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void validateUsername() {
        User.getUsers().clear();
        User u = new TeamMember("username", "password", "addr", "0324");
        assertTrue(User.validateUsername("username"));
        User.getUsers().put(u.getUsername(), u);
        assertFalse(User.validateUsername("username"));
    }

    @Test
    void validatePassword() {
        assertTrue(User.validatePassword("12345678"));
        assertFalse(User.validatePassword("1234"));
    }

    @Test
    void validateAddress() {
        assertTrue(User.validateAddress("anfoij"));
        assertFalse(User.validateAddress("403810"));
    }

    @Test
    void validatePhone() {
        assertTrue(User.validatePhone("024142"));
        assertFalse(User.validatePhone("123jij124"));
    }
}