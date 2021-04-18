package com.tappollo.urlshortener.shorten.service

import com.tappollo.urlshortener.shorten.entity.UrlConfig
import com.tappollo.urlshortener.shorten.repository.ShortenUrlRepository
import com.tappollo.urlshortener.utils.exception.IncorrectUrlException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URL

@Service
class ShortenService(
    @Autowired
    private val shortenUrlRepository: ShortenUrlRepository
) {

    @Throws(IncorrectUrlException::class)
    fun shortenUrl(url: String): String {
        return try {
            val uri = URL(url).toURI()
            shortenUrlRepository.saveUrl(uri).toString()
        } catch (e: Exception) {
            throw IncorrectUrlException()
        }
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