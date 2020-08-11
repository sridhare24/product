package com.hcl.product.controller;

import com.hcl.product.model.Manufacturer;
import com.hcl.product.model.Product;
import com.hcl.product.service.ManufacturerService;
import com.hcl.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ManufacturerController {

    private Logger logger = LogManager.getLogger(ManufacturerController.class);

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    ProductService productService;

    @GetMapping("/products/{productId}/manufacturers")
    public ResponseEntity<List<Manufacturer>> getManufacturers(@PathVariable Integer productId) {
        List<Manufacturer> manufacturers =manufacturerService. getManufacturerByProductId(productId);
        if (manufacturers != null && manufacturers.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(manufacturers);
        } else {
            logger.warn("No Manufacture details Found with product Id "+productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ArrayList<Manufacturer>());
        }

    }

    @GetMapping("/manufacturers/{manufacturerId}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable  Integer manufacturerId) {

        Manufacturer manufacturer = manufacturerService.getManufacturerById(manufacturerId);

        if (manufacturer != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(manufacturer);
        } else {
            logger.warn("No Manufacture details Found with manufacture Id "+manufacturerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }

    @PostMapping("products/{productId}/manufacturers")
    public ResponseEntity<Manufacturer> addManufacturer(@RequestBody Manufacturer manufacturer,@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        manufacturer.setProduct(product);

        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);

        if (createdManufacturer != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(createdManufacturer);
        } else {
            logger.error("Unable to add Manufacturer ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


    @PutMapping("/manufacturers")
    public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturer ) {
        Manufacturer updatedManufacturer = null;
        try {
            updatedManufacturer = manufacturerService.updateManufacturer( manufacturer);
        }  catch (Exception exception){
            logger.error("Unable to process the request "+ exception.getMessage());
        }

        if (updatedManufacturer != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedManufacturer);
        } else {
            logger.warn("Unable to update Manufacture details ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


    @DeleteMapping("/manufacturers")
    public ResponseEntity<String> deleteProduct(@RequestBody Manufacturer manufacturer) {
        try {
            manufacturerService.deleteManufacturer(manufacturer);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Manufacturer deleted successfully");
        } catch (Exception exception){
            logger.error("Unable to delete Manufacture "+ exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Manufacturer deleted successfully");
        }

    }

    @GetMapping("/manufacturers/{manufacturerRegion}/{manufacturingDate}")
    public ResponseEntity<List<Manufacturer>> getManufacturerByManufactureDate(@PathVariable String manufacturerRegion, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate manufacturingDate) {

        List<Manufacturer> manufacturer = manufacturerService.getManufacturerByManufactureRegionAndDate(manufacturerRegion , manufacturingDate);

        if (manufacturer != null && manufacturer.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(manufacturer);
        } else {
            logger.warn("No Manufacture details Found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }

    }
