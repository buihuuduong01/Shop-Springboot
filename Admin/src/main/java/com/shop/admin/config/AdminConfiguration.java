// file này chứa cấu hình bảo mật cho ứng dụng Spring Boot,
// xác định các quy tắc bảo mật cho các URL khác nhau trong ứng dụng
// và cung cấp cách xác thực người dùng khi họ truy cập các phần khác nhau của ứng dụng web.
package com.shop.admin.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AdminConfiguration {

    @Bean
    public UserDetailsService userDetailsService(){
        return new AdminServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity  http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authorizeHttpRequests()
                //Cấu hình cho việc truy cập các tài nguyên trong static
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // quyền truy cập đối với các URL bắt đầu bằng "/admin/**"
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/register","/forgot-password","/register-new").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/do-login")
                .defaultSuccessUrl("/index")

                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .authenticationManager(authenticationManager)
        ;
        return http.build();
    }
}