package com.doom.practice01.security;

import com.doom.practice01.auth.ApplicationUserService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final ApplicationUserService applicationUserService;

  @Autowired
  public ApplicationSecurityConfig(
      PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
    this.passwordEncoder = passwordEncoder;
    this.applicationUserService = applicationUserService;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Enable CSRF with XSRF if need -> uncomment this
    //    var repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
    //    var handler = new CsrfTokenRequestAttributeHandler();
    //    handler.setCsrfRequestAttributeName(null);
    //    http.csrf(csrf -> csrf.csrfTokenRepository(repo).csrfTokenRequestHandler(handler))
    //        .addFilterAfter(
    //            new CsrfCookieFilter(),
    //
    // org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class)
    //
    //
    http.csrf()
        .disable()
        .authorizeHttpRequests(
            auth -> {
              try {
                auth.requestMatchers("/error")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                    .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("theKeyForUsernameAndExpDate")
                    .rememberMeParameter("remember-me")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(
                        new AntPathRequestMatcher(
                            "/logout", "GET")) // delete this when .CSRF is enabled
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login"); // default to 2 weeks;

              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            })
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  AuthenticationManagerBuilder configure(AuthenticationManagerBuilder auth) {
    return auth.authenticationProvider(daoAuthProvider());
  }

  @Bean
  DaoAuthenticationProvider daoAuthProvider() {
    var p = new DaoAuthenticationProvider();
    p.setPasswordEncoder(passwordEncoder);
    p.setUserDetailsService(applicationUserService);
    return p;
  }
}
//  @Bean
//  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//    var repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
//    repo.setHeaderName("X-CSRF-TOKEN"); // align repo with default handler
//
//    http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//        .addFilterAfter(
//            new CsrfCookieFilter(),
//            org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class)
//        //        .csrf().disable()
//        .authorizeHttpRequests(
//            auth ->
//                auth.requestMatchers(
//                        "/", "/api/v1/students/all", "/index", "/index.html", "/favicon.ico")
//                    .permitAll()
//                    .requestMatchers("/api/**")
//                    .hasRole(STUDENT.name())
//                    //                    .requestMatchers(HttpMethod.DELETE,
// "management/api/**")
//                    //                    .hasAuthority(COURSE_WRITE.getPermission())
//                    //                    .requestMatchers(HttpMethod.PUT, "management/api/**")
//                    //                    .hasAuthority(COURSE_WRITE.getPermission())
//                    //                    .requestMatchers(HttpMethod.POST, "management/api/**")
//                    //                    .hasAuthority(COURSE_WRITE.getPermission())
//                    //                    .requestMatchers(HttpMethod.GET, "management/api/**")
//                    //                    .hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
//                    .anyRequest()
//                    .authenticated())
//        .httpBasic(Customizer.withDefaults());
//    return http.build();
//  }

// For using dummy users in InMemDB
// @Bean
// protected UserDetailsService userDetailsService() {
//  UserDetails annaSmith =
//      User.builder()
//          .username("anna")
//          .password(passwordEncoder.encode("anna"))
//          //            .roles(STUDENT.name()) // ROLE_STUDENT
//          .authorities(STUDENT.getGrantedAuthorities())
//          .build();
//
//  UserDetails mo =
//      User.builder()
//          .username("mo")
//          .password(passwordEncoder.encode("mo"))
//          //            .roles(ADMIN.name()) // ROLE_ADMIN
//          .authorities(ADMIN.getGrantedAuthorities())
//          .build();
//
//  UserDetails tom =
//      User.builder()
//          .username("tom")
//          .password(passwordEncoder.encode("tom"))
//          //            .roles(ADMIN_TRAINEE.name())
//          .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
//          .build();
//
//  return new InMemoryUserDetailsManager(annaSmith, mo, tom);
// }
