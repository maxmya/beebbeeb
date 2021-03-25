package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.PurchasingRequestEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.mappers.CarInstanceMapper;
import com.trixpert.beebbeeb.data.mappers.CustomerMapper;
import com.trixpert.beebbeeb.data.mappers.PurchasingRequestMapper;
import com.trixpert.beebbeeb.data.mappers.VendorMapper;
import com.trixpert.beebbeeb.data.repositories.CarInstanceRepository;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.repositories.PurchasingRequestRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.request.PurchasingRequestRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchasingRequestServiceImpl implements PurchasingRequestService {

    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;
    private final PurchasingRequestMapper purchasingRequestMapper;
    private final PurchasingRequestRepository purchasingRequestRepository;
    private final CarInstanceService carInstanceService;
    private final CustomerService customerService;
    private final VendorService vendorService;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final CarInstanceRepository carInstanceRepository;
    private final VendorMapper vendorMapper;
    private final CustomerMapper customerMapper;
    private final CarInstanceMapper carInstanceMapper;

    public PurchasingRequestServiceImpl(ReporterService reporterService, UserService userService,
                                        AuditService auditService,
                                        PurchasingRequestMapper purchasingRequestMapper,
                                        PurchasingRequestRepository purchasingRequestRepository,
                                        CarInstanceService carInstanceService,
                                        CustomerService customerService,
                                        VendorService vendorService, VendorRepository vendorRepository,
                                        CustomerRepository customerRepository,
                                        CarInstanceRepository carInstanceRepository,
                                        VendorMapper vendorMapper, CustomerMapper customerMapper,
                                        CarInstanceMapper carInstanceMapper) {
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
        this.purchasingRequestMapper = purchasingRequestMapper;
        this.purchasingRequestRepository = purchasingRequestRepository;
        this.carInstanceService = carInstanceService;
        this.customerService = customerService;
        this.vendorService = vendorService;
        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.carInstanceRepository = carInstanceRepository;
        this.vendorMapper = vendorMapper;
        this.customerMapper = customerMapper;
        this.carInstanceMapper = carInstanceMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerPurchasingRequest(
            PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
            String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try{
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(
                    purchasingRequestRegistrationRequest.getVendorId());
            if (!optionalVendorEntity.isPresent()) {
                throw new NotFoundException("This Vendor doesn't exits !");
            }
            VendorEntity vendorEntityRecord = optionalVendorEntity.get();

            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(
                    purchasingRequestRegistrationRequest.getCustomerId());
            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("This Customer doesn't exits !");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();

            Optional<CarInstanceEntity> optionalCarInstanceEntity = carInstanceRepository.findById(
                    purchasingRequestRegistrationRequest.getCarInstanceId());
            if (!optionalCarInstanceEntity.isPresent()) {
                throw new NotFoundException("This Customer doesn't exits !");
            }

            CarInstanceEntity carInstanceEntityRecord = optionalCarInstanceEntity.get();
            // we need to check price Service and add it after merge
            PurchasingRequestEntity purchasingRequestEntityRecord = PurchasingRequestEntity.builder()
                    .status(purchasingRequestRegistrationRequest.getStatus())
                    .payment_type(purchasingRequestRegistrationRequest.getPayment_type())
                    .comment(purchasingRequestRegistrationRequest.getComment())
                    .date(purchasingRequestRegistrationRequest.getDate())
                    .vendor(vendorEntityRecord)
                    .customer(customerEntityRecord)
                    .carInstanceEntity(carInstanceEntityRecord)
                    .build();

            purchasingRequestRepository.save(purchasingRequestEntityRecord);
            return reporterService.reportSuccess("PurchasingRequest Registerd Succesfully");
        }catch (Exception e){
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deletePurchasingRequest(long purchasingRequestId,
                                                            String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);
        try {
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findById(
                            purchasingRequestId);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("This Vendor doesn't exits !");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            purchasingRequestEntityRecord.setActive(false);
            purchasingRequestRepository.save(purchasingRequestEntityRecord);
            return reporterService.reportSuccess("PurchasingRequest Registerd Succesfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listAllPurchasingRequests(boolean active) {

        try {
            List<PurchasingRequestDTO> purchasingRequestDTOList = new ArrayList<>();
            purchasingRequestRepository.findAllByActive(active).forEach(purchasingRequestEntity ->
                    purchasingRequestDTOList.add(purchasingRequestMapper.
                            convertToDTO(purchasingRequestEntity)));
            return reporterService.reportSuccess(purchasingRequestDTOList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }    }

    @Override
    public ResponseWrapper<Boolean> updatePurchasingRequest(
            PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
        long purchasingRequstId, String AuthHeader) {
        return null;
    }

    @Override
    public ResponseWrapper<PurchasingRequestDTO> getPurchasingRequest(long purchasingRequestId) {
        return null;
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForVendor(long vendorId) {
        return null;
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> getPurchasingRequestForVendor(long vendorId) {
        return null;
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCustomer(long vendorId) {
        return null;
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> getPurchasingRequestForCustomer(long vendorId) {
        return null;
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCar(long vendorId) {
        return null;
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> getPurchasingRequestForCar(long vendorId) {
        return null;
    }
}
