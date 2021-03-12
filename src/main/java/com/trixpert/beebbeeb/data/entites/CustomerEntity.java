package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "preferred_bank")
    private String preferredBank;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_address")
    private String jobAddress;

    private long income;

    private boolean active;

    private String horoscope;

    @OneToMany(mappedBy = "customer")
    private List<AddressEntity> addresses;

}
