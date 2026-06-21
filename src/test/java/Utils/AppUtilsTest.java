package Utils;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppUtilsTest {

    // Catatan: AppUtils.inputInt()/inputString() menggunakan Scanner static yang
    // di-bind ke System.in pada saat class pertama kali dimuat, sehingga tidak aman
    // untuk diuji dengan redirection System.in di tengah eksekusi test.
    // Method getIntInput()/getDoubleInput()/getStringInput() membuat BufferedReader
    // baru setiap kali dipanggil, sehingga aman dan deterministik untuk diuji.

    @Test
    void getIntInputParsesValidInteger() {
        withSystemIn("123\n", () -> assertEquals(123, AppUtils.getIntInput()));
    }

    @Test
    void getDoubleInputParsesValidDouble() {
        withSystemIn("99.5\n", () -> assertEquals(99.5, AppUtils.getDoubleInput()));
    }

    @Test
    void getStringInputReturnsTypedLine() {
        withSystemIn("halo dunia\n", () -> assertEquals("halo dunia", AppUtils.getStringInput()));
    }

    private void withSystemIn(String content, Runnable assertion) {
        InputStream original = System.in;
        System.setIn(new ByteArrayInputStream(content.getBytes()));
        try {
            assertion.run();
        } finally {
            System.setIn(original);
        }
    }
}
