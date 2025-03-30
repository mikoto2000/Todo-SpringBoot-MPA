package dev.mikoto2000.todo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ClientRegistrationRepository clientRegistrationRepository;

    /**
     * 認可のための SecurityFilterChain を設定
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorize -> authorize
            // /admin 以下へのアクセスには admin 権限が必要
            .requestMatchers("/admin").hasAuthority("admin")
            // それ以外へのアクセスは認証だけは必要
            .anyRequest().authenticated()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(oidcLogoutSuccessHandler())
            .permitAll()
        )
        .oauth2Login();

        http.csrf();

        return http.build();
    }

    LogoutSuccessHandler oidcLogoutSuccessHandler() {
      OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler =
              new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

      oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/");

      return oidcClientInitiatedLogoutSuccessHandler;
    }
}
