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

import com.base.mockproject.entity.User;
import com.base.mockproject.enums.DeleteFlgEnum;
import com.base.mockproject.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByEmailAndStatusAndDeleteFlg(String email, StatusEnum status, DeleteFlgEnum deleteFlgEnum);

    boolean existsByEmailAndStatusNotAndDeleteFlg(String email,
                                                 StatusEnum status,
                                                 DeleteFlgEnum deleteFlgEnum);
}
