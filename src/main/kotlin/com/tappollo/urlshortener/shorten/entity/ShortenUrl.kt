package com.tappollo.urlshortener.shorten.entity

import java.time.LocalDateTime

class ShortenUrl(
    val shortUrl: String,
    val fullUrl: String,
    val createdAt: LocalDateTime
)