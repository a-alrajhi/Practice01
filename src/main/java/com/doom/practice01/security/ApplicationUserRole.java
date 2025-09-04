package com.doom.practice01.security;

import static com.doom.practice01.security.ApplicationUserPermissions.*;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
  STUDENT(Sets.newHashSet()), // since the student doesn't have any perms
  ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_WRITE, STUDENT_READ)),
  ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

  private final Set<ApplicationUserPermissions> permissions;

  ApplicationUserRole(Set<ApplicationUserPermissions> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermissions> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    // Java Stream way
    Set<SimpleGrantedAuthority> permissions =
        getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;

    // Java Simple Version
    //    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    //    for (ApplicationUserPermissions permission : getPermissions()) {
    //      authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
    //    }
    //    String role = "ROLE_" + this.name();
    //    SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority(role);
    //    authorities.add(roleAuthority);
    //    return authorities;
  }
}
