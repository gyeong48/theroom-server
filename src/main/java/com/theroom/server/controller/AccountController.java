package com.theroom.server.controller;

import com.theroom.server.domain.request.AccountModifyRequest;
import com.theroom.server.security.dto.AccountDto;
import com.theroom.server.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/auth")
    public ResponseEntity<Map<String, AccountDto>> auth() {
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().getContext();

        if (context == null) {
            throw new NoSuchElementException("invalid authentication");
        }

        AccountDto accountDto = (AccountDto) context.getAuthentication().getPrincipal();

        return new ResponseEntity<>(Map.of("data", accountDto), HttpStatus.OK);
    }

    @GetMapping("/logoutSuccess")
    public ResponseEntity<Map<String, String>> logout() {
        return new ResponseEntity<>(Map.of("message", "success"), HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<Map<String, String>> modify(@RequestBody AccountModifyRequest request) {
        accountService.modifyAccount(request);
        return new ResponseEntity<>(Map.of("message", "success"), HttpStatus.OK);
    }
}
