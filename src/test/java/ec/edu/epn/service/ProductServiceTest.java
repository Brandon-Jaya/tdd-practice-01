package ec.edu.epn.service;

import ec.edu.epn.model.Product;
import ec.edu.epn.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
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

    private static final String EXISTING_SKU = "SKU-001";
    private static final String MISSING_SKU = "SKU-NOT-FOUND";
    private static final String ACTIVE_SKU_ONE = "SKU-001";
    private static final String ACTIVE_SKU_TWO = "SKU-002";

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("findBySku: when product exists should return the product")
    void findBySku_WhenProductExists_ShouldReturnProduct() {
        Product product = createProduct(EXISTING_SKU, "Laptop", 10, BigDecimal.valueOf(1500), false);

        when(productRepository.findBySku(EXISTING_SKU)).thenReturn(Optional.of(product));

        Product result = productService.findBySku(EXISTING_SKU);

        assertNotNull(result);
        assertEquals(EXISTING_SKU, result.getSku());
    }

    @Test
    @DisplayName("findBySku: when product does not exist should throw RuntimeException")
    void findBySku_WhenProductDoesNotExist_ShouldThrowRuntimeException() {
        when(productRepository.findBySku(MISSING_SKU)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.findBySku(MISSING_SKU));
    }

    @Test
    @DisplayName("findActiveProducts: should return list of active products")
    void findActiveProducts_ShouldReturnListOfActiveProducts() {
        List<Product> activeProducts = List.of(
            createProduct(ACTIVE_SKU_ONE, "Laptop", 10, null, true),
            createProduct(ACTIVE_SKU_TWO, "Mouse", 25, null, true)
        );

        when(productRepository.findByActiveTrue()).thenReturn(activeProducts);

        List<Product> result = productService.findActiveProducts();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));
    }

    private Product createProduct(String sku, String name, Integer stock, BigDecimal price, boolean active) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);
        product.setStock(stock);
        product.setActive(active);

        if (price != null) {
            product.setPrice(price);
        }

        return product;
    }
}