package com.davmt.motivatr.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davmt.motivatr.model.Challenge;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.repository.ChallengeRepository;
import com.davmt.motivatr.repository.CompletedChallengeRepository;
import com.davmt.motivatr.repository.UserRepository;

@Service
public class ChallengeService {

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private CompletedChallengeRepository completedChallengeRepository;

  @Autowired
  private UserRepository userRepository;

  public Challenge getChallengeFromId(Long challenge_id) {
    Optional<Challenge> challengeOptionsl = challengeRepository.findById(challenge_id);
    Challenge challenge = challengeOptionsl.get();
    return challenge;
  }

  public Challenge getTodaysChallenge() {
    LocalDate today = LocalDateTime.now().toLocalDate();
    LocalDate mostRecentChallengeDate;

    if (challengeRepository.count() == 0) {
      User author = userRepository.findById(1L).get();
      Challenge challenge = new Challenge();
      challenge.setAuthor(author);
      challenge.setTitle("Empty Challenge!");
      challenge.setDescription("Auto created first challenge.");
      save(challenge);
    }

    List<Challenge> unpublishedChallenges = getUnpublishedChallenges();
    List<Challenge> publishedChallenges = getPublishedChallenges();
    if (publishedChallenges.size() > 0) {
      mostRecentChallengeDate = publishedChallenges.get(0).getPublishedOn().toLocalDate();

      if (today.equals(mostRecentChallengeDate) || unpublishedChallenges.size() == 0) {
        return publishedChallenges.get(0);
      }
    }

    Challenge unpublishedChallenge = unpublishedChallenges.get(0);
    unpublishedChallenge.setPublishedOn(LocalDateTime.now());
    save(unpublishedChallenge);

    return unpublishedChallenges.get(0);
  }

  public void save(Challenge challenge) {
    challengeRepository.save(challenge);
  }

  public List<Challenge> getUnpublishedChallenges() {
    return challengeRepository.findAllByPublishedOnIsNull();
  }

  public List<Challenge> getPublishedChallenges() {
    return challengeRepository.findAllByPublishedOnIsNotNullOrderByPublishedOnDesc();
  }

  public List<Challenge> getPublishedChallengesWithStatusAndCompleted(User user) {
    List<Challenge> challenges = getPublishedChallenges();
    Long userId = user.getId();

    for (Challenge challenge : challenges) {
      Long challengeId = challenge.getId();
      Boolean isDone = completedChallengeRepository.existsByUserIdAndChallengeId(userId, challengeId);
      if (isDone) {
        challenge.setIsDone(true);
      }
    }
    return challenges;
  }
}
