package com.davmt.motivatr.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.davmt.motivatr.model.User;
import com.davmt.motivatr.service.AuthoritiesService;
import com.davmt.motivatr.service.UserService;

@Controller
public class UsersController {

  @Autowired
  UserService userService;

  @Autowired
  AuthoritiesService authoritiesService;

  @GetMapping("/users/new")
  public String signup(Model model) {
    model.addAttribute("user", new User());
    return "users/new";
  }

  @GetMapping("/users/{username}")
  public String profile(Model model, @PathVariable String username, Principal principal) {
    User user = userService.getUserByUsername(username);
    model.addAttribute("user", user);
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "users/user_page";
  }

  @PostMapping("/users")
  public RedirectView signup(@ModelAttribute User userForm, RedirectAttributes redirAttrs) {
    if (!userService.validateUserDetails(userForm)) {
      redirAttrs.addFlashAttribute("message", userService.getStatusMessage());
      return new RedirectView("/users/new");
    }

    userService.createUser(userForm);

    return new RedirectView("/login");
  }

  @GetMapping("/users/search")
  public String showSearchResults(Model model, String keyword, Principal principal) {
    List<User> users = userService.searchUsers(keyword);
    model.addAttribute("users", users);
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "users/search";
  }

  @GetMapping("/users/{username}/set_role/")
  public RedirectView setRole(Model model, @PathVariable String username) {
    User user = userService.getUserByUsername(username);
    if (userService.isAdmin(user)) {
      authoritiesService.promoteToAdmin(user);
    } else {
      authoritiesService.demoteToUser(user);
    }
    return new RedirectView("/users/search");
  }
}
