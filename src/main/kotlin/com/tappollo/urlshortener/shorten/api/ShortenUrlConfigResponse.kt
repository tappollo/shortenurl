package com.tappollo.urlshortener.shorten.api

import com.fasterxml.jackson.annotation.JsonGetter

class ShortenUrlConfigResponse(
    @get:JsonGetter("target_url")
    val url: String,
    @get:JsonGetter("short_url")
    val shortUrl: String,
    @get:JsonGetter("created_at")
    val createdAt: Long,
)