package com.theroom.server.security.service;

import com.theroom.server.security.dto.AccountContext;
import com.theroom.server.security.dto.AccountDto;
import com.theroom.server.domain.entity.Account;
import com.theroom.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("FormUserDetailsService");

        Account account = accountRepository.findByUsername(username).orElse(null);

        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }

        AccountDto accountDto = AccountDto.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRoles())
                .build();

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));

        return new AccountContext(accountDto, authorities);
    }
}
