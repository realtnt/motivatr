package com.davmt.motivatr.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.davmt.motivatr.model.Challenge;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.service.ChallengeService;
import com.davmt.motivatr.service.UserService;

@Controller
public class ChallengesController {

  @Autowired
  ChallengeService challengeService;

  @Autowired
  UserService userService;

  @PostMapping("/challenges")
  public RedirectView saveNewChallenge(Principal principal, Challenge challengeForm) {
    User author = userService.getUserFromPrincipal(principal);
    challengeForm.setAuthor(author);
    challengeService.save(challengeForm);
    return new RedirectView("home");
  }

  @GetMapping("/challenges/new")
  public String newChallenge(Model model, Principal principal) {
    model.addAttribute("challenge", new Challenge());
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "challenges/new";
  }

  @GetMapping("/challenges/all")
  public String listChallenges(Model model, Principal principal) {
    User user = userService.getUserFromPrincipal(principal);
    List<Challenge> challenges = challengeService.getPublishedChallengesWithStatusAndCompleted(user);
    model.addAttribute("challenges", challenges);
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "challenges/all";
  }

  @GetMapping("/challenges/{id}")
  public String profile(Model model, @PathVariable Long id, Principal principal) {
    Challenge challenge = challengeService.getChallengeFromId(id);
    model.addAttribute("challenge", challenge);
    model.addAttribute("principal", userService.getUserFromPrincipal(principal));
    return "challenges/details";
  }
}
