package com.example.store.config;

import com.example.store.security.JwtAuthenticationFilter;
import com.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .filterSecurityInterceptorOncePerRequest(true)
                .antMatchers("/users/login").permitAll()

                .antMatchers(HttpMethod.POST,"/products/add").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET, "/products").hasAnyAuthority("admin", "user")
                .antMatchers(HttpMethod.GET, "/products/{id:\\d+}").hasAnyAuthority("admin", "user")
                .antMatchers(HttpMethod.DELETE, "/products/delete/{id:\\d+}").hasAnyAuthority("admin");

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
