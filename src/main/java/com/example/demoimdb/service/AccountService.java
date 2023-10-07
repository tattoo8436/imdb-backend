package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.AccountLoginResponseDTO;
import com.example.demoimdb.dto.account.AccountRegisterRequestDTO;
import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.Account;
import com.example.demoimdb.repository.AccountRepository;
import com.example.demoimdb.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public void checkAdmin(BaseAccountDTO baseAccountDTO){
        Account account = accountRepository.findByUsername(baseAccountDTO.getUsername());
        if (account == null) {
            throw new ApiInputException("Tài khoản không có quyền!");
        } else if (!account.getPassword().equals(baseAccountDTO.getPassword())) {
            throw new ApiInputException("Tài khoản không có quyền!");
        } else if (account.getRole().equals("ADMIN")){
            throw new ApiInputException("Tài khoản không có quyền!");
        }
    }

    public String register(AccountRegisterRequestDTO accountRegisterRequestDTO) {
        if (accountRepository.existsByUsername(accountRegisterRequestDTO.getUsername())) {
            throw new ApiInputException("Tên đăng nhập đã tồn tại!");
        }
        Account account = new Account(null, accountRegisterRequestDTO.getUsername(), accountRegisterRequestDTO.getPassword(),
                accountRegisterRequestDTO.getEmail(), "USER", null, null);
        accountRepository.save(account);
        return "Đăng ký thành công!";
    }

    public AccountLoginResponseDTO loginUser(BaseAccountDTO baseAccountDTO) {
        Account account = accountRepository.findByUsername(baseAccountDTO.getUsername());
        if (account == null) {
            throw new ApiInputException("Sai tài khoản hoặc mật khẩu!");
        } else if (!account.getPassword().equals(baseAccountDTO.getPassword())) {
            throw new ApiInputException("Sai tài khoản hoặc mật khẩu!");
        }
        AccountLoginResponseDTO accountResponse = ConvertUtils.convert(account, AccountLoginResponseDTO.class);
        return accountResponse;
    }

    public AccountLoginResponseDTO loginAdmin(BaseAccountDTO baseAccountDTO) {
        Account account = accountRepository.findByUsername(baseAccountDTO.getUsername());
        if (account == null) {
            throw new ApiInputException("Sai tài khoản hoặc mật khẩu!");
        } else if (!account.getPassword().equals(baseAccountDTO.getPassword())) {
            throw new ApiInputException("Sai tài khoản hoặc mật khẩu!");
        } else if(!account.getRole().equals("ADMIN")){
            throw new ApiInputException("Sai tài khoản hoặc mật khẩu!");
        }
        AccountLoginResponseDTO accountResponse = ConvertUtils.convert(account, AccountLoginResponseDTO.class);
        return accountResponse;
    }
}
