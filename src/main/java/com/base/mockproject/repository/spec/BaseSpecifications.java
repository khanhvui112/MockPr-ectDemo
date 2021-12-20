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
package com.base.mockproject.repository.spec;

import com.base.mockproject.annotations.FreeTextSearch;
import com.base.mockproject.entity.AppEntity;
import com.base.mockproject.entity.User;
import com.base.mockproject.enums.DeleteFlgEnum;
import com.base.mockproject.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

/**
 * @author <a href="mailto:phuongdp@pizsoft.com">PhuongDP</a>
 */
public class BaseSpecifications<M extends AppEntity> {

    public Specification<M> unDeleted() {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("deleteFlg"), DeleteFlgEnum.UNDELETED);
    }

    public Specification<M> searchByKeyword(String keyword) {
        String keywordSearchLike = "%" + keyword + "%";
        List<String> searchFieldNames = ReflectionUtil.getFieldNameByAnnotation(User.class,
                FreeTextSearch.class);
        if (CollectionUtils.isEmpty(searchFieldNames)) {
            throw new IllegalArgumentException("Class not support free text search");
        }

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.or(
                searchFieldNames.stream()
                        .map(fieldName -> criteriaBuilder.like(root.get(fieldName), keywordSearchLike))
                        .toArray(Predicate[]::new)
        );
    }
}
