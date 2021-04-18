package com.tappollo.urlshortener.shorten.config

import com.tappollo.urlshortener.shorten.dao.UrlConfigDao
import com.tappollo.urlshortener.shorten.entity.DomainConfig
import com.tappollo.urlshortener.shorten.repository.ShortenUrlClient
import com.tappollo.urlshortener.shorten.repository.ShortenUrlRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("com.tappollo.urlshortener.shorten.dao")
@EnableJpaRepositories("com.tappollo.urlshortener.shorten.dao")
@ImportResource("classpath:bean_config.xml")
class ShortenConfig {

    @Bean
    fun provideShortenRepo(
        urlConfigDao: UrlConfigDao,
        domainConfig: DomainConfig
    ): ShortenUrlRepository = ShortenUrlClient(urlConfigDao, domainConfig)

}