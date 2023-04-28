//package com.sogeti.filmland.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/dashboard")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .and()
//                .sessionManagement()
//                .maximumSessions(1)
//                .sessionRegistry(sessionRegistry());
//    }
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//}
//
//    In the configure(HttpSecurity http) method, we're configuring Spring Security to create an HTTP session upon successful authentication and store the authentication information in that session. We're also setting a maximum of one session per user to prevent multiple logins from the same account.
//
//        In your controller class, you can retrieve the authenticated user by adding the @AuthenticationPrincipal annotation to a method parameter, like so:
//
//        java
//
//@GetMapping("/categories")
//public ResponseEntity<?> getCategories(@AuthenticationPrincipal User user) {
//        // Use the "user" object to retrieve the authenticated user's information
//        }
//
//        This will allow you to retrieve the authenticated user's information in the getCategories method.
//
//        With these steps, your backend should now keep the user authenticated after logging in through the /login endpoint.
