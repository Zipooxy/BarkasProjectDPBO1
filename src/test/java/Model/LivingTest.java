package Model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LivingTest {

    @Test
    void gettersReturnConstructorValues() {
        Living item = new Living(3, "Meja Kayu", 750000, "Furniture");

        assertEquals(3, item.getId());
        assertEquals("Meja Kayu", item.getNama());
        assertEquals(750000, item.getHarga());
    }

    @Test
    void displayInfoPrintsAllProductDetails() {
        Living item = new Living(3, "Meja Kayu", 750000, "Furniture");

        String output = captureDisplayInfo(item);

        assertTrue(output.contains("Meja Kayu"));
        assertTrue(output.contains("Furniture"));
    }

    private String captureDisplayInfo(Living item) {
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
