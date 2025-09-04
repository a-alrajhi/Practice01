package com.doom.practice01.auth;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserDao {
  Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
