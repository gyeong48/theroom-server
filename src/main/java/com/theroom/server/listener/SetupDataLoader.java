package com.theroom.server.listener;

import com.theroom.server.domain.entity.Account;
import com.theroom.server.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean isAlreadySetup = false;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (isAlreadySetup) {
            return;
        }

        Account account = accountRepository.findByUsername("theroom0330").orElse(null);

        if (account == null) {
            account = Account.builder()
                    .username("theroom0330")
                    .password(passwordEncoder.encode("pass"))
                    .roles("ROLE_ADMIN")
                    .build();
        }

        accountRepository.save(account);
        isAlreadySetup = true;
    }
}
