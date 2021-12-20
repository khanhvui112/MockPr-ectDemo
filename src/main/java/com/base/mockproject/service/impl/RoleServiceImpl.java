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
package com.base.mockproject.service.impl;

import com.base.mockproject.entity.Role;
import com.base.mockproject.repository.RoleRepository;
import com.base.mockproject.security.MasterRoleEnum;
import com.base.mockproject.service.BaseService;
import com.base.mockproject.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<Role, String, RoleRepository>
        implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<String> getMasterRoleName(Class<?> masterRoleEnumClass) {
        List<String> masterRoleNames = Arrays.stream(masterRoleEnumClass.getEnumConstants())
                                            .map(Object::toString)
                                            .collect(Collectors.toList());
        return roleRepository.findExistRoleNames(masterRoleNames);
    }

}
