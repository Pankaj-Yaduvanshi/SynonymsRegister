//package assignment.synonymRegister.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class AppSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return  http
//                .authorizeHttpRequests(authz -> {
//                    authz.requestMatchers("/api/fetch").hasAuthority("READER");
//                    authz.requestMatchers("/api/words").permitAll();
//                    authz.anyRequest().authenticated();
//                })
//                .oauth2Login(withDefaults())
//                .formLogin(withDefaults())
//                .build();
//    }
//}
