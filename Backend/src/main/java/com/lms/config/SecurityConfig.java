
package com.lms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    logger.info("Configuring HttpSecurity for the application");

    http
      .csrf(csrf -> {
        csrf.disable();
        logger.info("CSRF is disabled");
      })
      .authorizeHttpRequests(authorize -> authorize
        .anyRequest().authenticated()
      )
      .formLogin(formLogin -> {
        formLogin
          .loginProcessingUrl("/perform_login")
          .defaultSuccessUrl("/", true)
          .successHandler((request, response, authentication) -> logger.info("Login success for user: {}", authentication.getName()))
          .failureHandler((request, response, exception) -> logger.error("Login failure: {}", exception.getMessage()));
        logger.info("Form login is configured");
      })
      .logout(logout -> {
        logout
          .logoutUrl("/logout")
          .invalidateHttpSession(true) // Invalidate the session on logout
          .deleteCookies("JSESSIONID") // Optional: delete session cookie
          .logoutSuccessUrl("/login?logout"); // Redirect after logout
        logger.info("Logout is configured");
      })

      .httpBasic(httpBasic -> {
        httpBasic
          .authenticationEntryPoint(authenticationEntryPoint());
        logger.info("HTTP Basic authentication is configured");
      })
      .sessionManagement(sessionManagement -> {
        sessionManagement
          .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          .maximumSessions(1) // Limit to one session per user
          .maxSessionsPreventsLogin(true) // Block login attempts when the max session is reached
          .sessionRegistry(sessionRegistry()); // Use a session registry to track active sessions
        logger.info("Session management is configured for single session per user");
      });

    return http.build();
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }


  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    logger.info("Configuring AuthenticationEntryPoint");
    return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    logger.info("Configuring global (AuthenticationManagerBuilder)");
    auth
      .ldapAuthentication()
      .userDnPatterns("uid={0},ou=people")
      .groupSearchBase("ou=groups")
      .contextSource()
      .url("ldap://localhost:8389/dc=springframework,dc=org")
      .and()
      .passwordCompare()
      .passwordEncoder(new BCryptPasswordEncoder())
      .passwordAttribute("userPassword");
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          .allowedOrigins("http://localhost:4200")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
          .allowedHeaders("*")
          .allowCredentials(true);
        logger.info("CORS is configured");
      }
    };
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    logger.info("Configuring HttpSessionEventPublisher for session event publishing");
    return new HttpSessionEventPublisher();
  }
}
