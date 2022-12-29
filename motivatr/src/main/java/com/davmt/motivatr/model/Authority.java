package com.davmt.motivatr.model;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "AUTHORITIES")
public class Authority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String authority;

  public Authority() {
  }

  public Authority(String username, String authority) {
    this.username = username;
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String userType) {
    authority = userType;
  }
}
