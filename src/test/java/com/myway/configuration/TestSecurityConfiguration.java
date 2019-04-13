package com.myway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Ebru Göksal
 */
@Configuration
@Profile("test")
@Order(1)
public class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

}
