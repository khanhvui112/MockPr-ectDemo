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
package com.base.mockproject.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public class PasswordEncoderUtil {

    public static void main(String[] args) {
        // $2a$10$VtA8KjFvQ8s0Bip1O5vd.e6tfupBjlHY98tJWAmMNodueRSaDENri
        System.out.println(new BCryptPasswordEncoder().encode("abcd1234"));
    }
}
