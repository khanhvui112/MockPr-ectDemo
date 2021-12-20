/*-============================================================================
 * PizSoft. PROPRIETARY
 * Copyright© 2021 PizSoft.
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 *
 * This software is the confidential and proprietary information of
 * PizSoft. ("Proprietary Information"). Any use, reproduction,
 * distribution or disclosure of the software or Proprietary Information,
 * in whole or in part, must comply with the terms of the license
 * agreement, nondisclosure agreement or contract entered into with
 * PizSoft. providing access to this software.
 *
 *=============================================================================
 */
package com.base.mockproject.security;

import com.base.mockproject.constant.SessionNameConstants;
import com.base.mockproject.dto.display.UserLoginDisplayDto;
import com.base.mockproject.entity.User;
import com.base.mockproject.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);

        // Set user login information to session
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow();
        User user = userService.findActiveUserByEmail(email);
        UserLoginDisplayDto userLoginDisplayDto = new UserLoginDisplayDto();
        BeanUtils.copyProperties(user, userLoginDisplayDto);

        request.getSession()
                .setAttribute(SessionNameConstants.USER_LOGIN_INFO, userLoginDisplayDto);
    }

    /**
     * Determine target url based on role
    */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<String> targetUrl = authorities.stream()
                                    .map(a -> SecurityConstant.roleTargetUrlMap.get(a.getAuthority()))
                                    .findFirst();

        return targetUrl.orElseThrow(IllegalArgumentException::new);
    }
}
