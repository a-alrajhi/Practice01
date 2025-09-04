package com.doom.practice01.auth;

import static com.doom.practice01.security.ApplicationUserRole.ADMIN;
import static com.doom.practice01.security.ApplicationUserRole.ADMIN_TRAINEE;
import static com.doom.practice01.security.ApplicationUserRole.STUDENT;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {
  private final PasswordEncoder passwordEncoder;

  public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
    return getApplicationUsers().stream()
        .filter(applicationUser -> username.equals(applicationUser.getUsername()))
        .findFirst();
  }

  public List<ApplicationUser> getApplicationUsers() {
    List<ApplicationUser> applicationUsers =
        Lists.newArrayList(
            new ApplicationUser(
                "anna",
                passwordEncoder.encode("anna"),
                STUDENT.getGrantedAuthorities(),
                true,
                true,
                true,
                true),
            new ApplicationUser(
                "mo",
                passwordEncoder.encode("mo"),
                ADMIN.getGrantedAuthorities(),
                true,
                true,
                true,
                true),
            new ApplicationUser(
                "tom",
                passwordEncoder.encode("tom"),
                ADMIN_TRAINEE.getGrantedAuthorities(),
                true,
                true,
                true,
                true));
    return applicationUsers;
  }
}
