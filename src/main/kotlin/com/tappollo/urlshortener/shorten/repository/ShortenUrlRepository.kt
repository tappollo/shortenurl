package com.tappollo.urlshortener.shorten.repository

import com.tappollo.urlshortener.shorten.entity.UrlConfig
import java.net.URI
import java.net.URL
import java.util.*

interface ShortenUrlRepository {

    fun saveUrl(url: URI): URI

    fun getAllUrls(): List<UrlConfig>

    fun getUrl(key: String): Optional<URI>
}