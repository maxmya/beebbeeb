package com.trixpert.beebbeeb.data.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column
    private String email;

    private String phone;

    private boolean active;

    @Column(name = "is_phone")
    private boolean phoneFlag;

    @Column(name = "pic_url")
    private String picUrl;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private List<CardEntity> cards;

    @OneToMany(mappedBy = "user")
    private List<AuditEntity> audits;

    @OneToMany(mappedBy = "user")
    private List<NotificationEntity> notifications;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RolesEntity> roles;

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> reviews;


}