package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    List<Product> products;
    Order order;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    // --- Voucher Code Tests ---

    @Test
    void testCreatePaymentVoucherCodeValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                "VOUCHER_CODE", paymentData);

        assertEquals("a1b2c3d4-e5f6-7890-abcd-ef1234567890", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherCodeInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeInvalidPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABCDE1234ABC5678");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeInvalidNumericalCount() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABCDEFGH");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    // --- Cash on Delivery Tests ---

    @Test
    void testCreatePaymentCashOnDeliveryValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Margonda Raya No. 100");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567891",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryEmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567891",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567891",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryEmptyDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Margonda Raya No. 100");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567891",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryNullDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Margonda Raya No. 100");
        paymentData.put("deliveryFee", null);

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567891",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    // --- setStatus Tests ---

    @Test
    void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                "VOUCHER_CODE", paymentData);

        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                "VOUCHER_CODE", paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
