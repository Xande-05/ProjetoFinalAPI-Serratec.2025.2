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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        http .csrf(AbstractHttpConfigurer::disable)
           .cors(cors -> cors.configurationSource(corsConfigurationSource()))
           .httpBasic(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(requests -> requests

               
                .requestMatchers("/", "/public/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes").permitAll() 
                .requestMatchers(HttpMethod.GET, "/produtos/**", "/categorias/**").permitAll() 
                .requestMatchers(HttpMethod.GET, "/usuarios/{id}/foto").permitAll() 

                
                .requestMatchers(HttpMethod.POST, "/pedidos").authenticated() 
                .requestMatchers(HttpMethod.GET, "/pedidos/**", "/pedidos").authenticated()
                .requestMatchers(HttpMethod.PUT, "/pedidos/**").authenticated() 
        
                
                .requestMatchers("/admin/**").hasAuthority("ADMIN") 
                .requestMatchers(HttpMethod.POST, "/produtos/**", "/categorias/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/produtos/**", "/categorias/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/produtos/**", "/categorias/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/clientes/**", "/usuarios/**").hasAuthority("ADMIN") 
                .requestMatchers(HttpMethod.DELETE, "/clientes/**", "/usuarios/**").hasAuthority("ADMIN") 
                .requestMatchers(HttpMethod.PUT, "/usuarios/{id}/promover-admin").hasAuthority("ADMIN") 

               
                .requestMatchers(HttpMethod.POST, "/usuarios/{id}/foto").authenticated() 

                
                .anyRequest().authenticated() 
                )
           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtUtil))
           .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
       
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173", "http://127.0.0.1:5173")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition")); 
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