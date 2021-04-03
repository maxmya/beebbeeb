package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.PurchasingRequestEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.mappers.PurchasingRequestMapper;
import com.trixpert.beebbeeb.data.repositories.CarInstanceRepository;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.repositories.PurchasingRequestRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.request.PurchasingRequestRegistrationRequest;
import com.trixpert.beebbeeb.data.response.PurchasingRequestResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.PurchasingRequestService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchasingRequestServiceImpl implements PurchasingRequestService {

    private final ReporterService reporterService;
    private final AuditService auditService;
    private final PurchasingRequestMapper purchasingRequestMapper;
    private final PurchasingRequestRepository purchasingRequestRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final CarInstanceRepository carInstanceRepository;

    public PurchasingRequestServiceImpl(ReporterService reporterService,
                                        AuditService auditService,
                                        PurchasingRequestMapper purchasingRequestMapper,
                                        PurchasingRequestRepository purchasingRequestRepository,
                                        VendorRepository vendorRepository,
                                        CustomerRepository customerRepository,
                                        CarInstanceRepository carInstanceRepository) {
        this.reporterService = reporterService;
        this.auditService = auditService;
        this.purchasingRequestMapper = purchasingRequestMapper;
        this.purchasingRequestRepository = purchasingRequestRepository;
        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.carInstanceRepository = carInstanceRepository;
    }

    @Override
    public ResponseWrapper<Boolean> registerPurchasingRequest(PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {
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
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

            CarInstanceEntity carInstanceEntityRecord = optionalCarInstanceEntity.get();
            PurchasingRequestEntity purchasingRequestEntityRecord = PurchasingRequestEntity.builder()
                    .status(purchasingRequestRegistrationRequest.getStatus())
                    .paymentType(purchasingRequestRegistrationRequest.getPaymentType())
                    .comment(purchasingRequestRegistrationRequest.getComment())
                    .date(date)
                    .active(true)
                    .vendor(vendorEntityRecord)
                    .customer(customerEntityRecord)
                    .carInstance(carInstanceEntityRecord)
                    .build();

            purchasingRequestRepository.save(purchasingRequestEntityRecord);
            return reporterService.reportSuccess("PurchasingRequest Registered Successfully");
        } catch (Exception e) {
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
                throw new NotFoundException("This Purchasing Request doesn't exits !");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            purchasingRequestEntityRecord.setActive(false);
            purchasingRequestRepository.save(purchasingRequestEntityRecord);
            return reporterService.reportSuccess("PurchasingRequest Registered Successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestResponse>> listAllPurchasingRequests(boolean active) {

        try {
            List<PurchasingRequestResponse> purchasingRequestResponseList = new ArrayList<>();
            purchasingRequestRepository.findAllByActive(active).forEach(purchasingRequestEntity -> {
                PurchasingRequestResponse purchasingRequestResponse = PurchasingRequestResponse.builder()
                        .id(purchasingRequestEntity.getId())
                        .paymentType(purchasingRequestEntity.getPaymentType())
                        .status(purchasingRequestEntity.getStatus())
                        .comment(purchasingRequestEntity.getComment())
                        .date(purchasingRequestEntity.getDate())
                        .vendorName(purchasingRequestEntity.getVendor().getName())
                        .customerName(purchasingRequestEntity.getCustomer().getUser().getName())
                        .customerPhone(purchasingRequestEntity.getCustomer().getUser().getPhone())
                        .carBrand(purchasingRequestEntity.getCarInstance().getCar().getBrand().getName())
                        .carModel(purchasingRequestEntity.getCarInstance().getCar().getModel().getName())
                        .active(purchasingRequestEntity.isActive())
                        .build();
                purchasingRequestResponseList.add(purchasingRequestResponse);
            });
            return reporterService.reportSuccess(purchasingRequestResponseList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updatePurchasingRequest(
            PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
            long purchasingRequestId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findById(purchasingRequestId);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("this purchasing request doesn't exist");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord = optionalPurchasingRequestEntity.get();
            if (purchasingRequestRegistrationRequest.getStatus() != null &&
                    !purchasingRequestRegistrationRequest.getStatus().equals(
                            purchasingRequestEntityRecord.getStatus())) {
                purchasingRequestEntityRecord.setStatus(
                        purchasingRequestRegistrationRequest.getStatus());
            }

            if (purchasingRequestRegistrationRequest.getPaymentType() != null &&
                    !purchasingRequestRegistrationRequest.getPaymentType().equals(
                            purchasingRequestEntityRecord.getPaymentType())) {
                purchasingRequestEntityRecord.setPaymentType(
                        purchasingRequestRegistrationRequest.getPaymentType());
            }
            if (purchasingRequestRegistrationRequest.getComment() != null &&
                    !purchasingRequestRegistrationRequest.getComment().equals(
                            purchasingRequestEntityRecord.getComment())) {
                purchasingRequestEntityRecord.setComment(
                        purchasingRequestRegistrationRequest.getComment());
            }
            if (purchasingRequestRegistrationRequest.getDate() != null &&
                    !purchasingRequestRegistrationRequest.getDate().equals(
                            purchasingRequestEntityRecord.getDate())) {
                purchasingRequestEntityRecord.setDate(
                        purchasingRequestRegistrationRequest.getDate());
            }
            if (vendorRepository.findById(purchasingRequestRegistrationRequest.getVendorId()) != null &&
                    vendorRepository.findById(purchasingRequestRegistrationRequest.getVendorId())
                            .equals(purchasingRequestEntityRecord.getVendor())) {
                purchasingRequestEntityRecord.setVendor(
                        vendorRepository.getOne(purchasingRequestRegistrationRequest.getVendorId()));
            }
            if (customerRepository.findById(purchasingRequestRegistrationRequest.getCustomerId()) != null &&
                    customerRepository.findById(purchasingRequestRegistrationRequest.getCustomerId())
                            .equals(purchasingRequestEntityRecord.getCustomer())) {
                purchasingRequestEntityRecord.setCustomer(
                        customerRepository.getOne(purchasingRequestRegistrationRequest.getCustomerId()));

            }
            if (carInstanceRepository.findById(purchasingRequestRegistrationRequest.getCarInstanceId()) != null &&
                    carInstanceRepository.findById(purchasingRequestRegistrationRequest.getCarInstanceId())
                            .equals(purchasingRequestEntityRecord.getCarInstance())) {
                purchasingRequestEntityRecord.setCarInstance(
                        carInstanceRepository.getOne(purchasingRequestRegistrationRequest.getCarInstanceId())
                );
            }
            purchasingRequestRepository.save(purchasingRequestEntityRecord);
            return reporterService.reportSuccess("Purchasing Request updated successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Override
    public ResponseWrapper<Boolean> updateStatusForPurchasingRequest(long purchasingRequestId,
                                                                     String status,
                                                                     String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findById(purchasingRequestId);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("this purchasing request doesn't exist");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            if (status != null && !status.equals(purchasingRequestEntityRecord.getStatus())) {
                purchasingRequestEntityRecord.setStatus(status);
            }

            purchasingRequestRepository.save(purchasingRequestEntityRecord);
            return reporterService.reportSuccess("Status of Purchasing Request updated successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<PurchasingRequestDTO> getPurchasingRequest(long purchasingRequestId) {
        try {
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findById(
                            purchasingRequestId);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("This Purchasing Request doesn't exits !");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            return reporterService.reportSuccess(purchasingRequestMapper.
                    convertToDTO(purchasingRequestEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForVendor(
            boolean active, long vendorId) {
        try {
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if (!optionalVendorEntity.isPresent()) {
                throw new NotFoundException("This Vendor doesn't exist");
            }
            VendorEntity vendorEntityRecord = optionalVendorEntity.get();

            List<PurchasingRequestDTO> purchasingRequestDTOList = new ArrayList<>();
            purchasingRequestRepository.findAllByActiveAndVendor(active, vendorEntityRecord)
                    .forEach(purchasingRequestEntity ->
                            purchasingRequestDTOList.add(purchasingRequestMapper.
                                    convertToDTO(purchasingRequestEntity)));
            return reporterService.reportSuccess(purchasingRequestDTOList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<PurchasingRequestDTO> getPurchasingRequestForVendor(
            long purchasingRequestId, long vendorId) {
        try {
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if (!optionalVendorEntity.isPresent()) {
                throw new NotFoundException("This Vendor doesn't exist");
            }
            VendorEntity vendorEntityRecord = optionalVendorEntity.get();
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findByIdAndVendor(
                            purchasingRequestId, vendorEntityRecord);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("This Purchasing Request doesn't exits !");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            return reporterService.reportSuccess(purchasingRequestMapper.
                    convertToDTO(purchasingRequestEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCustomer(
            boolean active, long customerId) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("This Customer doesn't exist");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();

            List<PurchasingRequestDTO> purchasingRequestDTOList = new ArrayList<>();
            purchasingRequestRepository.findAllByActiveAndCustomer(active, customerEntityRecord)
                    .forEach(purchasingRequestEntity ->
                            purchasingRequestDTOList.add(purchasingRequestMapper.
                                    convertToDTO(purchasingRequestEntity)));
            return reporterService.reportSuccess(purchasingRequestDTOList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<PurchasingRequestDTO> getPurchasingRequestForCustomer(
            long purchasingRequestId, long customerId) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity =
                    customerRepository.findById(customerId);
            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("This Customer doesn't exist");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findByIdAndCustomer(
                            purchasingRequestId, customerEntityRecord);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("This Purchasing Request doesn't exits !");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            return reporterService.reportSuccess(purchasingRequestMapper.
                    convertToDTO(purchasingRequestEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCar(
            boolean active, long carId) {
        try {
            Optional<CarInstanceEntity> optionalCarInstanceEntity =
                    carInstanceRepository.findById(carId);
            if (!optionalCarInstanceEntity.isPresent()) {
                throw new NotFoundException("This Car Instance doesn't exist");
            }
            CarInstanceEntity carInstanceEntityRecord = optionalCarInstanceEntity.get();

            List<PurchasingRequestDTO> purchasingRequestDTOList = new ArrayList<>();
            purchasingRequestRepository.findAllByActiveAndCarInstance(
                    active, carInstanceEntityRecord).forEach(purchasingRequestEntity ->
                    purchasingRequestDTOList.add(purchasingRequestMapper.
                            convertToDTO(purchasingRequestEntity)));
            return reporterService.reportSuccess(purchasingRequestDTOList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<PurchasingRequestDTO> getPurchasingRequestForCar(
            long purchasingRequestId, long carId) {
        try {
            Optional<CarInstanceEntity> optionalCarInstanceEntity =
                    carInstanceRepository.findById(carId);
            if (!optionalCarInstanceEntity.isPresent()) {
                throw new NotFoundException("This Car Instance doesn't exist");
            }
            CarInstanceEntity carInstanceEntityRecord = optionalCarInstanceEntity.get();
            Optional<PurchasingRequestEntity> optionalPurchasingRequestEntity =
                    purchasingRequestRepository.findByIdAndCarInstance(
                            purchasingRequestId, carInstanceEntityRecord);
            if (!optionalPurchasingRequestEntity.isPresent()) {
                throw new NotFoundException("This Purchasing Request doesn't exits !");
            }
            PurchasingRequestEntity purchasingRequestEntityRecord =
                    optionalPurchasingRequestEntity.get();
            return reporterService.reportSuccess(purchasingRequestMapper.
                    convertToDTO(purchasingRequestEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
