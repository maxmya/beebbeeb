package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.EmployeeEntity;
import com.trixpert.beebbeeb.data.to.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final UserMapper userMapper;
    private final BranchMapper branchMapper;

    public EmployeeMapper(UserMapper userMapper, BranchMapper branchMapper) {
        this.userMapper = userMapper;
        this.branchMapper = branchMapper;
    }

    public EmployeeEntity convertToEntity(EmployeeDTO employeeDTO){
        return EmployeeEntity.builder()
                .id(employeeDTO.getId())
                .title(employeeDTO.getTitle())
                .user(userMapper.convertToEntity(employeeDTO.getUser()))
                .branch(branchMapper.convertToEntity(employeeDTO.getBranch()))
                .active(employeeDTO.isActive())
                .build();
    }

    public EmployeeDTO convertToDTO(EmployeeEntity employeeEntity){
        return EmployeeDTO.builder()
                .id(employeeEntity.getId())
                .title(employeeEntity.getTitle())
                .user(userMapper.convertToDTO(employeeEntity.getUser()))
                .branch(branchMapper.convertToDTO(employeeEntity.getBranch()))
                .active(employeeEntity.isActive())
                .build();
    }
}
