package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "telephone", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeTelephoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendor;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "active")
    private boolean active;

}
