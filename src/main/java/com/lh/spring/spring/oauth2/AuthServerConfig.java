package com.lh.spring.spring.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LettuceConnectionFactory connectionFactory;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("SampleClientId")
                .resourceIds(ResourceServerConfiguration.DEMO_RESOURCE_ID)
                .secret("secret")
                .authorizedGrantTypes("authorization_code")
                .scopes("select")
                .autoApprove(true)
                // .authorities("USER")
                .redirectUris("http://localhost:8082/ui/login", "http://localhost:8083/ui2/login", "http://localhost:8082/login")
        // .accessTokenValiditySeconds(3600)
        ; // 1 hour
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(connectionFactory));
    }
}
