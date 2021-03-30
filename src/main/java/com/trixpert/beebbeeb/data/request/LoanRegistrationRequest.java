package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRegistrationRequest {

    private String amount;

    private long idNumber;

    private boolean haveCar ;

    private String eductionalLevel;

    private Date graduationDate;

    private String otherCertificates;

    private String companyName;

    private String jobType;

    private long yearsOfWork;

    private String workAddress;

    private String workPhoneNumber;

    private Date jobHiringDate;

    private long monthlySalary;

    private boolean extraIncome ;

    private String extraIncomeSource;

    private long annualIncome;

    private long loanAmount;

    private long noInstallmentYears ;

    private boolean active;

    private long customerId;

}

