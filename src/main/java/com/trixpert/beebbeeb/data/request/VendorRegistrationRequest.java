package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.entites.HomeTelephoneEntity;
import com.trixpert.beebbeeb.data.entites.SalesManEntity;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import com.trixpert.beebbeeb.data.to.HomeTelephoneDTO;
import com.trixpert.beebbeeb.data.to.SalesManDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRegistrationRequest extends RegistrationRequest{
    private String vendorName;
    private String mainAddress;
    private String gmName;
    private String gmPhone;
    private String accManagerName;
    private String accManagerPhone;
    private String bankAccountNumber;
    private int salesPerMonth;
    private List<String> homeTelephones;
    private List<SalesManDTO> salesMen;
    private boolean importer;
    private boolean homeDelivery;
    private List<BrandDTO> brandsAgent;
    private List<BrandDTO> brandsDistributor;
    private String taxRecordNumber;
    private String commercialRegisterNumber;


}
