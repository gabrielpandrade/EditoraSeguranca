package dw.secauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${app.cognito.logout-url}")
    private String logoutURL;

    @Value("${app.cognito.redirect-uri}")
    private String redirectURI;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/", "/secauth", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .defaultSuccessUrl("/principal", true)
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(new CustomLogoutHandler(logoutURL, redirectURI, clientId))
                );

        return http.build();
    }
}
