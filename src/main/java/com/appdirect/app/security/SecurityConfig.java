package com.appdirect.app.security;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.provider.*;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${oauth.consumer.key}")
    private String consumerKey;

    @Value("${oauth.consumer.secret}")
    private String consumerSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").csrf().disable();
//            .exceptionHandling().authenticationEntryPoint(new UnauthorizedRequestExceptionHandler())
//            .and()
//            .authorizeRequests().anyRequest().permitAll()
//            .and()
//            .addFilterBefore(oAuthProviderProcessingFilter(), ConcurrentSessionFilter.class);
    }

    @Bean
    //@Order(1)
    public OAuthProviderProcessingFilter oAuthProviderProcessingFilter() {

        final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter() {

            @Override
            protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
                OAuthProcessingFilterEntryPoint authenticationEntryPoint = new OAuthProcessingFilterEntryPoint();
                setAuthenticationEntryPoint(authenticationEntryPoint);
                String realmName = request.getRequestURL().toString();
                logger.info("RealmName: {}", realmName);
                authenticationEntryPoint.setRealmName(realmName);
                return true;
            }
        };
        filter.setConsumerDetailsService(consumerDetailsService());
        filter.setTokenServices(inMemoryProviderTokenServices());

        return filter;
    }

    @Bean
    public ConsumerDetailsService consumerDetailsService() {
        final BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(consumerKey);
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(consumerSecret));
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);

        final InMemoryConsumerDetailsService consumerDetailsService = new InMemoryConsumerDetailsService();
        consumerDetailsService.setConsumerDetailsStore(new HashMap<String, ConsumerDetails>() {{
            put(consumerKey, consumerDetails);
        }});
        return consumerDetailsService;
    }

    @Bean
    public InMemoryProviderTokenServices inMemoryProviderTokenServices() {
        return new InMemoryProviderTokenServices();
    }

    @Bean
    public ProtectedResourceDetails protectedResourceDetails() {
        final BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
        resource.setConsumerKey(consumerKey);
        resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));
        return resource;
    }

    private class UnauthorizedRequestExceptionHandler implements AuthenticationEntryPoint {

        private Logger logger = Logger.getLogger(UnauthorizedRequestExceptionHandler.class);

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            logger.info("Request not authorized");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }

    }
}
