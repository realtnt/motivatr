package com.davmt.motivatr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davmt.motivatr.model.NotificationSetting;
import com.davmt.motivatr.repository.NotificationSettingsRepository;

@Service
public class NotificationService {

  @Autowired
  NotificationSettingsRepository notificationRepository;

  public NotificationSetting createNotificationSetting() {
    NotificationSetting notificationSetting = new NotificationSetting(false, false, false);
    save(notificationSetting);
    return notificationSetting;
  }

  public void save(NotificationSetting notificationSetting) {
    notificationRepository.save(notificationSetting);
  }

  public NotificationSetting getNotificationSettingsFromUserId(Long userId) {
    NotificationSetting notificationSetting = notificationRepository.findByUserId(userId);
    return notificationSetting;
  }

  public void updateSettings(NotificationSetting notificationSetting, NotificationSetting formSettings) {
    notificationSetting.setDailyNotifications(formSettings.getDailyNotifications());
    notificationSetting.setTextNotifications(formSettings.getTextNotifications());
    notificationSetting.setEmailNotifications(formSettings.getEmailNotifications());
    save(notificationSetting);
  }
}
