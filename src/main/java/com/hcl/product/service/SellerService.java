package com.hcl.product.service;

import com.hcl.product.model.Seller;

import java.util.List;

public interface SellerService {

    public Seller createSeller(Seller seller);
    public  Seller updateSeller( Seller seller);
    public List<Seller> getAllSellers();
    public Seller getSellerById(int sellerId);
    public List<Seller> getSellerByProductId(int productId);
    public void deleteSeller( Seller seller);
}
