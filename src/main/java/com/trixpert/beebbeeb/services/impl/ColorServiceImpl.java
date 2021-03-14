package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.ColorEntity;
import com.trixpert.beebbeeb.data.entites.ParentColorEntity;
import com.trixpert.beebbeeb.data.mappers.CarMapper;
import com.trixpert.beebbeeb.data.mappers.ColorMapper;
import com.trixpert.beebbeeb.data.repositories.ColorRepository;
import com.trixpert.beebbeeb.data.repositories.ParentColorRepository;
import com.trixpert.beebbeeb.data.request.ColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ColorDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ColorService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ParentColorRepository parentColorRepository;

    private final ColorMapper colorMapper;
    private final CarMapper carMapper;

    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;



    public ColorServiceImpl(ColorRepository colorRepository,
                            ParentColorRepository parentColorRepository,
                            ColorMapper colorMapper,
                            CarMapper carMapper,
                            ReporterService reporterService,
                            UserService userService,
                            AuditService auditService) {
      
        this.colorRepository = colorRepository;
        this.parentColorRepository = parentColorRepository;
        this.colorMapper = colorMapper;
        this.carMapper = carMapper;
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
    }

    @Override
    public ResponseWrapper<Boolean> registerColor(ColorRegistrationRequest colorRegistrationRequest
                          ,  String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ParentColorEntity> optionalParentColorEntity = parentColorRepository.findById(colorRegistrationRequest.getParentColorId());
            if(!optionalParentColorEntity.isPresent()){
                throw new NotFoundException("Parent Color entity not found");
            }
            ParentColorEntity parentColorRecord = optionalParentColorEntity.get() ;

            ColorEntity colorEntityRecord = ColorEntity.builder()
                    .name(colorRegistrationRequest.getName())
                    .code(colorRegistrationRequest.getCode())
                    .active(colorRegistrationRequest.isActive())
                    .parentColor(parentColorRecord)
                    .build();
            colorRepository.save(colorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Inserting new Color entity " + colorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("color registered successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteColor(long colorId , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ColorEntity> optionalColorEntity = colorRepository.findById(colorId);
            if (!optionalColorEntity.isPresent()) {
                throw new NotFoundException("This Color does not exist");
            }
            ColorEntity colorEntityRecord = optionalColorEntity.get();
            colorEntityRecord.setActive(false);
            colorRepository.save(colorEntityRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new Color entity " + colorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);


            return reporterService.reportSuccess("Color Deleted Successful ID :".concat(Long.toString(colorId)));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateColor(ColorRegistrationRequest colorRegistrationRequest,
                                                long colorId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ColorEntity> optionalColorEntity = colorRepository.findById(colorId);
            if (!optionalColorEntity.isPresent()) {
                throw new NotFoundException("This Color Was Not Found");
            }
            ColorEntity colorEntityRecord = optionalColorEntity.get();

            if (colorRegistrationRequest.getName() != null
                    && !colorRegistrationRequest.getName().equals(colorEntityRecord.getName())) {
                colorEntityRecord.setName(colorRegistrationRequest.getName());
            }
            if (colorRegistrationRequest.getCode() != null
                    && !colorRegistrationRequest.getCode().equals(colorEntityRecord.getCode())) {
                colorEntityRecord.setCode(colorRegistrationRequest.getCode());
            }
            if(colorRegistrationRequest.getParentColorId() != -1
                    && colorRegistrationRequest.getParentColorId() != colorEntityRecord.getParentColor().getId()){
                Optional<ParentColorEntity> optionalParentColorEntity = parentColorRepository.findById(colorRegistrationRequest.getParentColorId());
                if(!optionalParentColorEntity.isPresent()){
                    throw new NotFoundException("Parent Color entity not found");
                }
                colorEntityRecord.setParentColor(optionalParentColorEntity.get());
            }

            colorRepository.save(colorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new Color entity " + colorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<ColorDTO>> getAllColors(boolean active) {
        try {
            List<ColorDTO> colorList = new ArrayList<>();
            colorRepository.findAllByActive(active).forEach(color ->
                    colorList.add(colorMapper.convertToDTO(color))
            );
            return reporterService.reportSuccess(colorList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CarDTO>> listCarsForColor(boolean active, long colorId){
        try{
            List<CarDTO> listCars = new ArrayList<>();
            Optional<ColorEntity> optionalColorEntity = colorRepository.findById(colorId);
            if(!optionalColorEntity.isPresent()){
                throw new NotFoundException("Color entity not found");
            }
            optionalColorEntity.get().getCars().forEach(car ->
                listCars.add(carMapper.convertToDTO(car))
            );
            return reporterService.reportSuccess(listCars);
        }
        catch (Exception e){
            return reporterService.reportError(e);
        }
    }
}
