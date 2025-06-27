package test;

import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    @Test
    void testUserCreation() {
        User user = new User(1, "Kotka", "kotka_1211@example.com");

        assertEquals(1, user.getId());
        assertEquals("Kotka", user.getName());
        assertEquals("kotka_1211@example.com", user.getEmail());
    }

    @Test
    void testInvalidEmail() {
        User user = new User(1, "Kotka", "kotka_1211@example.com");

        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalid-email"));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
    }

    @Test
    void testSettersAndGetters() {
        User user = new User(1, "Elena", "Elena@example.com");
        user.setName("Elena Mudraya");
        user.setEmail("Mudraya@example.com");

        assertEquals("Elena Mudraya", user.getName());
        assertEquals("Mudraya@example.com", user.getEmail());
    }
}