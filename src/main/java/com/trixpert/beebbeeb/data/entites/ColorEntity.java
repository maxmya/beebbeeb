package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "color",schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_color", referencedColumnName = "id")
    private ParentColorEntity parentColor;

    @OneToMany(mappedBy = "color")
    private List<CarEntity> cars;

    private boolean active;


}
