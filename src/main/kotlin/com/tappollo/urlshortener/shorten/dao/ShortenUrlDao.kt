package com.tappollo.urlshortener.shorten.dao

import org.springframework.data.repository.CrudRepository

interface ShortenUrlDao : CrudRepository<ShortenUrlEntity, Long>