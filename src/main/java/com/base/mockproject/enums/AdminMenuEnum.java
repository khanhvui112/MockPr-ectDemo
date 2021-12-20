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
package com.base.mockproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@AllArgsConstructor
@Getter
public enum AdminMenuEnum {
    DASHBOARD("Dashboard", "/admin/dashboard", true),
    USERS("User management","/admin/users", true),
    TICKETS("Ticket management","/admin/tickets", false);

    private final String display;
    private final String url;
    private final boolean active;
}
