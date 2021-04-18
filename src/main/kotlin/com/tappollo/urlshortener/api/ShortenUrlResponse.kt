package com.tappollo.urlshortener.api

import com.fasterxml.jackson.annotation.JsonGetter

class ShortenUrlResponse(
    @get:JsonGetter("url")
    val url: String
)
