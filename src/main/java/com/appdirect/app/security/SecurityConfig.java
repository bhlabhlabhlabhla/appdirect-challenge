package com.appdirect.app.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Main Configuration class which dictates security configurations into Spring Ecosystem.
 * Here we define all the filters and OAuth signature verification implementations which needs to be handled.
 *
 * oAuth related code is same as provided in Spring Security examples. I don't do anything special here but just feed
 * provided consumer key and secret from AppDirect account.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${oauth.consumer.key}")
    private String consumerKey;

    @Value("${oauth.consumer.secret}")
    private String consumerSecret;

    /**
     * HTTP custom configuration.
     *
     * All requests are defaulted to anonymous Authorization
     * On Path '/api/v1/integration/**' we added following:
     *      > Custom Filter to handle oAuth Signature verification on incoming requests.
     *      > Authorizes all requests on this URL without authentication.
     *
     * @param http HTTP SecurityBuilder object fed by Spring
     * @throws Exception Exception thrown in case something bad happens
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().anonymous();

        http.antMatcher("/api/v1/integration/**")
            //.exceptionHandling().authenticationEntryPoint(new UnauthorizedRequestExceptionHandler())
            //.and()
            .authorizeRequests().anyRequest().permitAll()
            .and()
            .addFilterBefore(oAuthProviderProcessingFilter(), ConcurrentSessionFilter.class);
    }

    /**
     * Custom Filter to handle oAuth Signature verification.
     * It instantiates Spring's OAuthProcessingFilterEntryPoint with consumer key and consumer secret from AppDirect security configuration
     * @return Custom Filter
     */
    @Bean
    public OAuthProviderProcessingFilter oAuthProviderProcessingFilter() {

        final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter() {

            @Override
            protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
                OAuthProcessingFilterEntryPoint authenticationEntryPoint = new OAuthProcessingFilterEntryPoint();
                setAuthenticationEntryPoint(authenticationEntryPoint);
                String realmName = request.getRequestURL().toString();
                authenticationEntryPoint.setRealmName(realmName);
                return true;
            }
        };
        filter.setConsumerDetailsService(consumerDetailsService());
        filter.setTokenServices(inMemoryProviderTokenServices());

        return filter;
    }

    /**
     * Implementation of ConsumerDetailsService interface to feed oAuth security details into Spring Filter
     * @return Custom Implementation of ConsumerDetailsService
     */
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

    /**
     * Configuration of Token service into Spring oAuth config
     *
     * Can be customized based on needs but we don't do anything special here.
     * @return Instance of InMemoryProviderTokenServices
     */
    @Bean
    public InMemoryProviderTokenServices inMemoryProviderTokenServices() {
        return new InMemoryProviderTokenServices();
    }

    /**
     * Implementation of Spring's ProtectedResourceDetails interface to set oAuth key and secret.
     *
     * @return ProtectedResourceDetails impl with AppDirect oAuth details.
     */
    @Bean
    public ProtectedResourceDetails protectedResourceDetails() {
        final BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
        resource.setConsumerKey(consumerKey);
        resource.setSharedSecret(new SharedConsumerSecretImpl(consumerSecret));
        return resource;
    }

    private class UnauthorizedRequestExceptionHandler implements AuthenticationEntryPoint {

        private Logger logger = LoggerFactory.getLogger(UnauthorizedRequestExceptionHandler.class);

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            logger.info("Request not authorized");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }

    }
}
