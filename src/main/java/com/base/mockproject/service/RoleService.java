/*-============================================================================
 * PizSoft. PROPRIETARY
 * CopyrightÂ© 2021 PizSoft.
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

import com.base.mockproject.entity.Role;
import com.base.mockproject.repository.RoleRepository;

import java.util.List;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public interface RoleService extends BaseService<Role, String> {

    List<String> getMasterRoleName(Class<?> roleEnum);
}
