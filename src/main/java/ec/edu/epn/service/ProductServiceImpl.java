package ec.edu.epn.service;

import ec.edu.epn.model.Product;
import ec.edu.epn.repository.ProductRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findBySku(String sku) {
        Optional<Product> product = productRepository.findBySku(sku);
        return product.orElseThrow(() -> new RuntimeException("Product not found with SKU: " + sku));
    }
}