package com.tappollo.urlshortener.shorten.repository

import com.nhaarman.mockitokotlin2.*
import com.tappollo.urlshortener.shorten.dao.UrlConfigDao
import com.tappollo.urlshortener.shorten.dao.UrlConfigEntity
import com.tappollo.urlshortener.shorten.entity.DomainConfig
import com.tappollo.urlshortener.utils.UrlShortener
import com.tappollo.urlshortener.utils.ext.toMillis
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import java.net.URI
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class ShortenUrlRepositoryTest {

    private val clock: Clock
    private val currentDate: LocalDateTime

    init {
        val localDate = LocalDate.of(2021, 2, 4)

        val systemDefault = ZoneId.of("America/New_York")

        clock = Clock.fixed(
            localDate.atStartOfDay(systemDefault).toInstant(),
            systemDefault
        )

        currentDate = LocalDateTime.now(clock)
    }

    @Test
    fun `Save Url should be saved properly`() {
        val urlConfigEntity = UrlConfigEntity("https://google.com.ua", currentDate.toMillis())
        val returnEntity = UrlConfigEntity("https://google.com.ua", currentDate.toMillis())
        returnEntity.id = 1

        val urlConfigDao: UrlConfigDao = mock {
            on { save(urlConfigEntity) } doReturn returnEntity
        }

        val domainConfig = DomainConfig("http://localhost")
        val shortenUrlRepository = ShortenUrlClient(urlConfigDao, domainConfig, clock)

        val saveUrl = shortenUrlRepository.saveUrl(URI.create("https://google.com.ua"))


        saveUrl shouldBe URI.create("http://localhost/${UrlShortener.encode(1)}")

        val captor = ArgumentCaptor.forClass(UrlConfigEntity::class.java)

        verify(urlConfigDao).save(capture(captor))
        val capture = captor.value
        capture.id shouldBe null
        capture.createdAt shouldBe currentDate.toMillis()
        capture.targetUrl shouldBe "https://google.com.ua"
    }

    @Test
    fun `Already exist url should not be saved`() {
        val urlConfigEntity = UrlConfigEntity("https://google.com.ua", currentDate.toMillis()).apply {
            id = 1
        }

        val urlConfigDao: UrlConfigDao = mock {
            on { findByUrl("https://google.com.ua") } doReturn Optional.of(urlConfigEntity)
        }

        val domainConfig = DomainConfig("http://localhost")
        val shortenUrlRepository = ShortenUrlClient(urlConfigDao, domainConfig, clock)

        val saveUrl = shortenUrlRepository.saveUrl(URI.create("https://google.com.ua"))

        saveUrl shouldBe URI.create("http://localhost/${UrlShortener.encode(1)}")

        verify(urlConfigDao).findByUrl(eq("https://google.com.ua"))
    }

    @Test
    fun `Get all urls should return all saved url configs`() {
        val urls = listOf(
            UrlConfigEntity("https://google.com", currentDate.toMillis()).apply {
                id = 1
            },
            UrlConfigEntity("https://yahoo.com", currentDate.toMillis()).apply {
                id = 2
            },
            UrlConfigEntity("https://bing.com", currentDate.toMillis()).apply {
                id = 3
            },
        )

        val urlConfigDao: UrlConfigDao = mock {
            on { findAll() } doReturn urls
        }

        val domainConfig = DomainConfig("http://localhost")
        val shortenUrlRepository = ShortenUrlClient(urlConfigDao, domainConfig, clock)

        val urlConfigs = shortenUrlRepository.getAllUrls()

        urlConfigs.size shouldBe 3

        with(urlConfigs[0]) {
            shortenUrl shouldBe URI.create("http://localhost/${UrlShortener.encode(1)}")
            targetUrl shouldBe "https://google.com"
            createdAt shouldBe currentDate
        }

        with(urlConfigs[1]) {
            shortenUrl shouldBe URI.create("http://localhost/${UrlShortener.encode(2)}")
            targetUrl shouldBe "https://yahoo.com"
            createdAt shouldBe currentDate
        }

        with(urlConfigs[2]) {
            shortenUrl shouldBe URI.create("http://localhost/${UrlShortener.encode(3)}")
            targetUrl shouldBe "https://bing.com"
            createdAt shouldBe currentDate
        }

        verify(urlConfigDao).findAll()
    }

    @Test
    fun `Get URL should return correct saved url`() {
        val configEntity = UrlConfigEntity("https://google.com", currentDate.toMillis()).apply {
            id = 1
        }

        val urlConfigDao: UrlConfigDao = mock {
            on { findById(1) } doReturn Optional.of(configEntity)
        }

        val domainConfig = DomainConfig("http://localhost")
        val shortenUrlRepository = ShortenUrlClient(urlConfigDao, domainConfig, clock)

        val url = shortenUrlRepository.getUrl("a")

        url.isEmpty shouldBe false
        url.get() shouldBe URI.create("https://google.com")
    }
}