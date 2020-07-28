package com.hcl.product.service;

import com.hcl.product.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    public List<ProductCategory> getProductCategories();
    public ProductCategory getProductCategoryById(int productCategoryId );
    public ProductCategory createProductCategory( ProductCategory productCategory);
    public ProductCategory updateProductCategory( ProductCategory  productCategory);
    public void deleteProductCategory( ProductCategory  productCategory);
}
