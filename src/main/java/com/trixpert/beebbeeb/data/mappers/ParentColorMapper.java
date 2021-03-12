package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.ParentColorEntity;
import com.trixpert.beebbeeb.data.to.ParentColorDTO;
import org.springframework.stereotype.Component;

@Component
public class ParentColorMapper {

    public ParentColorEntity convertToEntity(ParentColorDTO parentcolorDTO) {
        return ParentColorEntity.builder()
                .id(parentcolorDTO.getId())
                .name(parentcolorDTO.getName())
                .active(parentcolorDTO.isActive())
                .build();
    }

    public ParentColorDTO convertToDTO(ParentColorEntity parentcolorentity){
        return ParentColorDTO.builder()
                .id(parentcolorentity.getId())
                .name(parentcolorentity.getName())
                .numberofChildColors(parentcolorentity.getChildColors().size())
                .active(parentcolorentity.isActive())
                .build();

    }


}
