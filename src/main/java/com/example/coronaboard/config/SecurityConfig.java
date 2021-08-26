package com.example.coronaboard.config;

import com.example.coronaboard.security.MongoUserDetailService;
import com.example.coronaboard.security.filter.CustomAuthenticationFilter;
import com.example.coronaboard.security.filter.CustomAuthorizationFilter;
import com.example.coronaboard.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@PropertySource("classpath:jwt.properties")
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MongoUserDetailService mongoUserDetailService;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Value("${corona.app.signUpUrl}")
    private String signUpUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(customAuthenticationFilter()); // antetication filter
        http.addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // authorization
        http.authorizeRequests(authorize -> {
            authorize.antMatchers(HttpMethod.GET, "/corona/v1/time/**").permitAll();
        }).authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         /*               .and()
                .httpBasic()*/
    }
    @Bean
    public  CustomAuthorizationFilter customAuthorizationFilter(){
        return new CustomAuthorizationFilter(jwtUtils, mongoUserDetailService);
    }

    @Bean("customAuthenticationFilter")
    @Lazy
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(mongoUserDetailService,
                authenticationManagerBean(), jwtUtils, objectMapper);
        customAuthenticationFilter.setFilterProcessesUrl(signUpUrl);
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return  customAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
