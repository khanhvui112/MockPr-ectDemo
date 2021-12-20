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

import com.base.mockproject.constant.AppConstants;
import com.base.mockproject.entity.AppEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public interface BaseService<E extends AppEntity, ID extends Serializable> {

    E save(E model);

    List<E> save(List<E> models);

    Optional<E> findUndeletedRecordById(ID id);

    default <T> List<T> convertEntitiesToDtoList(List<E> entityList, Class<T> dtoClazz) {
        return entityList.stream()
                .map(e -> convertEntityToDto(e, dtoClazz))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    default <T> T convertEntityToDto(E entity, Class<T> dtoClazz) {
        try {
            T dto = dtoClazz.getConstructor().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    default PageRequest preparePageRequest(Integer page,
                                           Integer size,
                                           String sort,
                                           boolean includeDefaultSort) {
        page = Optional.ofNullable(page).orElse(AppConstants.DEFAULT_START_PAGE);
        return PageRequest.of(page, size, Sort.by(getSortOrders(sort, includeDefaultSort)));
    }

    default PageRequest preparePageRequest(Integer page,
                                           Integer size,
                                           String sort) {
        page = Optional.ofNullable(page).orElse(AppConstants.DEFAULT_START_PAGE);
        return PageRequest.of(page, size, Sort.by(getSortOrders(sort)));
    }

    default PageRequest preparePageRequest(Integer page,
                                           Integer size) {
        page = Optional.ofNullable(page).orElse(AppConstants.DEFAULT_START_PAGE);

        return PageRequest.of(page, size);
    }

    default List<Sort.Order> getSortOrders(String orderStr, boolean includeDefaultSort) {
        List<Sort.Order> orders = getSortOrders(orderStr);
        if (includeDefaultSort) {
            orders.add(new Sort.Order(Sort.Direction.fromString(AppConstants.DEFAULT_SORT_DIRECTION),
                                        AppConstants.DEFAULT_SORT));
        }
        return orders;
    }

    default List<Sort.Order> getSortOrders(String orderStr) {
        if (StringUtils.isBlank(orderStr)) {
            return new ArrayList<>();
        }

        String[] orderArr = StringUtils.split(orderStr, AppConstants.SORT_SEPARATOR);
        return Arrays.stream(orderArr)
                .map(orderField -> orderField.trim().startsWith(AppConstants.DESC_SORT_PREFIX) ?
                        Sort.Order.desc(orderField.trim().substring(1)) :
                        Sort.Order.asc(orderField.trim()))
                .collect(Collectors.toList());
    }
}
