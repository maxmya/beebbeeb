package com.trixpert.beebbeeb.data.to;

import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDTO {
    private Long id;

    private LocalDate date;

    private String amount;

    private long idNumber;

    private String photoIdSide1;

    private String photoIdSide2;

    private boolean haveCar ;

    private String eductionalLevel;

    private Date graduationDate;

    private String otherCertificates;

    private String companyName;

    private String jobType;

    private Long yearsOfWork;

    private String workAddress;

    private String workPhoneNumber;

    private Date jobHiringDate;

    private Long monthlySalary;

    private boolean extraIncome ;

    private String extraIncomeSource;

    private Long annualIncome;

    private Long loanAmount;

    private Long noInstallmentYears ;

    private boolean active;

    private CustomerDTO customer;

}
