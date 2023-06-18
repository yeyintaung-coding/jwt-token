package com.example.authorizationdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        var manager=new InMemoryUserDetailsManager();

        var user1= User.withUsername("john")
                .password("12345")
//                .authorities("READ")
//                .authorities("ROLE_ADMIN")
                .roles("ADMIN")
                .build();

        var user2=User.withUsername("jane")
                .password("12345")
//                .authorities("WRITE","READ","DELETE")
                .authorities("ROLE_MANAGER")
                .build();



        manager.createUser(user1);
        manager.createUser(user2);
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

//        http.authorizeRequests().anyRequest()
//                .hasAuthority("WRITE");
//                .hasAnyAuthority("READ","WRITE");
//                .access("hasAuthority('WRITE')");
//                .hasRole("ADMIN");

        http.authorizeRequests()
                .mvcMatchers("/hello").hasRole("ADMIN")
                .mvcMatchers("/greet").hasRole("MANAGER")
//                .anyRequest().permitAll();
                .anyRequest().authenticated();
    }
}
