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

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false)
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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RolesEntity> roles;

}