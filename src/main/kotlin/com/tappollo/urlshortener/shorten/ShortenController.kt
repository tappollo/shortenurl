package com.tappollo.urlshortener.shorten

import com.tappollo.urlshortener.api.ShortenUrlConfigResponse
import com.tappollo.urlshortener.api.ShortenUrlRequest
import com.tappollo.urlshortener.api.ShortenUrlResponse
import com.tappollo.urlshortener.shorten.mappers.toResponse
import com.tappollo.urlshortener.shorten.service.ShortenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URISyntaxException
import javax.servlet.http.HttpServletResponse

@RestController
class ShortenController(
    @Autowired
    private val service: ShortenService
) {

    @PostMapping("shorten")
    fun shortenUrl(@RequestBody body: ShortenUrlRequest): ResponseEntity<ShortenUrlResponse> {
        return try {
            val shortenUrl = service.shortenUrl(body.url)
            ResponseEntity(ShortenUrlResponse(shortenUrl), HttpStatus.OK)
        } catch (e: Exception) {
            if (e is URISyntaxException) {
                ResponseEntity(HttpStatus.BAD_REQUEST)
            } else {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }

    @GetMapping("shorten/all")
    fun getAllUrls(): ResponseEntity<List<ShortenUrlConfigResponse>> {
        return ResponseEntity(service.getAllUrls().map { it.toResponse() }, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun getUrl(@PathVariable id: String, response: HttpServletResponse): RedirectView {
        val url = service.getUrl(id)
        return RedirectView(url)
    }

    @ExceptionHandler(NullPointerException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException() {
    }

    @ExceptionHandler(URISyntaxException::class, MalformedURLException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUriSyntaxException() {
    }

}

