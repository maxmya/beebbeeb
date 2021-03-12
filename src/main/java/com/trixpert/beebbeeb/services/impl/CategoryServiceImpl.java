package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.CategoryEntity;
import com.trixpert.beebbeeb.data.mappers.CarMapper;
import com.trixpert.beebbeeb.data.mappers.CategoryMapper;
import com.trixpert.beebbeeb.data.mappers.TypeMapper;
import com.trixpert.beebbeeb.data.repositories.CategoryRepository;
import com.trixpert.beebbeeb.data.request.CategoryRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.CategoryDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.CategoryService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ReporterService reporterService;

    private final CategoryMapper categoryMapper;
    private final CarMapper carMapper;
    private final TypeMapper typeMapper;

    private final UserService userService;
    private final AuditService auditService;


    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ReporterService reporterService,
                               CategoryMapper categoryMapper,
                               CarMapper carMapper,
                               TypeMapper typeMapper,
                               UserService userService,
                               AuditService auditService) {
        this.categoryRepository = categoryRepository;
        this.reporterService = reporterService;
        this.categoryMapper = categoryMapper;
        this.carMapper = carMapper;
        this.typeMapper = typeMapper;
        this.userService = userService;
        this.auditService = auditService;
    }


    @Override
    public ResponseWrapper<Boolean> registerCategory(CategoryRegistrationRequest categoryRegistrationRequest,
                                                     String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            CategoryEntity categoryEntityRecord = CategoryEntity.builder()
                    .name(categoryRegistrationRequest.getName())
                    .type(typeMapper.convertToEntity(categoryRegistrationRequest.getType()))
                    .active(true)
                    .build();
            categoryRepository.save(categoryEntityRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("adding new Category entity " + categoryEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("Category Registered Successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteCategory(long categoryId , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);
            if (!categoryEntityOptional.isPresent()) {
                throw new NotFoundException("This Category Already Deleted or not exist !");
            }
            CategoryEntity categoryEntityRecord = categoryEntityOptional.get();
            categoryEntityRecord.setActive(false);
            categoryRepository.save(categoryEntityRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("deleting new Category entity " + categoryEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("Category Deleted Successful ID : ".concat(Long.toString(categoryId)));

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateCategory(CategoryDTO categoryDTO , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryDTO.getId());
            if(!optionalCategoryEntity.isPresent()){
                throw new NotFoundException("Category Entity Not Found!");
            }
            CategoryEntity categoryEntityRecord = optionalCategoryEntity.get();
            if(categoryDTO.getName()!=null && !categoryDTO.getName().equals(categoryEntityRecord.getName())){
                categoryEntityRecord.setName(categoryDTO.getName());
            }
            categoryRepository.save(categoryEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new Category entity " + categoryEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("Category ID : ".concat(Long.toString(categoryDTO.getId())).concat(" is Updated Successfully ! "));
        }catch (Exception e){
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CategoryDTO>> getAllCategories(boolean active) {
        try {
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            categoryRepository.findAllByActive(active).forEach(category -> {
                categoryDTOList.add(categoryMapper.convertToDTO(category));
            });
            return reporterService.reportSuccess(categoryDTOList);
        }catch (Exception e){
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CarDTO>> listCarsForCategory(boolean active, long categoryId){
        try{
            List<CarDTO> listCars = new ArrayList<>();
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
            if(!optionalCategoryEntity.isPresent()){
                throw new NotFoundException("Category entity not found");
            }
            optionalCategoryEntity.get().getCars().forEach(car ->
                listCars.add(carMapper.convertToDTO(car))
            );
            return reporterService.reportSuccess(listCars);
        }
        catch(Exception e){
            return reporterService.reportError(e);
        }
    }
}
