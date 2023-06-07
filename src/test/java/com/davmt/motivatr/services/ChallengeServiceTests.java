package com.davmt.motivatr.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.davmt.motivatr.MotivatrApplication;
import com.davmt.motivatr.model.Challenge;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.service.ChallengeService;
import com.davmt.motivatr.service.CompletedChallengeService;
import com.davmt.motivatr.service.UserService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotivatrApplication.class)
public class ChallengeServiceTests {

  @Autowired
  private ChallengeService challengeService;

  @Autowired
  private UserService userService;

  @Autowired
  private CompletedChallengeService completedChallengeService;

  private User user;
  private Challenge challenge1 = new Challenge();
  private Challenge challenge2 = new Challenge();
  private Challenge challenge3 = new Challenge();
  private LocalDateTime today = LocalDateTime.now();
  private LocalDateTime yesterday = today.minusDays(1);
  private LocalDateTime dayBeforeYesterday = today.minusDays(2);

  private static Boolean setupDone = false;

  @Before
  public void init() {
    if (setupDone)
      return;

    user = new User("John",
        "James",
        "jjames",
        "jjames@gmail.com",
        "password");
    userService.createUser(user);

    challenge1.setTitle("Challenge 1");
    challenge1.setDescription("Challenge description 1");
    challenge1.setAuthor(user);
    challenge1.setPublishedOn(dayBeforeYesterday);
    challengeService.save(challenge1);

    challenge2.setTitle("Challenge 2");
    challenge2.setDescription("Challenge description 2");
    challenge2.setAuthor(user);
    challenge2.setPublishedOn(yesterday);
    challengeService.save(challenge2);

    challenge3.setTitle("Challenge 3");
    challenge3.setDescription("Challenge description 3");
    challenge3.setAuthor(user);
    challenge3.setPublishedOn(today);
    challengeService.save(challenge3);

    setupDone = true;
  }

  @Test
  public void returnTodaysChallengeIfLatestChallengesDateIsSameAsTodaysDate() {
    assertThat(challengeService.getTodaysChallenge().getTitle()).isEqualTo("Challenge 3");
  }

  @Test
  public void returnNewChallengeIfLatestChallengesDateIsNotTodaysDate() {
    Challenge challenge = challengeService.getChallengeFromId(3L);
    challenge.setPublishedOn(null);
    challengeService.save(challenge);
    assertThat(challengeService.getTodaysChallenge().getTitle()).isEqualTo("Challenge 3");
  }

  @Test
  public void returnYesterdaysChallengeIfThereAreNoNewChallenges() {
    Challenge challenge = challengeService.getChallengeFromId(3L);
    challenge.setPublishedOn(dayBeforeYesterday);
    challengeService.save(challenge);
    assertThat(challengeService.getTodaysChallenge().getTitle()).isEqualTo("Challenge 2");
  }

  @Test
  public void returnListOfUnpublishedChallenges() {
    Challenge challenge = challengeService.getChallengeFromId(3L);
    challenge.setPublishedOn(today);
    challengeService.save(challenge);
    assertThat(challengeService.getUnpublishedChallenges().size()).isEqualTo(0);
    challenge.setPublishedOn(null);
    challengeService.save(challenge);
    assertThat(challengeService.getUnpublishedChallenges().size()).isEqualTo(1);
  }

  @Test
  public void returnListOfPublishedChallenges() {
    Challenge challenge = challengeService.getChallengeFromId(3L);
    challenge.setPublishedOn(yesterday);
    challengeService.save(challenge);
    assertThat(challengeService.getPublishedChallenges().size()).isEqualTo(3);
    challenge.setPublishedOn(null);
    challengeService.save(challenge);
    assertThat(challengeService.getPublishedChallenges().size()).isEqualTo(2);
  }

  @Test
  public void returnFalseForAllThreeUncompletedChallenges() {
    User user = userService.getUserByUsername("jjames");
    Challenge challenge = challengeService.getChallengeFromId(3L);
    challenge.setPublishedOn(yesterday);
    challengeService.save(challenge);
    List<Challenge> challenges = challengeService.getPublishedChallengesWithStatusAndCompleted(user);

    assertThat(challenges.get(0).getIsDone()).isFalse();
    assertThat(challenges.get(1).getIsDone()).isFalse();
    assertThat(challenges.get(2).getIsDone()).isFalse();
  }

  @Test
  public void returnFalseForUncompletedChallengesAndTrueForMarkedAsComplete() {
    Challenge challenge = challengeService.getChallengeFromId(3L);
    User user = userService.getUserByUsername("jjames");

    completedChallengeService.completeChallenge(user, challenge);

    List<Challenge> challenges = challengeService.getPublishedChallengesWithStatusAndCompleted(user);

    assertThat(challenges.get(0).getIsDone()).isTrue();
    assertThat(challenges.get(1).getIsDone()).isFalse();
    assertThat(challenges.get(2).getIsDone()).isFalse();
  }
}
