package com.davmt.motivatr.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.davmt.motivatr.model.CompletedChallenge;

public interface CompletedChallengeRepository extends CrudRepository<CompletedChallenge, Long> {

  public List<CompletedChallenge> findByUserId(Long userId);

  public List<CompletedChallenge> findByChallengeId(Long challengeId);

  public Boolean existsByUserIdAndChallengeId(Long userId, Long challengeId);

  public List<CompletedChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);
}
