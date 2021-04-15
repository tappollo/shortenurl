package com.tappollo.urlshortener.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

//CHARS - abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
//Length - 52
class UrlShortenerTest {

    @Test
    //1
    fun `a should decoded to 1`() {
        assertEquals(UrlShortener.decode("a"), 1)
    }

    @Test
    //52 + 1
    fun `aa should decoded to 53`() {
        assertEquals(UrlShortener.decode("aa"), 53)
    }

    @Test
    //52 + 2
    fun `ab should decoded to 54`() {
        assertEquals(UrlShortener.decode("ab"), 54)
    }


    @Test
    //52 * 52 * 52 + 52 + 1 formula for aaa
    fun `aaa should decoded to 2757`() {
        assertEquals(UrlShortener.decode("aaa"), 2757)
    }

    @Test
    //52 * 52 * 52 + 52 + 27 formula for aaA
    fun `aaA should decoded to 2783`() {
        assertEquals(UrlShortener.decode("aaA"), 2783)
    }

    @Test
    //a
    fun `1 should encoded to a`() {
        assertEquals(UrlShortener.encode(1), "a")
    }

    @Test
    //aa = 52 + 1
    fun `53 should encoded to aa`() {
        assertEquals(UrlShortener.encode(53), "aa")
    }

    @Test
    //ab = 52 + 2
    fun `54 should encoded to ab`() {
        assertEquals(UrlShortener.encode( 54),"ab")
    }


    @Test
    //52 * 52 * 52 + 52 + 1 formula for aaa
    fun `2757 should encoded to aaa`() {
        assertEquals(UrlShortener.encode(2757), "aaa")
    }

    @Test
    //52 * 52 * 52 + 52 + 27 formula for aaA
    fun `2783 should encoded to aaA`() {
        assertEquals(UrlShortener.encode(2783), "aaA")
    }
}