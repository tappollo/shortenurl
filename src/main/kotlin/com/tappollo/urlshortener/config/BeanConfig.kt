package com.tappollo.urlshortener.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("com.tappollo.urlshortener.shorten.dao")
@EnableJpaRepositories("com.tappollo.urlshortener.shorten.dao")
class BeanConfig