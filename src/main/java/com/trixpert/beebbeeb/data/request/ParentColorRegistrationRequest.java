package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.entites.ColorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentColorRegistrationRequest {
    @NotNull(message = "parent color name can't be null")
    private String name;

}
