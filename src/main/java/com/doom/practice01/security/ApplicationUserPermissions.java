package com.doom.practice01.security;

public enum ApplicationUserPermissions {
  COURSE_READ("course:read"),
  COURSE_WRITE("course:write"),
  STUDENT_READ("student:read"),
  STUDENT_WRITE("student:write");

  private final String permission;

  ApplicationUserPermissions(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
