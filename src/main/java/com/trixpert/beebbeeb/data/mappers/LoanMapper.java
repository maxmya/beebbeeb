package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.LoanEntity;
import com.trixpert.beebbeeb.data.to.LoanDTO;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    private final CustomerMapper customerMapper;

    public LoanMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public LoanDTO convertToDTO(LoanEntity loanEntity) {
        return LoanDTO.builder()
                .id(loanEntity.getId())
                .date(loanEntity.getDate())
                .amount(loanEntity.getAmount())
                .idNumber(loanEntity.getIdNumber())
                .photoIdSide1(loanEntity.getPhotoIdSide1())
                .photoIdSide2(loanEntity.getPhotoIdSide2())
                .haveCar(loanEntity.isHaveCar())
                .eductionalLevel(loanEntity.getEductionalLevel())
                .graduationDate(loanEntity.getGraduationDate())
                .otherCertificates(loanEntity.getOtherCertificates())
                .companyName(loanEntity.getCompanyName())
                .jobType(loanEntity.getJobType())
                .yearsOfWork(loanEntity.getYearsOfWork())
                .workAddress(loanEntity.getWorkAddress())
                .workPhoneNumber(loanEntity.getWorkPhoneNumber())
                .jobHiringDate(loanEntity.getJobHiringDate())
                .monthlySalary(loanEntity.getMonthlySalary())
                .extraIncome(loanEntity.isExtraIncome())
                .extraIncomeSource(loanEntity.getExtraIncomeSource())
                .annualIncome(loanEntity.getAnnualIncome())
                .loanAmount(loanEntity.getLoanAmount())
                .noInstallmentYears(loanEntity.getNoInstallmentYears())
                .active(loanEntity.isActive())
                .customer(customerMapper.convertToDTO(loanEntity.getCustomerEntity()))
                .build();
    }

    public LoanEntity convertToDTO(LoanDTO loanDTO) {
        return LoanEntity.builder()
                .id(loanDTO.getId())
                .date(loanDTO.getDate())
                .amount(loanDTO.getAmount())
                .idNumber(loanDTO.getIdNumber())
                .photoIdSide1(loanDTO.getPhotoIdSide1())
                .photoIdSide2(loanDTO.getPhotoIdSide2())
                .haveCar(loanDTO.isHaveCar())
                .eductionalLevel(loanDTO.getEductionalLevel())
                .graduationDate(loanDTO.getGraduationDate())
                .otherCertificates(loanDTO.getOtherCertificates())
                .companyName(loanDTO.getCompanyName())
                .jobType(loanDTO.getJobType())
                .yearsOfWork(loanDTO.getYearsOfWork())
                .workAddress(loanDTO.getWorkAddress())
                .workPhoneNumber(loanDTO.getWorkPhoneNumber())
                .jobHiringDate(loanDTO.getJobHiringDate())
                .monthlySalary(loanDTO.getMonthlySalary())
                .extraIncome(loanDTO.isExtraIncome())
                .extraIncomeSource(loanDTO.getExtraIncomeSource())
                .annualIncome(loanDTO.getAnnualIncome())
                .loanAmount(loanDTO.getLoanAmount())
                .noInstallmentYears(loanDTO.getNoInstallmentYears())
                .active(loanDTO.isActive())
                .customerEntity(customerMapper.convertToEntity(loanDTO.getCustomer()))
                .build();
    }
}
