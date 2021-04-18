package com.tappollo.urlshortener.shorten.dao

import javax.persistence.*

@Entity(name = "url_config")
data class UrlConfigEntity(
    @Column(name = "target_url")
    val targetUrl: String,
    @Column(name = "created_at")
    val createdAt: Long,
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null
}