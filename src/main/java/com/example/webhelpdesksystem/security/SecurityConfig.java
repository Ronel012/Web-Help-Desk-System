package com.example.webhelpdesksystem.security;

import com.example.webhelpdesksystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    // FILTER THE END POINT
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()

                // EMPLOYEE
                .antMatchers(HttpMethod.GET, "/employees").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/employees/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/employees").hasRole("ADMIN")

                // TICKET
                .antMatchers(HttpMethod.GET, "/tickets").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/tickets/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/tickets/employee/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/tickets/**/watchers").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/tickets").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/tickets/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/tickets/**").hasRole("ADMIN")

                .and()
                .formLogin()
                .and()
                .build();
    }

    // AUTHENTICATION
    // HARD CODED
    @Bean
    public UserDetailsService userDetailsService() {

        // ADMIN
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        // USER
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        // NEED TO STORE THE ADMIN/USER IN MEMORY
        return new InMemoryUserDetailsManager(admin, user);
    }

    //PASSWORD
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    END OF CLASS
}
