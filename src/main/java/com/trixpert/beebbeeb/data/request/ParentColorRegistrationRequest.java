package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.entites.ColorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentColorRegistrationRequest {
    private Long id;

    private String name;

}
