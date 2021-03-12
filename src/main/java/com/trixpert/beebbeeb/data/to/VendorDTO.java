package com.trixpert.beebbeeb.data.to;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
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
}
