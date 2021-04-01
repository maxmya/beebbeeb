package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_instance", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarInstanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condition;

    @Column(name = "original_price")
    private String originalPrice;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarEntity car;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendor;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BranchEntity branch;

    @OneToMany(mappedBy = "car")
    private List<PriceEntity> prices;

    @Column(name = "best_seller")
    private boolean bestSeller;

    private boolean active;

    @OneToMany(mappedBy ="carInstance")
    private List<PurchasingRequestEntity> purchasingRequests;


}
