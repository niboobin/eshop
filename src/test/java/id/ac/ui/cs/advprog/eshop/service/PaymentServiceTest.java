package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentMethods;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    List<Order> orders = new ArrayList<>();

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName ("Sampo Cap Bambang"); product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order ("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);

        payments = new ArrayList<>();
        HashMap<String, String> paymentDataVoucher = new HashMap<>();
        HashMap<String, String> paymentDataCOD = new HashMap<>();

        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC5678");
        paymentDataCOD.put("address", "Apartemen Melati Taman");
        paymentDataCOD.put("deliveryFee", "10000");

        Payment payment1 = new Payment(order1.getId(), PaymentMethods.CASHONDELIVERY.getValue(),  paymentDataCOD);
        payments.add(payment1);
        Payment payment2 = new Payment(order2.getId(), PaymentMethods.VOUCHER.getValue(), paymentDataVoucher);
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        doReturn(payment).when(paymentRepository).add(any(Payment.class));

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        verify(paymentRepository, times(1)).add(any(Payment.class));

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testGetPaymentByIdIfFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).getPaymentById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        verify(paymentRepository, times(1)).getPaymentById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentByIdIfNotFound() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("zczc", PaymentMethods.VOUCHER.getValue(), paymentData);
        doThrow(NoSuchElementException.class).when(paymentRepository).getPaymentById(payment.getId());

        assertThrows(NoSuchElementException.class, () -> paymentService.getPayment(payment.getId()));
        verify(paymentRepository, times(1)).getPaymentById(payment.getId());
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();
        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments.size(), results.size());
    }

    @Test
    void testGetAllPaymentsIfEmpty() {
        List<Payment> result = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).getAllPayments();
        assertEquals(0, result.size());
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        order.setStatus(PaymentStatus.SUCCESS.getValue());
        Payment editedStatusPayment = new Payment(payment.getId(), payment.getMethod(), PaymentStatus.SUCCESS.getValue(), payment.getPaymentData());
        doReturn(payment).when(paymentRepository).add(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        verify(paymentRepository, times(1)).add(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).findById(payment.getId());
        assertEquals(editedStatusPayment.getStatus(), result.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        order.setStatus("FAILED");
        Payment editedStatusPayment = new Payment(payment.getId(), payment.getMethod(), PaymentStatus.REJECTED.getValue(), payment.getPaymentData());
        doReturn(payment).when(paymentRepository).add(any(Payment.class));
        doReturn(order).when(orderRepository).save(any(Order.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        verify(paymentRepository, times(1)).add(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).findById(payment.getId());
        assertEquals(editedStatusPayment.getStatus(), result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = payments.get(1);
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "MEOW"));
    }

    @Test
    void testPayIfMethodIsVoucher() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), paymentData);
        Order order = orders.get(1);
        Order editedOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), PaymentStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).add(any(Payment.class));
        doReturn(editedOrder).when(orderRepository).save(any(Order.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.pay(payment, order);
        verify(paymentRepository, times(1)).add(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).findById(payment.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), editedOrder.getStatus());
    }

    @Test
    void testPayIfMethodIsBankTransfer() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Apartemen Melati Taman");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.CASHONDELIVERY.getValue(), paymentData);
        Order order = orders.get(1);
        Order editedOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), PaymentStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).add(any(Payment.class));
        doReturn(editedOrder).when(orderRepository).save(any(Order.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.pay(payment, order);
        verify(paymentRepository, times(1)).add(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).findById(payment.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), editedOrder.getStatus()); }

    @Test
    void testPayIfMethodIsVoucherAndVoucherCodeIsInvalid() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.VOUCHER.getValue(), paymentData);
        Order order = orders.get(1);
        Order editedOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), OrderStatus.FAILED.getValue());
        doReturn(payment).when(paymentRepository).add(any(Payment.class));
        doReturn(editedOrder).when(orderRepository).save(any(Order.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.pay(payment, order);
        verify(paymentRepository, times(1)).add(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).findById(payment.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), editedOrder.getStatus());
    }

    @Test
    void testPayIfMethodIsBankTransferAndPaymentDataIsInvalid() {
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.CASHONDELIVERY.getValue(), paymentData);
        Order order = orders.get(1);
        Order editedOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), OrderStatus.FAILED.getValue());
        doReturn(payment).when(paymentRepository).add(any(Payment.class));
        doReturn(editedOrder).when(orderRepository).save(any(Order.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.pay(payment, order);
        verify(paymentRepository, times(1)).add(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).findById(payment.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), editedOrder.getStatus());
    }

}
