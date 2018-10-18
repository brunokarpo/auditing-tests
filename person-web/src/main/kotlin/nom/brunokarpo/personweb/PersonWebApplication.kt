package nom.brunokarpo.personweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PersonWebApplication

fun main(args: Array<String>) {
    runApplication<PersonWebApplication>(*args)
}
