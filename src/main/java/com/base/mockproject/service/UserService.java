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
package com.base.mockproject.service;

import com.base.mockproject.entity.User;
import org.springframework.data.domain.Page;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public interface UserService extends BaseService<User, Long> {

    User findActiveUserByEmail(String email);

    boolean emailAlreadyRegister(String email);

    Page<User> findUsers(Integer page, Integer size, String keyword, String sort);
}
