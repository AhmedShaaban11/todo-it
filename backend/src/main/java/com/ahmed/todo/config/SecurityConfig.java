package com.ahmed.todo.config;

import com.ahmed.todo.config.filters.ErrorHandlerFilter;
import com.ahmed.todo.config.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtFilter jwtFilter;
  private final ErrorHandlerFilter errorHandlerFilter;
  private final UserDetailsService userDetailsService;

  public SecurityConfig(JwtFilter jwtFilter, ErrorHandlerFilter errorHandlerFilter, UserDetailsService userDetailsService) {
    this.jwtFilter = jwtFilter;
    this.errorHandlerFilter = errorHandlerFilter;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:" + System.getenv("FRONTEND_PORT")));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(List.of("content-type", "X-XSRF-TOKEN"));
    config.setAllowCredentials(true);
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/v1/**", config);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.cors(Customizer.withDefaults())
        .csrf(csrf -> {
          var tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
          tokenRepository.setCookieCustomizer(cookie -> {
            cookie.path("/");
            cookie.sameSite("Strict");
            cookie.secure(true);
          });
          var delegate = new CsrfTokenRequestAttributeHandler();
          csrf.csrfTokenRepository(tokenRepository);
          csrf.csrfTokenRequestHandler(delegate);
        })
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/api/v1/access/**").permitAll();
          auth.requestMatchers("/api/v1/*/**").authenticated();
          auth.anyRequest().denyAll();
        })
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(errorHandlerFilter, jwtFilter.getClass())
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authenticationProvider);
  }

}
