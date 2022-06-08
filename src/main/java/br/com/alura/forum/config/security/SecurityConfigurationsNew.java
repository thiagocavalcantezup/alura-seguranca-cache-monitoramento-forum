/* package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigurationsNew {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/topicos")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

} */

// Sources:
//
// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
// https://stackoverflow.com/questions/72493425/how-to-update-deprecated-websecurityconfigureradapter-with-userdetailsservice-in
// https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
// https://stackoverflow.com/questions/57020818/authenticationmanager-authenticates-gives-me-stackoverflowerror
