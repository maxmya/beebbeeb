package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.LoanEntity;
import com.trixpert.beebbeeb.data.mappers.LoanMapper;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.repositories.LoanRepository;
import com.trixpert.beebbeeb.data.request.LoanRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.LoanDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.NotActiveException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {


    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final CloudStorageService cloudStorageService;
    private final CustomerRepository customerRepository;


    public LoanServiceImpl(ReporterService reporterService, UserService userService,
                           AuditService auditService, LoanRepository loanRepository,
                           LoanMapper loanMapper, CloudStorageService cloudStorageService, CustomerRepository customerRepository) {
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.cloudStorageService = cloudStorageService;
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseWrapper<Boolean> registerLoan(LoanRegistrationRequest loanRegistrationRequest,
                                                 MultipartFile photoSide1file,
                                                 MultipartFile photoSide2file, String authHeader)
            throws IOException {
        try {
            String photoIdSide1 = cloudStorageService.uploadFile(photoSide1file);
            String photoIdSide2 = cloudStorageService.uploadFile(photoSide2file);

            LoanEntity loanEntityRecord = LoanEntity.builder()
                    .amount(loanRegistrationRequest.getAmount())
                    .idNumber(loanRegistrationRequest.getIdNumber())
                    .photoIdSide1(photoIdSide1)
                    .photoIdSide2(photoIdSide2)
                    .haveCar(loanRegistrationRequest.isHaveCar())
                    .educationalLevel(loanRegistrationRequest.getEducationalLevel())
                    .graduationDate(loanRegistrationRequest.getGraduationDate())
                    .otherCertificates(loanRegistrationRequest.getOtherCertificates())
                    .companyName(loanRegistrationRequest.getCompanyName())
                    .jobType(loanRegistrationRequest.getJobType())
                    .yearsOfWork(loanRegistrationRequest.getYearsOfWork())
                    .workAddress(loanRegistrationRequest.getWorkAddress())
                    .workPhoneNumber(loanRegistrationRequest.getWorkPhoneNumber())
                    .jobHiringDate(loanRegistrationRequest.getJobHiringDate())
                    .monthlySalary(loanRegistrationRequest.getMonthlySalary())
                    .extraIncome(loanRegistrationRequest.isExtraIncome())
                    .extraIncomeSource(loanRegistrationRequest.getExtraIncomeSource())
                    .annualIncome(loanRegistrationRequest.getAnnualIncome())
                    .loanAmount(loanRegistrationRequest.getLoanAmount())
                    .noInstallmentYears(loanRegistrationRequest.getNoInstallmentYears())
                    .customer(customerRepository.getOne(loanRegistrationRequest.getCustomerId()))
                    .active(true)
                    .build();
            loanRepository.save(loanEntityRecord);
            return reporterService.reportSuccess("Loan added successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteLoan(long loanId, String authHeader) {
        Optional<LoanEntity> optionalLoanEntity = loanRepository.findById(loanId);
        try {
            if (!optionalLoanEntity.isPresent())
                throw new NotFoundException("This Loan doesn't exist");

            LoanEntity loanEntityRecord = optionalLoanEntity.get();
            loanEntityRecord.setActive(false);
            loanRepository.save(loanEntityRecord);
            return reporterService.reportSuccess("this loan deleted sucessfully");
        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }

    @Override
    public ResponseWrapper<Boolean> updateLoan(LoanRegistrationRequest loanRegistrationRequest,
                                               long loanId, MultipartFile photoSide1file,
                                               MultipartFile photoSide2file, String authHeader) throws IOException {

        try {
            Optional<LoanEntity> optionalLoanEntity = loanRepository.findById(loanId);
            if (!optionalLoanEntity.isPresent()) {
                throw new NotActiveException("this loan doesn't exist");
            }
            LoanEntity loanEntityRecord = optionalLoanEntity.get();
            String photoIdSide1 = cloudStorageService.uploadFile(photoSide1file);
            String photoIdSide2 = cloudStorageService.uploadFile(photoSide2file);

            if (loanRegistrationRequest.getAmount() != null && !loanRegistrationRequest.getAmount().
                    equals(loanEntityRecord.getAmount())) {
                loanEntityRecord.setAmount(loanRegistrationRequest.getAmount());
            }
            if (loanRegistrationRequest.getIdNumber() != -1 && loanRegistrationRequest.getIdNumber() !=
                    loanEntityRecord.getIdNumber()) {
                loanEntityRecord.setIdNumber(loanRegistrationRequest.getIdNumber());
            }

            if (photoIdSide1 != null && !photoIdSide1.equals(loanEntityRecord.getPhotoIdSide1())) {
                loanEntityRecord.setPhotoIdSide1(photoIdSide1);
            }
            if (photoIdSide2 != null && !photoIdSide2.equals(loanEntityRecord.getPhotoIdSide2())) {
                loanEntityRecord.setPhotoIdSide2(photoIdSide2);
            }
            if (loanRegistrationRequest.isHaveCar() != loanEntityRecord.isHaveCar()) {
                loanEntityRecord.setHaveCar(loanEntityRecord.isHaveCar());
            }
            if (loanRegistrationRequest.getEducationalLevel() != null &&
                    !loanRegistrationRequest.getEducationalLevel().equals(loanEntityRecord.getEducationalLevel())) {
                loanEntityRecord.setEducationalLevel(loanRegistrationRequest.getEducationalLevel());
            }
            if (loanRegistrationRequest.getGraduationDate() != null &&
                    !loanRegistrationRequest.getGraduationDate().
                            equals(loanEntityRecord.getGraduationDate())) {
                loanEntityRecord.setGraduationDate(loanRegistrationRequest.getGraduationDate());
            }
            if (loanRegistrationRequest.getOtherCertificates() != null &&
                    !loanRegistrationRequest.getOtherCertificates().
                            equals(loanEntityRecord.getOtherCertificates())) {
                loanEntityRecord.setOtherCertificates(
                        loanRegistrationRequest.getOtherCertificates());
            }
            if (loanRegistrationRequest.getCompanyName() != null &&
                    !loanRegistrationRequest.getCompanyName().
                            equals(loanEntityRecord.getCompanyName())) {
                loanEntityRecord.setCompanyName(
                        loanRegistrationRequest.getCompanyName());
            }

            if (loanRegistrationRequest.getJobType() != null &&
                    !loanRegistrationRequest.getJobType().
                            equals(loanEntityRecord.getJobType())) {
                loanEntityRecord.setJobType(
                        loanRegistrationRequest.getJobType());
            }
            if (loanRegistrationRequest.getYearsOfWork() != null && loanRegistrationRequest.getYearsOfWork() !=
                    loanEntityRecord.getYearsOfWork()) {
                loanEntityRecord.setYearsOfWork(loanRegistrationRequest.getYearsOfWork());
            }
            if (loanRegistrationRequest.getWorkAddress() != null &&
                    !loanRegistrationRequest.getWorkAddress().equals(
                            loanEntityRecord.getWorkAddress()
                    )) {
                loanEntityRecord.setWorkAddress(loanRegistrationRequest.getWorkAddress());
            }
            if (loanRegistrationRequest.getWorkPhoneNumber() != null &&
                    loanRegistrationRequest.getWorkPhoneNumber().equals(
                            loanEntityRecord.getWorkPhoneNumber())) {
                loanEntityRecord.setWorkPhoneNumber(loanRegistrationRequest.getWorkPhoneNumber());

            }
            if (loanRegistrationRequest.getJobHiringDate() != null &&
                    loanRegistrationRequest.getJobHiringDate().equals(
                            loanEntityRecord.getJobHiringDate())) {
                loanEntityRecord.setJobHiringDate(loanRegistrationRequest.getJobHiringDate());
            }
            if (loanRegistrationRequest.getMonthlySalary() != null &&
                    loanRegistrationRequest.getMonthlySalary() !=
                            loanEntityRecord.getMonthlySalary()) {
                loanEntityRecord.setMonthlySalary(loanRegistrationRequest.getMonthlySalary());
            }
            if (loanRegistrationRequest.isExtraIncome() != loanEntityRecord.isExtraIncome()) {
                loanEntityRecord.setExtraIncome(loanEntityRecord.isExtraIncome());
            }
            if (loanRegistrationRequest.getExtraIncomeSource() != null &&
                    loanRegistrationRequest.getExtraIncomeSource() !=
                            loanEntityRecord.getExtraIncomeSource()) {
                loanEntityRecord.setExtraIncomeSource(
                        loanRegistrationRequest.getExtraIncomeSource());
            }
            if (loanRegistrationRequest.getAnnualIncome() != null &&
                    loanRegistrationRequest.getAnnualIncome() !=
                            loanEntityRecord.getAnnualIncome()) {
                loanEntityRecord.setAnnualIncome(loanRegistrationRequest.getAnnualIncome());
            }
            if (loanRegistrationRequest.getLoanAmount() != null &&
                    loanRegistrationRequest.getLoanAmount() !=
                            loanEntityRecord.getLoanAmount()) {
                loanEntityRecord.setLoanAmount(loanRegistrationRequest.getLoanAmount());
            }
            if (loanRegistrationRequest.getNoInstallmentYears() != null &&
                    loanRegistrationRequest.getNoInstallmentYears() !=
                            loanEntityRecord.getNoInstallmentYears()) {
                loanEntityRecord.setNoInstallmentYears(
                        loanRegistrationRequest.getNoInstallmentYears());
            }
            if (loanRegistrationRequest.getCustomerId() != -1 &&
                    customerRepository.findById(loanRegistrationRequest.getCustomerId()).equals(
                            loanEntityRecord.getCustomer())) {
                loanEntityRecord.setCustomer(
                        customerRepository.getOne(loanRegistrationRequest.getCustomerId()));
            }

            loanRepository.save(loanEntityRecord);


            return reporterService.reportSuccess("the loan form updated sucessfully");
        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }

    @Override
    public ResponseWrapper<List<LoanDTO>> getAllLoans(boolean active) {
        try {
            List<LoanDTO> loanList = new ArrayList<>();
            loanRepository.findAllByActive(active).forEach(loan ->
                    loanList.add(loanMapper.convertToDTO(loan))
            );

            return reporterService.reportSuccess(loanList);


        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }

    @Override
    public ResponseWrapper<LoanDTO> getLoan(long loanId) {
        try {
            Optional<LoanEntity> optionalLoanEntity = loanRepository.findById(loanId);
            if (!optionalLoanEntity.isPresent())
                throw new NotFoundException("This Loan doesn't exist");

            LoanEntity loanEntityRecord = optionalLoanEntity.get();
            return reporterService.reportSuccess(loanMapper.convertToDTO(loanEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }
}
