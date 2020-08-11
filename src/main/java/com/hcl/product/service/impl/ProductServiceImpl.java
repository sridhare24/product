package com.hcl.product.service.impl;
import java.util.List;
import java.util.Optional;
import com.hcl.product.Repository.ProductRepository;
import com.hcl.product.model.Product;
import com.hcl.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository ;


    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct( Product product){
        return  productRepository.save(product);
    }

    @Override
    public Product updateProduct( Product product){
        return  productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts(){
       return  productRepository.findAll();
    }

    @Override
    public Product getProductById(int productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        else {
            return null;
        }
    }

    @Override
    public List<Product> getProductByProductCategoryId(int productCategoryId){
        List<Product> products = productRepository.findByProductCategoryProductCategoryId(productCategoryId);
        return products;
    }

    @Override
    public void deleteProduct( Product product){
         productRepository.delete(product);
     }

}
