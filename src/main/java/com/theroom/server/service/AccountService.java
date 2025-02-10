package com.theroom.server.service;

import com.theroom.server.domain.entity.Account;
import com.theroom.server.domain.request.AccountModifyRequest;
import com.theroom.server.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void modifyAccount(AccountModifyRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername()).orElseThrow();

        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }

        if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new BadCredentialsException("password not match");
        }

        account.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }
}
