package com.tappollo.urlshortener.shorten.entity

import java.net.URI
import java.time.LocalDateTime

class UrlConfig(
    val shortenUrl: URI,
    val targetUrl: String,
    val createdAt: LocalDateTime
)