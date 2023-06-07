package com.davmt.motivatr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davmt.motivatr.model.Authority;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.repository.AuthoritiesRepository;

@Service
public class AuthoritiesService {

  @Autowired
  AuthoritiesRepository authoritiesRepository;

  public void promoteToAdmin(User user) {
    Authority authority = authoritiesRepository.findById(user.getId()).get();
    authority.setAuthority("ROLE_ADMIN");
    authoritiesRepository.save(authority);
  }

  public void demoteToUser(User user) {
    Authority authority = authoritiesRepository.findById(user.getId()).get();
    authority.setAuthority("ROLE_USER");
    authoritiesRepository.save(authority);
  }
}
