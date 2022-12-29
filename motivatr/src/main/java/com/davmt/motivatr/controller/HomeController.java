package com.davmt.motivatr.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.davmt.motivatr.model.Challenge;
import com.davmt.motivatr.service.ChallengeService;
import com.davmt.motivatr.service.CompletedChallengeService;
import com.davmt.motivatr.service.UserService;

@Controller
public class HomeController {

  @Autowired
  private ChallengeService challengeService;

  @Autowired
  private UserService userService;

  @Autowired
  private CompletedChallengeService completedChallengeService;

  @RequestMapping(value = "/")
  public RedirectView index() {
    return new RedirectView("/home");
  }

  @GetMapping("/home")
  public String home(Model model, Principal principal) {
    Challenge todaysChallenge = challengeService.getTodaysChallenge();
    model.addAttribute("challenge", todaysChallenge);
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    model.addAttribute("topten", userService.getTopTenUsers());
    model.addAttribute("isChallengeCompleted",
        completedChallengeService.checkChallengeStatus(principal, todaysChallenge));
    return "home";
  }

}
