package com.userCrudSpring.config;

import com.userCrudSpring.handler.AccessDeniedHandlerImpl;
import com.userCrudSpring.handler.AuthenticationFailureHandlerImpl;
import com.userCrudSpring.handler.AuthenticationSuccessHandlerImpl;
import com.userCrudSpring.model.RoleType;
import com.userCrudSpring.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    // Registrar UserDetailsService implimentation
    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                // Pass encoder/decoder (not native pass data, bad practice)
                .passwordEncoder(encoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF def
        http.csrf()
                .disable()
                .authorizeRequests()
                // Permit for resources
                .antMatchers( "/static", "/static/**").permitAll()
                .antMatchers("/admin", "/admin/*").hasAuthority(RoleType.ADMIN.name())
                .antMatchers("/user", "/user/*").hasAuthority(RoleType.USER.name())
                .anyRequest().denyAll();
        
        // Handle accept denied
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());
        
        http.formLogin()
                // Url with login page
                .loginPage("/")
                // Url for proceed
                .loginProcessingUrl("/sign-in")
                // Url for fail login
                .failureUrl("/")
                .failureHandler(new AuthenticationFailureHandlerImpl())
                .successHandler(new AuthenticationSuccessHandlerImpl())
                // Login and password param names
                .usernameParameter("login")
                .passwordParameter("password")
                // Set permit for all users from internet
                .permitAll();
        
        http.logout()
                // Permit all for sign out
                .permitAll()
                // Set url for logout
                .logoutUrl("/sign-out")
                // Set url for fail logout
                .logoutSuccessUrl("/")
                // Invalidate current session
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
    
    // BCrypt encoder bean
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    // @Bean
    // GrantedAuthorityDefaults grantedAuthorityDefaults() {
    //     return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    // }
}
