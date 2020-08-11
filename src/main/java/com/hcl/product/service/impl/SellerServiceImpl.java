package com.hcl.product.service.impl;


import com.hcl.product.Repository.SellerRepository;
import com.hcl.product.model.Seller;
import com.hcl.product.service.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private Logger logger = LogManager.getLogger(SellerServiceImpl.class);

    @Autowired
    SellerRepository sellerRepository ;

    public void setSellerRepository(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller createSeller( Seller seller){
        return  sellerRepository.save(seller);
    }

    @Override
    public  Seller updateSeller( Seller seller){
        return  sellerRepository.save(seller);
    }

    @Override
    public List<Seller> getAllSellers(){
       return  (List<Seller>)sellerRepository.findAll();
    }

    @Override
    public Seller getSellerById(int sellerId){
        Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
        if(optionalSeller.isPresent()) {
            return optionalSeller.get();
        }
        else {
            return null;
        }
    }

    @Override
    public List<Seller> getSellerByProductId(int productId){
       return sellerRepository.findByProductProductId(productId);
    }

    @Override
    public void deleteSeller( Seller seller){
        sellerRepository.delete(seller);
     }

}
