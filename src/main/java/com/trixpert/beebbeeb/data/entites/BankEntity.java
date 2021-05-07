package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank",schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "logo_url")
    private String logoUrl;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "bank_vendors",
            joinColumns = @JoinColumn(name = "bank_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id")
    )
    private List<VendorEntity> vendors;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "customer_banks",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private List<CustomerEntity> customers;
    private boolean active;
}
