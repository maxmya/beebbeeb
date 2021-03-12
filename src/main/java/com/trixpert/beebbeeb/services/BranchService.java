package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;

import java.util.List;

public interface BranchService {
    ResponseWrapper<Boolean> registerBranchForVendor(BranchDTO branchDTO, Long vendorId
                                                    , String authHeader);

    ResponseWrapper<List<BranchDTO>> getAllBranchesForVendor(Long vendorId );

    ResponseWrapper<Boolean> updateBranchForVendor(BranchDTO branchDTO , String authHeader);

    ResponseWrapper<Boolean> deleteBranchForVendor(Long branchID , String authHeader);

    ResponseWrapper<List<CarDTO>> listCarsForBranch(long branchId);
}
