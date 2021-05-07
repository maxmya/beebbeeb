package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.BankRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BankDTO;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BankService {

    ResponseWrapper<Boolean> registerBank(MultipartFile logoFile,
                                          BankRegistrationRequest bankRegistrationRequest,
                                          String authHeader) throws IOException;

    ResponseWrapper<Boolean> updateBank(MultipartFile logoFile,
                                        BankRegistrationRequest bankRegistrationRequest , long bankId ,
                                        String authHeader);

    ResponseWrapper<Boolean> deleteBank(Long bankId , String authHeader);

    ResponseWrapper<List<BankDTO>> getAllBanks(boolean active );

    ResponseWrapper<BankDTO> getBank(long bankId);

    ResponseWrapper<List<BankDTO>> getBanksForVendor(long vendorId);

    ResponseWrapper<List<BankDTO>> getBanksForCustomer(long customerId);


}
