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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

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
}