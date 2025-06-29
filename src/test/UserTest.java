package test;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserTest {

    @BeforeEach
    void resetCounter() throws Exception {
        TestUtils.resetAutoIncrementCounters(User.class);
    }

    @Test
    void testUserCreation() {
        User user = new User("Kotka", "kotka_1211@example.com");

        assertEquals(1, user.getId());
        assertEquals("Kotka", user.getName());
        assertEquals("kotka_1211@example.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User( "Elena", "Elena@example.com");
        user.setName("Elena Mudraya");
        user.setEmail("Mudraya@example.com");

        assertEquals("Elena Mudraya", user.getName());
        assertEquals("Mudraya@example.com", user.getEmail());
    }
}