package org.serratec.ecommerce.config; 

import java.util.Arrays;

import org.serratec.ecommerce.security.JwtAuthenticationFilter;
import org.serratec.ecommerce.security.JwtAuthorizationFilter;
import org.serratec.ecommerce.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity 
public class ConfigSeguranca {

    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; 

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http .csrf(AbstractHttpConfigurer::disable) 
           .cors(cors -> cors.configurationSource(corsConfigurationSource()))
           .httpBasic(AbstractHttpConfigurer::disable) 
           .authorizeHttpRequests(requests -> requests
                      
           .requestMatchers("/", "/public/**").permitAll() 
           .requestMatchers(HttpMethod.GET, "/produtos", "/categorias").permitAll()
           .requestMatchers("/auth/login").permitAll() 
           .requestMatchers(HttpMethod.GET, "/usuario").hasRole("CLIENTE") 
           .requestMatchers(HttpMethod.POST, "/pedidos").hasRole("USER")
           .requestMatchers("/admin/**").hasRole("ADMIN")
           .anyRequest().authenticated()
                )
           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .addFilter(new JwtAuthenticationFilter(
            authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil))
           .addFilter(new JwtAuthorizationFilter(
            authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));

        return http.build();
    }

    @Bean 
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean 
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

