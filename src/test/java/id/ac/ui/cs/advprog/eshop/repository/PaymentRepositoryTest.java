package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    Payment makeVoucherPayment(String id, String code) {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", code);
        return new Payment(id, "VOUCHER_CODE", data);
    }

    Payment makeCodPayment(String id, String address, String fee) {
        Map<String, String> data = new HashMap<>();
        data.put("address", address);
        data.put("deliveryFee", fee);
        return new Payment(id, "CASH_ON_DELIVERY", data);
    }

    @Test
    void testSaveCreate() {
        Payment payment = makeVoucherPayment("pay-001", "ESHOP1234ABC5678");
        Payment result = paymentRepository.save(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getStatus(), result.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = makeVoucherPayment("pay-001", "ESHOP1234ABC5678");
        paymentRepository.save(payment);

        payment.setStatus("REJECTED");
        Payment result = paymentRepository.save(payment);

        Payment found = paymentRepository.findById("pay-001");
        assertEquals("REJECTED", found.getStatus());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdFound() {
        Payment payment = makeVoucherPayment("pay-001", "ESHOP1234ABC5678");
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("pay-001");
        assertNotNull(found);
        assertEquals("pay-001", found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("not-exist");
        assertNull(found);
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.save(makeVoucherPayment("pay-001", "ESHOP1234ABC5678"));
        paymentRepository.save(makeCodPayment("pay-002", "Jl. Margonda", "5000"));

        List<Payment> all = paymentRepository.getAllPayments();
        assertEquals(2, all.size());
    }
}
