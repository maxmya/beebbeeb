package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "comment" ,schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="body")
    private String body;

    @Column(name = "date")
    private Date timeStamp;

}
