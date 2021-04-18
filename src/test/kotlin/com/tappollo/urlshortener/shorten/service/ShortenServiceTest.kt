package com.tappollo.urlshortener.shorten.service

import com.nhaarman.mockitokotlin2.*
import com.tappollo.urlshortener.shorten.entity.UrlConfig
import com.tappollo.urlshortener.shorten.repository.ShortenUrlRepository
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.util.*

class ShortenServiceTest {

    @Test
    fun `Correct url should be shorten properly`() {
        val uri = URI.create("http://localhost/a")

        val shortenUrlRepository = mock<ShortenUrlRepository> {
            on { saveUrl(any()) } doReturn uri
        }
        val shortenService = ShortenService(shortenUrlRepository)
        val shortenUrl = shortenService.shortenUrl("https://google.com")

        shortenUrl shouldBe uri.toString()

        verify(shortenUrlRepository).saveUrl(eq(URI.create("https://google.com")))
    }

    @Test
    fun `Incorrect url should be handled properly`() {
        val shortenService = ShortenService(mock())

        assertThrows<MalformedURLException> {
            shortenService.shortenUrl("htasdfasfm")
        }
    }

    @Test
    fun `Get all urls should be handled properly`() {
        val shortenUrlRepository = mock<ShortenUrlRepository> {
            on { getAllUrls() } doReturn listOf(
                UrlConfig("http://localhost/a", "https://google.com", 1L),
                UrlConfig("http://localhost/b", "https://yahoo.com", 2L)
            )
        }
        val shortenService = ShortenService(shortenUrlRepository)
        val urls = shortenService.getAllUrls()

        verify(shortenUrlRepository).getAllUrls()

        with(urls.first()) {
            shortenUrl shouldBe "http://localhost/a"
            targetUrl shouldBe "https://google.com"
            createdAt shouldBe 1L
        }

        with(urls.last()) {
            shortenUrl shouldBe "http://localhost/b"
            targetUrl shouldBe "https://yahoo.com"
            createdAt shouldBe 2L
        }
    }

    @Test
    fun `Get exist shorten url should be handled properly`() {
        val shortenUrlRepository = mock<ShortenUrlRepository> {
            on { getUrl("a") } doReturn Optional.of(URI.create("https://google.com"))
        }
        val shortenService = ShortenService(shortenUrlRepository)
        val url = shortenService.getUrl("a")

        verify(shortenUrlRepository).getUrl(eq("a"))

        url shouldBe "https://google.com"
    }

    @Test
    fun `Get not exist shorten url should be handled properly`() {
        val shortenUrlRepository = mock<ShortenUrlRepository> {
            on { getUrl("a") } doReturn Optional.empty()
        }
        val shortenService = ShortenService(shortenUrlRepository)

        assertThrows<NullPointerException> {
            shortenService.getUrl("a")
        }
    }

}