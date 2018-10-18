package nom.brunokarpo.anotherwebproject.controller

import nom.brunokarpo.anotherwebproject.model.Book
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/book")
class BookController {

    @PostMapping
    fun save(@RequestBody book: Book, request: HttpServletRequest): ResponseEntity<Book> {
        var uuid = UUID.randomUUID()
        book.uuid = uuid
        return ResponseEntity.created(URI.create("${request.requestURI}/$uuid")).body(book)
    }
}