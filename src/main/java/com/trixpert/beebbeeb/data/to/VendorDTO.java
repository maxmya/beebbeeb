package com.trixpert.beebbeeb.data.to;
import com.trixpert.beebbeeb.data.entites.BrandEntity;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private Long id;
    private UserDTO manager;
    private String vendorName;
    private String mainAddress;
    private String generalManagerName;
    private String generalManagerPhone;
    private String accountManagerName;
    private String accountManagerPhone;
    private boolean active;
    private String WorkingTime;
}
