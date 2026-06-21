package Service;

import Exception.LoginException;
import Exception.UserNotFoundException;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        user = new User(1, "budi", "rahasia", 50000);
        userService.registerUser(user);
    }

    @Test
    void loginUserSucceedsWithCorrectCredentials() throws Exception {
        User loggedIn = userService.loginUser(1, "budi", "rahasia");

        assertEquals(user, loggedIn);
    }

    @Test
    void loginUserThrowsWhenUserIdNotFound() {
        assertThrows(UserNotFoundException.class,
                () -> userService.loginUser(99, "budi", "rahasia"));
    }

    @Test
    void loginUserThrowsWhenPasswordIsWrong() {
        assertThrows(LoginException.class,
                () -> userService.loginUser(1, "budi", "passwordsalah"));
    }

    @Test
    void loginUserThrowsWhenUsernameIsWrong() {
        assertThrows(LoginException.class,
                () -> userService.loginUser(1, "usernamesalah", "rahasia"));
    }

    @Test
    void daftarMemberDoesNothingWhenNoActiveUser() {
        assertDoesNotThrow(() -> userService.daftarMember());
    }

    @Test
    void newUserServiceStartsLoggedOut() {
        assertEquals(false, userService.isIsLoggedIn());
    }
}
