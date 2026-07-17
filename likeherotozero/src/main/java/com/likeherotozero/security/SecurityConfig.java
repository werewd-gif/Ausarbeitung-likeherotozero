package com.likeherotozero.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
public class SecurityConfig {

    // Sicherheitsregeln
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/api/**", "/logout")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            )

            .authorizeHttpRequests(auth -> auth
            	    // Public pages
            	    .requestMatchers("/", "/emissions/**").permitAll()

            	    // H2 console (dev)
            	    .requestMatchers("/h2-console/**").permitAll()

            	    // Public API: only GET is public
            	    .requestMatchers(HttpMethod.GET, "/api/emissions/**").permitAll()

            	    // Create emissions: only scientist/admin
            	    .requestMatchers(HttpMethod.POST, "/api/emissions").hasAnyRole("SCIENTIST", "ADMIN")

            	    // Admin UI / admin API
            	    .requestMatchers("/admin/**").hasRole("ADMIN")

            	    // Add-emission page should require login
            	    .requestMatchers("/add-emission.html").hasAnyRole("SCIENTIST", "ADMIN")

            	    .anyRequest().permitAll()
            	)
            	    

            // Browser login
            .formLogin(form -> form
            	    .successHandler((request, response, authentication) -> {

            	        boolean isAdmin = authentication.getAuthorities().stream()
            	            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            	        if (isAdmin) {
            	            response.sendRedirect("/admin/emissions");
            	        } else {
            	            response.sendRedirect("/add-emission.html");
            	        }
            	    })
            	    .permitAll()

            )

            // Logout 
            .logout(logout -> logout
            	    .logoutUrl("/logout")
            	    .logoutSuccessUrl("/")
            	    .invalidateHttpSession(true)
            	    .deleteCookies("JSESSIONID")
            	    .permitAll()
            );

        return http.build();
    }


    // Login Nutzer 
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails scientist = User.builder()
            .username("scientist")
            .password(passwordEncoder.encode("scientist123"))
            .roles("SCIENTIST")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(scientist, admin);
    }

   
    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
