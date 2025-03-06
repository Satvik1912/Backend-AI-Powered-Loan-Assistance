package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.dto.BankInfoDTO;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountBankAcc {




    private int bankCount;
    private List<BankInfoDTO> bankDetails;

}
