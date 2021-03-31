package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "purchasing_request", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchasingRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @Column(name = "payment_type")
    private String paymentType;

    private String comment;

    private Date date;

    @ManyToOne
    @JoinColumn(name ="vendor_id",referencedColumnName = "id" )
    private VendorEntity vendor;

    @ManyToOne
    @JoinColumn(name ="customer_id" , referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn (name ="car_instance_id" , referencedColumnName = "id")
    private CarInstanceEntity carInstance;

    private boolean active;





}
