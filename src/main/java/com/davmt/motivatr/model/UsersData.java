package com.davmt.motivatr.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS_DATA")
public class UsersData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Integer streak;
  private Integer highestStreak;
  private Integer totalCompleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @OneToOne(mappedBy = "usersData")
  private User user;

  public UsersData() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public UsersData(Integer highestStreak, Integer streak, Integer totalCompleted) {
    this.highestStreak = highestStreak;
    this.streak = streak;
    this.totalCompleted = totalCompleted;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

}