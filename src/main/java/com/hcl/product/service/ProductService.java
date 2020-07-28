package com.hcl.product.service;

import com.hcl.product.model.Product;

import java.util.List;

public interface ProductService {

    public Product createProduct( Product product);
    public Product updateProduct( Product product);
    public List<Product> getAllProducts();
    public Product getProductById(int productId);
    public List<Product> getProductByProductCategoryId(int productCategoryId);
    public void deleteProduct( Product product);
}
