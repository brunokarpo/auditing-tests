package nom.brunokarpo.personweb.model

import java.util.*

data class Person(
        var name: String? = null,
        var age: Int? = null,
        var uuid: UUID? = null
) {
    class Builder {

        private var person = Person()

        fun name(name: String): Builder{
            person.name = name
            return this
        }

        fun age(age: Int): Builder {
            person.age = age
            return this
        }

        fun uuid(uuid: UUID): Builder {
            person.uuid = uuid
            return this
        }

        fun build(): Person {
            return person
        }
    }
}