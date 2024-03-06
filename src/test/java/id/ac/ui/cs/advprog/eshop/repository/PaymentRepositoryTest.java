package id.ac.ui.cs.advprog.eshop.repository;

import enums.PaymentMethods;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class PaymentRepositoryTest {
    PaymentRepository paymentRepository = new PaymentRepository();
    List<Payment> paymentList = new ArrayList<>();
    List<Order> orders = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    HashMap<String, String> paymentDataVoucher = new HashMap<>();
    HashMap<String, String> paymentDataCOD = new HashMap<>();

    @BeforeEach
    void setUp() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
        Order order3 = new Order("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                products, 1708570000L, "Bambang Sudrajat");
        orders.add(order3);

        paymentDataVoucher.put("voucherCode", "ESHOP-123");
        paymentDataCOD.put("address", "Apartemen Melati Taman");
        paymentDataCOD.put("deliveryFee", "10000");

        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.CASHONDELIVERY.getValue(),  paymentDataCOD);
        paymentList.add(payment1);
        Payment payment2 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078", PaymentMethods.VOUCHER.getValue(), paymentDataVoucher);
        paymentList.add(payment2);
        Payment payment3 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e", PaymentMethods.CASHONDELIVERY.getValue(), paymentDataCOD);
        paymentList.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = paymentList.get(1);
        Payment result = paymentRepository.add(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = paymentList.get(1);
        paymentRepository.add(payment);
        Payment newPayment = new Payment(payment.getId(), payment.getMethod(), payment.getPaymentData());
        Payment result = paymentRepository.add(newPayment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getPaymentData(), result.getPaymentData()); }


    @Test
    void testGetPaymentById() {
        Payment payment = paymentList.get(1);
        paymentRepository.add(payment);
        Payment result = paymentRepository.getPaymentById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testGetPaymentByIdIfNotFound() {
        Payment result = paymentRepository.getPaymentById("13652556-012a-4c07-b546-54eb1396d79b");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : paymentList) {
            paymentRepository.add(payment);
        }

        List<Payment> result = paymentRepository.getAllPayments();

        assertEquals(paymentList.size(), result.size());
    }

    @Test
    void testGetAllPaymentsIfEmpty() {
        List<Payment> result = paymentRepository.getAllPayments();

        assertEquals(0, result.size());
    }

}