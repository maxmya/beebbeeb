package com.trixpert.beebbeeb.data.to;
import lombok.*;

import java.util.List;

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
}
