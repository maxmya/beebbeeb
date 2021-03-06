package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.mappers.BrandMapper;
import com.trixpert.beebbeeb.data.repositories.BrandRepository;
import com.trixpert.beebbeeb.data.request.BrandRegisterRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final CloudStorageService cloudStorageService;
    private final ReporterService reporterService;
    private final AuditService auditService;
    private final UserService userService;

    private final BrandMapper brandMapper;


    public BrandServiceImpl(BrandRepository brandRepository,
                            CloudStorageService cloudStorageService,
                            ReporterService reporterService,
                            AuditService auditService,
                            UserService userService, BrandMapper brandMapper) {

        this.brandRepository = brandRepository;
        this.cloudStorageService = cloudStorageService;
        this.reporterService = reporterService;
        this.auditService = auditService;
        this.userService = userService;
        this.brandMapper = brandMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerBrand(MultipartFile logoFile,
                                                  BrandRegisterRequest brandRegisterRequest,
                                                  String authHeader
    ) throws IOException {
        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord = cloudStorageService.uploadFile(logoFile);
        try {
            BrandEntity brandEntityRecord = BrandEntity.builder()
                    .name(brandRegisterRequest.getName())
                    .origin(brandRegisterRequest.getOrigin())
                    .logoUrl(logoUrlRecord)
                    .englishName(brandRegisterRequest.getEnglishName())
                    .active(true)
                    .build();
            brandRepository.save(brandEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Insert new brand entity " + brandEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("vendor registered successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteBrand(long brandId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(brandId);
            if (!optionalBrandEntity.isPresent()) {
                throw new NotFoundException("This Brand does not exist");
            }
            BrandEntity brandEntityRecord = optionalBrandEntity.get();
            // was missing from the one who created it
            brandEntityRecord.setActive(false);
            brandRepository.save(brandEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new brand entity " + brandEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("Brand Deleted Successful ID :".concat(Long.toString(brandId)));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateBrand(MultipartFile logoFile,
                                                BrandRegisterRequest brandRegisterRequest, long brandId,
                                                String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(brandId);
            if (!optionalBrandEntity.isPresent()) {
                throw new NotFoundException("This Brand Was Not Found");
            }
            BrandEntity brandEntityRecord = optionalBrandEntity.get();
            if (brandRegisterRequest.getName() != null && !brandRegisterRequest.getName()
                    .equals(brandEntityRecord.getName())) {
                brandEntityRecord.setName(brandRegisterRequest.getName());
            }
            if (brandRegisterRequest.getEnglishName() != null &&
                    !brandRegisterRequest.getEnglishName().equals(brandEntityRecord.getEnglishName())) {
                brandEntityRecord.setEnglishName(brandRegisterRequest.getEnglishName());
            }
            if (brandRegisterRequest.getOrigin() != null &&
                    !brandRegisterRequest.getOrigin().equals(brandEntityRecord.getOrigin())) {
                brandEntityRecord.setOrigin(brandRegisterRequest.getOrigin());
            }
            if (logoFile != null) {
                String logoUrlRecord = cloudStorageService.uploadFile(logoFile);
                brandEntityRecord.setLogoUrl(logoUrlRecord);
            }
            if (logoFile == null) {
                brandEntityRecord.setLogoUrl(brandEntityRecord.getLogoUrl());
            }
            brandRepository.save(brandEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new brand entity " + brandEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<BrandDTO>> getAllBrands(boolean active) {
        try {
            List<BrandDTO> brandList = new ArrayList<>();
            brandRepository.findAllByActive(active).forEach(brand ->
                    brandList.add(brandMapper.convertToDTO(brand))
            );
            return reporterService.reportSuccess(brandList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<BrandDTO> getBrand(long brandId) {
        try {
            Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(brandId);

            if (!optionalBrandEntity.isPresent()) {
                throw new NotFoundException("This Brand does not exist");
            }
            BrandEntity brandEntityRecord = optionalBrandEntity.get();

            return reporterService.reportSuccess(brandMapper.convertToDTO(brandEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
