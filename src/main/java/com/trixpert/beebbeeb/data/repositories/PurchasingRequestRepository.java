package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.PurchasingRequestEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasingRequestRepository extends JpaRepository<PurchasingRequestEntity , Long> {
    public List<PurchasingRequestEntity> findAllByActive(boolean active);
    public List<PurchasingRequestEntity> findAllByActiveAndVendor(
            boolean active , VendorEntity vendorEntity);
    public List<PurchasingRequestEntity> findAllByActiveAndCarInstanceEntity(
            boolean active , CarInstanceEntity carInstanceEntity);
    public List<PurchasingRequestEntity> findAllByActiveAndCustomer(boolean active ,
                                                                    CustomerEntity customerEntity);
    public Optional<PurchasingRequestEntity> findByIdAndVendor(long purchasingRequestId ,
                                                              VendorEntity vendorEntity);

    public Optional<PurchasingRequestEntity> findByIdANDAndCarInstanceEntity(
            long purchasingRequestId , CarInstanceEntity carInstanceEntity);

    public Optional<PurchasingRequestEntity> findByIdAndCustomer(
            long purchasingRequestId , CustomerEntity customerEntity);
}
