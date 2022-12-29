package com.davmt.motivatr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.davmt.motivatr.service.UserService;
import com.davmt.motivatr.service.NotificationService;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.model.NotificationSetting;

import java.security.Principal;

@Controller
public class ProfileController {
  @Autowired
  UserService userService;

  @Autowired
  NotificationService notificationService;

  @GetMapping("/users/notifications")
  public String getNotifications(Model model, Principal principal, NotificationSetting notificationSetting) {
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "users/notifications";
  }

  @PostMapping("/users/notifications")
  public RedirectView setNotifications(Model model, NotificationSetting notificationSettingPage, Principal principal) {

    notificationService.updateSettings(userService.getUserFromPrincipal(principal).getNotificationSetting(),
        notificationSettingPage);
    return new RedirectView("/users/notifications");
  }

  @GetMapping("/users/profile")
  public String profile(Model model, Principal principal) {
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "users/profile";
  }

  @GetMapping("/users/edit")
  public String edit(Model model, Principal principal) {
    model.addAttribute("user", new User());
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "users/edit";
  }

  @PostMapping("/users/edit")
  public RedirectView updateProfile(@ModelAttribute User profileForm, RedirectAttributes redirAttrs,
      Principal principal) {
    userService.updateUser(profileForm, principal);
    return new RedirectView("/users/profile");
  }

}