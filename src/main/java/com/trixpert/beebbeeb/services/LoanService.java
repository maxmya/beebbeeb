package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.LoanRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.LoanDTO;

import java.util.List;

public interface LoanService {

    ResponseWrapper<Boolean> registerLoan(LoanRegistrationRequest loanRegistrationRequest
            , String authHeader) ;

    ResponseWrapper<Boolean> deleteLoan(long loanId , String authHeader);

    ResponseWrapper<Boolean> updateLoan(LoanRegistrationRequest loanRegistrationRequest,
                                         long loanId, String authHeader);

    ResponseWrapper<List<LoanDTO>> getAllLoans(boolean active);


    ResponseWrapper<LoanDTO> getLoan(long loanId);

}
