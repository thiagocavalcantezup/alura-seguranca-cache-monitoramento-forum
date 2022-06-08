package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityConfigurations(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/topicos")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/auth")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(
                new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                           .antMatchers(
                               "/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
                               "/swagger-resources/**"
                           );
    }

}

// FONTES:
//
// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
// https://stackoverflow.com/questions/72493425/how-to-update-deprecated-websecurityconfigureradapter-with-userdetailsservice-in
// https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
// https://stackoverflow.com/questions/57020818/authenticationmanager-authenticates-gives-me-stackoverflowerror
