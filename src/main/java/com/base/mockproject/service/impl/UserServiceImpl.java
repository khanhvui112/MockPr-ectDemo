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

import com.base.mockproject.entity.User;
import com.base.mockproject.enums.DeleteFlgEnum;
import com.base.mockproject.enums.StatusEnum;
import com.base.mockproject.repository.UserRepository;
import com.base.mockproject.repository.spec.UserSpec;
import com.base.mockproject.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository>
    implements UserService {

    @Override
    public User findActiveUserByEmail(String email) {
        return repository
                .findByEmailAndStatusAndDeleteFlg(email, StatusEnum.ACTIVE, DeleteFlgEnum.UNDELETED)
                .orElseThrow();
    }

    @Override
    public boolean emailAlreadyRegister(String email) {
        return repository
                .existsByEmailAndStatusNotAndDeleteFlg(email, StatusEnum.INACTIVE, DeleteFlgEnum.UNDELETED);
    }

    @Override
    public Page<User> findUsers(Integer page,
                                Integer size,
                                String keyword,
                                String sort) {
        UserSpec spec = new UserSpec();
        Specification<User> specification = spec.unDeleted();
        if (StringUtils.isNotBlank(keyword)) {
            Specification<User> freeTextSearchSpec = spec.searchByKeyword(keyword);
            specification = specification.and(freeTextSearchSpec);
        }

        PageRequest pageRequest = preparePageRequest(page, size, sort, true);

        return repository.findAll(specification, pageRequest);
    }
}
