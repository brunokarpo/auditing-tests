package nom.brunokarpo.anotherwebproject.model

import java.util.*

data class Book(var name: String? = null,
                var author: String? = null,
                var uuid: UUID? = null) {

    class Builder {

        private var book: Book = Book()

        fun name(name: String): Builder {
            book.name = name
            return this
        }

        fun author(author: String): Builder {
            book.author = author
            return this
        }

        fun uuid(uuid: UUID): Builder {
            book.uuid = uuid
            return this
        }

        fun build(): Book {
            return book
        }

    }
}