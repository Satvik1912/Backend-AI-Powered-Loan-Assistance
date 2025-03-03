package com.gemini.gemini.Controller;


import com.gemini.gemini.Services.BankDetailsService;
import com.gemini.gemini.Services.impl.BankDetailsServiceImpl;
import com.gemini.gemini.data.entity.BankDetailsEntity;
import com.gemini.gemini.data.request.CreateBankDetails;
import com.gemini.gemini.data.request.GetBankDetailsOfUser;
import com.gemini.gemini.data.response.GetBankDetailsRespUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bankdetails")
@Service
@RequiredArgsConstructor
public class BankDetailsController {

    private final BankDetailsServiceImpl bankDetailsService;
    @PostMapping("create")
    public String bankdetails(@RequestBody CreateBankDetails createBankDetails){

        bankDetailsService.createBankDetails(createBankDetails);

        return "Bank Details Added Successfully";
    }

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
