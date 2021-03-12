package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parent_color", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parentColor")
    private List<ColorEntity> childColors;

    private boolean active;


}
