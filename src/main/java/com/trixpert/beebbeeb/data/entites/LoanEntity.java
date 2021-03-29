package com.trixpert.beebbeeb.data.entites;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    private boolean haveCar ;

    @Column(name ="eductiona_level")
    private String eductionalLevel;

    @Column(name="graduation_date")
    private Date graduationDate;

    @Column(name="other_certificates")
    private String otherCertificates;

    @Column(name="company_name")
    private String companyName;

    @Column(name="job_type")
    private String jobType;

    @Column(name = "years_of_work")
    private Long yearsOfWork;

    @Column(name ="work_address")
    private String workAddress;

    @Column(name ="work_phone_no")
    private String workPhoneNumber;

    @Column(name="job_hiring_Date")
    private Date jobHiringDate;

    @Column(name="monthly_salary")
    private Long monthlySalary;

    @Column(name="extra_income")
    private boolean extraIncome ;

    @Column(name="extra_income_source")
    private String extraIncomeSource;

    @Column(name="annual_income")
    private Long annualIncome;

    @Column(name = "loan_amount")
    private Long loanAmount;

    @Column(name = "no_installment_years")
    private Long noInstallmentYears ;

    private boolean active;


    @OneToOne
    private CustomerEntity customerEntity;
}
