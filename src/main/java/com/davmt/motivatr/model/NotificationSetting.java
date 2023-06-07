package com.davmt.motivatr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "NOTIFICATION_SETTINGS")
public class NotificationSetting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private Boolean dailyNotifications;
  private Boolean textNotifications;
  private Boolean emailNotifications;

  @OneToOne(mappedBy = "notificationSetting")
  private User user;

  public NotificationSetting() {
    dailyNotifications = false;
    textNotifications = false;
    emailNotifications = false;
  }

  public NotificationSetting(Boolean dailyNotifications, Boolean textNotifications, Boolean emailNotifications) {
    this.dailyNotifications = dailyNotifications;
    this.textNotifications = textNotifications;
    this.emailNotifications = emailNotifications;
  }

}