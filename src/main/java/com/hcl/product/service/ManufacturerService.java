package com.hcl.product.service;

import com.hcl.product.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    Manufacturer getManufacturerById(int manufacturerId);
    Manufacturer createManufacturer( Manufacturer manufacturer);
    Manufacturer updateManufacturer( Manufacturer manufacturer);
    void deleteManufacturer( Manufacturer manufacturer);
    List<Manufacturer> getManufacturerByProductId(Integer productId);
}
