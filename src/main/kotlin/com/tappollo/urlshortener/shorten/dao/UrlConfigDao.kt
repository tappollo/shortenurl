package com.tappollo.urlshortener.shorten.dao

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UrlConfigDao : CrudRepository<UrlConfigEntity, Int> {

    @Query("from url_config where targetUrl=:targetUrl")
    fun findByUrl(@Param("targetUrl") targetUrl: String): UrlConfigEntity?
}