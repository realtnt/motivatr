package com.davmt.motivatr.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.davmt.motivatr.MotivatrApplication;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.repository.AuthoritiesRepository;
import com.davmt.motivatr.service.UserService;

import java.security.Principal;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotivatrApplication.class)
public class UserServiceTests {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthoritiesRepository authoritiesRepository;

  private static Boolean setupDone = false;

  @Before
  public void init() {
    if (setupDone)
      return;

    User user = new User("John",
        "James",
        "jjames",
        "jjames@gmail.com",
        "password");
    userService.createUser(user);

    User user1 = new User("a", "a", "a", "a", "a");
    User user2 = new User("b", "a", "b", "b", "a");
    User user3 = new User("c", "a", "c", "c", "a");
    User user4 = new User("d", "a", "d", "d", "a");
    User user5 = new User("e", "a", "e", "e", "a");
    User user6 = new User("f", "a", "f", "f", "a");
    User user7 = new User("g", "a", "g", "g", "a");
    User user8 = new User("h", "a", "h", "h", "a");
    User user9 = new User("i", "a", "i", "i", "a");
    User user10 = new User("j", "a", "j", "j", "a");
    userService.createUser(user1);
    userService.createUser(user2);
    userService.createUser(user3);
    userService.createUser(user4);
    userService.createUser(user5);
    userService.createUser(user6);
    userService.createUser(user7);
    userService.createUser(user8);
    userService.createUser(user9);
    userService.createUser(user10);

    setupDone = true;
  }

  @Test
  public void checkThatTheFirstUserCreatedHasAdminRights() {
    assertThat(authoritiesRepository.findByUsername("jjames").get(0).getAuthority()).isEqualTo("ROLE_ADMIN");
  }

  @Test
  public void validateUserReturnsFalseIfEmailExists() {
    User user = new User("John",
        "James",
        "johnjames",
        "jjames@gmail.com",
        "password");

    assertThat(userService.validateUserDetails(user)).isEqualTo(false);
    assertThat(userService.getStatusMessage()).isEqualTo("Email already exists!");
  }

  @Test
  public void validateUserReturnsFalseIfUsernameExists() {
    User user = new User("John",
        "James",
        "jjames",
        "johnjames@gmail.com",
        "password");

    assertThat(userService.validateUserDetails(user)).isEqualTo(false);
    assertThat(userService.getStatusMessage()).isEqualTo("Username taken!");
  }

  @Test
  public void validateUserReturnsFalseIfPasswordsDoNotMatch() {
    User user = new User("John",
        "James",
        "johnjames",
        "johnjames@gmail.com",
        "password");
    user.setPasswordConfirm("PASSWORD");

    assertThat(userService.validateUserDetails(user)).isEqualTo(false);
    assertThat(userService.getStatusMessage()).isEqualTo("Passwords don't match!");
  }

  @Test
  public void validateUserReturnsTrue() {
    User user = new User("Peter",
        "Barnes",
        "pbarnes",
        "pbarnes@gmail.com",
        "password");
    user.setPasswordConfirm("password");

    assertThat(userService.validateUserDetails(user)).isEqualTo(true);
    assertThat(userService.getStatusMessage()).isEqualTo(null);
  }

  @Test
  public void getUserFromPrincipalReturnsListOfOneUserObject() {
    Principal mockPrincipal = Mockito.mock(Principal.class);
    Mockito.when(mockPrincipal.getName()).thenReturn("jjames");
    assertThat(userService.getUserFromPrincipal(mockPrincipal).getEmail()).isEqualTo("jjames@gmail.com");
  }

  @Test
  public void getUserByUsernameReturnsAUserObject() {
    assertThat(userService.getUserByUsername("jjames").getEmail()).isEqualTo("jjames@gmail.com");
  }

  @Test
  public void getTopTenUsersReturnsListOfTenUserObjects() {
    List<User> users = userService.getTopTenUsers();
    assertThat(users.size()).isEqualTo(10);
  }

  @Test
  public void getStatusMessageReturnsAnErrorMessageAndSetsItToNull() {
    User user = new User("John",
        "James",
        "jjames",
        "jjames@gmail.com",
        "password");
    userService.createUser(user);

    assertThat(userService.validateUserDetails(user)).isEqualTo(false);
    assertThat(userService.getStatusMessage()).isEqualTo("Email already exists!");
    assertThat(userService.getStatusMessage()).isEqualTo(null);
  }

  @Test
  public void createUserCreatesUsersDataAndSavesInDatabase() {
    User newUser = new User("Abel",
        "Jones",
        "ajones",
        "ajones@gmail.com",
        "password");
    userService.createUser(newUser);

    assertThat(userService.getUserByUsername("ajones").getUsersData().getHighestStreak()).isEqualTo(0);
    assertThat(userService.getUserByUsername("ajones").getUsersData().getStreak()).isEqualTo(0);
    assertThat(userService.getUserByUsername("ajones").getUsersData().getTotalCompleted()).isEqualTo(0);
    assertThat(userService.getUserByUsername("ajones").getFirstName()).isEqualTo("Abel");
    assertThat(userService.getUserByUsername("ajones").getLastName()).isEqualTo("Jones");
    assertThat(userService.getUserByUsername("ajones").getEmail()).isEqualTo("ajones@gmail.com");
  }
}
