package id.ac.ui.cs.advprog.eshop.model;


import org.junit.jupiter.api.Test;
import enums.PaymentMethods;
import enums.PaymentStatus;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    @Test
    void testCreateSuccessStatus() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), PaymentStatus.SUCCESS.getValue(),paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateRejectedStatus() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), PaymentStatus.REJECTED.getValue(),paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateInvalidStatus() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");


        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), "MEOW", paymentData);
        });
    }

    @Test
    void testCreate() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }
    @Test
    void testCreatePaymentEmptyPaymentData() {
        HashMap<String, String> paymentData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.CASHONDELIVERY.getValue(), paymentData);
        });
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "Meow", paymentData);
        });
    }

    @Test
    void testSetStatusToRejected() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "Ultra");

        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }

    @Test
    void testSetPaymentStatusToSuccess() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), paymentData);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals("SUCCESS", payment.getStatus());
    }
}