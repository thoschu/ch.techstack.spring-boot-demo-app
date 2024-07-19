package ch.techstack.demo_rest_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {

//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.build();
//    }

//    @Bean
//    SecurityFilterChain filterChainUser(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(request -> {
//                            request
//                                    .requestMatchers("/user/**")
//                                    .authenticated();
//                        }
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable());
//        return http.build();
//    }

    @Bean
    SecurityFilterChain filterChainTodo(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request.requestMatchers("/todo/**").authenticated())
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("tom1")
                .password(passwordEncoder.encode("abc123"))
                .roles() // No roles for now
                .build();
        return new InMemoryUserDetailsManager(sarah);
    }

    @Bean
    SecurityFilterChain filterChainRegistration(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> {
                request
                    .requestMatchers("/registration/**")
                    .authenticated();
                }
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
