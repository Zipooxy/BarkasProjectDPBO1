package Service;

import Model.Electronic;
import Model.Product;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionServiceTest {

    private ProductService productService;
    private HistoryService historyService;
    private TransactionService transactionService;
    private User user;
    private Product item;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        historyService = new HistoryService();
        transactionService = new TransactionService(productService, historyService);
        user = new User(1, "budi", "rahasia", 1000000);
        item = new Electronic(1, "Laptop", 500000, "Laptop");
        productService.addProduct(item);
    }

    @Test
    void buyItemSucceedsWhenBalanceIsSufficient() {
        transactionService.buyItem(item, user);

        assertEquals(500000, user.getSaldo());
        assertNull(productService.findProductById(1));
    }

    @Test
    void buyItemAppliesTenPercentMemberDiscount() {
        user.menjadiMember();

        transactionService.buyItem(item, user);

        // Harga setelah diskon 10%: 500000 * 0.9 = 450000, sisa saldo 550000
        assertEquals(550000, user.getSaldo());
    }

    @Test
    void buyItemFailsWhenBalanceInsufficient() {
        User poorUser = new User(2, "ani", "rahasia2", 1000);

        transactionService.buyItem(item, poorUser);

        assertEquals(1000, poorUser.getSaldo());
        assertNotNull(productService.findProductById(1));
    }

    @Test
    void buyItemDoesNothingWhenItemIsNull() {
        assertDoesNotThrow(() -> transactionService.buyItem(null, user));
        assertEquals(1000000, user.getSaldo());
    }
}
