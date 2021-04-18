package com.tappollo.urlshortener.api

import com.fasterxml.jackson.annotation.JsonGetter

class ShortenUrlRequest(
    @get:JsonGetter("url")
    val url: String
)
