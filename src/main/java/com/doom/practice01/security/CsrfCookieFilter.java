package com.doom.practice01.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

class CsrfCookieFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    // Accessing the token forces generation so the cookie is written
    CsrfToken token = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
    if (token != null) { token.getToken(); }
    chain.doFilter(req, res);
  }
}

