package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRegistrationRequest {
    private String amount;
    private long idNumber;
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
    private long customerId;
}

