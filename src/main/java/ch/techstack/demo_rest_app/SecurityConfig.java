package ch.techstack.demo_rest_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// https://reflectoring.io/spring-resttemplate/

@Configuration
@EnableWebSecurity
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

//    @Bean
//    SecurityFilterChain filterChainTest(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(
//                        request -> request.requestMatchers("/test").authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }

//    @Bean
//    SecurityFilterChain filterChainRegistration(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(
//                        request -> request.requestMatchers("/registration").authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }

//    @Bean
//    SecurityFilterChain filterChainRegistration(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(request ->
//                    request
//                        .requestMatchers("/registration/**")
//                            .permitAll()
//                        //.hasRole("OWNER")
//                        //.fullyAuthenticated()
//
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//
//        ;
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                        .permitAll()
//                )
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/greeting/**").authenticated()
                )
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/todo/**").authenticated()
                )
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/registration").authenticated()
                )
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/user/**").authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder encoder) {
        User.UserBuilder users = User.builder();

        UserDetails tom1 = users
                .username("tom1")
                .password(encoder.encode("abc123"))
                .roles() // No roles for now
                .build();

        UserDetails sarah = users
                .username("sarah")
                .password(encoder().encode("xyz123"))
                .roles("OWNER") // new role
                .build();

        return new InMemoryUserDetailsManager(tom1, sarah);
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
