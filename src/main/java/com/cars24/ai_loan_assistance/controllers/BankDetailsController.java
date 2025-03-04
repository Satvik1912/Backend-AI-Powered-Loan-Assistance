package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import com.cars24.ai_loan_assistance.services.impl.BankDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankdetails")
@Service
@RequiredArgsConstructor
public class BankDetailsController {

    private final BankDetailsServiceImpl bankDetailsService;
//    @PostMapping("/create")
//    public String bankdetails(@RequestBody CreateBankDetails createBankDetails){
//
//        bankDetailsService.createBankDetails(createBankDetails);
//
//        return "Bank Details Added Successfully";
//    }

    @GetMapping("fetchbyid")
    public GetBankDetailsRespUID userbank(@RequestBody GetBankDetailsOfUser getBankDetailsOfUser){
        GetBankDetailsRespUID getBankDetailsRespUID = bankDetailsService.getBankDetailsRespUID(getBankDetailsOfUser);
        return getBankDetailsRespUID;

    }

    @GetMapping("fetch")
    public ResponseEntity<List<BankDetailsEntity>> getResponse(){
        List<BankDetailsEntity> getResponse = bankDetailsService.getAllBank();
        return ResponseEntity.ok(getResponse);
    }

}
