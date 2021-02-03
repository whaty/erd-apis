package com.java2e.martin.erd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 狮少
 * @version 1.0
 * @date 2019/5/17
 * @describtion WebSecurityConfigurer
 * @since 1.0
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }


    // password 方案三：支持多种编码，通过密码的前缀区分编码方式,推荐
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        HttpMethod.OPTIONS,
                        "/**/*.js",
                        "/**/*.css",
                        "/erd/oauth/**",
                        "/oauth/**",
                        "/actuator/**"

                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                // 自动登录
                /*.and()
                    .rememberMe()
                    // 加密的秘钥
                    .key("unique-and-secret")
                    // 存放在浏览器端cookie的key
                    .rememberMeCookieName("remember-me-cookie-name")
                    // token失效的时间，单位为秒
                    .tokenValiditySeconds(60 * 60 * 25)*/
                .and()
                // 暂时禁用CSRF，否则无法提交登录表单
                .csrf().disable();
    }
}
