package dev.mikoto2000.todo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

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
        )
        .oauth2Login();

        http.csrf();

        return http.build();
    }
}
