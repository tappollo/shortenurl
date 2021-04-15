package com.tappollo.urlshortener.shorten.dao

import javax.persistence.*

@Entity
class ShortenUrlEntity(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    val targetUrl: String,
    val shortenUrl: String,
    val createdAt: Long,
)