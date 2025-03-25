package com.cars24.ai_loan_assistance.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDto {
    // User Basic Information
    private String name;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;

    // User Information Details
    private UserInfoDto userInfo;

    // Bank Details
    private List<BankDetailDto> bankDetails;

    // Loan Details
    private List<LoanDetailDto> loans;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto {
        private String pan;
        private String aadhar;
        private Double salary;
        private Integer cibil;
        private String incomeType;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankDetailDto {
        private String accountNumber;
        private String accountHolderName;
        private String ifscCode;
        private String bankName;
        private String bankAccountType;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanDetailDto {
        private String loanStatus;
        private String loanType;
        private LocalDate disbursedDate;
        private Double principal;
        private Double tenure;
        private Double interest;
        private Integer totalEmiCount;
        private Integer overdueEmiCount;
        private EmiDetailDto latestEmi;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmiDetailDto {
        private Double emiAmount;
        private LocalDate dueDate;
        private String status;
        private Double lateFee;
    }
}