package com.trixpert.beebbeeb.data.response;

import com.trixpert.beebbeeb.data.to.BrandDTO;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileHomeResponse {
    private List<TypeDTO> carTypes;
    private List<BrandDTO> carBrands;
    private List<LinkableImage> mainBanners;
    private List<LinkableImage> sliderImages;
    private List<CarItemResponse> bestSellers;
}