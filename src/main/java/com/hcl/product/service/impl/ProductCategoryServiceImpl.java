package com.hcl.product.service.impl;
import com.hcl.product.Repository.ProductCategoryRepository;
import com.hcl.product.model.Manufacturer;
import com.hcl.product.model.Product;
import com.hcl.product.model.ProductCategory;
import com.hcl.product.model.Seller;
import com.hcl.product.service.ManufacturerService;
import com.hcl.product.service.ProductCategoryService;
import com.hcl.product.service.ProductService;
import com.hcl.product.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private Logger logger = (Logger) LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    ProductCategoryRepository  productCategoryRepository;

    public void setProductCategoryRepository(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public  List<ProductCategory> getProductCategories(){
      return (List<ProductCategory>)  productCategoryRepository.findAll();

    }

    @Override
    public ProductCategory getProductCategoryById(int productCategoryId ){
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(productCategoryId);
        if(optionalProductCategory .isPresent()) {
            return optionalProductCategory .get();
        }
        else {
            return null;
        }
    }

    @Override
    public ProductCategory createProductCategory( ProductCategory productCategory){
       List<Product> products = productCategory.getProducts();
       products.forEach(product -> {
           product.setProductCategory(productCategory);
           List<Manufacturer> manufacturers = product.getManufacturers();
           manufacturers.forEach(manufacturer -> manufacturer.setProduct(product));
           product.setManufacturers(manufacturers);
           List<Seller> sellers = product.getSellers();
           sellers.forEach(seller -> seller.setProduct(product));
           product.setSellers(sellers);
       });

        productCategory.setProducts(products);


       return  productCategoryRepository.saveAndFlush(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory( ProductCategory  productCategory){
        return  productCategoryRepository.save(productCategory);
    }


    @Override
    public void deleteProductCategory( ProductCategory  productCategory){
        productCategoryRepository.delete(productCategory);
    }

}
