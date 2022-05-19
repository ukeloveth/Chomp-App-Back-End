package com.decagon.chompapp.controllers;


import com.decagon.chompapp.dtos.WalletTransactionRequest;
import com.decagon.chompapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/fund-wallet")
    public ResponseEntity<String> fundWallet(@RequestBody WalletTransactionRequest walletTransactionRequest){

        return walletService.fundWalletAccount(walletTransactionRequest);
    }
    @PostMapping("/withdrawal")
    public ResponseEntity<String> WithdrawalFromWallet(@RequestBody WalletTransactionRequest walletTransactionRequest){

        return walletService.withdrawFromWallet(walletTransactionRequest);
    }
}