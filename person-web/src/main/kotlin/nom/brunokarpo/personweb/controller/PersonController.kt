package nom.brunokarpo.personweb.controller

import nom.brunokarpo.personweb.model.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var request: HttpServletRequest

    private var persons: MutableList<Person> = mutableListOf(
            Person.Builder().name("Dayane").age(32).uuid(UUID.fromString("30afc0da-6654-4d2a-a453-79ee009aab0d")).build(),
            Person.Builder().name("Eliana").age(52).uuid(UUID.fromString("8e432579-e507-4cf6-a923-c35550a3ef04")).build(),
            Person.Builder().name("Aldemiro").age(53).uuid(UUID.fromString("8e432579-e507-4cf6-a923-c35550a3ef04")).build(),
            Person.Builder().name("Ademir").age(27).uuid(UUID.fromString("e00af2ca-2972-4e9e-bb8c-2e4029a67667")).build()
    )

    @PostMapping
    fun save(@RequestBody person: Person): ResponseEntity<Person> {
        var uuid = UUID.randomUUID()
        person.uuid = uuid
        persons.add(person)
        return ResponseEntity.created(URI.create("${request.requestURI}/$uuid")).body(person)
    }

    @PutMapping("/{uuid}")
    fun update(@RequestBody person: Person, @PathVariable("uuid") uuid: UUID): ResponseEntity<Person> {
        var optional = persons.stream().filter { it.uuid?.equals(uuid)!! }.findFirst()
        if (!optional.isPresent) {
            return ResponseEntity.notFound().build()
        }
        var p = optional.get()
        p.name = person.name
        p.age = person.age

        return ResponseEntity.ok(p)
    }


}