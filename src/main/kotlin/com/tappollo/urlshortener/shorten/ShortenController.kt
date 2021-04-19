package com.tappollo.urlshortener.shorten

import com.tappollo.urlshortener.shorten.api.ShortenUrlConfigResponse
import com.tappollo.urlshortener.shorten.api.ShortenUrlRequest
import com.tappollo.urlshortener.shorten.api.ShortenUrlResponse
import com.tappollo.urlshortener.shorten.mappers.toResponse
import com.tappollo.urlshortener.shorten.service.ShortenService
import com.tappollo.urlshortener.utils.exception.IncorrectUrlException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletResponse

@RestController
class ShortenController(
    @Autowired
    private val service: ShortenService
) {

    @PostMapping("/api/shorten")
    fun shortenUrl(@RequestBody body: ShortenUrlRequest): ResponseEntity<ShortenUrlResponse> {
        val shortenUrl = service.shortenUrl(body.url)
        return ResponseEntity(ShortenUrlResponse(shortenUrl), HttpStatus.OK)
    }

    @GetMapping("/api/shorten/all")
    fun getAllUrls(): ResponseEntity<List<ShortenUrlConfigResponse>> {
        return ResponseEntity(service.getAllUrls().map { it.toResponse() }, HttpStatus.OK)
    }

    @GetMapping("/api/{id}")
    fun getUrl(@PathVariable id: String, response: HttpServletResponse): RedirectView {
        val url = service.getUrl(id)
        return RedirectView(url)
    }

    @ExceptionHandler(NullPointerException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException() {
    }

    @ExceptionHandler(IncorrectUrlException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUriSyntaxException() {
    }

}

