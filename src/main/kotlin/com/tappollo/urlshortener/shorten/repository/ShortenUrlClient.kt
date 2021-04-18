package com.tappollo.urlshortener.shorten.repository

import com.tappollo.urlshortener.shorten.dao.UrlConfigDao
import com.tappollo.urlshortener.shorten.dao.UrlConfigEntity
import com.tappollo.urlshortener.shorten.entity.DomainConfig
import com.tappollo.urlshortener.shorten.entity.UrlConfig
import com.tappollo.urlshortener.utils.UrlShortener
import com.tappollo.urlshortener.utils.ext.toLocalDateTime
import com.tappollo.urlshortener.utils.ext.toMillis
import java.net.URI
import java.time.Clock
import java.time.LocalDateTime
import java.util.*

class ShortenUrlClient(
    private val urlConfigDao: UrlConfigDao,
    private val domainConfig: DomainConfig,
    private val clock: Clock = Clock.systemDefaultZone()
) : ShortenUrlRepository {
    override fun saveUrl(url: URI): URI {
        val targetUrl = url.toString()

        var entity = urlConfigDao.findByUrl(targetUrl)

        if (entity.isEmpty) {
            entity = Optional.of(urlConfigDao.save(UrlConfigEntity(targetUrl, LocalDateTime.now(clock).toMillis())))
        }

        return entity.get().id?.let {
            URI.create("${domainConfig.domain}/${UrlShortener.encode(it)}")
        } ?: throw IllegalStateException("ID cannot be null")
    }

    override fun getAllUrls(): List<UrlConfig> = urlConfigDao.findAll().map {
        it.toUrlConfig(domainConfig)
    }

    override fun getUrl(key: String): Optional<URI> {
        val id = UrlShortener.decode(key)

        val findById = urlConfigDao.findById(id)

        return if (findById.isPresent) {
            Optional.of(URI.create(findById.get().targetUrl))
        } else {
            Optional.empty()
        }
    }

}

private fun UrlConfigEntity.toUrlConfig(domainConfig: DomainConfig): UrlConfig {
    val innerId = id ?: throw IllegalStateException("Id cannot be null!")

    return UrlConfig(
        "${domainConfig.domain}/${UrlShortener.encode(innerId)}",
        targetUrl,
        createdAt
    )

}