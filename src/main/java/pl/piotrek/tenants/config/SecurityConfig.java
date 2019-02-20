package pl.piotrek.tenants.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.piotrek.tenants.security.JwtAuthenticationEntryPoint;
import pl.piotrek.tenants.security.UserDetailsServiceImpl;

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .cors()
                    .and()
//                .exceptionHandling()
//                    .authenticationEntryPoint(unauthorizedHandler)
//                    .and()
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
                        "/**/*.js"
                    ).permitAll()
                    .anyRequest()
                .authenticated()
                    .and()
                .httpBasic();

//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
//                .inMemoryAuthentication()
//                    .passwordEncoder(passwordEncoder())
//                    .withUser("user")
//                    .password(passwordEncoder().encode("password"))
//                    .roles("USER");

    }


}