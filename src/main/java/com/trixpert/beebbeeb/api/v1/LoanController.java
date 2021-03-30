package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.BannerRegistrationRequest;
import com.trixpert.beebbeeb.data.request.LoanRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.LoanDTO;
import com.trixpert.beebbeeb.services.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Loan API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get all active Loan List")
    public ResponseEntity<ResponseWrapper<List<LoanDTO>>> getAllActiveLoans() {
        return ResponseEntity.ok(loanService.getAllLoans(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get all in active Loan List")
    public ResponseEntity<ResponseWrapper<List<LoanDTO>>> getAllInActiveLoans() {
        return ResponseEntity.ok(loanService.getAllLoans(false));
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value ="/update/{loanId}" , consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Update an existing Loan with new data")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> updateloan(
            @RequestParam(name = "file") MultipartFile photoSide1,
            @RequestParam(name = "file") MultipartFile photoSide2,
            @Valid @RequestParam(name = "body") String regRequest,
            @PathVariable("loanId") long loanId,
            HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        LoanRegistrationRequest loanRegistrationRequest = objectMapper.readValue(
                regRequest, LoanRegistrationRequest.class);

        return ResponseEntity.ok(loanService.updateLoan(loanRegistrationRequest,loanId
                ,photoSide1,photoSide2,authorizationHeader));
    }
    @CrossOrigin(origins = {"*"})
    @PostMapping(value="/add" , consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Add New Loan")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addloan(
            @RequestParam(name = "file") MultipartFile photoSide1,
            @RequestParam(name = "file") MultipartFile photoSide2,
            @Valid @RequestParam(name = "body") String regRequest,
            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        LoanRegistrationRequest loanRegistrationRequest = objectMapper.readValue(
                regRequest, LoanRegistrationRequest.class);


        return ResponseEntity.ok(loanService.registerLoan(
                loanRegistrationRequest, photoSide1 , photoSide2, authorizationHeader));
    }

    @PutMapping("/delete/{loanId}")
    @ApiOperation("Remove  Loan By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteLoan(
            @PathVariable("loanId") Long loanId, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(loanService.deleteLoan(loanId, authorizationHeader));
    }

    @PutMapping("/Get/{loanId}")
    @ApiOperation("Get  Loan By Id")
    public ResponseEntity<ResponseWrapper<LoanDTO>> getLoan(
            @PathVariable("loanId") Long loanId) {

        return ResponseEntity.ok(loanService.getLoan(loanId));
    }
}
