package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "review",schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity reviewer;

    @Column(name = "rate")
    private Double rate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id",referencedColumnName = "id")
    private CommentEntity comment;

    @Column(name="date")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "vendor_id",referencedColumnName = "id")
    private VendorEntity vendor;

    @ManyToOne
    @JoinColumn(name = "car_instance_id", referencedColumnName = "id")
    private CarInstanceEntity carInstance;



}
