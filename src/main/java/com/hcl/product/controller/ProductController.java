package com.hcl.product.controller;

import com.hcl.product.model.Product;
import com.hcl.product.model.ProductCategory;
import com.hcl.product.service.impl.ManufacturerServiceImpl;
import com.hcl.product.service.impl.ProductCategoryServiceImpl;
import com.hcl.product.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductCategoryServiceImpl productCategoryService;
    @Autowired
    ManufacturerServiceImpl manufacturerService ;


    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> existingProducts = productService.getAllProducts();

        if (existingProducts != null && existingProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(existingProducts);
        } else {
            logger.warn("NO Product details Found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ArrayList<Product>());
        }
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductByProductId(@PathVariable  Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(product);
        } else {
            logger.warn("NO Product details Found "+ productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }

    @PostMapping("/{productCategoryId}")
    public ResponseEntity<Product> addProductWithCategory(@RequestBody Product product, @PathVariable Integer productCategoryId ) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(productCategoryId);
        product.setProductCategory(productCategory);
        Product createdProduct = productService.createProduct(product);
        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(createdProduct);
        } else {
            logger.warn("Unable to add Product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {

        Product updatedProduct = productService.updateProduct( product);

        if (updatedProduct != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedProduct);
        } else {
            logger.warn("Unable to update Product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody Product product) {
        try {
            productService.deleteProduct( product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product deleted successfully");
        }catch (Exception exception){
            logger.error("Unable to Delete the Product" +exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unable to Delete the Product");
        }


    }

    @GetMapping("/productCategories/{productCategoryId}")
    public  ResponseEntity<List<Product>> getAllProductByProductCategoryId(@PathVariable (value = "productCategoryId") int productCategoryId) {
        List<Product> products = productService.getProductByProductCategoryId(productCategoryId);
        if(products != null && products.size() >0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(products);
        } else {
            logger.warn("NO Product details Found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ArrayList<Product>());
        }
    }

    @PostMapping("/productCategories/{productCategoryId}")
    public Product createProduct(@PathVariable (value = "productCategoryId") int  productCategoryId, @RequestBody Product product) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        product.setProductCategory(productCategory);
        return productService.createProduct(product);
    }

}


