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


import com.base.mockproject.entity.AbstractAuditingEntity;
import com.base.mockproject.entity.AppEntity;
import com.base.mockproject.enums.DeleteFlgEnum;
import com.base.mockproject.repository.BaseRepository;
import com.base.mockproject.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:phuongdp@pizsoft.com">PhuongDP</a>
 */
@Slf4j
public abstract class BaseServiceImpl<E extends AppEntity, ID extends Serializable, R extends BaseRepository<E, ID>>
		implements BaseService<E, ID> {
	
	@Autowired
	protected R repository;

	public E save(E model) {
		if (model instanceof AbstractAuditingEntity) {
			((AbstractAuditingEntity) model).setDeleteFlg(DeleteFlgEnum.UNDELETED);
		}
		return repository.save(model);
	}

	public List<E> save(List<E> models) {
		return repository.saveAll(models);
	}

	@Override
	public Optional<E> findUndeletedRecordById(ID id) {
		return repository.findById(id).filter(e -> {
			if (e instanceof AbstractAuditingEntity) {
				return ((AbstractAuditingEntity) e).getDeleteFlg().equals(DeleteFlgEnum.UNDELETED);
			}
			return true;
		});
	}
}