package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendor", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity manager;

    private String name;

    private String mainAddress;

    private boolean active;

    @Column(name = "gm_name")
    private String generalManagerName;

    @Column(name = "gm_phone")
    private String generalManagerPhone;

    @Column(name = "gm_id_document_url")
    private String generalManagerIdDocumentUrl;

    @Column(name = "acc_manager_name")
    private String accountManagerName;

    @Column(name = "acc_manager_phone")
    private String accountManagerPhone;

    @Column(name = "acc_manager_id_document_url")
    private String accountManagerIdDocumentUrl;

    @Column(name = "tax_record_document_url")
    private String taxRecordDocumentUrl;

    @Column(name = "commercial_register_document_url")
    private String commercialRegisterDocumentUrl;

    @OneToMany(mappedBy = "vendor")
    private List<BranchEntity> branches;


}
