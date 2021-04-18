package com.tappollo.urlshortener.utils.ext

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.toMillis(): Long = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()