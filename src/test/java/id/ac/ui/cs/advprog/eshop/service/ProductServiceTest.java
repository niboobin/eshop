package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductServiceImpl service;

    @Mock
    ProductRepository productRepository;

    @Test
    void testCreateService() {
        Product product1 = new Product();

        doNothing().when(productRepository).create(product1);

        service.create(product1);
        verify(productRepository).create(product1);

        assertDoesNotThrow(() -> UUID.fromString(product1.getProductId()));
    }

    @Test
    void testDeleteService() {
        Product product1 = Mockito.mock(Product.class);

        when(productRepository.delete(any(Product.class))).thenReturn(true);
        when(productRepository.get(product1.getProductId())).thenReturn(product1);

        boolean returnValue = service.delete(product1.getProductId());

        verify(productRepository).delete(product1);
        assertTrue(returnValue);
    }

    @Test
    void testDeleteNotFoundService() {
        Product product1 = Mockito.mock(Product.class);

        when(productRepository.get(product1.getProductId())).thenReturn(null);

        boolean returnValue = service.delete(product1.getProductId());

        assertFalse(returnValue);
    }

    @Test
    void testEditService() {
        Product product1 = Mockito.mock(Product.class);
        Product product2 = Mockito.mock(Product.class);

        when(productRepository.edit(product1.getProductId(), product2)).thenReturn(product2);

        Product returnValue = service.edit(product1.getProductId(), product2);

        verify(productRepository).edit(product1.getProductId(), product2);
        assertNotNull(returnValue);
    }

    @Test
    void testGetService() {
        Product product1 = Mockito.mock(Product.class);

        when(productRepository.get(product1.getProductId())).thenReturn(product1);

        Product returnValue = service.get(product1.getProductId());

        verify(productRepository).get(product1.getProductId());
        assertNotNull(returnValue);
    }

    @Test
    void testFindAll() {
        Product product1 = Mockito.mock(Product.class);
        Product product2 = Mockito.mock(Product.class);

        List<Product> mockData = new ArrayList<>();
        mockData.add(product1);
        mockData.add(product2);

        when(productRepository.findAll()).thenReturn(mockData.iterator());

        List<Product> returnValue = service.findAll();
        verify(productRepository).findAll();
        assertEquals(mockData, returnValue);
    }
}
