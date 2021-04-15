package com.tappollo.urlshortener.utils

import java.lang.StringBuilder

private const val CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

object UrlShortener {

    fun encode(id: Int): String {
        if (id <= 0) throw IllegalArgumentException("Id cannot zero or lower")

        if (id <= CHARS.length) {
            return CHARS[id - 1].toString()
        }

        val sb = StringBuilder()

        var current = id
        while (current > 0) {
            sb.append(CHARS[--current % CHARS.length])
            current /= CHARS.length
        }

        return sb.reverse().toString()
    }

    fun decode(str: String): Int {
        var num = 0
        for (element in str) {
            num = num * CHARS.length + CHARS.indexOf(element + 1)
        }
        return num
    }
}