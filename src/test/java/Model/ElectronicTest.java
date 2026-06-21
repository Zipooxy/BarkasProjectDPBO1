package Model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElectronicTest {

    @Test
    void gettersReturnConstructorValues() {
        Electronic item = new Electronic(1, "Laptop Lenovo", 5000000, "Laptop");

        assertEquals(1, item.getId());
        assertEquals("Laptop Lenovo", item.getNama());
        assertEquals(5000000, item.getHarga());
    }

    @Test
    void displayInfoPrintsAllProductDetails() {
        Electronic item = new Electronic(1, "Laptop Lenovo", 5000000, "Laptop");

        String output = captureDisplayInfo(item);

        assertTrue(output.contains("Laptop Lenovo"));
        assertTrue(output.contains("Laptop"));
    }

    private String captureDisplayInfo(Electronic item) {
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
