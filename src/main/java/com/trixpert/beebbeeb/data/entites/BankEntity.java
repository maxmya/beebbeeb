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

    private boolean active;

//    @Fetch(value = FetchMode.SUBSELECT)
//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    }, fetch = FetchType.EAGER)
//    @JoinTable(name = "bank_vendors",
//            joinColumns = @JoinColumn(name = "bank_id"),
//            inverseJoinColumns = @JoinColumn(name = "vendor_id")
//    )
//    private List<BankEntity> banks;
}
