package com.davmt.motivatr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.davmt.motivatr.model.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class SmsService {

  private static final Logger log = LoggerFactory.getLogger(SmsService.class);

  private Boolean smsEnabled = false;

  @Autowired
  UserService userService;

  // TODO: can change to: @Scheduled(cron="0 0 0 * * ?");
  @Scheduled(fixedRate = 30000)
  public void reportCurrentTime() {
    List<User> users = userService.notifyUserList();
    for (User user : users) {
      sendSMS(user);
    }
  }

  private void sendSMS(User user) {
    Dotenv dotenv = Dotenv.load();
    String message = "Hey " + StringUtils.capitalize(user.getFirstName()) + " "
        + StringUtils.capitalize(user.getLastName()) + "! It's time to get playing! 🎸🎸🎸";
    if (smsEnabled) {
      Twilio.init(dotenv.get("TWILIO_ACCOUNT_SID"), dotenv.get("TWILIO_AUTH_TOKEN"));

      String mobileNumber = dotenv.get("RECEIVING_PHONE"); // TODO: THIS NEEDS TO BE REMOVED
      Message.creator(new PhoneNumber(mobileNumber),
          new PhoneNumber(dotenv.get("TWILIO_NUM")), message).create();
    } else {
      log.info(message);
    }
  }
}