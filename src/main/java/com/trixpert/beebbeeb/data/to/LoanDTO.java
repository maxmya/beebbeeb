package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDTO {
    private Long id;
    private LocalDate date;
    private String amount;
    private String idNumber;
    private String photoIdSide1;
    private String photoIdSide2;
    private boolean haveCar;
    private String educationalLevel;
    private LocalDate graduationDate;
    private String otherCertificates;
    private String companyName;
    private String jobType;
    private String yearsOfWork;
    private String workAddress;
    private String workPhoneNumber;
    private LocalDate jobHiringDate;
    private String monthlySalary;
    private boolean extraIncome;
    private String extraIncomeSource;
    private String annualIncome;
    private String loanAmount;
    private String noInstallmentYears;
    private boolean active;
    private CustomerDTO customer;

}
