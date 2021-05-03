package com.trixpert.beebbeeb.data.entites;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendor", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity manager;

    private String name;

    private String mainAddress;

    private boolean active;

    @Column(name = "gm_name")
    private String generalManagerName;

    @Column(name = "gm_phone")
    private String generalManagerPhone;

    @Column(name = "gm_id_document_face_url")
    private String generalManagerIdDocumentFaceUrl;

    @Column(name = "gm_id_document_back_url")
    private String generalManagerIdDocumentBackUrl;

    @Column(name = "acc_manager_name")
    private String accountManagerName;

    @Column(name = "acc_manager_phone")
    private String accountManagerPhone;

    @Column(name = "acc_manager_id_document_face_url")
    private String accountManagerIdDocumentFaceUrl;

    @Column(name = "acc_manager_id_document_back_url")
    private String accountManagerIdDocumentBackUrl;

    @Column(name = "tax_record_document_url")
    private String taxRecordDocumentUrl;

    @Column(name = "tax_record_number")
    private String taxRecordNumber;

    @Column(name = "commercial_register_document_url")
    private String commercialRegisterDocumentUrl;

    @Column(name = "commercial_register_number")
    private String commercialRegisterNumber;

    @OneToMany(mappedBy = "vendor")
    private List<BranchEntity> branches;

    @OneToMany(mappedBy = "vendor")
    private List<HomeTelephoneEntity> homeTelephones;

    @OneToMany(mappedBy = "vendor")
    private List<SalesManEntity> salesMen;


    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "brands_agent",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private List<BrandEntity> brandsAgent;


    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "brands_distributor",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private List<BrandEntity> brandDistributor;

    @Column(name = "importer")
    private Boolean importer;

    @Column(name = "home_delivery")
    private Boolean homeDelivery;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "contract_doc_url")
    private String contractDocumentUrl;

    @OneToMany(mappedBy = "vendor")
    private List<CarInstanceEntity> instances;

    @OneToMany(mappedBy ="vendor")
    private List<PurchasingRequestEntity> purchasingRequests;

    @Column(name = "working_time")
    private String workingTime;

    @Column(name = "sales_per_month")
    private Integer salesPerMonth;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "vendor_banks",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private List<BankEntity> banks;

    @OneToMany(mappedBy = "vendor")
    private List<ReviewEntity> reviews;


}
