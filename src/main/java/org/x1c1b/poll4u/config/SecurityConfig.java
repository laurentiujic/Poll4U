package org.x1c1b.poll4u.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
import org.x1c1b.poll4u.security.UserPrincipalService;
import org.x1c1b.poll4u.web.auth.TokenAuthenticationEntryPoint;
import org.x1c1b.poll4u.web.auth.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("org.x1c1b.poll4u.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private UserPrincipalService principalService;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {

        return new TokenAuthenticationFilter();
    }

    @Bean public TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint() {

        return new TokenAuthenticationEntryPoint();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder
                .userDetailsService(principalService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(tokenAuthenticationEntryPoint())
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js")
                        .permitAll()
                    .antMatchers("/v2/api-docs",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**")
                        .permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/auth", "/api/v1/users")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/users/**", "/api/v1/polls/**")
                        .permitAll()
                    .anyRequest()
                        .authenticated();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
