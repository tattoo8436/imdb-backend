package com.example.demoimdb.controller;

import com.example.demoimdb.dto.account.AccountLoginResponseDTO;
import com.example.demoimdb.dto.account.AccountRegisterRequestDTO;
import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid AccountRegisterRequestDTO accountRegisterRequestDTO) {
        return accountService.register(accountRegisterRequestDTO);
    }

    @PostMapping("/login-user")
    public ResponseEntity<AccountLoginResponseDTO> loginUser(@RequestBody @Valid BaseAccountDTO baseAccountDTO) {
        return ResponseEntity.ok(accountService.loginUser(baseAccountDTO));
    }

    @PostMapping("/login-admin")
    public ResponseEntity<AccountLoginResponseDTO> loginAdmin(@RequestBody @Valid BaseAccountDTO baseAccountDTO) {
        return ResponseEntity.ok(accountService.loginAdmin(baseAccountDTO));
    }
}
