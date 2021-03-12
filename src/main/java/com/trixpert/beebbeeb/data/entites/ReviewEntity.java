package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "review",schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity reviewer;

    private int rate;

    private String comment;

    private Date timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id",referencedColumnName = "id")
    private VendorEntity vendor;

    //todo : model


}
