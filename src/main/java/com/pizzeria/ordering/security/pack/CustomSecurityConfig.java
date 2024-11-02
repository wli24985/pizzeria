package com.pizzeria.ordering.security.pack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
	    httpSecurity
		    // .authorizeHttpRequests((requests) -> requests
            //     .requestMatchers("*").permitAll()
            //     .anyRequest().authenticated()
            // )
            // .formLogin((form) -> form
            //     //.loginPage("/login")
            //     .permitAll()
            // )
            //.logout((logout) -> logout.permitAll())
		    .csrf(csrf -> csrf.disable());
	    return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("pUser")
            .password("{bcrypt}$2a$10$R9rAYuViwomBnev65JuoFe5F.hzXottaEkHlUGkW9nvqhRFXjRisC")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

}