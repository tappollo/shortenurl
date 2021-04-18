package com.tappollo.urlshortener.shorten

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import com.tappollo.urlshortener.api.ShortenUrlRequest
import com.tappollo.urlshortener.api.ShortenUrlResponse
import com.tappollo.urlshortener.shorten.entity.UrlConfig
import com.tappollo.urlshortener.shorten.mappers.toResponse
import com.tappollo.urlshortener.shorten.service.ShortenService
import com.tappollo.urlshortener.utils.exception.IncorrectUrlException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(ShortenController::class)
class WebShortenControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: ShortenService

    @Test
    fun `Get all method should return correct values`() {
        val config = UrlConfig("http://localhost/a", "https://google.com", 1L)
        whenever(service.getAllUrls()).doReturn(
            listOf(config),
        )
        val mapper = ObjectMapper()

        val json = mapper.writeValueAsString(listOf(config.toResponse()))

        mockMvc.perform(get("/shorten/all")).andDo(print()).andExpect(status().isOk)
            .andExpect(
                content().json(json)
            )
    }

    @Test
    fun `Shorten url method should return shorten url`() {
        val shortUrl = "http://localhost/a"
        whenever(service.shortenUrl("https://google.com")).doReturn(shortUrl)

        val mapper = ObjectMapper()

        val response = mapper.writeValueAsString(ShortenUrlResponse(shortUrl))
        val request = mapper.writeValueAsString(ShortenUrlRequest("https://google.com"))

        mockMvc.perform(post("/shorten").contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
            .andExpect(status().isOk)
            .andExpect(
                content().json(response)
            )
    }


    @Test
    fun `Shorten incorrect url method should return shorten url`() {
        whenever(service.shortenUrl("asdfsdfasdf")).thenThrow(IncorrectUrlException())

        val mapper = ObjectMapper()

        val request = mapper.writeValueAsString(ShortenUrlRequest("asdfsdfasdf"))

        mockMvc.perform(post("/shorten").contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Get exist shorten url method should return shorten url`() {
        val url = "https://google.com"

        whenever(service.getUrl("a")).doReturn(url)

        mockMvc.perform(get("/a")).andDo(print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl(url))
    }

    @Test
    fun `Get not exist shorten url method should return bad request`() {
        whenever(service.getUrl("a")).doThrow(NullPointerException())

        mockMvc.perform(get("/a")).andDo(print())
            .andExpect(status().isNotFound)
    }
}