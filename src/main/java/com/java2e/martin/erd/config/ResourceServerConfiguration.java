package com.java2e.martin.erd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/11/10
 * @describtion ResourceServerConfiguration
 * @since 1.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Value("${server.port}")
    private Integer port;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Primary
    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:" + port + "/erd/oauth/check_token");
        remoteTokenServices.setClientId("client1");
        remoteTokenServices.setClientSecret("123456");
        return remoteTokenServices;
    }
}
