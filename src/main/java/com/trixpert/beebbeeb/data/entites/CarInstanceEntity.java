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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarEntity car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BranchEntity branch;

    @OneToMany(mappedBy = "car")
    private List<PriceEntity> prices;

    @Column(name = "best_seller")
    private boolean bestSeller;

    private long quantity;

    private boolean active;

    @OneToMany(mappedBy = "carInstance")
    private List<PurchasingRequestEntity> purchasingRequests;

    @OneToMany(mappedBy = "carInstance")
    private List<CarSKUHolderEntity> skus;

    @Column(name = "brochure_url")
    private String brochureUrl;


}
