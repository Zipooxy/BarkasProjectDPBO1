package Exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionMessagesTest {

    @Test
    void loginExceptionCarriesMessage() {
        LoginException ex = new LoginException("login gagal");
        assertEquals("login gagal", ex.getMessage());
    }

    @Test
    void userNotFoundExceptionCarriesMessage() {
        UserNotFoundException ex = new UserNotFoundException("user tidak ditemukan");
        assertEquals("user tidak ditemukan", ex.getMessage());
    }

    @Test
    void invalidRegistrationExceptionCarriesMessage() {
        InvalidRegistrationException ex = new InvalidRegistrationException("registrasi gagal");
        assertEquals("registrasi gagal", ex.getMessage());
    }
}
