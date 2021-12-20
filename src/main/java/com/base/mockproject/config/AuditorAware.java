package com.base.mockproject.config;

import com.base.mockproject.constant.AppConstants;
import com.base.mockproject.security.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAware implements org.springframework.data.domain.AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(AppConstants.SYSTEM));
    }
}
