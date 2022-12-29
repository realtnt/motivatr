package com.davmt.motivatr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davmt.motivatr.model.User;
import com.davmt.motivatr.model.UsersData;
import com.davmt.motivatr.repository.UsersDataRepository;

@Service
public class UsersDataService {

  @Autowired
  UsersDataRepository usersDataRepository;

  public UsersData createUsersData() {
    UsersData usersData = new UsersData(0, 0, 0);
    save(usersData);
    return usersData;
  }

  public void save(UsersData usersData) {
    usersDataRepository.save(usersData);
  }

  public void addPoint(User User, Integer points) {
  }

}