package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.mappers.LoanMapper;
import com.trixpert.beebbeeb.data.repositories.LoanRepository;
import com.trixpert.beebbeeb.data.request.LoanRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.LoanDTO;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.LoanService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {


    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    public LoanServiceImpl(ReporterService reporterService, UserService userService,
                           AuditService auditService, LoanRepository loanRepository,
                           LoanMapper loanMapper) {
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerLoan(LoanRegistrationRequest loanRegistrationRequest,
                                                 String authHeader) {
        return null;
    }

    @Override
    public ResponseWrapper<Boolean> deleteLoan(long loanId, String authHeader) {
        return null;
    }

    @Override
    public ResponseWrapper<Boolean> updateLoan(LoanRegistrationRequest loanRegistrationRequest,
                                               long loanId, String authHeader) {
        return null;
    }

    @Override
    public ResponseWrapper<List<LoanDTO>> getAllLoans(boolean active) {
        return null;
    }

    @Override
    public ResponseWrapper<LoanDTO> getLoan(long loanId) {
        return null;
    }
}
