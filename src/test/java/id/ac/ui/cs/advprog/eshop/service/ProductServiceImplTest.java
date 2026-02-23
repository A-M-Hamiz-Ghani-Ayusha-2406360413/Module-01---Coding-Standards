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
    // CREATE TESTS
    // ========================

    @Test
    void createProduct_positive_createSuccess() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        Product created = service.create(product);

        assertNotNull(created);
        assertNotNull(created.getProductId());
        assertEquals("Test Product", created.getProductName());
        assertEquals(10, created.getProductQuantity());
    }

    // ========================
    // FIND TESTS
    // ========================

    @Test
    void findAllProducts_positive_returnsAllProducts() {
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        service.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        service.create(product2);

        List<Product> products = service.findAll();

        assertEquals(2, products.size());
    }

    @Test
    void findAllProducts_positive_emptyList() {
        List<Product> products = service.findAll();

        assertTrue(products.isEmpty());
    }

    @Test
    void findById_positive_productExists() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        Product saved = service.create(product);

        Product found = service.findById(saved.getProductId());

        assertNotNull(found);
        assertEquals(saved.getProductId(), found.getProductId());
        assertEquals("Test Product", found.getProductName());
    }

    @Test
    void findById_negative_productDoesNotExist() {
        Product found = service.findById("non-existent-id");

        assertNull(found);
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
