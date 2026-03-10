package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    Map<String, String> validVoucherData() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        return data;
    }

    Map<String, String> invalidVoucherData() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID");
        return data;
    }

    Map<String, String> validCodData() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jl. Margonda Raya No. 100");
        data.put("deliveryFee", "10000");
        return data;
    }

    Map<String, String> invalidCodData() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "10000");
        return data;
    }

    @Test
    void testAddPaymentVoucherValid() {
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", validVoucherData());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", validVoucherData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalid() {
        Payment payment = new Payment("pay-002", "VOUCHER_CODE", invalidVoucherData());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", invalidVoucherData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCodValid() {
        Payment payment = new Payment("pay-003", "CASH_ON_DELIVERY", validCodData());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", validCodData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCodInvalid() {
        Payment payment = new Payment("pay-004", "CASH_ON_DELIVERY", invalidCodData());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", invalidCodData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Map<String, String> data = validVoucherData();
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", data);

        Payment result = paymentService.setStatus(payment, order, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Map<String, String> data = validVoucherData();
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", data);

        Payment result = paymentService.setStatus(payment, order, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> data = validVoucherData();
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", data);

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, order, "MEOW"));
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = new Payment("pay-001", "VOUCHER_CODE", validVoucherData());
        doReturn(payment).when(paymentRepository).findById("pay-001");

        Payment result = paymentService.getPayment("pay-001");
        assertEquals("pay-001", result.getId());
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).findById("not-exist");
        assertNull(paymentService.getPayment("not-exist"));
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("pay-001", "VOUCHER_CODE", validVoucherData()));
        payments.add(new Payment("pay-002", "CASH_ON_DELIVERY", validCodData()));
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }
}
