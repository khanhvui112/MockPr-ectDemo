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
package com.base.mockproject.repository;

import com.base.mockproject.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

    @Query(value = "SELECT r.name FROM Role r WHERE r.name IN :roleName")
    List<String> findExistRoleNames(List<String> roleName);
}
