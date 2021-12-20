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
package com.base.mockproject.security;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public class SecurityConstant {

    public final static Map<String, String>
            roleTargetUrlMap = new HashMap<>() {{
                put(MasterRoleEnum.ROLE_ADMIN.name(), "/admin/dashboard");
                put(MasterRoleEnum.ROLE_USER.name(), "/user/dashboard");
    }};
}
