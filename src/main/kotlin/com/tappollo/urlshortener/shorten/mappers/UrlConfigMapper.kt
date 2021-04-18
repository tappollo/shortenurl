package com.tappollo.urlshortener.shorten.mappers

import com.tappollo.urlshortener.api.ShortenUrlConfigResponse
import com.tappollo.urlshortener.shorten.entity.UrlConfig
import com.tappollo.urlshortener.utils.ext.toMillis

fun UrlConfig.toResponse(): ShortenUrlConfigResponse = ShortenUrlConfigResponse(
    targetUrl,
    shortenUrl,
    createdAt
)