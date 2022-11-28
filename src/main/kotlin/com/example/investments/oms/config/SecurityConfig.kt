package com.example.investments.oms.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@Profile(value = ["!test"])
class SecurityConfig {

    @Throws(Exception::class)
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain =
        http
            .authorizeRequests {
                it
                    .antMatchers(HttpMethod.GET, "/orders/**").hasAuthority("SCOPE_orders.read")
                    .antMatchers(HttpMethod.POST, "/orders").hasAuthority("SCOPE_orders.write")
                    .antMatchers(HttpMethod.PUT, "/orders").hasAuthority("SCOPE_orders.write")
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2: OAuth2ResourceServerConfigurer<HttpSecurity?> -> oauth2.jwt() }
            .build()
}