package com.tappollo.urlshortener.shorten.service

import com.tappollo.urlshortener.shorten.entity.UrlConfig
import com.tappollo.urlshortener.shorten.repository.ShortenUrlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URL

@Service
class ShortenService(
    @Autowired
    private val shortenUrlRepository: ShortenUrlRepository
) {

    fun shortenUrl(url: String): String {
        val uri = URL(url).toURI()
        return shortenUrlRepository.saveUrl(uri).toString()
    }

    fun getAllUrls(): List<UrlConfig> = shortenUrlRepository.getAllUrls()

    fun getUrl(key: String): String {
        val url = shortenUrlRepository.getUrl(key)

        return if (url.isPresent) {
            url.get().toString()
        } else {
            throw NullPointerException()
        }
    }

}