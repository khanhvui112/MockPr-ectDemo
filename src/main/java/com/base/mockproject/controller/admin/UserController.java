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
package com.base.mockproject.controller.admin;

import com.base.mockproject.constant.AppConstants;
import com.base.mockproject.dto.display.UserDisplayDto;
import com.base.mockproject.dto.param.UserFormDataDto;
import com.base.mockproject.entity.Role;
import com.base.mockproject.entity.User;
import com.base.mockproject.entity.UserRole;
import com.base.mockproject.enums.CRUDEnum;
import com.base.mockproject.enums.StatusEnum;
import com.base.mockproject.exception.ItemNotFoundException;
import com.base.mockproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Controller
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController extends AdminBaseController {

    private final UserService userService;

    @GetMapping
    public String showUsers(@RequestParam(name = "page") Optional<Integer> pageOpt,
                            @RequestParam(name = "keyword") Optional<String> keywordOpt,
                            @RequestParam(name = "sort") Optional<String> sortOpt,
                            Model model) {

        Page<User> userPage = userService.findUsers(pageOpt.orElse(AppConstants.DEFAULT_START_PAGE),
                                                    pageSize,
                                                    keywordOpt.orElse(null),
                                                    sortOpt.orElse(null));
        List<UserDisplayDto> displayDtoList =
                userService.convertEntitiesToDtoList(userPage.getContent(), UserDisplayDto.class);
        Page<UserDisplayDto> pageDisplay = new PageImpl<>(displayDtoList,
                                                          userPage.getPageable(),
                                                          userPage.getTotalElements());
        model.addAttribute("userPage", pageDisplay);
        return "admin/users";
    }

    @Transactional
    @GetMapping({"/add", "/update/{id}"})
    public String showFormUser(@PathVariable(name = "id") Optional<Long> idOpt,
                              UserFormDataDto userFormDataDto,
                              Model model) {
        idOpt.ifPresent(id -> {
            User user = userService.findUndeletedRecordById(id)
                    .orElseThrow(ItemNotFoundException::new);
            BeanUtils.copyProperties(user, userFormDataDto);
            Optional<UserRole> userRole = user.getAuthorities().stream().findFirst();
            userFormDataDto.setRole(userRole.orElseThrow().getRole().getName());

            model.addAttribute("targetUpdateId", id);
        });

        model.addAttribute("userFormDataDto", userFormDataDto);
        return "admin/user-form";
    }

    @Transactional
    @PostMapping()
    public String addUser(@ModelAttribute @Valid UserFormDataDto userFormDataDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/user-form";
        }

        if (userService.emailAlreadyRegister(userFormDataDto.getEmail())) {
            FieldError error = new FieldError("userFormDataDto",
                                                    "email",
                                            "Email already registered");
            bindingResult.addError(error);
            return "admin/user-form";
        }

        User user = new User();
        BeanUtils.copyProperties(userFormDataDto, user);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(new Role(userFormDataDto.getRole()));

        user.setStatus(StatusEnum.NEW);
        user.setAuthorities(new HashSet<>(Collections.singleton(userRole)));

        userService.save(user);

        return "redirect:/admin/users?addSuccess";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable(name = "id") Long id,
                          @ModelAttribute @Valid UserFormDataDto userFormDataDto,
                          BindingResult bindingResult,
                          Model model) {
        model.addAttribute("targetUpdateId", id);
        if (bindingResult.hasErrors()) {
            return "admin/user-form";
        }

        User user = userService.findUndeletedRecordById(id)
                .orElseThrow(ItemNotFoundException::new);

        user.setFirstName(userFormDataDto.getFirstName());
        user.setLastName(userFormDataDto.getLastName());

        userService.save(user);

        return "redirect:/admin/users?updateSuccess";
    }
}
