package com.hcl.product.Repository;

import com.hcl.product.model.Manufacturer;
import com.hcl.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository <Manufacturer, Integer>{
    List<Manufacturer> findByProductProductId(Integer  productId);
    List<Manufacturer> findByManufacturerRegion(String manufacturerRegion);
    List<Manufacturer> findByManufacturerRegionAndManufacturingDate(String manufacturerRegion, LocalDate date);

}
