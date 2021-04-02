package com.trixpert.beebbeeb.data.entites;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "loan", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String amount;
    private long idNumber;

    @Column(name = "photo_id_side1")
    private String photoIdSide1;

    @Column(name = "photo_id_side2")
    private String photoIdSide2;

    @Column(name = "have_car")
    private boolean haveCar;

    @Column(name = "educational_level")
    private String educationalLevel;

    @Column(name = "graduation_date")
    private LocalDate graduationDate;

    @Column(name = "other_certificates")
    private String otherCertificates;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "years_of_work")
    private String yearsOfWork;

    @Column(name = "work_address")
    private String workAddress;

    @Column(name = "work_phone_number")
    private String workPhoneNumber;

    @Column(name = "job_hiring_Date")
    private LocalDate jobHiringDate;

    @Column(name = "monthly_salary")
    private String monthlySalary;

    @Column(name = "extra_income")
    private boolean extraIncome;

    @Column(name = "extra_income_source")
    private String extraIncomeSource;

    @Column(name = "annual_income")
    private String annualIncome;

    @Column(name = "loan_amount")
    private String loanAmount;

    @Column(name = "no_installment_years")
    private String noInstallmentYears;

    private boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customerEntity;
}
