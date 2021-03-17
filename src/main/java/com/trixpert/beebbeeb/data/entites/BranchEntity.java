package com.trixpert.beebbeeb.data.entites;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "branch", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendor;

    @OneToMany(mappedBy = "branch")
    private List<EmployeeEntity> employees;

    @OneToMany(mappedBy = "branch")
    private List<CarEntity> cars;

    boolean active;
}
