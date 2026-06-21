package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "budi", "rahasia", 50000);
    }

    @Test
    void newUserIsNotMemberByDefault() {
        assertFalse(user.isMember());
    }

    @Test
    void gettersReturnConstructorValues() {
        assertEquals(1, user.getId());
        assertEquals("budi", user.getUsername());
        assertEquals("rahasia", user.getPassword());
        assertEquals(50000, user.getSaldo());
    }

    @Test
    void menjadiMemberSetsMemberFlagToTrue() {
        user.menjadiMember();
        assertTrue(user.isMember());
    }

    @Test
    void tambahSaldoIncreasesBalance() {
        user.tambahSaldo(10000);
        assertEquals(60000, user.getSaldo());
    }

    @Test
    void kurangiSaldoDecreasesBalance() {
        user.kurangiSaldo(20000);
        assertEquals(30000, user.getSaldo());
    }
}
