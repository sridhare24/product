package com.hcl.product.Repository;

import com.hcl.product.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository  extends JpaRepository<Seller,Integer> {
    public List<Seller> findByProductProductId(int productId);
}
