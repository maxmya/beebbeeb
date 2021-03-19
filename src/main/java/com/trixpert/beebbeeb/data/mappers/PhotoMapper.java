package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {

    public PhotoEntity convertToEntity(PhotoDTO photoDTO) {
        return PhotoEntity.builder()
                .interior(photoDTO.isInterior())
                .caption(photoDTO.getCaption())
                .mainPhoto(photoDTO.isMainPhoto())
                .photoUrl(photoDTO.getPhotoUrl())
                .description(photoDTO.getDescription())
                .id(photoDTO.getId())
                .build();
    }


    public PhotoDTO convertToDTO(PhotoEntity photoEntity) {
        return PhotoDTO.builder()
                .interior(photoEntity.isInterior())
                .caption(photoEntity.getCaption())
                .mainPhoto(photoEntity.isMainPhoto())
                .photoUrl(photoEntity.getPhotoUrl())
                .description(photoEntity.getDescription())
                .id(photoEntity.getId())
                .build();
    }


}
