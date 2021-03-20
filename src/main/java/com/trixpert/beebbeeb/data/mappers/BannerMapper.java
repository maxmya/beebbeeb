package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.BannerEntity;
import com.trixpert.beebbeeb.data.to.BannerDTO;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {

    private final PhotoMapper photoMapper;


    public BannerMapper(PhotoMapper photoMapper) {
        this.photoMapper = photoMapper;
    }

    public BannerEntity convertToEntity(BannerDTO bannerDTO) {
        return BannerEntity.builder()
                .id(bannerDTO.getId())
                .name(bannerDTO.getName())
                .main(bannerDTO.isMain())
                .photo(photoMapper.convertToEntity(bannerDTO.getPhoto()))
                .build();
    }

    public BannerDTO convertToDTO(BannerEntity bannerEntity) {
        return BannerDTO.builder()
                .id(bannerEntity.getId())
                .name(bannerEntity.getName())
                .main(bannerEntity.isMain())
                .photo(photoMapper.convertToDTO(bannerEntity.getPhoto()))
                .build();
    }


}
