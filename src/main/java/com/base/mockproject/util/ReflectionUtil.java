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

import com.base.mockproject.annotations.FreeTextSearch;
import com.base.mockproject.entity.PersistentToken;
import com.base.mockproject.entity.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public final class ReflectionUtil {

    private ReflectionUtil() {}

    public static List<String> getFieldNameByAnnotation(Class<?> clazz,
                                                        Class<? extends Annotation> markAnnotation) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(markAnnotation)))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(getFieldNameByAnnotation(PersistentToken.class, FreeTextSearch.class));
    }
}
