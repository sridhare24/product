package com.hcl.product.service;

import com.hcl.product.model.Manufacturer;
import com.hcl.product.model.Product;

import java.util.List;

public interface SearchService {

    public List<Product> searchProductsByName(String productName);
    public List<Product> searchProductsByRegion(String manufacturerRegion );
    public List<Manufacturer> getManufacturerByRegion(String manufacturerRegion );
}
