package jwt.contact.authTool.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; 
// This is used to import the Bean annotation
import org.springframework.context.annotation.Configuration; // This is used to import the Configuration annotation
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
// This is used to import the HttpSecurity class
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jwt.contact.authTool.Model.MyAppUserService;
import lombok.AllArgsConstructor;

@Configuration // This is a configuration class
@EnableWebSecurity // This is used to enable web security
@AllArgsConstructor
public class SecurityConfig { // This is a class named SecurityConfig


    @Autowired 
    // This annotation is used to let Spring know that it should inject 
    //an instance of MyAppUserService here
    private final MyAppUserService myAppUserService;

    @Bean // This is a method named userDetailsService
    public UserDetailsService userDetailsService(){ 
        return myAppUserService;
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() { 
        // This is a method named authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
        // This is used to create a new DaoAuthenticationProvider object
        provider.setUserDetailsService(myAppUserService); 
        // This is used to set the user details service
        provider.setPasswordEncoder(passwordEncoder()); 
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity
                .formLogin(login -> login 
                        .loginPage("/login") // This is used to set the login page
                        .permitAll() // This is used to permit all
                        
                )
                .authorizeHttpRequests(auth -> auth // This is used to authorize HTTP requests
                        .requestMatchers("/","/login", "/req/signup", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated() // This is used to authenticate any request
                )                
                .build(); // This is used to build the security filter chain
    }

   
}
