package com.hcl.product.service.impl;

import com.hcl.product.Repository.ManufacturerRepository;
import com.hcl.product.controller.SellerController;
import com.hcl.product.model.Manufacturer;

import com.hcl.product.service.ManufacturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private Logger logger = (Logger) LoggerFactory.getLogger(ManufacturerServiceImpl.class);

    @Autowired
    ManufacturerRepository  manufacturerRepository  ;

    public void setManufacturerRepository(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer createManufacturer(Manufacturer manufacturer){
        return  manufacturerRepository.save(manufacturer);
    }

    public Manufacturer updateManufacturer( Manufacturer manufacturer){
        return  manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers(){
        return  (List<Manufacturer>)manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(int manufacturerId){
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturerId);
        if(optionalManufacturer.isPresent()) {
            return optionalManufacturer.get();
        }
        else {
            return null;
        }
    }


    public void deleteManufacturer( Manufacturer manufacturer){
        manufacturerRepository.delete(manufacturer);
    }


    public List<Manufacturer> getManufacturerByProductId(Integer productId){
      return   manufacturerRepository.findByProductProductId(productId);
    }

}
