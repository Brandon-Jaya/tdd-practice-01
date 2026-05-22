package ec.edu.epn.service;

import ec.edu.epn.model.Product;

public interface ProductService {

    Product findBySku(String sku);
}