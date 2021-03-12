package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.BankRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BankDTO;
import com.trixpert.beebbeeb.services.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Banks API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @PostMapping("/add")
    @ApiOperation("Adding a new bank")
    public ResponseEntity<ResponseWrapper<Boolean>> registerBank(
            @RequestPart(name = "file") MultipartFile logoFile,
            @RequestPart(name = "body") BankRegistrationRequest bankRegisterRequest,
            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(bankService.registerBank(logoFile, bankRegisterRequest,authorizationHeader));
    }

    @PutMapping("/update")
    @ApiOperation("Updating a bank")
    public ResponseEntity<ResponseWrapper<Boolean>> updateBank(@RequestPart(name = "file") MultipartFile logoFile
                                                               , HttpServletRequest request,
                                                               @RequestBody BankDTO bankDTO) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(bankService.updateBank(logoFile, bankDTO , authorizationHeader ));
    }

    @PutMapping("/delete/{bankId}")
    @ApiOperation("Api for deleting a bank")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteBank(@PathVariable("bankId") Long bankId ,
                                                               HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(bankService.deleteBank(bankId , authorizationHeader));
    }

    @GetMapping("/list/active")
    @ApiOperation("Get list of active banks")
    public ResponseEntity<ResponseWrapper<List<BankDTO>>> getActiveBanks() {

        return ResponseEntity.ok(bankService.getAllBanks(true ));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get list of inactive banks")
    public ResponseEntity<ResponseWrapper<List<BankDTO>>> getInactiveBanks() {

        return ResponseEntity.ok(bankService.getAllBanks(false ));
    }

}
