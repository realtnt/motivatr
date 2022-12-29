package com.davmt.motivatr.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.view.RedirectView;

import com.davmt.motivatr.service.ChallengeService;
import com.davmt.motivatr.service.CompletedChallengeService;

@Controller
public class CompletedChallengesController {

  @Autowired
  CompletedChallengeService completedChallengeService;

  @Autowired
  ChallengeService challengeService;

  @GetMapping("/challenge/complete/{id}")
  public RedirectView listCompletedChallenges(Model model, Principal principal, @PathVariable Long id) {
    completedChallengeService.toggleChallengeStatus(principal, id);
    return new RedirectView("/home");
  }

}
