package com.example.myapp.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http)
                        throws Exception {
                http.headers(
                                headersConfigurer -> headersConfigurer
                                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/book/valoration/new/**", "/book/valoration/new/submit")
                                .hasAnyRole("ADMIN", "USER", "MANAGER")
                                .requestMatchers("/book/valoration/delete/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/book/new", "/book/new/submit").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/book/edit/**", "/book/edit/submit").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/book/delete/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/book/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                                .requestMatchers("/genre/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                                .requestMatchers("/h2-console/**").hasRole("ADMIN")
                                .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated())
                                .formLogin(formLogin -> formLogin
                                                .defaultSuccessUrl("/public/home", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .permitAll())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers(
                                                                AntPathRequestMatcher.antMatcher("/h2-console/**")))
                                .httpBasic(Customizer.withDefaults());
                http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/public/accessError"));
                return http.build();
        }
}
