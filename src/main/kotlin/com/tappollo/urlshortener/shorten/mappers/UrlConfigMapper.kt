package com.tappollo.urlshortener.shorten.mappers

import com.tappollo.urlshortener.shorten.api.ShortenUrlConfigResponse
import com.tappollo.urlshortener.shorten.entity.UrlConfig

fun UrlConfig.toResponse(): ShortenUrlConfigResponse = ShortenUrlConfigResponse(
    targetUrl,
    shortenUrl,
    createdAt
)