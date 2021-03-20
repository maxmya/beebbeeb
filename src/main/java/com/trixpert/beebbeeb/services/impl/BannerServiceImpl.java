package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.BannerEntity;
import com.trixpert.beebbeeb.data.mappers.BannerMapper;
import com.trixpert.beebbeeb.data.mappers.TypeMapper;
import com.trixpert.beebbeeb.data.repositories.BannerRepository;
import com.trixpert.beebbeeb.data.repositories.TypeRepository;
import com.trixpert.beebbeeb.data.request.BannerRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.BannerDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {
    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;
    private final CloudStorageService cloudStorageService;
    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;
    private final PhotoService photoService;
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    public BannerServiceImpl(ReporterService reporterService, UserService userService,
                             AuditService auditService,
                             CloudStorageService cloudStorageService, TypeRepository typeRepository,
                             TypeMapper typeMapper, PhotoService photoService, BannerRepository bannerRepository, BannerMapper bannerMapper) {
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
        this.cloudStorageService = cloudStorageService;
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
        this.photoService = photoService;
        this.bannerRepository = bannerRepository;
        this.bannerMapper = bannerMapper;
    }


    @Override
    public ResponseWrapper<Boolean> registerBanner(
            BannerRegistrationRequest bannerRegistrationRequest,
            MultipartFile logoFile, String authHeader) throws IOException {
        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord = cloudStorageService.uploadFile(logoFile);
        try {
            BannerEntity bannerEntityRecord = BannerEntity.builder()
                    .name(bannerRegistrationRequest.getName())
                    .main(bannerRegistrationRequest.isMain())
                    .build();

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new banner entity " + bannerEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Banner added succesfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteBanner(long bannerId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BannerEntity> optionalBannerEntity = bannerRepository.findById(bannerId);
            if (!optionalBannerEntity.isPresent()) {
                throw new NotFoundException("Banner Id deosn't exist");
            }
            BannerEntity bannerEntityRecord = optionalBannerEntity.get();
            bannerEntityRecord.setActive(false);
            bannerRepository.save(bannerEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new banner entity " + bannerEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Banner deleted successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateBanner(BannerRegistrationRequest bannerRegistrationRequest,
                                                 MultipartFile logoFile, long bannerId,
                                                 String authHeader) throws IOException {
        String username = auditService.getUsernameForAudit(authHeader);
        try {
            Optional<BannerEntity> optionalBannerEntity = bannerRepository.findById(bannerId);
            if (!optionalBannerEntity.isPresent()) {
                throw new NotFoundException("Banner Id doesn't exist");
            }
            BannerEntity bannerEntityRecord = optionalBannerEntity.get();
            if (bannerRegistrationRequest.getName() != null && !bannerRegistrationRequest.getName().
                    equals(bannerEntityRecord.getName())) {
                bannerEntityRecord.setName(bannerRegistrationRequest.getName());
            }
            if (bannerRegistrationRequest.isMain() != bannerEntityRecord.isMain()) {
                bannerEntityRecord.setMain(bannerEntityRecord.isMain());
            }
            bannerRepository.save(bannerEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new banner entity " + bannerEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Banner deleted successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }


    @Override
    public ResponseWrapper<BannerDTO> getBanner(long bannerId) {
        try {
            Optional<BannerEntity> optionalBannerEntity = bannerRepository.findById(bannerId);
            if (!optionalBannerEntity.isPresent()) {
                throw new NotFoundException("Banner Id deosn't exist");
            }
            BannerEntity bannerEntityRecord = optionalBannerEntity.get();


            return reporterService.reportSuccess(bannerMapper.convertToDTO(bannerEntityRecord));

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<BannerDTO>> listAllBanners(boolean active) {
        try {
        List<BannerDTO> bannerList = new ArrayList<>();
        bannerRepository.findAllByActive(active).forEach(banner ->
                bannerList.add(bannerMapper.convertToDTO(banner)));

        return reporterService.reportSuccess(bannerList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
