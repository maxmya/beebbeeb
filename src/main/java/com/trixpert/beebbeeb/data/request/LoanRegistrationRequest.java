package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRegistrationRequest {
    private String amount;
    private String idNumber;
    private boolean haveCar;
    private String educationalLevel;
    private String graduationDate;
    private String otherCertificates;
    private String companyName;
    private String jobType;
    private String yearsOfWork;
    private String workAddress;
    private String workPhoneNumber;
    private String jobHiringDate;
    private String monthlySalary;
    private boolean extraIncome;
    private String extraIncomeSource;
    private String annualIncome;
    private String loanAmount;
    private String noInstallmentYears;
    private boolean active;
    private long customerId;
}

