package Service;

import Model.Electronic;
import Model.Fashion;
import Model.Living;
import Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void addProductMakesItFindableById() {
        Product item = new Electronic(1, "Laptop", 5000000, "Laptop");

        productService.addProduct(item);

        assertEquals(item, productService.findProductById(1));
    }

    @Test
    void findProductByIdReturnsNullWhenNotFound() {
        assertNull(productService.findProductById(99));
    }

    @Test
    void deleteProductRemovesItemFromCatalog() {
        Product item = new Electronic(1, "Laptop", 5000000, "Laptop");
        productService.addProduct(item);

        productService.deleteProduct(item);

        assertNull(productService.findProductById(1));
    }

    @Test
    void getProdukByKategoriOnlyReturnsMatchingClass() {
        productService.addProduct(new Fashion(1, "Kaos", 100000, "Putih", "L", "Atasan"));
        productService.addProduct(new Electronic(2, "TV", 2000000, "Elektronik"));

        List<Product> fashionList = productService.getProdukByKategori(Fashion.class);

        assertEquals(1, fashionList.size());
        assertTrue(fashionList.get(0) instanceof Fashion);
    }

    @Test
    void initializeProductPopulatesDefaultCatalog() {
        productService.initializeProduct();

        assertNotNull(productService.findProductById(1));
        assertNotNull(productService.findProductById(2));
    }

    @Test
    void showAllProductReportsEmptyCatalogMessage() {
        String output = captureOutput(productService::showAllProduct);

        assertTrue(output.contains("Tidak ada produk"));
    }

    @Test
    void showAllProductListsEveryProduct() {
        productService.addProduct(new Electronic(1, "Laptop", 5000000, "Laptop"));
        productService.addProduct(new Fashion(2, "Kaos", 100000, "Putih", "L", "Atasan"));

        String output = captureOutput(productService::showAllProduct);

        assertTrue(output.contains("Laptop"));
        assertTrue(output.contains("Kaos"));
    }

    @Test
    void showFashionProductOnlyShowsFashionItems() {
        productService.addProduct(new Electronic(1, "Laptop", 5000000, "Laptop"));
        productService.addProduct(new Fashion(2, "Kaos", 100000, "Putih", "L", "Atasan"));

        String output = captureOutput(productService::showFashionProduct);

        assertTrue(output.contains("Kaos"));
        assertTrue(!output.contains("Laptop"));
    }

    @Test
    void showElectronicProductOnlyShowsElectronicItems() {
        productService.addProduct(new Electronic(1, "Laptop", 5000000, "Laptop"));
        productService.addProduct(new Fashion(2, "Kaos", 100000, "Putih", "L", "Atasan"));

        String output = captureOutput(productService::showElectronicProduct);

        assertTrue(output.contains("Laptop"));
        assertTrue(!output.contains("Kaos"));
    }

    @Test
    void showLivingProductOnlyShowsLivingItems() {
        productService.addProduct(new Living(1, "Meja", 750000, "Furniture"));
        productService.addProduct(new Fashion(2, "Kaos", 100000, "Putih", "L", "Atasan"));

        String output = captureOutput(productService::showLivingProduct);

        assertTrue(output.contains("Meja"));
        assertTrue(!output.contains("Kaos"));
    }

    private String captureOutput(Runnable action) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return out.toString();
    }
}
