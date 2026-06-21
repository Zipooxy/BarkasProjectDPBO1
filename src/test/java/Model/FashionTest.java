package Model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FashionTest {

    @Test
    void gettersReturnConstructorValues() {
        Fashion item = new Fashion(2, "T-shirt Band", 150000, "Hitam", "M", "Pakaian");

        assertEquals(2, item.getId());
        assertEquals("T-shirt Band", item.getNama());
        assertEquals(150000, item.getHarga());
    }

    @Test
    void displayInfoPrintsAllProductDetails() {
        Fashion item = new Fashion(2, "T-shirt Band", 150000, "Hitam", "M", "Pakaian");

        String output = captureDisplayInfo(item);

        assertTrue(output.contains("T-shirt Band"));
        assertTrue(output.contains("Hitam"));
        assertTrue(output.contains("M"));
    }

    private String captureDisplayInfo(Fashion item) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            item.displayInfo();
        } finally {
            System.setOut(original);
        }
        return out.toString();
    }
}
