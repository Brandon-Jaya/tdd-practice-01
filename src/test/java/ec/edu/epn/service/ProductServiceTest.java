package ec.edu.epn.service;

import ec.edu.epn.model.Product;
import ec.edu.epn.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("findBySku: when product exists should return the product")
    void findBySku_WhenProductExists_ShouldReturnProduct() {
        String sku = "SKU-001";

        Product product = new Product();
        product.setSku(sku);
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setStock(10);

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(product));

        Product result = productService.findBySku(sku);

        assertNotNull(result);
        assertEquals(sku, result.getSku());
    }

    @Test
    @DisplayName("findBySku: when product does not exist should throw RuntimeException")
    void findBySku_WhenProductDoesNotExist_ShouldThrowRuntimeException() {
        String sku = "SKU-NOT-FOUND";

        when(productRepository.findBySku(sku)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.findBySku(sku));
    }

    @Test
    @DisplayName("findActiveProducts: should return list of active products")
    void findActiveProducts_ShouldReturnListOfActiveProducts() {
        Product product1 = new Product();
        product1.setSku("SKU-001");
        product1.setName("Laptop");
        product1.setStock(10);
        product1.setActive(true);

        Product product2 = new Product();
        product2.setSku("SKU-002");
        product2.setName("Mouse");
        product2.setStock(25);
        product2.setActive(true);

        List<Product> activeProducts = List.of(product1, product2);

        when(productRepository.findByActiveTrue()).thenReturn(activeProducts);

        List<Product> result = productService.findActiveProducts();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));
    }
}