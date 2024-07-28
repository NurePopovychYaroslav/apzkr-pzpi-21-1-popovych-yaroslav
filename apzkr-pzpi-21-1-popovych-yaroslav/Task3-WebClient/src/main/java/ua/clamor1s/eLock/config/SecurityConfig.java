package ua.clamor1s.eLock.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(login -> login
                        .tokenEndpoint(Customizer.withDefaults())
                        .userInfoEndpoint(Customizer.withDefaults()))

                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .authorizeHttpRequests(req -> req
                        .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**", "/", "/error").permitAll()
                        .anyRequest().fullyAuthenticated()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("http://localhost:19090/realms/elock/protocol/openid-connect/logout?post_logout_redirect_uri=http://localhost:8080&client_id=elock-client"))
                .build();
    }
}
