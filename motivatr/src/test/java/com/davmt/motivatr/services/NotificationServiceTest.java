package com.davmt.motivatr.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.davmt.motivatr.MotivatrApplication;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.service.NotificationService;
import com.davmt.motivatr.service.UserService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotivatrApplication.class)
public class NotificationServiceTest {

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private UserService userService;

  @Test
  public void notificationSettingsCanBeSaved() {
    User user = new User("blah", "blah", "blah", "blah", "blah");
    userService.createUser(user);

    user.getNotificationSetting().setDailyNotifications(true);
    user.getNotificationSetting().setTextNotifications(true);
    user.getNotificationSetting().setEmailNotifications(true);
    notificationService.save(user.getNotificationSetting());

    assertThat(notificationService.getNotificationSettingsFromUserId(user.getId()).getDailyNotifications())
        .isEqualTo(true);
    assertThat(notificationService.getNotificationSettingsFromUserId(user.getId()).getTextNotifications())
        .isEqualTo(true);
    assertThat(notificationService.getNotificationSettingsFromUserId(user.getId()).getEmailNotifications())
        .isEqualTo(true);
  }
}