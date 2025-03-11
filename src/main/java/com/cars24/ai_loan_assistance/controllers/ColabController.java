package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.services.ColabApiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colab")
@Validated
public class ColabController {

    private final ColabApiService colabApiService;

    public ColabController(ColabApiService colabApiService) {
        this.colabApiService = colabApiService;
    }

    @GetMapping
    public String getColabMessage() {
        return colabApiService.callColabApi();
    }
}

