package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("test-id-123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void createProductPage_shouldReturnCreateProductView() {
        String viewName = productController.createProductPage(model);

        assertEquals("createProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void createProductPost_shouldCreateProductAndRedirect() {
        when(productService.create(any(Product.class))).thenReturn(product);

        String viewName = productController.createProductPost(product, model);

        assertEquals("redirect:list", viewName);
        verify(productService, times(1)).create(product);
    }

    @Test
    void productListPage_shouldReturnProductListView() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productService.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        assertEquals("productList", viewName);
        verify(productService, times(1)).findAll();
        verify(model, times(1)).addAttribute("products", products);
    }

    @Test
    void editProductPage_shouldReturnEditProductView() {
        when(productService.findById("test-id-123")).thenReturn(product);

        String viewName = productController.editProductPage("test-id-123", model);

        assertEquals("editProduct", viewName);
        verify(productService, times(1)).findById("test-id-123");
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void editProductPost_shouldUpdateProductAndRedirect() {
        when(productService.update(any(Product.class))).thenReturn(product);

        String viewName = productController.editProductPost(product);

        assertEquals("redirect:list", viewName);
        verify(productService, times(1)).update(product);
    }

    @Test
    void deleteProduct_shouldDeleteProductAndRedirect() {
        when(productService.delete("test-id-123")).thenReturn(true);

        String viewName = productController.deleteProduct("test-id-123");

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete("test-id-123");
    }
}
