package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.LoanRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.LoanDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LoanService {

    ResponseWrapper<Boolean> registerLoan(LoanRegistrationRequest loanRegistrationRequest
            ,  MultipartFile photoSide1file, MultipartFile photoSide2file , String authHeader )
            throws IOException;

    ResponseWrapper<Boolean> deleteLoan(long loanId , String authHeader);

    ResponseWrapper<Boolean> updateLoan(LoanRegistrationRequest loanRegistrationRequest,
                                         long loanId,  MultipartFile photoSide1file,
                                        MultipartFile photoSide2file , String authHeader)throws IOException;

    ResponseWrapper<List<LoanDTO>> getAllLoans(boolean active);


    ResponseWrapper<LoanDTO> getLoan(long loanId);

}
