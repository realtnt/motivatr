package com.davmt.motivatr.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CHALLENGES_COMPLETIONS")

public class CompletedChallenge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne
  @JoinColumn(name = "challenge_id")
  Challenge challenge;

  private LocalDateTime completedAt;

  public CompletedChallenge() {
    this.completedAt = LocalDateTime.now();
  }

}