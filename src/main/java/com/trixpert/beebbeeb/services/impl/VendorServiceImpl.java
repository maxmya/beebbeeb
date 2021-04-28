package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.BrandMapper;
import com.trixpert.beebbeeb.data.mappers.UserMapper;
import com.trixpert.beebbeeb.data.mappers.VendorMapper;
import com.trixpert.beebbeeb.data.repositories.*;
import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.*;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    private final Logger logger = LoggerFactory.getLogger("VendorService");

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final RolesRepository rolesRepository;

    private final ReporterService reporterService;
    private final UserService userService;
    private final CarInstanceRepository carInstanceRepository;

    private final BrandRepository brandRepository;
    private final HomeTelephoneRepository homeTelephoneRepository;
    private final SalesManRepository salesManRepository;
    private final BrandMapper brandMapper;

    private final UserMapper userMapper;
    private final VendorMapper vendorMapper;

    private final AuditService auditService;
    private final CloudStorageService cloudStorageService;


    public VendorServiceImpl(UserRepository userRepository,
                             VendorRepository vendorRepository,
                             RolesRepository rolesRepository,
                             ReporterService reporterService,
                             UserService userService,
                             CarInstanceRepository carInstanceRepository, BrandRepository brandRepository, HomeTelephoneRepository homeTelephoneRepository, SalesManRepository salesManRepository, BrandMapper brandMapper, UserMapper userMapper,
                             VendorMapper vendorMapper, AuditService auditService, CloudStorageService cloudStorageService) {
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.rolesRepository = rolesRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.carInstanceRepository = carInstanceRepository;
        this.brandRepository = brandRepository;
        this.homeTelephoneRepository = homeTelephoneRepository;
        this.salesManRepository = salesManRepository;
        this.brandMapper = brandMapper;
        this.userMapper = userMapper;
        this.vendorMapper = vendorMapper;
        this.auditService = auditService;
        this.cloudStorageService = cloudStorageService;
    }

    @Override
    public ResponseWrapper<Boolean> registerVendor(VendorRegistrationRequest vendorRegistrationRequest,
                                                   MultipartFile generalManagerIdDocumentFace,
                                                   MultipartFile generalManagerIdDocumentBack,
                                                   MultipartFile accountManagerIdDocumentFace,
                                                   MultipartFile accountManagerIdDocumentBack,
                                                   MultipartFile taxRecordDocument,
                                                   MultipartFile commercialRegisterDocument,
                                                   MultipartFile contractDocument, String authHeader){

        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord ="";


        if (userRepository.existsByEmail(vendorRegistrationRequest.getEmail())) {
            logger.error("attempt to register existed email ".concat(vendorRegistrationRequest.getEmail()));
            return new ResponseWrapper<>(false,
                    "email already registered", HttpStatus.BAD_REQUEST, false);
        }

        try {
            Optional<RolesEntity> vendorRole = rolesRepository.findByName(Roles.ROLE_VENDOR);
            if (!vendorRole.isPresent()) {
                throw new NotFoundException("Vendor Roles Not Found");
            }

            UserEntity userEntityRecord = userService.registerUser(
                    vendorRegistrationRequest.getEmail(),
                    vendorRole.get(),
                    vendorRegistrationRequest,
                    logoUrlRecord,
                    false
            ).getData();

            /**********Uploading Files**********/
            String generalManagerIdDocumentFaceUrl = cloudStorageService.uploadFile(generalManagerIdDocumentFace);
            String generalManagerIdDocumentBackUrl = cloudStorageService.uploadFile(generalManagerIdDocumentBack);
            String accountManagerIdDocumentFaceUrl = cloudStorageService.uploadFile(accountManagerIdDocumentFace);
            String accountManagerIdDocumentBackUrl = cloudStorageService.uploadFile(accountManagerIdDocumentBack);
            String taxRecordDocumentUrl = cloudStorageService.uploadFile(taxRecordDocument);
            String commercialRegisterDocumentUrl = cloudStorageService.uploadFile(commercialRegisterDocument);
            String contractDocumentUrl = cloudStorageService.uploadFile(contractDocument);

            /*********List of brands for agent*******/

            List<BrandEntity> brandsAgent = new ArrayList<>();
            if(vendorRegistrationRequest.getBrandsAgent()!=null){
                for(BrandDTO brandDTO : vendorRegistrationRequest.getBrandsAgent()){
                    Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(brandDTO.getId());
                    if(!optionalBrandEntity.isPresent()){
                        throw new NotFoundException("This Brand is not exist!");
                    }
                    brandsAgent.add(optionalBrandEntity.get());
                }
            }


            /*********List of brands for Distributor*******/
            List<BrandEntity> brandsDistributor = new ArrayList<>();
            if(vendorRegistrationRequest.getBrandsDistributor()!=null){
                for(BrandDTO brandDTO : vendorRegistrationRequest.getBrandsDistributor()){
                    Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(brandDTO.getId());
                    if(!optionalBrandEntity.isPresent()){
                        throw new NotFoundException("This Brand is not exist!");
                    }
                    brandsDistributor.add(optionalBrandEntity.get());
                }
            }


            /******Building HomePhone Entities****/
            List<HomeTelephoneEntity> homeTelephoneEntities  = new ArrayList<>();
            if(vendorRegistrationRequest.getTaxRecordNumber()!=null){
                for(String homeTelephone : vendorRegistrationRequest.getHomeTelephones()){
                    HomeTelephoneEntity homeTelephoneEntity =HomeTelephoneEntity.builder()
                            .telephoneNumber(homeTelephone)
                            .active(true).build();
                    homeTelephoneEntities.add(homeTelephoneEntity);
                    homeTelephoneRepository.save(homeTelephoneEntity);
                }
            }


            /******Building SalesMan Entities****/
            List<SalesManEntity> salesManEntities  = new ArrayList<>();
            if(vendorRegistrationRequest.getSalesMen()!=null){
                for(SalesManDTO salesManDTO : vendorRegistrationRequest.getSalesMen()){
                    SalesManEntity salesManEntity =SalesManEntity.builder()
                            .name(salesManDTO.getName())
                            .phone(salesManDTO.getPhone())
                            .active(true).build();
                    salesManEntities.add(salesManEntity);
                    salesManRepository.save(salesManEntity);
                }
            }


            VendorEntity vendorEntityRecord = VendorEntity.builder()
                    .generalManagerIdDocumentFaceUrl(generalManagerIdDocumentFaceUrl)
                    .generalManagerIdDocumentBackUrl(generalManagerIdDocumentBackUrl)
                    .accountManagerIdDocumentFaceUrl(accountManagerIdDocumentFaceUrl)
                    .accountManagerIdDocumentBackUrl(accountManagerIdDocumentBackUrl)
                    .taxRecordDocumentUrl(taxRecordDocumentUrl)
                    .commercialRegisterDocumentUrl(commercialRegisterDocumentUrl)
                    .contractDocumentUrl(contractDocumentUrl)
                    .bankAccountNumber(vendorRegistrationRequest.getBankAccountNumber())
                    .taxRecordNumber(vendorRegistrationRequest.getTaxRecordNumber())
                    .commercialRegisterNumber(vendorRegistrationRequest.getCommercialRegisterNumber())
                    .importer(vendorRegistrationRequest.isImporter())
                    .homeDelivery(vendorRegistrationRequest.isHomeDelivery())
                    .salesPerMonth(vendorRegistrationRequest.getSalesPerMonth())
                    .brandsAgent(brandsAgent)
                    .brandDistributor(brandsDistributor)
                    .name(vendorRegistrationRequest.getVendorName())
                    .manager(userEntityRecord)
                    .mainAddress(vendorRegistrationRequest.getMainAddress())
                    .generalManagerName(vendorRegistrationRequest.getGmName())
                    .generalManagerPhone(vendorRegistrationRequest.getGmPhone())
                    .accountManagerName(vendorRegistrationRequest.getAccManagerName())
                    .accountManagerPhone(vendorRegistrationRequest.getAccManagerPhone())
                    .salesMen(salesManEntities)
                    .homeTelephones(homeTelephoneEntities)
                    .active(true)
                    .build();

            vendorRepository.save(vendorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new vendor entity " + vendorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            logger.info("new vendor registered ".concat(vendorRegistrationRequest.getEmail()));
            return reporterService.reportSuccess("vendor registered successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }



    @Override
    public ResponseWrapper<List<VendorDTO>> getAllVendors(boolean active) {
        try {
            //Creating ArrayList for store all vendors on it
            List<VendorDTO> allVendors = new ArrayList<>();
            //Looping On All Vendors on database one by one
            vendorRepository.findAllByActive(active).forEach(vendor -> {
                UserDTO userDTO = userMapper.convertToDTO(vendor.getManager());
                // For Every Vendor Create new Object from vendorDTO and Send needed data
                // From vendorEntity to vendorDTO by passing this data on constructor
                VendorDTO vendorDTO = vendorMapper.convertToDTO(vendor);
                vendorDTO.setManager(userDTO);
                //Append new vendorDTO Object in the list of all vendors
                allVendors.add(vendorDTO);
            });

            return reporterService.reportSuccess(allVendors);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteVendor(long vendorId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorId);
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            } else {
                VendorEntity vendorRecord = optionalVendorRecord.get();
                vendorRecord.setActive(false);
                vendorRepository.save(vendorRecord);

                AuditDTO auditDTO =
                        AuditDTO.builder()
                                .user(userService.getUserByUsername(username))
                                .action(AuditActions.DELETE)
                                .description("Deleting new vendor entity " + vendorRecord.toString())
                                .timestamp(LocalDateTime.now())
                                .build();

                auditService.logAudit(auditDTO);

                return reporterService.reportSuccess("Vendor soft deleted(deActivated) successfully");
            }
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<PurchasingRequestResponse>> listPurchasingRequestsForVendor(boolean active, String authHeader){
        try{
            List<PurchasingRequestResponse> purchasingRequestResponseList = new ArrayList<>();
            String username = auditService.getUsernameForAudit(authHeader);

            Optional<UserEntity> optionalUserEntityRecord = userRepository.findByEmail(username);
            if(!optionalUserEntityRecord.isPresent()){
                throw new NotFoundException("User Entity not found");
            }
            UserEntity userEntityRecord = optionalUserEntityRecord.get();

            Optional<VendorEntity> optionalVendorEntityRecord = vendorRepository.findAllByManager(userEntityRecord);
            if(!optionalVendorEntityRecord.isPresent()){
                throw new NotFoundException("This user doesn't represent a vendor");
            }

            optionalVendorEntityRecord.get().getPurchasingRequests().forEach(purchasingRequestEntity -> {
                PurchasingRequestResponse purchasingRequestResponse = PurchasingRequestResponse.builder()
                        .id(purchasingRequestEntity.getId())
                        .paymentType(purchasingRequestEntity.getPaymentType())
                        .status(purchasingRequestEntity.getStatus())
                        .comment(purchasingRequestEntity.getComment())
                        .date(purchasingRequestEntity.getDate())
                        .customerId(purchasingRequestEntity.getCustomer().getId())
                        .customerName(purchasingRequestEntity.getCustomer().getUser().getName())
                        .carBrand(purchasingRequestEntity.getCarInstance().getCar().getBrand().getName())
                        .carModel(purchasingRequestEntity.getCarInstance().getCar().getModel().getName())
                        .active(purchasingRequestEntity.isActive())
                        .build();

                purchasingRequestResponseList.add(purchasingRequestResponse);
            });
            return reporterService.reportSuccess(purchasingRequestResponseList);
        } catch(Exception e){
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateVendor(VendorRegistrationRequest vendorRegistrationRequest
            ,long vendorId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorId);
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            }

            VendorEntity vendorRecord = optionalVendorRecord.get();

            if (vendorRegistrationRequest.getVendorName() != null) {
                vendorRecord.setName(vendorRegistrationRequest.getVendorName());
            }

            if (vendorRegistrationRequest.getMainAddress() != null) {
                vendorRecord.setMainAddress(vendorRegistrationRequest.getMainAddress());
            }

            if (vendorRegistrationRequest.getGmName() != null) {
                userService.updateUser(userMapper.convertToDTO(vendorRecord.getManager()));
            }
            if (vendorRegistrationRequest.getGmPhone() != null) {
                vendorRecord.setGeneralManagerPhone(vendorRegistrationRequest.getGmPhone());
            }
            if (vendorRegistrationRequest.getAccManagerName() != null) {
                vendorRecord.setAccountManagerName(vendorRegistrationRequest.getAccManagerName());
            }
            if (vendorRegistrationRequest.getAccManagerPhone() != null) {
                vendorRecord.setAccountManagerPhone(vendorRegistrationRequest.getAccManagerPhone());
            }
            vendorRepository.save(vendorRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new vendor entity " + vendorRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Vendor updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<VendorDTO> getVendor(long vendorId) {
        try {
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if(!optionalVendorEntity.isPresent()){
                throw new NotFoundException("Vendor doesn't exist");
            }
            VendorEntity vendorEntityRecord = optionalVendorEntity.get();
            return reporterService.reportSuccess(vendorMapper.convertToDTO(vendorEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }

    @Override
    public ResponseWrapper<Boolean> addVendorPhoto(long vendorId, MultipartFile vendorPhoto) {
        try {
            Optional<VendorEntity> vendorEntityOptional = vendorRepository.findById(vendorId);
            if(!vendorEntityOptional.isPresent()){
                throw new NotFoundException("This vendor not exist");
            }
            VendorEntity vendorEntityRecord = vendorEntityOptional.get();
            String photoUrlRecord = cloudStorageService.uploadFile(vendorPhoto);
            Optional<UserEntity> userEntityOptional = userRepository.findById(vendorEntityRecord.getManager().getId());
            if(!userEntityOptional.isPresent()){
                throw new NotFoundException("This User not exist");
            }
            UserEntity userEntityRecord = userEntityOptional.get();
            userEntityRecord.setPicUrl(photoUrlRecord);
            userRepository.save(userEntityRecord);
            vendorRepository.save(vendorEntityRecord);
            return reporterService.reportSuccess("Vendor's Photo Added !");

        }catch (Exception e){
           return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> registerVendorWorkingDays(long vendorId, String wokringTimsRegistrationRequest) {

        try {
            Optional<VendorEntity> vendorEntityOptional = vendorRepository.findById(vendorId);
            if(!vendorEntityOptional.isPresent()){
                throw new NotFoundException("This vendor not exist");
            }
            VendorEntity vendorEntityRecord = vendorEntityOptional.get();
            vendorEntityRecord.setWorkingTime(wokringTimsRegistrationRequest);
            vendorRepository.save(vendorEntityRecord);
            return reporterService.reportSuccess("Vendor's Photo Added !");

        }catch (Exception e){
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<VendorResponse> getVendorDetails(long vendorId) {
//        try {
//            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
//            if(!optionalVendorEntity.isPresent()){
//                throw new NotFoundException("This Vendor with id "+Long.toString(vendorId)+" Is not Exist !");
//            }
//
//            VendorEntity vendorEntityRecord = optionalVendorEntity.get();
//            LinkableImage linkableImage = LinkableImage.builder()
//                    .url(vendorEntityRecord.getManager().getPicUrl())
//                    .build();
//            List<CarInstanceEntity> carInstanceEntityList = carInstanceRepository.findAllByVendorAndActive(vendorEntityRecord,true);
//            System.out.println();
//            VendorResponse vendorResponse = new VendorResponse();
////            VendorResponse vendorResponse =VendorResponse.builder()
////                    .name(vendorEntityRecord.getName())
////                    .address(vendorEntityRecord.getMainAddress())
////                    .importer(vendorEntityRecord.isImporter())
////                    .homeDelivery(vendorEntityRecord.isHomeDelivery())
////                    .logo(linkableImage)
////                    .workingHours(vendorEntityRecord.getWorkingTime()
////                    .build();
//
//            return reporterService.reportSuccess(vendorResponse);
//        }catch (Exception e){
//            return reporterService.reportError(e);
//        }
        return null;
    }
}
