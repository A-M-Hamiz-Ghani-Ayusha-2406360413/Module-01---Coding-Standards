package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl(new ProductRepository());
    }

    // ========================
    // EDIT TESTS
    // ========================

    @Test
    void editProduct_positive_updateSuccess() {
        Product product = new Product();
        product.setProductName("Old");
        product.setProductQuantity(10);

        Product saved = service.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(saved.getProductId());
        updatedProduct.setProductName("New");
        updatedProduct.setProductQuantity(50);

        Product updated = service.update(updatedProduct);

        assertNotNull(updated);
        assertEquals("New", updated.getProductName());
    }


    @Test
    void editProduct_negative_productNotFound() {
        Product product = new Product();
        product.setProductId("999");

        Product result = service.update(product);

        assertNull(result);
    }

    // ========================
    // DELETE TESTS
    // ========================

    @Test
    void deleteProduct_positive_deleteSuccess() {
        Product product = new Product();
        product.setProductName("Test");

        Product saved = service.create(product);

        boolean result = service.delete(saved.getProductId());

        assertTrue(result);
    }


    @Test
    void deleteProduct_negative_deleteNonExisting() {
        service.delete("999"); // should not crash

        assertTrue(service.findAll().isEmpty());
    }
}
